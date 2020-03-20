package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private EditText ed1,ed2,ed3,ed4,ed5,ed6;
    private FirebaseAuth mAuth;
    private Button btn1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);
        ed5=(EditText)findViewById(R.id.ed5);
        ed6=(EditText)findViewById(R.id.ed6);

        btn1=(Button)findViewById(R.id.btn1);

        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }

        });

    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = ed1.getText().toString().trim();
        String password  = ed5.getText().toString().trim();
        String confirmpassword  = ed6.getText().toString().trim();
        String Name  = ed4.getText().toString().trim();
        String contact  = ed2.getText().toString().trim();
        String instaorfb  = ed3.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Name)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirmpassword)){
            Toast.makeText(this,"Please enter same password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.show();


        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(signup.this,"Successfully registered \n Please login to Continue",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(signup.this, Login_Activity.class);
                            startActivity(intent);

                        }else{
                            //display some message here
                            Toast.makeText(signup.this,"Registration Error", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(signup.this, Login_Activity.class);
                            startActivity(intent);
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onBackPressed(){
        Intent intent=new Intent(signup.this , Login_Activity.class);
        startActivity(intent);
        finish();
    }
}
