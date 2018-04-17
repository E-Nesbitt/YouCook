package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RecipePage extends AppCompatActivity
{
    //declaring variables required for viewing an individual recipe
    private TextView recipeName, ingredientsList, methodList, prepTime, cookTime;
    private Button deleteButton;
    private String recipeID;
    private FirebaseAuth mAuth;

    //declaring array list so that ingredients can be added to the shopping list
    private ArrayList<String> ingredientList = null;

    //all string variables required for the recipe
    private String id, name, pTime, cTime, type, ingredientOne, ingredientTwo, ingredientThree, ingredientFour, ingredientFive, ingredientSix, ingredientSeven, ingredientEight,
            ingredientNine, ingredientTen, ingredientEleven, ingredientTwelve, ingredientThirteen, ingredientFourteen, ingredientFifteen, ingredientSixteen,
            ingredientSeventeen, ingredientEighteen, ingredientNineteen, ingredientTwenty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Bundle recipePageBundle = getIntent().getExtras();

        //initialising shopping list
        ingredientList = getIngredientsList(getApplicationContext());

        //initialising text views and delete button
        recipeName = findViewById(R.id.title_recipe_page);
        prepTime = findViewById(R.id.prep_time_display);
        cookTime = findViewById(R.id.cook_time_display);
        ingredientsList = findViewById(R.id.list_ingredients);
        methodList = findViewById(R.id.list_method);
        deleteButton = findViewById(R.id.delete_recipe);

        //retrieve all the recipe information from the selected item in the list of recipes
        if(recipePageBundle != null)
        {
            //getting all recipe details passed over from the previous activity on the list item click
            id = recipePageBundle.getString("Recipe id");
            name = recipePageBundle.getString("Recipe name");
            pTime = recipePageBundle.getString("Recipe prepTime");
            cTime = recipePageBundle.getString("Recipe cookTime");
            type = recipePageBundle.getString("Recipe type");
            ingredientOne = recipePageBundle.getString("Recipe ingredient one");
            ingredientTwo = recipePageBundle.getString("Recipe ingredient 0");
            ingredientThree = recipePageBundle.getString("Recipe ingredient 1");
            ingredientFour = recipePageBundle.getString("Recipe ingredient 2");
            ingredientFive = recipePageBundle.getString("Recipe ingredient 3");
            ingredientSix = recipePageBundle.getString("Recipe ingredient 4");
            ingredientSeven = recipePageBundle.getString("Recipe ingredient 5");
            ingredientEight = recipePageBundle.getString("Recipe ingredient 6");
            ingredientNine = recipePageBundle.getString("Recipe ingredient 7");
            ingredientTen = recipePageBundle.getString("Recipe ingredient 8");
            ingredientEleven = recipePageBundle.getString("Recipe ingredient 9");
            ingredientTwelve = recipePageBundle.getString("Recipe ingredient 10");
            ingredientThirteen = recipePageBundle.getString("Recipe ingredient 11");
            ingredientFourteen = recipePageBundle.getString("Recipe ingredient 12");
            ingredientFifteen = recipePageBundle.getString("Recipe ingredient 13");
            ingredientSixteen = recipePageBundle.getString("Recipe ingredient 14");
            ingredientSeventeen = recipePageBundle.getString("Recipe ingredient 15");
            ingredientEighteen = recipePageBundle.getString("Recipe ingredient 16");
            ingredientNineteen = recipePageBundle.getString("Recipe ingredient 17");
            ingredientTwenty = recipePageBundle.getString("Recipe ingredient 18");
            String[] ingredients = new String[]{ingredientTwo, ingredientThree, ingredientFour, ingredientFive, ingredientSix, ingredientSeven, ingredientEight,
                    ingredientNine, ingredientTen, ingredientEleven, ingredientTwelve, ingredientThirteen, ingredientFourteen, ingredientFifteen,
                    ingredientSixteen, ingredientSeventeen, ingredientEighteen, ingredientNineteen, ingredientTwenty};

            String method = recipePageBundle.getString("Recipe method");

            //setting text views to have the information from the selected recipe in the DB
            recipeName.setText(name);
            prepTime.setText(pTime);
            cookTime.setText(cTime);
            ingredientsList.setText(ingredientOne);

            //loop to only retrieve valid ingredients
            for(int i = 0; i <=18 ; i++)
            {
                try
                {
                    if (!ingredients[i].equals(""))
                    {
                        ingredientsList.append("\n" + ingredients[i]);
                    }
                }catch (NullPointerException e)
                {
                    Toast.makeText(this, "ERROR LOADING INGREDIENTS!", Toast.LENGTH_SHORT).show();
                }
            }
            methodList.setText(method);

            //setting the recipe id to String ID for delete method
            recipeID = id;
        }
        else
        {
            //error message to be displayed to user if no recipe can be retrieved
            recipeName.setText("Error Loading Recipe!");
            Toast.makeText(this, "LOADING ERROR!", Toast.LENGTH_SHORT).show();
        }

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //deleting the recipe that is currently being viewed
                deleteRecipe(recipeID);
            }
        });

        //on click for the ingredients so that they can be added to the users shopping list
        ingredientsList.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                //Creating an alert prompt to ask user if they want to add the ingredients to the shopping list
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipePage.this);
                builder.setTitle("Add all ingredients to the shopping list?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String itemsList = ingredientsList.getText().toString();
                        String[] items = itemsList.split("\n");

                        for(int j = 0; j < items.length ; j++)
                        {
                            ingredientList.add(items[j]);
                        }

                        storeIngredientsList(ingredientList, getApplicationContext());

                        Toast.makeText(RecipePage.this, "Ingredients Added Successfully to your Shopping List!", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
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

    //method used to delete the recipe from the firebase database
    private void deleteRecipe(String recipeId)
    {
        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        //getting the specific recipe that the user is viewing
        final DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("recipes").child(uid).child(recipeId);

        //Creating an alert prompt to ask user if recipe is to be deleted or not
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete the recipe?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //Removes the recipe from the database
                recipeDB.removeValue();
                Toast.makeText(RecipePage.this, "Recipe Deleted Successfully!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(RecipePage.this, Recipes.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }

    //stores the shopping list in shared preferences so it can be accessed when app is reopened
    public static void storeIngredientsList (ArrayList currentList, Context context)
    {
        Set writeList = new HashSet(currentList);
        SharedPreferences putPreferences=context.getSharedPreferences("shoppingListValues",Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor=putPreferences.edit();
        preferenceEditor.putStringSet("theShoppingList", writeList);
        preferenceEditor.apply();
    }

    //opens the shopping list stored in shared preferences when app is opened
    public static ArrayList getIngredientsList(Context stored)
    {
        SharedPreferences getPreferences = stored.getSharedPreferences("shoppingListValues", Activity.MODE_PRIVATE);
        Set temporarySet = new HashSet();
        temporarySet = getPreferences.getStringSet("theShoppingList", temporarySet);
        return new ArrayList<>(temporarySet);
    }
}