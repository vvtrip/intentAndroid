package com.example.android.mailintent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mailintent.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity;
    int cost=5;
    CheckBox cream_check;
    CheckBox chocolate_check;
    EditText nm;




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){


       cream_check=(CheckBox)findViewById(R.id.whipped_cream_check);
        boolean cream_checked=cream_check.isChecked();

        chocolate_check=(CheckBox)findViewById(R.id.chocolate_check);
        boolean chocolate_checked = chocolate_check.isChecked();

        int price= calculatePrice(cost,cream_checked,chocolate_checked);

        nm=(EditText) findViewById(R.id.name);
        String customer = nm.getText().toString();

        String priceMessage =createOrderSummary(price,cream_checked,chocolate_checked,customer);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"My Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
        }



        }



    /**
     * This method procesess quatity
     */
    private int calculatePrice(int cost,boolean cream_checked , boolean chocolate_checked) {

        if(cream_checked && chocolate_checked){
            cost =cost + 3 + 2;
        }else if(cream_checked){
            cost = cost + 3;
        }else if(chocolate_checked){
            cost = cost +2;
        }else{
            cost=cost;
        }

        int total = quantity * cost ;
        return total;
    }




    /**
     * THis method creates orcder summary
     */

    private String createOrderSummary(int price ,boolean cream_checked,boolean chocolate_checked,String customer){
    String summary = customer;
    summary = summary + "\nAdd Whipped Cream? (3$ per cup)  " + cream_checked;
    summary = summary + "\nAdd Chocolate?            (2$ per cup)  " + chocolate_checked;
        summary = summary + "\nOrdered: " + quantity +" cup(s) of Coffee (5$ per cup)";
    summary = summary + "\nTotal: $" + price;
    summary = summary + "\nThank you !";
    return summary;}






    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You can not have more than 100 cups of Coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity<=1){
            Toast.makeText(this,"You can not have less than 1 cup of Coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}