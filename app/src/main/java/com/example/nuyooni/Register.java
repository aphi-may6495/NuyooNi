package com.example.nuyooni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;

    private EditText etEmail;
    private EditText etPass;
    private EditText etF_name;
    private EditText etS_name;
    private EditText etPh_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.editTextTextEmailAddress2);
        etPass = findViewById(R.id.editTextTextPassword2);
        etF_name = findViewById(R.id.editTextTextPersonName);
        etS_name = findViewById(R.id.editTextTextPersonName2);
        etPh_num = findViewById(R.id.editTextPhone);

        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataEmpty(
                        etEmail.getText().toString(),
                        etPass.getText().toString(),
                        etPh_num.getText().toString(),
                        etF_name.getText().toString(),
                        etS_name.getText().toString())){
                    regFunc(etEmail.getText().toString(),etPass.getText().toString());
                    Log.d("Register Data Check: ","All Data is Valid");
                } //end if statement
            }

        });

        Button back = (Button)findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regNext(false);
            }

        });
    }

    private void regFunc(String email, String pass){
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Log.d("DebugLog", "Uid form refFunc => "+ uid);
                            addData(uid);
                        } else {
                            Log.d("DebugLog", "Register Fail");
                            Toast.makeText(getBaseContext(), "สมัครสมาชิกไม่สำเร็จ โปรดตรวจสอบข้อมูลอีกครั้ง",Toast.LENGTH_LONG).show();
                        } //end if else statement
                    }
                });//end sign up func
    } //end regFunc method

    private void addData(String uid){
        db = FirebaseFirestore.getInstance();

        final String email = etEmail.getText().toString();
        final String fname = etF_name.getText().toString();
        final String sname = etS_name.getText().toString();
        final String tel = etPh_num.getText().toString();

        Map<String, Object> user_m = new HashMap<>(); //create data object
        user_m.put("email",email);
        user_m.put("f_name",fname);
        user_m.put("s_name",sname);
        user_m.put("tel",tel);

        db.collection("user_data").document(uid).set(user_m)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DebugLog", "Text form adding => " + email);
                        Log.d("DebugLog", "Text form adding => " + fname);
                        Log.d("DebugLog", "Text form adding => " + sname);
                        Log.d("DebugLog", "Text form adding => " + tel);
                        //Toast.makeText(getBaseContext(),"สมัครสมาชิกเสร็จสิ้น",Toast.LENGTH_SHORT).show();
                        regNext(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DebugLog", "Error writing document", e);

                        Toast.makeText(getBaseContext(),"เพิ่มข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                    }
                });
    } //end addData method

    public boolean checkDataEmpty(String email, String pass, String tel, String fname, String sname){
        if(email.isEmpty()){
            Toast.makeText(getBaseContext(), "ช่องอีเมลจำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getApplicationContext(),"รูปแบบของที่อยู่อีเมลไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Log.d("Register Data Check: ","Email Data is Valid");
            }
        }
        if(pass.isEmpty()){
            Toast.makeText(getBaseContext(), "ช่องรหัสผ่านจำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
            return false;
        } else if(tel.isEmpty()){
            Toast.makeText(getBaseContext(), "ช่องเบอร์โทรศัพท์จำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
            return false;
        } else if(fname.isEmpty()){
            Toast.makeText(getBaseContext(), "ช่องนามสกุลจำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
            return false;
        } else if(sname.isEmpty()){
            Toast.makeText(getBaseContext(), "ช่องชื่อจริงจำเป็นต้องใส่ข้อมูล",Toast.LENGTH_SHORT).show();
            return false;
        } //end if else statement
        return true;
    } //end checkDataEmpty method

    public void regNext(boolean b){
        Intent intent;
        if(b){
            intent = new Intent(this, RegisterSuccess.class);
        } else{
            intent = new Intent(this, Login.class);
        }
        startActivity(intent);
        finish();
    } //end regNext method

}