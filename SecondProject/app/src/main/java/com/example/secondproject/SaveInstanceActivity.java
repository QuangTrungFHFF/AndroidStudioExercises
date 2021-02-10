package com.example.secondproject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SaveInstanceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFirstNum, etSecondNum;
    private TextView tvResult;
    private Button calButton;

    private int firstNum, secondNum, result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstNum = (EditText)findViewById(R.id.firstNum);
        etSecondNum = (EditText)findViewById(R.id.secondNum);
        tvResult = (TextView)findViewById(R.id.result);
        calButton = (Button)findViewById(R.id.resultButton);

        calButton.setOnClickListener(this);

        if(savedInstanceState != null)
        {
            etFirstNum.setText(String.valueOf(savedInstanceState.getInt("SO_THU_NHAT")));
            etSecondNum.setText(String.valueOf(savedInstanceState.getInt("SO_THU_HAI")));
            tvResult.setText(String.valueOf(savedInstanceState.getInt("KET_QUA")));
        }
    }

    @Override
    public void onClick(View v) {
        if(v == calButton)
        {
            if(etFirstNum.getText().toString().isEmpty() || etSecondNum.getText().toString().isEmpty())
            {
                Toast.makeText(this,"Vui long nhap hai so vao cho trong!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                firstNum = Integer.parseInt(etFirstNum.getText().toString());
                secondNum = Integer.parseInt(etSecondNum.getText().toString());

                result = firstNum + secondNum;

                tvResult.setText(String.valueOf(result));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!tvResult.getText().toString().isEmpty()) {
            outState.putInt("SO_THU_NHAT", Integer.parseInt(etFirstNum.getText().toString()));
            outState.putInt("SO_THU_HAI", Integer.parseInt(etSecondNum.getText().toString()));
            outState.putInt("KET_QUA", Integer.parseInt(tvResult.getText().toString()));
        }
        super.onSaveInstanceState(outState);
    }
}
