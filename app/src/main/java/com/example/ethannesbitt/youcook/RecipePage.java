package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RecipePage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        Bundle recipePageBundle = getIntent().getExtras();

        if(recipePageBundle != null)
        {
            String id = recipePageBundle.getString("Recipe id");
            String name = recipePageBundle.getString("Recipe name");
            String type = recipePageBundle.getString("Recipe type");
            String ingredients = recipePageBundle.getString("Recipe ingredients");
            String method = recipePageBundle.getString("Recipe method");
            Log.d("listtest", id + " " + name + " " + type + " " + ingredients + " " + method);
        }
    }
}
