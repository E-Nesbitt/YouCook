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

public class RecipeIngredientsTab extends Fragment
{

    //declaring variavbes for each of the ingredient items, these will be concatenated into one string per ingredient.
    private Spinner ingredientOneUnit, ingredientTwoUnit, ingredientThreeUnit, ingredientFourUnit, ingredientFiveUnit,
            ingredientSixUnit, ingredientSevenUnit, ingredientEightUnit, ingredientNineUnit, ingredientTenUnit,
            ingredientElevenUnit, ingredientTwelveUnit, ingredientThirteenUnit, ingredientFourteenUnit, ingredientFifteenUnit,
            ingredientSixteenUnit, ingredientSeventeenUnit, ingredientEighteenUnit, ingredientNineteenUnit, ingredientTwentyUnit;

    private EditText ingredientOneName, ingredientTwoName, ingredientThreeName, ingredientFourName, ingredientFiveName,
            ingredientSixName, ingredientSevenName, ingredientEightName, ingredientNineName, ingredientTenName,
            ingredientElevenName, ingredientTwelveName, ingredientThirteenName, ingredientFourteenName, ingredientFifteenName,
            ingredientSixteenName, ingredientSeventeenName, ingredientEighteenName, ingredientNineteenName, ingredientTwentyName;

    private EditText ingredientOneAmount, ingredientTwoAmount, ingredientThreeAmount, ingredientFourAmount, ingredientFiveAmount,
            ingredientSixAmount, ingredientSevenAmount, ingredientEightAmount, ingredientNineAmount, ingredientTenAmount,
            ingredientElevenAmount, ingredientTwelveAmount, ingredientThirteenAmount, ingredientFourteenAmount, ingredientFifteenAmount,
            ingredientSixteenAmount, ingredientSeventeenAmount, ingredientEighteenAmount, ingredientNineteenAmount, ingredientTwentyAmount;

    //declaring variables needed for button and onclick
    private Button saveAndNext;
    private ViewPager viewPager;


    public RecipeIngredientsTab()
    {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients_tab, container, false);

        //All 20 Ingredients Names, edit text initialisation
        ingredientOneName = view.findViewById(R.id.ingredient_one_name);
        ingredientTwoName = view.findViewById(R.id.ingredient_two_name);
        ingredientThreeName = view.findViewById(R.id.ingredient_three_name);
        ingredientFourName = view.findViewById(R.id.ingredient_four_name);
        ingredientFiveName = view.findViewById(R.id.ingredient_five_name);
        ingredientSixName = view.findViewById(R.id.ingredient_six_name);
        ingredientSevenName = view.findViewById(R.id.ingredient_seven_name);
        ingredientEightName = view.findViewById(R.id.ingredient_eight_name);
        ingredientNineName = view.findViewById(R.id.ingredient_nine_name);
        ingredientTenName = view.findViewById(R.id.ingredient_ten_name);
        ingredientElevenName = view.findViewById(R.id.ingredient_eleven_name);
        ingredientTwelveName = view.findViewById(R.id.ingredient_twelve_name);
        ingredientThirteenName = view.findViewById(R.id.ingredient_thirteen_name);
        ingredientFourteenName = view.findViewById(R.id.ingredient_fourteen_name);
        ingredientFifteenName = view.findViewById(R.id.ingredient_fifteen_name);
        ingredientSixteenName = view.findViewById(R.id.ingredient_sixteen_name);
        ingredientSeventeenName = view.findViewById(R.id.ingredient_seventeen_name);
        ingredientEighteenName = view.findViewById(R.id.ingredient_eighteen_name);
        ingredientNineteenName = view.findViewById(R.id.ingredient_nineteen_name);
        ingredientTwentyName = view.findViewById(R.id.ingredient_twenty_name);

        //All 20 Ingredients Amounts, edit text initialisation
        ingredientOneAmount = view.findViewById(R.id.ingredient_one_amount);
        ingredientTwoAmount = view.findViewById(R.id.ingredient_two_amount);
        ingredientThreeAmount = view.findViewById(R.id.ingredient_three_amount);
        ingredientFourAmount = view.findViewById(R.id.ingredient_four_amount);
        ingredientFiveAmount = view.findViewById(R.id.ingredient_five_amount);
        ingredientSixAmount = view.findViewById(R.id.ingredient_six_amount);
        ingredientSevenAmount = view.findViewById(R.id.ingredient_seven_amount);
        ingredientEightAmount = view.findViewById(R.id.ingredient_eight_amount);
        ingredientNineAmount = view.findViewById(R.id.ingredient_nine_amount);
        ingredientTenAmount = view.findViewById(R.id.ingredient_ten_amount);
        ingredientElevenAmount = view.findViewById(R.id.ingredient_eleven_amount);
        ingredientTwelveAmount = view.findViewById(R.id.ingredient_twelve_amount);
        ingredientThirteenAmount = view.findViewById(R.id.ingredient_thirteen_amount);
        ingredientFourteenAmount = view.findViewById(R.id.ingredient_fourteen_amount);
        ingredientFifteenAmount = view.findViewById(R.id.ingredient_fifteen_amount);
        ingredientSixteenAmount = view.findViewById(R.id.ingredient_sixteen_amount);
        ingredientSeventeenAmount = view.findViewById(R.id.ingredient_seventeen_amount);
        ingredientEighteenAmount = view.findViewById(R.id.ingredient_eighteen_amount);
        ingredientNineteenAmount = view.findViewById(R.id.ingredient_nineteen_amount);
        ingredientTwentyAmount = view.findViewById(R.id.ingredient_twenty_amount);

        //All 20 Ingredients Units, spinners initialisation
        ingredientOneUnit = view.findViewById(R.id.ingredient_one_unit);
        ingredientTwoUnit = view.findViewById(R.id.ingredient_two_unit);
        ingredientThreeUnit = view.findViewById(R.id.ingredient_three_unit);
        ingredientFourUnit = view.findViewById(R.id.ingredient_four_unit);
        ingredientFiveUnit = view.findViewById(R.id.ingredient_five_unit);
        ingredientSixUnit = view.findViewById(R.id.ingredient_six_unit);
        ingredientSevenUnit = view.findViewById(R.id.ingredient_seven_unit);
        ingredientEightUnit= view.findViewById(R.id.ingredient_eight_unit);
        ingredientNineUnit = view.findViewById(R.id.ingredient_nine_unit);
        ingredientTenUnit = view.findViewById(R.id.ingredient_ten_unit);
        ingredientElevenUnit = view.findViewById(R.id.ingredient_eleven_unit);
        ingredientTwelveUnit = view.findViewById(R.id.ingredient_twelve_unit);
        ingredientThirteenUnit = view.findViewById(R.id.ingredient_thirteen_unit);
        ingredientFourteenUnit = view.findViewById(R.id.ingredient_fourteen_unit);
        ingredientFifteenUnit = view.findViewById(R.id.ingredient_fifteen_unit);
        ingredientSixteenUnit = view.findViewById(R.id.ingredient_sixteen_unit);
        ingredientSeventeenUnit = view.findViewById(R.id.ingredient_seventeen_unit);
        ingredientEighteenUnit = view.findViewById(R.id.ingredient_eighteen_unit);
        ingredientNineteenUnit = view.findViewById(R.id.ingredient_nineteen_unit);
        ingredientTwentyUnit = view.findViewById(R.id.ingredient_twenty_unit);

        saveAndNext = view.findViewById(R.id.save_and_next_button_ingred);
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

    private void setSaveAndNext()
    {
        //takes inputs from user and sets them to strings
        String ingredientOne = ingredientOneName.getText().toString().trim() + " " + ingredientOneAmount.getText().toString().trim() + " " + ingredientOneUnit.getSelectedItem().toString();
        String ingredientTwo = ingredientTwoName.getText().toString().trim() + " " + ingredientTwoAmount.getText().toString().trim() + " " + ingredientTwoUnit.getSelectedItem().toString();
        String ingredientThree = ingredientThreeName.getText().toString().trim() + " " + ingredientThreeAmount.getText().toString().trim() + " " + ingredientThreeUnit.getSelectedItem().toString();
        String ingredientFour = ingredientFourName.getText().toString().trim() + " " + ingredientFourAmount.getText().toString().trim() + " " + ingredientFourUnit.getSelectedItem().toString();
        String ingredientFive = ingredientFiveName.getText().toString().trim() + " " + ingredientFiveAmount.getText().toString().trim() + " " + ingredientFiveUnit.getSelectedItem().toString();
        String ingredientSix = ingredientSixName.getText().toString().trim() + " " + ingredientSixAmount.getText().toString().trim() + " " + ingredientSixUnit.getSelectedItem().toString();
        String ingredientSeven = ingredientSevenName.getText().toString().trim() + " " + ingredientSevenAmount.getText().toString().trim() + " " + ingredientSevenUnit.getSelectedItem().toString();
        String ingredientEight = ingredientEightName.getText().toString().trim() + " " + ingredientEightAmount.getText().toString().trim() + " " + ingredientEightUnit.getSelectedItem().toString();
        String ingredientNine = ingredientNineName.getText().toString().trim() + " " + ingredientNineAmount.getText().toString().trim() + " " + ingredientNineUnit.getSelectedItem().toString();
        String ingredientTen = ingredientTenName.getText().toString().trim() + " " + ingredientTenAmount.getText().toString().trim() + " " + ingredientTenUnit.getSelectedItem().toString();
        String ingredientEleven = ingredientElevenName.getText().toString().trim() + " " + ingredientElevenAmount.getText().toString().trim() + " " + ingredientElevenUnit.getSelectedItem().toString();
        String ingredientTwelve = ingredientTwelveName.getText().toString().trim() + " " + ingredientTwelveAmount.getText().toString().trim() + " " + ingredientTwelveUnit.getSelectedItem().toString();
        String ingredientThirteen = ingredientThirteenName.getText().toString().trim() + " " + ingredientThirteenAmount.getText().toString().trim() + " " + ingredientThirteenUnit.getSelectedItem().toString();
        String ingredientFourteen = ingredientFourteenName.getText().toString().trim() + " " + ingredientFourteenAmount.getText().toString().trim() + " " + ingredientFourteenUnit.getSelectedItem().toString();
        String ingredientFifteen = ingredientFifteenName.getText().toString().trim() + " " + ingredientFifteenAmount.getText().toString().trim() + " " + ingredientFifteenUnit.getSelectedItem().toString();
        String ingredientSixteen = ingredientSixteenName.getText().toString().trim() + " " + ingredientSixteenAmount.getText().toString().trim() + " " + ingredientSixteenUnit.getSelectedItem().toString();
        String ingredientSeventeen = ingredientSeventeenName.getText().toString().trim() + " " + ingredientSeventeenAmount.getText().toString().trim() + " " + ingredientSeventeenUnit.getSelectedItem().toString();
        String ingredientEighteen = ingredientEighteenName.getText().toString().trim() + " " + ingredientEighteenAmount.getText().toString().trim() + " " + ingredientEighteenUnit.getSelectedItem().toString();
        String ingredientNineteen = ingredientNineName.getText().toString().trim() + " " + ingredientNineteenAmount.getText().toString().trim() + " " + ingredientNineteenUnit.getSelectedItem().toString();
        String ingredientTwenty = ingredientTwentyName.getText().toString().trim() + " " + ingredientTwentyAmount.getText().toString().trim() + " " + ingredientTwentyUnit.getSelectedItem().toString();

        if(!TextUtils.isEmpty(ingredientOneName.getText().toString()))
        {
            SharedPreferences putPreferences = getActivity().getSharedPreferences("RecipeIngred", Activity.MODE_PRIVATE);
            SharedPreferences.Editor preferenceEditor = putPreferences.edit();
            preferenceEditor.putString("IngredientOne", ingredientOne);
            preferenceEditor.putString("IngredientTwo", ingredientTwo);
            preferenceEditor.putString("IngredientThree", ingredientThree);
            preferenceEditor.putString("IngredientFour", ingredientFour);
            preferenceEditor.putString("IngredientFive", ingredientFive);
            preferenceEditor.putString("IngredientSix", ingredientSix);
            preferenceEditor.putString("IngredientSeven", ingredientSeven);
            preferenceEditor.putString("IngredientEight", ingredientEight);
            preferenceEditor.putString("IngredientNine", ingredientNine);
            preferenceEditor.putString("IngredientTen", ingredientTen);
            preferenceEditor.putString("IngredientEleven", ingredientEleven);
            preferenceEditor.putString("IngredientTwelve", ingredientTwelve);
            preferenceEditor.putString("IngredientThirteen", ingredientThirteen);
            preferenceEditor.putString("IngredientFourteen", ingredientFourteen);
            preferenceEditor.putString("IngredientFifteen", ingredientFifteen);
            preferenceEditor.putString("IngredientSixteen", ingredientSixteen);
            preferenceEditor.putString("IngredientSeventeen", ingredientSeventeen);
            preferenceEditor.putString("IngredientEighteen", ingredientEighteen);
            preferenceEditor.putString("IngredientNineteen", ingredientNineteen);
            preferenceEditor.putString("IngredientTwenty", ingredientTwenty);
            preferenceEditor.apply();

            //move the user to the next tab
            viewPager = getActivity().findViewById(R.id.container_add_recipe);
            viewPager.setCurrentItem(2);
            Toast.makeText(getContext(), "Info saved, now fill in the method!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Error toast message to let user know more data needs entered before save can be completed
            Toast.makeText(getContext(), "Ensure at least one ingredient has been added!", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset()
    {
        //rName.setText("");
    }
}