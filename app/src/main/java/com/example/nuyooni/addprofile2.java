package com.example.nuyooni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class addprofile2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprofile2);


    }
    public void showDialogFragment(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();

        dialogflagment dialogFragment = new dialogflagment();
        dialogFragment.show(fragmentManager,"Dialog");
        ImageButton add2 = (ImageButton)findViewById(R.id.add2);
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addprofile2.this , addprofile.class);
                startActivity(i);



            }

        });

    }



}
