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

/**
 * Created by EthanNesbitt on 12/02/2018.
 */

public class RecipeList extends ArrayAdapter<Recipe>
{
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

        TextView name = (TextView) recipeItem.findViewById(R.id.name);
        TextView type = (TextView) recipeItem.findViewById(R.id.type);

        Recipe recipe = recipeList.get(position);

        name.setText(recipe.getRecipeName());
        type.setText(recipe.getRecipeType());

        return recipeItem;
    }
}
