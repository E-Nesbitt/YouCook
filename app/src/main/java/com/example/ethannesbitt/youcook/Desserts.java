package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Desserts extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desserts);
    }

    private void deleteRecipe(String recipeId)
    {
        DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("Recipes").child(recipeId);

        recipeDB.removeValue();

        Toast.makeText(Desserts.this, "Recipe Deleted Successfully!", Toast.LENGTH_LONG).show();
    }
}
