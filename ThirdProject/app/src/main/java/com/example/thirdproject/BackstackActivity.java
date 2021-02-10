package com.example.thirdproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BackstackActivity extends AppCompatActivity {

    private Button btnReplaceFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstack);

        btnReplaceFragment =(Button)findViewById(R.id.btn_replace_fragment);
        btnReplaceFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                addFragment(new Fragment2());

                replaceFragmentContent(new Fragment2());
                Log.e("Replace Fragment", "2");
            }
        });

        initFragment();

    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body,fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    private void replaceFragmentContent(Fragment fragment) {
        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body,fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentTransaction.commit();
        }

    }

    private void initFragment() {
        Fragment1 firstFragment = new Fragment1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body,firstFragment);
        fragmentTransaction.commit();
    }
}