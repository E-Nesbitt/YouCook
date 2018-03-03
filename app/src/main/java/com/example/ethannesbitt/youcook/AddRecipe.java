package com.example.ethannesbitt.youcook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    private Button save;
    private EditText rName, rPrepTime, rCookTime, rIngredients, rMethod;
    private Spinner rType, cookTimeUnit, prepTimeUnit;

    private DatabaseReference recipeDatabase;

    //testing
    private TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");

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

        //setting up recipe inputs
        rName = findViewById(R.id.recipeNameInput);
        rPrepTime = findViewById(R.id.prep_time_input);
        prepTimeUnit = findViewById(R.id.prep_time_unit);
        rCookTime = findViewById(R.id.cooking_time_input);
        cookTimeUnit = findViewById(R.id.cook_time_unit);
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

        ingredients = findViewById(R.id.recipeIngredients);

        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(AddRecipe.this);
                View ingredientView = inflater.inflate(R.layout.ingredient_inputs, null);

                EditText ingredientOneName = ingredientView.findViewById(R.id.ingredient_one_name);
                EditText ingredientOneAmount = ingredientView.findViewById(R.id.ingredient_one_amount);
                Spinner ingredientOneUnit = ingredientView.findViewById(R.id.i_g_1_unit);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipe.this);
                builder.setTitle("Add Ingredients");
                builder.setView(ingredientView);//passing the edit text to the alert prompt view
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }

    //method to save the recipe
    private void saveRecipe()
    {
        //takes inputs from user and sets them to strings
        String name = rName.getText().toString().trim();
        String prepTime = rPrepTime.getText().toString().trim() + " " + prepTimeUnit.getSelectedItem().toString();
        String cookTime = rCookTime.getText().toString().trim() + " " + cookTimeUnit.getSelectedItem().toString();
        String type = rType.getSelectedItem().toString();
        String ingredients = rIngredients.getText().toString().trim();
        String method = rMethod.getText().toString().trim();

        //error handling to check all the inputs have data filled in
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(ingredients) && !TextUtils.isEmpty(method))
        {
            //save the input data to the Firebase database, setting a new Unique id each time a save is actioned
            String id = recipeDatabase.push().getKey();

            Recipe recipe = new Recipe(id, name, type, prepTime, cookTime, ingredients, method);

            recipeDatabase.child(id).setValue(recipe);

            Toast.makeText(AddRecipe.this, "Recipe "+ name + " saved!", Toast.LENGTH_LONG).show();

            reset();
        }
        else
        {
            //Error toast message to let user know more data needs entered before save can be completed
            Toast.makeText(AddRecipe.this, "Ensure all details are entered and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    //empty the input text areas so that the next recipe is ready to be entered i.e. reset activity to default
    private void reset()
    {
        rName.setText("");
        rPrepTime.setText("");
        rCookTime.setText("");
        rType.setSelection(0);
        rIngredients.setText("");
        rMethod.setText("");
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
