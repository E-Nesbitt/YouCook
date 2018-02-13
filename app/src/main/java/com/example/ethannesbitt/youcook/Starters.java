package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Starters extends AppCompatActivity
{

    //initialising database
    DatabaseReference databaseReference;
    ListView listRecipes;
    List<Recipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starters);

        //testing db
        databaseReference = FirebaseDatabase.getInstance().getReference("recipes");
        listRecipes = (ListView) findViewById(R.id.starterslist);
        recipeList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();

                for(DataSnapshot recipeSnapshot: dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                    if(recipe.recipeType.equals("Starter"))
                    {
                        recipeList.add(recipe);
                    }
                }

                RecipeList adapter = new RecipeList(Starters.this, recipeList);
                listRecipes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
