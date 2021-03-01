package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView tvQuantity, tvPrice,btnIncrement, btnDecrement;
    EditText eName;
    boolean whippedCream = false;
    boolean chocolate = false;
    int quantity = 0;
    int basePrice = 5;
    String customerName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrement = (Button)findViewById(R.id.increment_button);
        btnDecrement = (Button)findViewById(R.id.decrement_button);
        eName =(EditText)findViewById(R.id.name_edit_text);

        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity+=1;
                display(quantity);
                displayPrice(calculatePrice(basePrice),false);
            }
        });

        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>0)
                {
                    quantity-=1;
                }
                else
                {
                    quantity =0;
                }
                display(quantity);
                displayPrice(calculatePrice(basePrice),false);
            }
        });
        }




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        eName = (EditText)findViewById(R.id.name_edit_text);
        if(isNameEmpty(eName)||quantity ==0)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Please input Name and quantity!",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            getCustomerName(eName);
            displayPrice(calculatePrice(basePrice),true);
            Toast toast = Toast.makeText(getApplicationContext(),"Order success!",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_textview);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number, boolean isOrdered)
    {
        if(!isOrdered)
        {
            tvPrice = (TextView)findViewById(R.id.price_textview);
            tvPrice.setText("Total: " + NumberFormat.getCurrencyInstance().format(number));
        }
        else
        {
            tvPrice = (TextView)findViewById(R.id.price_textview);//
            String order = createOrderSummary(number);
            tvPrice.setText(order);
        }

    }

    private int calculatePrice(int pricePerCup)
    {
        return quantity * pricePerCup;
    }

    private String createOrderSummary(int price)
    {

        String name = "Name: " + customerName;
        String quantityTotal = "Quantity: " + quantity;
        String topping = "Topping: " + getToppingInfo();
        String total = "Total: " + NumberFormat.getCurrencyInstance().format(price);
        String thanks = "Thank you!";
        String result = name + "\n" + quantityTotal + "\n"+topping + "\n" + total + "\r\n" + thanks;
        return result;
    }


    public void hasToppingCheck(View view) {
        boolean isChecked = ((CheckBox)view).isChecked();
        switch(view.getId())
        {
            case R.id.topping_checkbox_cream:
                if (isChecked)
                {
                    whippedCream = true;
                    basePrice+=2;
                }
                else
                {
                    whippedCream =false;
                    basePrice-=2;
                }
                break;
            case R.id.topping_checkbox_chocolate:
                if (isChecked)
                {
                    chocolate = true;
                    basePrice+=3;
                }
                else
                {
                    chocolate =false;
                    basePrice-=3;
                }
                break;
        }


        display(quantity);
        displayPrice(calculatePrice(basePrice),false);
    }

    private String getToppingInfo()
    {
        String info;
        if(whippedCream&&chocolate)
        {
            info = "Whipped Cream and Chocolate.";
        }
        else if(whippedCream && !chocolate)
        {
            info = "Whipped Cream.";
        }
        else if(!whippedCream && chocolate)
        {
            info = "Chocolate.";
        }
        else
        {
            info = "None";
        }

        return info;
    }

    private boolean isNameEmpty(EditText editText)
    {
        return editText.getText().toString().trim().length() == 0;
    }

    private void getCustomerName(EditText editText)
    {
        customerName = editText.getText().toString().trim();
    }
}