package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    private TextView signup, forgot_pass, mlogin;
    private EditText editText_1, editText_2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mlogin = (TextView) findViewById(R.id.tv_1);
        signup = (TextView) findViewById(R.id.tv_4);
        editText_1 = (EditText) findViewById(R.id.edit_text_1);
        editText_2 = (EditText) findViewById(R.id.edit_text_2);
        mAuth = FirebaseAuth.getInstance();
        forgot_pass = (TextView) findViewById(R.id.tv_2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, signup.class);
                startActivity(intent);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Toast.makeText(login.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(Login_Activity.this, HomeActivity.class);
                    startActivity(I);
                }
            }
        };

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = editText_1.getText().toString();
                String userPaswd = editText_2.getText().toString();
                if (userEmail.isEmpty()) {
                    editText_1.setError("Provide your Email first!");
                    editText_1.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    editText_2.setError("Enter Password!");
                    editText_2.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(Login_Activity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Login_Activity.this, "Check Your email or password", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(Login_Activity.this, HomeActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        Login_Activity.super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed(){
        //finish();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
