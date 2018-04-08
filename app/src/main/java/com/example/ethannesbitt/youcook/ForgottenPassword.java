package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgottenPassword extends AppCompatActivity implements View.OnClickListener
{

    //return to login screen variable
    private TextView returnToLogin;
    private Button sendButton;
    private EditText forgotInput;

    //Firebase variables
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        //initialising return to login text and its onclick
        returnToLogin = findViewById(R.id.return_to_login);
        returnToLogin.setOnClickListener(this);

        //initialising button and edit text for forgotten password
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
        forgotInput = findViewById(R.id.forgot_input);

        //initialising firebase
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view)
    {
        if(view == returnToLogin)
        {
            startActivity(new Intent(ForgottenPassword.this, Login.class));
        }
        else if(view == sendButton)
        {
            sendResetEmail(forgotInput.getText().toString());
        }
    }

    private void sendResetEmail(String emailAddress)
    {
        if(!emailAddress.isEmpty())
        {
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgottenPassword.this, "Email Successfully Sent!", Toast.LENGTH_SHORT).show();
                                forgotInput.setText("");
                            } else {
                                Toast.makeText(ForgottenPassword.this, "Error sending reset email, check the email is valid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(ForgottenPassword.this, "Enter a Valid Email Address!", Toast.LENGTH_SHORT).show();
        }
    }
}
