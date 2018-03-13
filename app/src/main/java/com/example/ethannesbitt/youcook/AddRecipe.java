package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    private Button save;
    private EditText rName, rPrepTime, rCookTime, rIngredients, rMethod;
    private Spinner rType, cookTimeUnit, prepTimeUnit;


    //testing tabs
    private SectionsPageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //setting up drawer menu
        drawerMenu = findViewById(R.id.add_recipes);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.addrecipednav);
        navigationView.setNavigationItemSelectedListener(this);

        //initialising tabs
        viewPager = findViewById(R.id.container_add_recipe);
        viewPagerCreate(viewPager);
        tabLayout = findViewById(R.id.recipeTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    //method to add the tabs (fragments to the page adapter and then set the page adapter to the viewPager
    private void viewPagerCreate(ViewPager viewPager)
    {
        SectionsPageAdapter sPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sPageAdapter.addFragment("Information", new RecipeInformationTab());
        sPageAdapter.addFragment("Ingredients", new RecipeIngredientsTab());
        sPageAdapter.addFragment("Method", new RecipeMethodTab());

        viewPager.setAdapter(sPageAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //making drawer menu open if menu option is selected in title bar
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

            case R.id.delete_account : mOptions = new Intent(this, DisableAccount.class);
                finish();
                startActivity(mOptions);
                break;

            default:
                break;
        }
        return false;
    }

    //sign out method so user can log out of the app
    private void signOut()
    {
        mAuth.signOut();
    }
}
