package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Timer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;
    private Handler timeHandler;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //Drawer Navigation Menu set up
        drawerMenu = (DrawerLayout) findViewById(R.id.timer);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.timerdnav);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up the time
        timeHandler = new Handler();

        final Runnable timerTask = new Runnable()
        {
            @Override
            public void run()
            {
                time = time - 1000;

                if(time > 0)
                {
                    timeHandler.postDelayed(this, 1000);
                }
            }
        };

        timeHandler.postDelayed(timerTask, 1000);
    }

    @Override //needed to toggle the drawer menu
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override //on clicks for the different items in the drawer menu
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Intent mOptions;

        switch (item.getItemId())
        {
            case R.id.home : mOptions = new Intent(this, MainActivity.class);
                startActivity(mOptions);
                break;

            case R.id.search : mOptions = new Intent(this, Search.class);
                startActivity(mOptions);
                break;

            case R.id.shoppinglist : mOptions = new Intent(this, ShoppingList.class);
                startActivity(mOptions);
                break;

            case R.id.recipes : mOptions = new Intent(this, Recipes.class);
                startActivity(mOptions);
                break;

            case R.id.timer : mOptions = new Intent(this, Timer.class);
                startActivity(mOptions);
                break;

            case R.id.converter : mOptions = new Intent(this, Converter.class);
                startActivity(mOptions);
                break;

            default:
                break;
        }
        return false;
    }


}
