package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView searchOption;
    private CardView shoppingListOption;
    private CardView recipeOption;
    private CardView timerOption;
    private CardView converterOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link menu options to card views, defining each of the options
        searchOption = (CardView) findViewById(R.id.searchmenuoption);
        shoppingListOption = (CardView) findViewById(R.id.shoppinglistmenuoption);
        recipeOption = (CardView) findViewById(R.id.recipesmenuoption);
        timerOption = (CardView) findViewById(R.id.timermenuoption);
        converterOption = (CardView) findViewById(R.id.convertermenuoption);

        //Make options clickable
        searchOption.setOnClickListener(this);
        shoppingListOption.setOnClickListener(this);
        recipeOption.setOnClickListener(this);
        timerOption.setOnClickListener(this);
        converterOption.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        Intent options;

        switch (view.getId())
        {
            case R.id.searchmenuoption : options = new Intent(this, Search.class);
                startActivity(options);
                break;

            case R.id.shoppinglistmenuoption : options = new Intent(this, ShoppingList.class);
                startActivity(options);
                break;

            case R.id.recipesmenuoption : options = new Intent(this, Recipes.class);
                startActivity(options);
                break;

            case R.id.timermenuoption : options = new Intent(this, Timer.class);
                startActivity(options);
                break;

            case R.id.convertermenuoption : options = new Intent(this, Converter.class);
                startActivity(options);
                break;

            default:
                break;
        }
    }
}
