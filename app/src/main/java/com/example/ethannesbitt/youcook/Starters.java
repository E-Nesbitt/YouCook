package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Recipe recipe = recipeList.get(position);

                String rid = recipe.getRecipeId();
                String name = recipe.getRecipeName();
                String type = recipe.getRecipeType();
                String ingredients = recipe.getRecipeIngredients();
                String method = recipe.getRecipeMethod();

                Intent recipePage = new Intent(Starters.this, RecipePage.class);
                recipePage.putExtra("Recipe id", rid);
                recipePage.putExtra("Recipe name", name);
                recipePage.putExtra("Recipe type", type);
                recipePage.putExtra("Recipe ingredients", ingredients);
                recipePage.putExtra("Recipe method", method);

                startActivity(recipePage);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                //clearing the list prior to adding items, so we get a fresh list each time
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

    private void deleteRecipe(String recipeId)
    {
        DatabaseReference recipeDB = FirebaseDatabase.getInstance().getReference("recipes").child(recipeId);

        recipeDB.removeValue();

        Toast.makeText(Starters.this, "Recipe Deleted Successfully!", Toast.LENGTH_LONG).show();
    }

}
