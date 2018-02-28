package com.example.ethannesbitt.youcook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DisableAccount extends AppCompatActivity
{
    //firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    //delete button variable
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable_account);

        //initialise firebase and the user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //initialising delete button
        deleteButton = findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are you sure you want to delete your account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deleteAccount();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    public void deleteAccount()
    {
        if(user != null)
        {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(DisableAccount.this, "Account has been deleted!",Toast.LENGTH_LONG).show();
                                finish();
                                Intent login = new Intent(DisableAccount.this, Login.class);
                                startActivity(login);
                            }
                            else
                            {
                                Toast.makeText(DisableAccount.this, "Something went wrong, account could not be deleted!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
