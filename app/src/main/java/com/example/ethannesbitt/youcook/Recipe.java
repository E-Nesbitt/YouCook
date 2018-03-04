package com.example.ethannesbitt.youcook;

public class Recipe
{
    private String recipeId, recipeName, recipePrepTime, recipeCookTime, ingredientOne, ingredientTwo, ingredientThree, ingredientFour,
            ingredientFive, ingredientSix, ingredientSeven, ingredientEight, ingredientNine, ingredientTen,recipeMethod;
    String recipeType;


    public Recipe()
    {

    }

    public Recipe(String recipeId, String recipeName, String recipeType, String recipePrepTime, String recipeCookTime, String ingredientOne,  String ingredientTwo,
                  String ingredientThree, String ingredientFour, String ingredientFive, String ingredientSix, String ingredientSeven, String ingredientEight,
                  String ingredientNine, String ingredientTen, String recipeMethod)
    {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipePrepTime = recipePrepTime;
        this.recipeCookTime = recipeCookTime;
        this.recipeType = recipeType;
        this.ingredientOne = ingredientOne;
        this.ingredientTwo = ingredientTwo;
        this.ingredientThree = ingredientThree;
        this.ingredientFour= ingredientFour;
        this.ingredientFive = ingredientFive;
        this.ingredientSix = ingredientSix;
        this.ingredientSeven = ingredientSeven;
        this.ingredientEight = ingredientEight;
        this.ingredientNine = ingredientNine;
        this.ingredientTen = ingredientTen;
        this.recipeMethod = recipeMethod;
    }

    public String getRecipeId()
    {
        return recipeId;
    }

    public String getRecipeName()
    {
        return recipeName;
    }

    public String getRecipePrepTime()
    {
        return recipePrepTime;
    }

    public String getRecipeCookTime()
    {
        return recipeCookTime;
    }

    public String getRecipeType()
    {
        return recipeType;
    }

    public String getIngredientOne() {
        return ingredientOne;
    }

    public String getIngredientTwo() {
        return ingredientTwo;
    }

    public String getIngredientThree() {
        return ingredientThree;
    }

    public String getIngredientFour() {
        return ingredientFour;
    }

    public String getIngredientFive() {
        return ingredientFive;
    }

    public String getIngredientSix() {
        return ingredientSix;
    }

    public String getIngredientSeven() {
        return ingredientSeven;
    }

    public String getIngredientEight() {
        return ingredientEight;
    }

    public String getIngredientNine() {
        return ingredientNine;
    }

    public String getIngredientTen() {
        return ingredientTen;
    }

    public String getRecipeMethod()
    {
        return recipeMethod;
    }


}
