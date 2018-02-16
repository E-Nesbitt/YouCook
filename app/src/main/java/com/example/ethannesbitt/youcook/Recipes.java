package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Recipes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    private Button save;
    private Button startersButton, mainsButton, dessertsButton;
    private EditText rName, rIngredients, rMethod;
    private Spinner rType;

    DatabaseReference recipeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //setting up drawer menu
        drawerMenu = findViewById(R.id.recipes);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.recipednav);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up recipe inputs
        rName = findViewById(R.id.recipeNameInput);
        rIngredients = findViewById(R.id.recipeIngredientsInput);
        rMethod = findViewById(R.id.recipeMethodInput);
        rType = findViewById(R.id.recipeTypeInput);
        save = findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveRecipe();
            }
        });

        //view recipes buttons and their onClicks to redirect to the corresponding activities
        startersButton = findViewById(R.id.starterButton);
        mainsButton = findViewById(R.id.mainButton);
        dessertsButton = findViewById(R.id.dessertButton);

        startersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Recipes.this, Starters.class));
            }
        });

        mainsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Recipes.this, Mains.class));
            }
        });

        dessertsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Recipes.this, Desserts.class));
            }
        });
    }

    //method to save the recipe
    private void saveRecipe()
    {
        //takes inputs from user and sets them to strings
        String name = rName.getText().toString().trim();
        String type = rType.getSelectedItem().toString();
        String ingredients = rIngredients.getText().toString().trim();
        String method = rMethod.getText().toString().trim();

        //error handling to check all the inputs have data filled in
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(ingredients) && !TextUtils.isEmpty(method))
        {
            //save the input data to the Firebase database, setting a new Unique id each time a save is actioned
            String id = recipeDatabase.push().getKey();

            Recipe recipe = new Recipe(id, name, type, ingredients, method);

            recipeDatabase.child(id).setValue(recipe);

            Toast.makeText(Recipes.this, "Recipe "+ name + " saved!", Toast.LENGTH_LONG).show();

            reset();
        }
        else
        {
            //Error toast message to let user know more data needs entered before save can be completed
            Toast.makeText(Recipes.this, "Ensure all details are entered and try again!", Toast.LENGTH_SHORT).show();
        }
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

            default:
                break;
        }
        return false;
    }

    //empty the input text areas so that the next recipe is ready to be entered i.e. reset activity to default
    private void reset()
    {
        rName.setText("");
        rType.setSelection(0);
        rIngredients.setText("");
        rMethod.setText("");
    }

    //sign out method so user can log out of the app
    private void signOut()
    {
        mAuth.signOut();
    }
}
