package com.example.ethannesbitt.youcook;

public class Recipe
{
    private String recipeId;
    private String recipeName;
    private String recipePrepTime;
    private String recipeCookTime;
    String recipeType;
    private String recipeIngredients;
    private String recipeMethod;

    public Recipe()
    {

    }

    public Recipe(String recipeId, String recipeName, String recipeType, String recipePrepTime, String recipeCookTime, String recipeIngredients, String recipeMethod)
    {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipePrepTime = recipePrepTime;
        this.recipeCookTime = recipeCookTime;
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

    public String getRecipeIngredients()
    {
        return recipeIngredients;
    }

    public String getRecipeMethod()
    {
        return recipeMethod;
    }


}
