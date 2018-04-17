package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

//custom recipe list to hold the name and type of a recipe, to then be displayed in a listview to the user
public class RecipeList extends ArrayAdapter<Recipe>
{
    //vairables for list
    private Activity context;
    private List<Recipe> recipeList;

    public RecipeList(@NonNull Activity context, List<Recipe> recipeList)
    {
        super(context, R.layout.recipe_list, recipeList);
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View recipeItem = inflater.inflate(R.layout.recipe_list, null, true);

        //retrieving name and type of recipe from the database
        TextView name = recipeItem.findViewById(R.id.name);
        TextView type = recipeItem.findViewById(R.id.type);

        Recipe recipe = recipeList.get(position);

        //setting the name and type to the recipe retrieved from the database
        name.setText(recipe.getRecipeName());
        type.setText(recipe.getRecipeType());

        return recipeItem;
    }
}
