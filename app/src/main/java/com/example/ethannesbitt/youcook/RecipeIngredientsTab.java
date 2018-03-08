package com.example.ethannesbitt.youcook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class RecipeIngredientsTab extends Fragment
{

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


    public RecipeIngredientsTab()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //All 20 Ingredients Units, spinners initialisation
        ingredientOneUnit = container.findViewById(R.id.ingredient_one_unit);
        ingredientTwoUnit = container.findViewById(R.id.ingredient_two_unit);
        ingredientThreeUnit = container.findViewById(R.id.ingredient_three_unit);
        ingredientFourUnit = container.findViewById(R.id.ingredient_four_unit);
        ingredientFiveUnit = container.findViewById(R.id.ingredient_five_unit);
        ingredientSixUnit = container.findViewById(R.id.ingredient_six_unit);
        ingredientSevenUnit = container.findViewById(R.id.ingredient_seven_unit);
        ingredientEightUnit= container.findViewById(R.id.ingredient_eight_unit);
        ingredientNineUnit = container.findViewById(R.id.ingredient_nine_unit);
        ingredientTenUnit = container.findViewById(R.id.ingredient_ten_unit);
        ingredientElevenUnit = container.findViewById(R.id.ingredient_eleven_unit);
        ingredientTwelveUnit = container.findViewById(R.id.ingredient_twelve_unit);
        ingredientThirteenUnit = container.findViewById(R.id.ingredient_thirteen_unit);
        ingredientFourteenUnit = container.findViewById(R.id.ingredient_fourteen_unit);
        ingredientFifteenUnit = container.findViewById(R.id.ingredient_fifteen_unit);
        ingredientSixteenUnit = container.findViewById(R.id.ingredient_sixteen_unit);
        ingredientSeventeenUnit = container.findViewById(R.id.ingredient_seventeen_unit);
        ingredientEighteenUnit = container.findViewById(R.id.ingredient_eighteen_unit);
        ingredientNineteenUnit = container.findViewById(R.id.ingredient_nineteen_unit);
        ingredientTwentyUnit = container.findViewById(R.id.ingredient_twenty_unit);

        //All 20 Ingredients Names, edit text initialisation
        ingredientOneName = container.findViewById(R.id.ingredient_one_name);
        ingredientTwoName = container.findViewById(R.id.ingredient_two_name);
        ingredientThreeName = container.findViewById(R.id.ingredient_three_name);
        ingredientFourName = container.findViewById(R.id.ingredient_four_name);
        ingredientFiveName = container.findViewById(R.id.ingredient_five_name);
        ingredientSixName = container.findViewById(R.id.ingredient_six_name);
        ingredientSevenName = container.findViewById(R.id.ingredient_seven_name);
        ingredientEightName = container.findViewById(R.id.ingredient_eight_name);
        ingredientNineName = container.findViewById(R.id.ingredient_nine_name);
        ingredientTenName = container.findViewById(R.id.ingredient_ten_name);
        ingredientElevenName = container.findViewById(R.id.ingredient_eleven_name);
        ingredientTwelveName = container.findViewById(R.id.ingredient_twelve_name);
        ingredientThirteenName = container.findViewById(R.id.ingredient_thirteen_name);
        ingredientFourteenName = container.findViewById(R.id.ingredient_fourteen_name);
        ingredientFifteenName = container.findViewById(R.id.ingredient_fifteen_name);
        ingredientSixteenName = container.findViewById(R.id.ingredient_sixteen_name);
        ingredientSeventeenName = container.findViewById(R.id.ingredient_seventeen_name);
        ingredientEighteenName = container.findViewById(R.id.ingredient_eighteen_name);
        ingredientNineteenName = container.findViewById(R.id.ingredient_nineteen_name);
        ingredientTwentyName = container.findViewById(R.id.ingredient_twenty_name);


        //All 20 Ingredients Amounts, edit text initialisation
        ingredientOneAmount = container.findViewById(R.id.ingredient_one_amount);
        ingredientTwoAmount = container.findViewById(R.id.ingredient_two_amount);
        ingredientThreeAmount = container.findViewById(R.id.ingredient_three_amount);
        ingredientFourAmount = container.findViewById(R.id.ingredient_four_amount);
        ingredientFiveAmount = container.findViewById(R.id.ingredient_five_amount);
        ingredientSixAmount = container.findViewById(R.id.ingredient_six_amount);
        ingredientSevenAmount = container.findViewById(R.id.ingredient_seven_amount);
        ingredientEightAmount = container.findViewById(R.id.ingredient_eight_amount);
        ingredientNineAmount = container.findViewById(R.id.ingredient_nine_amount);
        ingredientTenAmount = container.findViewById(R.id.ingredient_ten_amount);
        ingredientElevenAmount = container.findViewById(R.id.ingredient_eleven_amount);
        ingredientTwelveAmount = container.findViewById(R.id.ingredient_twelve_amount);
        ingredientThirteenAmount = container.findViewById(R.id.ingredient_thirteen_amount);
        ingredientFourteenAmount = container.findViewById(R.id.ingredient_fourteen_amount);
        ingredientFifteenAmount = container.findViewById(R.id.ingredient_fifteen_amount);
        ingredientSixteenAmount = container.findViewById(R.id.ingredient_sixteen_amount);
        ingredientSeventeenAmount = container.findViewById(R.id.ingredient_seventeen_amount);
        ingredientEighteenAmount = container.findViewById(R.id.ingredient_eighteen_amount);
        ingredientNineteenAmount = container.findViewById(R.id.ingredient_nineteen_amount);
        ingredientTwentyAmount = container.findViewById(R.id.ingredient_twenty_amount);

        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients_tab, container, false);
    }

}
