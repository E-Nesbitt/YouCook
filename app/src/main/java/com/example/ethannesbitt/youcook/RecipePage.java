package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RecipePage extends AppCompatActivity
{

    private TextView recipeName, ingredientsList, methodList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Bundle recipePageBundle = getIntent().getExtras();

        recipeName = findViewById(R.id.title_recipe_page);
        ingredientsList = findViewById(R.id.list_ingredients);
        methodList = findViewById(R.id.list_method);

        if(recipePageBundle != null)
        {
            String id = recipePageBundle.getString("Recipe id");
            String name = recipePageBundle.getString("Recipe name");
            String type = recipePageBundle.getString("Recipe type");
            String ingredients = recipePageBundle.getString("Recipe ingredients");
            String method = recipePageBundle.getString("Recipe method");
            Log.d("listtest", id + " " + name + " " + type + " " + ingredients + " " + method);

            recipeName.setText(name);
            ingredientsList.setText(ingredients);
            methodList.setText(method);
        }
        else
        {
            recipeName.setText("Error Loading Recipe!");
            Toast.makeText(this, "LOADING ERROR!", Toast.LENGTH_SHORT).show();
        }
    }
}
