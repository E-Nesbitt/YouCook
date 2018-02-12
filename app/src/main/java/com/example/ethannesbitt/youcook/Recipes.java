package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recipes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    private Button save;
    private Button startersButton, mainsButton, dessertsButton;
    private EditText rName, rIngredients, rMethod;
    private Spinner rType;

    TextView testRe;

    DatabaseReference recipeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //test
        testRe = (TextView) findViewById(R.id.testRe);

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("Recipes");

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //setting up drawer menu
        drawerMenu = (DrawerLayout) findViewById(R.id.recipes);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.recipednav);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up recipe inputs
        rName = (EditText) findViewById(R.id.recipeNameInput);
        rIngredients = (EditText) findViewById(R.id.recipeIngredientsInput);
        rMethod = (EditText) findViewById(R.id.recipeMethodInput);
        rType = (Spinner) findViewById(R.id.recipeTypeInput);
        save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveRecipe();
            }
        });

        //view recipes buttons and their onClicks to redirect to the corresponding activities
        startersButton = (Button) findViewById(R.id.starterButton);
        mainsButton = (Button) findViewById(R.id.mainButton);
        dessertsButton = (Button) findViewById(R.id.dessertButton);

        startersButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                recipeDatabase.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
                        {
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                            try
                            {
                                String test = recipe.recipeName;
                                Log.d("test", test);
                                Intent Starters = new Intent(Recipes.this, Starters.class);
                                Starters.putExtra("DB Data1", recipe.recipeName);
                                Starters.putExtra("DB Data2", recipe.recipeType);
                                Starters.putExtra("DB Data3", recipe.recipeIngredients);
                                Starters.putExtra("DB Data4", recipe.recipeMethod);
                                startActivity(Starters);
                            }
                            catch (NullPointerException e)
                            {
                                Toast.makeText(Recipes.this, "Null Pointer caught", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Log.d("Error", "loadPost:onCancelled", databaseError.toException());
                        Toast.makeText(Recipes.this, "Failed to load recipe.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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
        rIngredients.setText("");
        rMethod.setText("");
    }

    //sign out method so user can log out of the app
    private void signOut()
    {
        mAuth.signOut();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        recipeDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                    try
                    {
                        testRe.setText(recipe.recipeName);

                        String test = recipe.recipeName;
                        Log.d("test", test);

                    }
                    catch (NullPointerException e)
                    {
                        Toast.makeText(Recipes.this, "Null Pointer caught", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("Error", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(Recipes.this, "Failed to load recipe.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
