package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShoppingList extends AppCompatActivity
{
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    ArrayList<String> ingredientList = null;
    ArrayAdapter<String> aAdapter = null;
    ListView lView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        drawerMenu = (DrawerLayout) findViewById(R.id.shoppinglist);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ingredientList = getIngredientsList(getApplicationContext());
        aAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        lView = (ListView) findViewById(R.id.list);
        lView.setAdapter(aAdapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long itemID)
            {
                String activeItem = ((TextView) view).getText().toString();
                if(activeItem.trim().equals(ingredientList.get(position).trim()))
                {
                    deleteItem(activeItem, position);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error trying to delete item", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(menuToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        int id = item.getItemId();

        if(id == R.id.action_add)
        {
            final EditText newItem = new EditText(this);

            //creating an alert prompt for user to enter the name of the item
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add New Item");
            builder.setView(newItem);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    ingredientList.add(standardCase(newItem.getText().toString()));
                    lView.setAdapter(aAdapter);
                    storeIngredientsList(ingredientList, getApplicationContext());
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
            builder.show();

            return true;
        }

        if(id == R.id.action_clear_all)
        {
            //creating an alert prompt to ask user if shopping list can be cleared or not
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear Shopping List?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    ingredientList.clear();
                    storeIngredientsList(ingredientList, getApplicationContext());
                    lView.setAdapter(aAdapter);
                    Toast.makeText(getApplicationContext(), "Shopping List has been cleared!", Toast.LENGTH_LONG).show();

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteItem(final String activeItem, final int position)
    {
        //Creating an alert prompt to ask user if item has been got
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Have you got " + activeItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //Removes the item and re-adds it with a tick beside it to show its been got
                ingredientList.remove(position);
                ingredientList.add(activeItem + " \u2714");
                storeIngredientsList(ingredientList, getApplicationContext());
                lView.setAdapter(aAdapter);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }

    //stores the shopping list in shared preferences so it can be accessed when app is reopened
    public static void storeIngredientsList(ArrayList currentList, Context context)
    {
        Set writeList = new HashSet(currentList);
        SharedPreferences putPreferences = context.getSharedPreferences("theArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor = putPreferences.edit();
        preferenceEditor.putStringSet("theArrayList", writeList);
        preferenceEditor.commit();
    }

    //opens the shopping list stored in shared preferences when app is opened
    public static ArrayList getIngredientsList(Context stored)
    {
        SharedPreferences getPreferences = stored.getSharedPreferences("theArrayValues", Activity.MODE_PRIVATE);
        Set temporarySet = new HashSet();
        temporarySet = getPreferences.getStringSet("theArrayList", temporarySet);
        return new ArrayList<>(temporarySet);
    }

    //setting a standard case to all items put into the shopping list, i.e. capital first letter
    public static String standardCase(String input)
    {
        if(input.isEmpty())
        {
            return input;
        }

        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

}
