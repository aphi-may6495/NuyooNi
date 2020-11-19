package com.example.nuyooni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        new CountDownTimer(3000, 1000){
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                finish();
                checkLogin();
            }
        }.start();
    } // end onCreate

    private void checkLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user != null) {
            intent = new Intent(this, MainActivity.class);
        }else {
            intent = new Intent(this, Login.class);
        }
        startActivity(intent);

    } //end method

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}