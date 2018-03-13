package com.example.ethannesbitt.youcook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipePage extends AppCompatActivity
{

    private TextView recipeName, ingredientsList, methodList, prepTime, cookTime;
    private Button deleteButton;
    private String recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Bundle recipePageBundle = getIntent().getExtras();

        //initialising text views and delete button
        recipeName = findViewById(R.id.title_recipe_page);
        prepTime = findViewById(R.id.prep_time_display);
        cookTime = findViewById(R.id.cook_time_display);
        ingredientsList = findViewById(R.id.list_ingredients);
        methodList = findViewById(R.id.list_method);
        deleteButton = findViewById(R.id.delete_recipe);

        if(recipePageBundle != null)
        {
            //getting all recipe details passed over from the previous activity on the list item click
            String id = recipePageBundle.getString("Recipe id");
            String name = recipePageBundle.getString("Recipe name");
            String pTime = recipePageBundle.getString("Recipe prepTime");
            String cTime = recipePageBundle.getString("Recipe cookTime");
            String type = recipePageBundle.getString("Recipe type");
            String ingredientOne = recipePageBundle.getString("Recipe ingredient one");
            String ingredientTwo = recipePageBundle.getString("Recipe ingredient 0");
            String ingredientThree = recipePageBundle.getString("Recipe ingredient 1");
            String ingredientFour = recipePageBundle.getString("Recipe ingredient 2");
            String ingredientFive = recipePageBundle.getString("Recipe ingredient 3");
            String ingredientSix = recipePageBundle.getString("Recipe ingredient 4");
            String ingredientSeven = recipePageBundle.getString("Recipe ingredient 5");
            String ingredientEight = recipePageBundle.getString("Recipe ingredient 6");
            String ingredientNine = recipePageBundle.getString("Recipe ingredient 7");
            String ingredientTen = recipePageBundle.getString("Recipe ingredient 8");
            String ingredientEleven = recipePageBundle.getString("Recipe ingredient 9");
            String ingredientTwelve = recipePageBundle.getString("Recipe ingredient 10");
            String ingredientThirteen = recipePageBundle.getString("Recipe ingredient 11");
            String ingredientFourteen = recipePageBundle.getString("Recipe ingredient 12");
            String ingredientFifteen = recipePageBundle.getString("Recipe ingredient 13");
            String ingredientSixteen = recipePageBundle.getString("Recipe ingredient 14");
            String ingredientSeventeen = recipePageBundle.getString("Recipe ingredient 15");
            String ingredientEighteen = recipePageBundle.getString("Recipe ingredient 16");
            String ingredientNineteen = recipePageBundle.getString("Recipe ingredient 17");
            String ingredientTwenty = recipePageBundle.getString("Recipe ingredient 18");
            String[] ingredients = new String[] {ingredientTwo, ingredientThree, ingredientFour, ingredientFive, ingredientSix, ingredientSeven, ingredientEight,
                    ingredientNine, ingredientTen, ingredientEleven, ingredientTwelve, ingredientThirteen, ingredientFourteen, ingredientFifteen,
                    ingredientSixteen, ingredientSeventeen, ingredientEighteen, ingredientNineteen, ingredientTwenty};

            String method = recipePageBundle.getString("Recipe method");

            //setting text views to have the information from the selected recipe in the DB
            recipeName.setText(name);
            prepTime.setText(pTime);
            cookTime.setText(cTime);
            ingredientsList.setText(ingredientOne);

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
            recipeName.setText("Error Loading Recipe!");
            Toast.makeText(this, "LOADING ERROR!", Toast.LENGTH_SHORT).show();
        }

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deleteRecipe(recipeID);
            }
        });
    }

    private void deleteRecipe(String recipeId)
    {
        final DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("recipes").child(recipeId);

        //Creating an alert prompt to ask user if item has been got
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete the recipe?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //Removes the item and re-adds it with a tick beside it to show its been got
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
}
