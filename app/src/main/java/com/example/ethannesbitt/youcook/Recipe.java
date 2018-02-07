package com.example.ethannesbitt.youcook;

/**
 * Created by EthanNesbitt on 07/02/2018.
 */

public class Recipe
{
    String recipeId;
    String recipeName;
    String recipeType;
    String recipeIngredients;
    String recipeMethod;

    public Recipe()
    {

    }

    public Recipe(String recipeId, String recipeName, String recipeType, String recipeIngredients, String recipeMethod)
    {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeType = recipeType;
        this.recipeIngredients = recipeIngredients;
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

    public String getRecipeType()
    {
        return recipeType;
    }

    public String getRecipeIngredients()
    {
        return recipeIngredients;
    }

    public String getRecipeMethod()
    {
        return recipeMethod;
    }
}
