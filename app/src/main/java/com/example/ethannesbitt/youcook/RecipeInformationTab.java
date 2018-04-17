package com.example.ethannesbitt.youcook;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RecipeInformationTab extends Fragment
{
    //declaring variables needed for recipe information
    private Spinner prepTimeUnit, cookTimeUnit, course;
    private EditText name, prepTime, cookTime;
    private Button saveAndNext;

    //pager variable required for changing tabs on button click
    private ViewPager viewPager;

    public RecipeInformationTab()
    {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_information_tab, container, false);

        //initialising user inputs
        name = view.findViewById(R.id.recipeNameInput);
        prepTime = view.findViewById(R.id.prep_time_input);
        prepTimeUnit = view.findViewById(R.id.prep_time_unit);
        cookTime = view.findViewById(R.id.cooking_time_input);
        cookTimeUnit = view.findViewById(R.id.cook_time_unit);
        course = view.findViewById(R.id.recipeTypeInput);

        //initialising button and setting up its on click
        saveAndNext = view.findViewById(R.id.save_and_next_button_info);
        saveAndNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setSaveAndNext();
            }
        });

        //return the view inflated
        return view;
    }

    //method to save all the entered information to shared preferences and move the user to the next tab
    private void setSaveAndNext()
    {
        //takes inputs from user and sets them to strings
        String setName = name.getText().toString().trim();
        String setPrepTime = prepTime.getText().toString().trim() + " " + prepTimeUnit.getSelectedItem().toString();
        String setCookTime = cookTime.getText().toString().trim() + " " + cookTimeUnit.getSelectedItem().toString();
        String setType = course.getSelectedItem().toString();

        //checks to ensure all input fields have been filled in correctly
        if(!TextUtils.isEmpty(setName) && !prepTime.getText().toString().isEmpty() && !cookTime.getText().toString().isEmpty() && !TextUtils.isEmpty(setType))
        {
            SharedPreferences putPreferences = getActivity().getSharedPreferences("RecipeInfo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor = putPreferences.edit();
            preferenceEditor.putString("Name", setName);
            preferenceEditor.putString("PrepTime", setPrepTime);
            preferenceEditor.putString("CookTime", setCookTime);
            preferenceEditor.putString("Type", setType);
            preferenceEditor.apply();

            //move the user to the next tab
            viewPager = getActivity().findViewById(R.id.container_add_recipe);
            viewPager.setCurrentItem(1);
            Toast.makeText(getContext(), "Info saved, now fill in the ingredients!", Toast.LENGTH_SHORT).show();
            reset();
        }
        else
        {
            //Error toast message to let user know more data needs entered before save can be completed
            if(name.getText().toString().isEmpty())
            {
                name.setError("Enter a Recipe Name!");
            }
            if(prepTime.getText().toString().isEmpty())
            {
                prepTime.setError("Enter Prep Time!");
            }
            if(cookTime.getText().toString().isEmpty())
            {
                cookTime.setError("Enter a Recipe Name!");
            }
            Toast.makeText(getContext(), "Ensure all details are entered and try again!", Toast.LENGTH_SHORT).show();
        }
    }

    //method to reset all the input fields on moving to next tab
    private void reset()
    {
        name.setText("");
        prepTime.setText("");
        cookTime.setText("");
        course.setSelection(0);
    }
}