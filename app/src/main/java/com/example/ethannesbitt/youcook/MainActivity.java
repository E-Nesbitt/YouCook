package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{

    private CardView searchOption;
    private CardView shoppingListOption;
    private CardView recipeOption;
    private CardView timerOption;
    private CardView converterOption;
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link menu options to card views, defining each of the options
        searchOption = (CardView) findViewById(R.id.searchmenuoption);
        shoppingListOption = (CardView) findViewById(R.id.shoppinglistmenuoption);
        recipeOption = (CardView) findViewById(R.id.recipesmenuoption);
        timerOption = (CardView) findViewById(R.id.timermenuoption);
        converterOption = (CardView) findViewById(R.id.convertermenuoption);

        //Make options clickable
        searchOption.setOnClickListener(this);
        shoppingListOption.setOnClickListener(this);
        recipeOption.setOnClickListener(this);
        timerOption.setOnClickListener(this);
        converterOption.setOnClickListener(this);

        //Drawer Navigation Menu set up
        drawerMenu = (DrawerLayout) findViewById(R.id.mainactivity);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.mainmenudnav);
        navigationView.setNavigationItemSelectedListener(this);

        //setting mAuth Variable to current instance
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    finish();
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
            }
        };


        String username = user.getEmail().toString();
        Log.d("Username=", username);
        int index = username.indexOf("@");
        username = username.substring(0, index);
        setTitle("YouCook - " + username);
    }

    @Override
    public void onClick(View view)
    {
        Intent options;

        switch (view.getId())
        {
            case R.id.searchmenuoption : options = new Intent(this, Search.class);
                startActivity(options);
                break;

            case R.id.shoppinglistmenuoption : options = new Intent(this, ShoppingList.class);
                startActivity(options);
                break;

            case R.id.recipesmenuoption : options = new Intent(this, Recipes.class);
                startActivity(options);
                break;

            case R.id.timermenuoption : options = new Intent(this, Timer.class);
                startActivity(options);
                break;

            case R.id.convertermenuoption : options = new Intent(this, Converter.class);
                startActivity(options);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent mOptions;

        switch (item.getItemId())
        {
            case R.id.home : mOptions = new Intent(this, MainActivity.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.search : mOptions = new Intent(this, Search.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.shoppinglist : mOptions = new Intent(this, ShoppingList.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.recipes : mOptions = new Intent(this, Recipes.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.timer : mOptions = new Intent(this, Timer.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.converter : mOptions = new Intent(this, Converter.class);
            finish();
            startActivity(mOptions);
            break;

            case R.id.signout :
                signOut();
                break;

            default:
                break;
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut()
    {
        mAuth.signOut();
    }
}
