package com.example.ethannesbitt.youcook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login extends AppCompatActivity
{
    //google login
    private SignInButton googleSignInButton;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog pDialog;


    //Email and Password Login
    private Button signInButton;
    private EditText emailInput;
    private EditText passwordInput;

    //Registration activity link
    private TextView registration;


    private static final int RC_SIGN_IN = 2;

    @Override
    protected void onStart()
    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //google sign in set up
        googleSignInButton = (SignInButton) findViewById(R.id.googleSignInButton);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() != null)
                {
                    pDialog.setMessage("Authenticating Account...");
                    pDialog.show();
                    finish();
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(Login.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        pDialog = new ProgressDialog(this);

        googleSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        //email and password sign in setup
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signIn();
            }
        });

        //registration text
        registration = (TextView) findViewById(R.id.alreadyRegistered);
        registration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void googleSignIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else
            {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(Login.this, "Authentication went wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else
                            {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn()
    {
        String userEmail = emailInput.getText().toString().trim();
        String userPassword = passwordInput.getText().toString().trim();

        if(TextUtils.isEmpty(userEmail))
        {
            Toast.makeText(Login.this, "You have not entered an email!", Toast.LENGTH_SHORT).show();
            //stopping the method from executing
            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            Toast.makeText(Login.this, "You have not entered an password!", Toast.LENGTH_SHORT).show();
            //stopping the method from executing
            return;
        }

        pDialog.setMessage("Authenticating User..");
        pDialog.show();

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        FirebaseUser user = mAuth.getCurrentUser();
                        pDialog.dismiss();
                    }
                });

    }
}
