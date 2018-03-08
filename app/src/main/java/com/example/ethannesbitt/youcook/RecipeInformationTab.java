package com.example.ethannesbitt.youcook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class RecipeInformationTab extends Fragment
{

    private Spinner prepTimeUnit, cookTimeUnit, course;
    private EditText name, prepTime, cookTime;


    public RecipeInformationTab()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        name = container.findViewById(R.id.recipeNameInput);
        prepTime = container.findViewById(R.id.prep_time_input);
        prepTimeUnit = container.findViewById(R.id.prep_time_unit);
        cookTime = container.findViewById(R.id.cooking_time_input);
        cookTimeUnit = container.findViewById(R.id.cook_time_unit);
        course = container.findViewById(R.id.recipeTypeInput);

        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_information_tab, container, false);
    }


}
