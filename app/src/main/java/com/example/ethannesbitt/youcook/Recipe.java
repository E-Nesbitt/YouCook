package com.example.ethannesbitt.youcook;

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

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void setRecipeMethod(String recipeMethod) {
        this.recipeMethod = recipeMethod;
    }
}
