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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

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
    private MultiAutoCompleteTextView searchInput;
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

        //setting up the search suggestions, once 2 characters are entered it will make suggestions, using a comma to separate ingredients (split it into sub-strings)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, INGREDIENTS);
        searchInput.setThreshold(2);
        searchInput.setAdapter(adapter);
        searchInput.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //Search button onclick, takes user input and passes it to Api to use to carry out search in next activity
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                userInput = searchInput.getText().toString();

                if(userInput.equals(""))
                {
                    searchInput.setError("Enter an Ingredient!");
                }
                else
                {
                    Intent searchResultsActivity = new Intent(Search.this, SearchResults.class);
                    searchResultsActivity.putExtra("User Input", userInput);
                    startActivity(searchResultsActivity);
                }
            }
        });

        //replaces user input with the value needed to return trending results in the api, starts new activity showing these
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

        //replaces user input with the value needed to return highest rated results in the api, starts new activity showing these
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

    //autocomplete list of possible ingredients, other ingredients can be used but these are a guide to make it easier
    private static final String[] INGREDIENTS = new String[] {
            /*A*/ "Almond", "Almond Milk", "Anchovies", "Apple", "Apple Juice", "Artichoke", "Aubergine", "Avocado",
            /*B*/ "Bacon", "Bagel", "Baguette", "Baking Powder", "Balsamic Vinegar", "Bamboo Shoots", "Banana", "Basil",
                "Bay Leaf", "Beans", "Beef", "Beer", "Beetroot", "Blackcurrant", "Brie", "Brioche", "Brown Rice", "Butter", "Butternut Squash",
            /*C*/ "Cabbage", "Capers", "Caramel", "Cardamom", "Carrot", "Cashew", "Caster Sugar", "Cayenne Pepper", "Celery", "Cheese", "Cherry",
                "Chestnut", "Chicken", "Chicken Breast", "Chicken Thigh", "Chicken Leg", "Chicken Stock", "Chilli", "Chocolate", "Coconut", "Coriander",
            /*D*/ "Dijon Mustard", "Dill", "Duck", "Double Cream",
            /*E*/ "Edam", "Egg", "Egg White", "Egg Yolk",
            /*F*/ "Fennel", "Fig", "Fish", "Flour", "Filo Pastry",
            /*G*/ "Gammon", "Garlic", "Gherkin", "Gin", "Ginger", "Gingerbread", "Golden Syrup", "Grapes", "Grapefruit", "Gravy",
            /*H*/ "Haddock", "Ham", "Hazelnut", "Herbs", "Honey", "Horseradish", "Hummus",
            /*I*/ "Ice Cream", "Iceberg Lettuce", "Icing", "Icing Sugar",
            /*J*/ "Jam", "Jelly",
            /*K*/ "Kale", "Ketchup", "Kiwi", "Kumquat",
            /*L*/ "Lager", "Lamb", "Lasagne", "Leek", "Lemon", "Lime", "Linguine", "Liver", "Lychee",
            /*M*/ "Mango", "Maple Syrup", "Margarine", "Marrow", "Marshmallow", "Melon", "Milk", "Mince", "Mint", "Mustard", "Mushroom", "Muesli",
            /*N*/ "Nachos", "Noodles", "Nutmeg",
            /*O*/ "Oatmeal", "Oil", "Olive", "Onion", "Orange", "Oregano",
            /*P*/ "Pancetta", "Paprika", "Parmesan", "Parsnip", "Pasta", "Pastry", "Peach", "Peanut Butter", "Pepper", "Pineapple", "Potato", "Prawn", "Pork",
            /*Q*/ "Quinoa",
            /*R*/ "Raisins", "Raspberry", "Red Onion", "Red Wine", "Rhubarb", "Rice", "Rigatoni", "Risotto", "Rosemary",
            /*S*/ "Sage", "Salmon", "Salsa", "Salt", "Sausage", "SelfRaising Flour", "Single Cream", "Soy Sauce", "Soya Milk", "Spaghetti", "Stock", "Sugar",
                "Spring Onion",
            /*T*/ "Tabasco", "Tapioca", "Tea", "Thyme", "Tuna", "Tofu", "Tomato", "Tomato Puree", "Turkey", "Turnip",
            /*U none to note */
            /*V*/ "Vanilla", "Vinegar", "Vodka",
            /*W*/ "Walnut", "Wasabi", "Water", "Whiskey", "Wine",
            /*X none to note*/
            /*Y*/ "Yeast", "Yogurt",
            /*Z none to note*/
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //drawer menu navigation, on clicks for each item in the menu, finishes current activity and starts the new activity
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

    //sign out method to allow user to sign out of account/app
    private void signOut() {
        mAuth.signOut();
    }
}