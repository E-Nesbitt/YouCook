package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Starters extends AppCompatActivity
{
    DatabaseReference recipeDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("Recipes");

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


    }

    private void deleteRecipe(String recipeId)
    {
        DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("Recipes").child(recipeId);

        recipeDB.removeValue();

        Toast.makeText(Starters.this, "Recipe Deleted Successfully!", Toast.LENGTH_LONG).show();
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
//                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren())
//                {
//                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
//
//                    String t = Long.toString(dataSnapshot.getChildrenCount());
//                    Log.d("childCount test", t);
//
//                    String r = Long.toString(recipeSnapshot.getChildrenCount());
//                    Log.d("childCount test", r);
//
//                    try
//                    {
//                        test.setText(recipe.recipeName);
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
}

