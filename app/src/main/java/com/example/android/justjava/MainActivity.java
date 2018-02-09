/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        CheckBox hasWhippedCream = findViewById(R.id.whipped_cream_checkbox);
        boolean whippedCreamState = hasWhippedCream.isChecked();

        CheckBox hasChocolate = findViewById(R.id.chocolate_checkbox);
        boolean chocolateState = hasChocolate.isChecked();

        int price = calculatePrice(whippedCreamState, chocolateState);

        String message = createOrderSummary(name, NumberFormat.getCurrencyInstance().format(price), whippedCreamState, chocolateState);
//        displayMessage(message);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee ordered by " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            showToastMessage(getString(R.string.to_much_coffees));
            return;
        }

        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            showToastMessage(getString(R.string.to_less_coffees));
            return;
        }

        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean whippedCreamState, boolean hasChocolate) {
        int totalCoffeePrice = 5;

        if(whippedCreamState) {
            totalCoffeePrice++;
        }

        if(hasChocolate) {
            totalCoffeePrice += 2;
        }

        return quantity * totalCoffeePrice;
    }

    /**
     * Creates order summary.
     *
     * @param name            is name of customer
     * @param total           is price for whole order
     * @param hasWhippedCream is boolean if add whipped cream
     * @param hasChocolate    is boolean if add chocolate
     */
    private String createOrderSummary(String name, String total, boolean hasWhippedCream, boolean hasChocolate) {
//        String orderMessage = "Name: " + name + "\n" +
//                "Add whipped cream: " + hasWhippedCream + "\n" +
//                "Add chocolate: " + hasChocolate + "\n" +
//                "Order quantity: " + quantity + "\n" +
//                "Total: " + total + "\n" +
//                "Thank you!";

        String orderMessage = getString(R.string.order_summary, name, hasWhippedCream, hasChocolate, quantity, total);

        return orderMessage;
    }

    private void showToastMessage(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}