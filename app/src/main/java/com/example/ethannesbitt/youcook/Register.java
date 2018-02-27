package com.example.ethannesbitt.youcook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener
{
    private EditText emailInput, passwordInput;
    private Button registerButton;
    private TextView signIn;
    private ProgressDialog pDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        pDialog = new ProgressDialog(this);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

    }

    private void registerUserAccount()
    {
        String userEmail = emailInput.getText().toString().trim();
        String userPassword = passwordInput.getText().toString().trim();

        //error handling if email or password text boxes are empty
        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(Register.this, "You have not entered an email!", Toast.LENGTH_SHORT).show();
            //stopping the method from executing
            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(Register.this, "You have not entered an password!", Toast.LENGTH_SHORT).show();
            //stopping the method from executing
            return;
        }

        //registering user if email and password are OK
        pDialog.setMessage("Authenticating Account...");
        pDialog.show();

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "Registration Complete!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view)
    {
        if(view == registerButton)
        {
            //run the method to register the user account with Firebase Database
            registerUserAccount();
        }

        if(view == signIn)
        {
            //take you to the login screen
            startActivity(new Intent(Register.this, Login.class));
        }
    }
}
