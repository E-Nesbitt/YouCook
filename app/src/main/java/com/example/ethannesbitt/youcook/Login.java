package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{
    private SignInButton googleSignInButton;
    private Button signInButton;
    private EditText emailInput;
    private EditText passwordInput;
    private GoogleApiClient theGoogleApiClient;

    private static final int SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //google sign in set up
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();

        theGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        googleSignInButton = (SignInButton) findViewById(R.id.googleSignInButton);
        googleSignInButton.setOnClickListener(this);

        //email and password sign in setup
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.signInButton);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.googleSignInButton:
                googleSignIn();
                break;
            case R.id.signInButton:
                signIn();
                break;
        }

    }

    private void googleSignIn()
    {
        Intent googleSignInIntent = Auth.GoogleSignInApi.getSignInIntent(theGoogleApiClient);
        startActivityForResult(googleSignInIntent, SIGN_IN);
    }

    private void signIn()
    {

    }

    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processGoogleSignIn (result);
        }
    }

    private void processGoogleSignIn (GoogleSignInResult result)
    {
        Intent home;

        if (result.isSuccess())
        {
            GoogleSignInAccount userAccount = result.getSignInAccount();
            userAccount.getDisplayName();
            home = new Intent(this, Login.class);
            startActivity(home);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.d("Connection Failed", "Error with Google API:" + connectionResult);
    }
}
