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
            String name = recipePageBundle.getString("Recipe name");
            Log.d("listtest", name);
            Toast.makeText(this, "Recipe Name: " + name, Toast.LENGTH_SHORT);
        }
    }
}
