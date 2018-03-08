package com.example.ethannesbitt.youcook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecipeMethodTab extends Fragment
{
    private EditText method;

    private Button saveButton;

    public RecipeMethodTab()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        method = container.findViewById(R.id.recipeMethodInput);
        saveButton = container.findViewById(R.id.saveButton);

//        saveButton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                saveRecipe();
//            }
//        });

        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_method_tab, container, false);
    }

    private void saveRecipe()
    {
        String recipeMethod = method.getText().toString();

    }

}
