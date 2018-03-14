package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecipeMethodTab extends Fragment
{
    private FirebaseAuth mAuth;

    private DatabaseReference recipeDatabase;

    private EditText method;

    private Button saveButton;

    private ViewPager viewPager;


    public RecipeMethodTab()
    {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_method_tab, container, false);

        //Initialise recipe database
        recipeDatabase = FirebaseDatabase.getInstance().getReference("recipes");

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        method = view.findViewById(R.id.recipeMethodInput);
        saveButton = view.findViewById(R.id.save_all_button);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveRecipe();
            }
        });

        //return the view inflated
        return view;
    }

    private void saveRecipe()
    {
        //get all the recipe info data
        SharedPreferences getPreferencesInfo = getActivity().getSharedPreferences("RecipeInfo", Activity.MODE_PRIVATE);
        String name = getPreferencesInfo.getString("Name", "");
        String prepTime = getPreferencesInfo.getString("PrepTime", "");
        String cookTime = getPreferencesInfo.getString("CookTime", "");
        String type = getPreferencesInfo.getString("Type", "");

        //get all the recipe ingredients data
        SharedPreferences getPreferences = getActivity().getSharedPreferences("RecipeIngred", Activity.MODE_PRIVATE);
        String ingredientOne = getPreferences.getString("IngredientOne", "");
        String ingredientTwo = getPreferences.getString("IngredientTwo", "");
        String ingredientThree = getPreferences.getString("IngredientThree", "");
        String ingredientFour = getPreferences.getString("IngredientFour", "");
        String ingredientFive = getPreferences.getString("IngredientFive", "");
        String ingredientSix = getPreferences.getString("IngredientSix", "");
        String ingredientSeven = getPreferences.getString("IngredientSeven", "");
        String ingredientEight = getPreferences.getString("IngredientEight", "");
        String ingredientNine = getPreferences.getString("IngredientNine", "");
        String ingredientTen = getPreferences.getString("IngredientTen", "");
        String ingredientEleven = getPreferences.getString("IngredientEleven", "");
        String ingredientTwelve = getPreferences.getString("IngredientTwelve", "");
        String ingredientThirteen = getPreferences.getString("IngredientThirteen", "");
        String ingredientFourteen = getPreferences.getString("IngredientFourteen", "");
        String ingredientFifteen = getPreferences.getString("IngredientFifteen", "");
        String ingredientSixteen = getPreferences.getString("IngredientSixteen", "");
        String ingredientSeventeen = getPreferences.getString("IngredientSeventeen", "");
        String ingredientEighteen = getPreferences.getString("IngredientEighteen", "");
        String ingredientNineteen = getPreferences.getString("IngredientNineteen", "");
        String ingredientTwenty = getPreferences.getString("IngredientTwenty", "");

        //get the recipe method data
        String recipeMethod = method.getText().toString();
        if(!name.equals("") && !prepTime.equals("") && !cookTime.equals("") && !ingredientOne.equals(""))
        {
            if (!recipeMethod.equals("")) {
                //store all the recipe information to the firebase database
                // save the input data to the Firebase database, setting a new Unique id each time a save is actioned
                String id = recipeDatabase.push().getKey();

                Recipe recipe = new Recipe(id, name, type, prepTime, cookTime, ingredientOne, ingredientTwo, ingredientThree, ingredientFour, ingredientFive,
                        ingredientSix, ingredientSeven, ingredientEight, ingredientNine, ingredientTen, ingredientEleven, ingredientTwelve, ingredientThirteen,
                        ingredientFourteen, ingredientFifteen, ingredientSixteen, ingredientSeventeen, ingredientEighteen, ingredientNineteen, ingredientTwenty,
                        recipeMethod);

                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                recipeDatabase.child(uid).child(id).setValue(recipe);

                Toast.makeText(getContext(), "Recipe " + name + " saved!", Toast.LENGTH_LONG).show();

                //clear the shared preferences
                getPreferencesInfo.edit().clear().apply();
                getPreferences.edit().clear().apply();

                reset();
                getActivity().finish();
                startActivity(new Intent(getContext(), Recipes.class));
            } else {
                Toast.makeText(getContext(), "Add a method first before saving!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), "Some information is missing go back and enter details again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset()
    {
        method.setText("");
    }
}