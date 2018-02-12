package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Starters extends AppCompatActivity
{
    DatabaseReference recipeDatabase;
    private FirebaseAuth mAuth;
    List<Recipe> recipeList;
    ListView startersList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startersList = (ListView) findViewById(R.id.startersList);
        recipeList = new ArrayList<>();

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("Recipes");

    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        recipeDatabase.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                recipeList.clear();
//
//                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
//                {
//                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
//
//                    recipeList.add(recipe);
//
//                    try
//                    {
//                        //testRe.setText(recipe.recipeName);
//
//                        String test = recipe.recipeName;
//                        Log.d("test", test);
//
//                    }
//                    catch (NullPointerException e)
//                    {
//                        Toast.makeText(Starters.this, "Null Pointer caught", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                RecipeList adapter = new RecipeList(Starters.this, recipeList);
//                startersList.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError)
//            {
//                Log.d("Error", "loadPost:onCancelled", databaseError.toException());
//                Toast.makeText(Starters.this, "Failed to load recipe.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    protected void onStart()
    {
        super.onStart();

        recipeDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                recipeList.clear();

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                        recipeList.add(recipe);
                        String test = recipe.recipeName;
                        Log.d("test", test);

                    }
                    catch (NullPointerException e)
                    {
                        Toast.makeText(Starters.this, "Null Pointer caught", Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    RecipeList adapter = new RecipeList(Starters.this, recipeList);
                    startersList.setAdapter(adapter);
                }
                catch (NullPointerException e)
                {
                    Toast.makeText(Starters.this, "Null Pointer caught", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("Error", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(Starters.this, "Failed to load recipe.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteRecipe(String recipeId)
    {
        DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("Recipes").child(recipeId);

        recipeDB.removeValue();

        Toast.makeText(Starters.this, "Recipe Deleted Successfully!", Toast.LENGTH_LONG).show();
    }
}

