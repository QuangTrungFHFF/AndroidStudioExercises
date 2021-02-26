package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView tvQuantity, tvPrice,btnIncrement, btnDecrement;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrement = (Button)findViewById(R.id.increment_button);
        btnDecrement = (Button)findViewById(R.id.decrement_button);

        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity+=1;
                display(quantity);
                displayPrice(quantity*5,false);
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
                displayPrice(quantity*5,false);
            }
        });
        }




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //display(quantity);
        displayPrice(quantity*5,true);
        Toast toast = Toast.makeText(getApplicationContext(),"Order success!",Toast.LENGTH_SHORT);
        toast.show();
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
            tvPrice = (TextView)findViewById(R.id.price_textview);
            String total = "Total: " + NumberFormat.getCurrencyInstance().format(number);
            String thanks = "Thank you!";
            tvPrice.setText(total + "\r\n" + thanks);
        }

    }
}