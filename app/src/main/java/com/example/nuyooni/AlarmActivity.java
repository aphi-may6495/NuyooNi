package com.example.nuyooni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.nuyooni.Service.AlarmService;

public class AlarmActivity extends AppCompatActivity {

    Intent intent;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        serviceIntent = new Intent(this , AlarmService.class);
        intent = new Intent(this , MainActivity.class);

        ImageButton imageButton = findViewById(R.id.btnWNSTP);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}