package com.example.ethannesbitt.youcook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Search extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //declaring variables for drawer navigation
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    //declaring variables for user
    private FirebaseAuth mAuth;

    //declaring search feature variables
    private Button searchButton, trendingButton, highestRatedButton;
    private EditText searchInput;
    private String userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //initialising drawer navigation
        drawerMenu = findViewById(R.id.search);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.searchdnav);
        navigationView.setNavigationItemSelectedListener(this);

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialising search feature
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);

        //initialising trending and highest rated button
        trendingButton = findViewById(R.id.search_trending_button);
        highestRatedButton= findViewById(R.id.search_highestrated_button);

        //edamam api app_id=c4896013 --- app_key=8bacbcab9d13e1f47ae0a8febb727655
        //test api https://api.edamam.com/search?q=turkey,onions&app_id=c4896013&app_key=8bacbcab9d13e1f47ae0a8febb727655

        //food to fork  api key = 51bc38640178924d013b85854b8d7a52
        //http://food2fork.com/api/search?key=51bc38640178924d013b85854b8d7a52&q=chicken

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                userInput = searchInput.getText().toString();

                Intent searchResultsActivity = new Intent(Search.this, SearchResults.class);
                searchResultsActivity.putExtra("User Input", userInput);

                startActivity(searchResultsActivity);
            }
        });

        //
        trendingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent trendingResultsActivity = new Intent(Search.this, SearchResults.class);
                trendingResultsActivity.putExtra("User Input", "&sort=t");
                startActivity(trendingResultsActivity);
            }
        });

        highestRatedButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent highestRatedActivity = new Intent(Search.this, SearchResults.class);
                highestRatedActivity.putExtra("User Input", "&sort=r");

                startActivity(highestRatedActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent mOptions;

        switch (item.getItemId()) {
            case R.id.home:
                mOptions = new Intent(this, MainActivity.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.search:
                mOptions = new Intent(this, Search.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.shoppinglist:
                mOptions = new Intent(this, ShoppingList.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.recipes:
                mOptions = new Intent(this, Recipes.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.timer:
                mOptions = new Intent(this, Timer.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.converter:
                mOptions = new Intent(this, Converter.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.signout:
                signOut();
                break;

            case R.id.delete_account : mOptions = new Intent(this, DisableAccount.class);
                finish();
                startActivity(mOptions);
                break;

            default:
                break;
        }
        return false;
    }

    private void signOut() {
        mAuth.signOut();
    }
}