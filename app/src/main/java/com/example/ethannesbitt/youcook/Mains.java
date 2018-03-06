package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mains extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    //declaring variables for drawer menu
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    //declaring variables for  user
    private FirebaseAuth mAuth;

    //declaring variables for database
    private DatabaseReference databaseReference;
    private ListView listRecipes;
    private List<Recipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);


        //initialising database
        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");
        listRecipes = findViewById(R.id.mainsList);
        recipeList = new ArrayList<>();

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialising drawer menu
        drawerMenu = findViewById(R.id.mains);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.mainsdnav);
        navigationView.setNavigationItemSelectedListener(this);

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Recipe recipe = recipeList.get(position);

                String rid = recipe.getRecipeId();
                String name = recipe.getRecipeName();
                String prepTime = recipe.getRecipePrepTime();
                String cookTime = recipe.getRecipePrepTime();
                String type = recipe.getRecipeType();
                //String ingredients = recipe.getRecipeIngredients();
                String method = recipe.getRecipeMethod();

                Intent recipePage = new Intent(Mains.this, RecipePage.class);
                recipePage.putExtra("Recipe id", rid);
                recipePage.putExtra("Recipe name", name);
                recipePage.putExtra("Recipe prepTime", prepTime);
                recipePage.putExtra("Recipe cookTime", cookTime);
                recipePage.putExtra("Recipe type", type);
                //recipePage.putExtra("Recipe ingredients", ingredients);
                recipePage.putExtra("Recipe method", method);

                startActivity(recipePage);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();

                for(DataSnapshot recipeSnapshot: dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                    if(recipe != null && recipe.recipeType.equals("Main"))
                    {
                        recipeList.add(recipe);
                    }
                }

                RecipeList adapter = new RecipeList(Mains.this, recipeList);
                listRecipes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

            case R.id.delete_account : mOptions = new Intent(this, DisableAccount.class);
                finish();
                startActivity(mOptions);
                break;

            default:
                break;
        }
        return false;
    }

    private void signOut()
    {
        mAuth.signOut();
    }

}
