package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Converter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialising drawer menu
        drawerMenu = (DrawerLayout) findViewById(R.id.converter);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.converterdnav);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //allows drawer menu to be opened via a button in title bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //drawer menu navigation, on clicks for each item in the menu, finishes current activity and starts the new activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
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


    //conversion methods 'to Grams'
    private String kiloToGram (double k)
    {
        String finalResult = ;
        return finalResult;
    }

    private String poundToGram (double p)
    {
        String finalResult = ;
        return finalResult;
    }

    private String ounceToGram (double o)
    {
        String finalResult = ;
        return finalResult;
    }

    private String cupToGram (double c)
    {
        String finalResult = ;
        return finalResult;
    }


    //conversion methods 'to Kilos'
    private String gramToKilo (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String poundToKilo (double p)
    {
        String finalResult = ;
        return finalResult;
    }

    private String ounceToKilo (double o)
    {
        String finalResult = ;
        return finalResult;
    }

    private String cupToKilo (double c)
    {
        String finalResult = ;
        return finalResult;
    }


    //conversion methods 'to Pounds'
    private String kiloToPound (double k)
    {
        String finalResult = ;
        return finalResult;
    }

    private String gramToPound (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String ounceToPound (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String cupToPound (double g)
    {
        String finalResult = ;
        return finalResult;
    }


    //conversion methods 'to Ounces'
    private String kiloToOunce (double k)
    {
        String finalResult = ;
        return finalResult;
    }

    private String gramToOunce (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String poundToPound (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String cupToPound (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    //conversion methods 'to Cups'
    private String kiloToCup (double k)
    {
        String finalResult = ;
        return finalResult;
    }

    private String gramToCup (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String poundToCup (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    private String ounceToCup (double g)
    {
        String finalResult = ;
        return finalResult;
    }

    //sign out method to allow user to sign out of account/app
    private void signOut()
    {
        mAuth.signOut();
    }

}
