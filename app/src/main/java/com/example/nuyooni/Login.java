package com.example.nuyooni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText etUser;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.editTextTextEmailAddress);
        etPass = findViewById(R.id.editTextTextPassword);

        Button login = (Button)findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etUser.getText().toString())){
                    Toast.makeText(getBaseContext(),"ช่องอีเมล์จำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(etUser.getText().toString()).matches()){
                    Toast.makeText(getBaseContext(),"รูปแบบของอีเมลไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etPass.getText().toString())){
                    Toast.makeText(getBaseContext(),"ช่องรหัสจำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
                }else{
                    loginFunc();
                }
            }

        });

        Button register = (Button)findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this , Register.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void goToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    } //end goToLogin method
    private void loginFunc(){
        Toast.makeText(getBaseContext(),"กำลังเข้าสู่ระบบ",Toast.LENGTH_SHORT).show();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        etUser = findViewById(R.id.editTextTextEmailAddress);
        etPass = findViewById(R.id.editTextTextPassword);

        mAuth.signInWithEmailAndPassword(etUser.getText().toString(),etPass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"เข้าสู่ระบบสำเร็จ",Toast.LENGTH_SHORT).show();
                            goToLogin();
                        }else{
                            Toast.makeText(getBaseContext(),"ผิดพลาด! กรุณาเช็คที่อยู่อีเมลและรหัสผ่าน",Toast.LENGTH_LONG).show();
                        }
                    }
                }); //end sign in func
    } //end loginFunc method

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    } //end onBackPressed method
}
