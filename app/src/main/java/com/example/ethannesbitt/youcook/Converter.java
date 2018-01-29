package com.example.ethannesbitt.youcook;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Converter extends AppCompatActivity
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        drawerMenu = (DrawerLayout) findViewById(R.id.converter);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
