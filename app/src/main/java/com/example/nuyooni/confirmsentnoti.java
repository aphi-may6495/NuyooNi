package com.example.nuyooni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class confirmsentnoti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmsentnoti);
        ImageButton correct = (ImageButton)findViewById(R.id.imageButton9);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(confirmsentnoti.this , successsentnoti.class);
                startActivity(i);



            }

        });
        ImageButton dis = (ImageButton)findViewById(R.id.imageButton10);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(confirmsentnoti.this , addprofile2.class);
                startActivity(i);



            }

        });
    }


}
