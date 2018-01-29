package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

    ArrayList<String> ingredientList = null;
    ArrayAdapter<String> aAdapter = null;
    ListView lView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

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
        int id = item.getItemId();

        if(id == R.id.action_add)
        {
            //code needed to add an item to the array list here**
            final EditText newItem = new EditText(this);

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

    public void deleteItem(String activeItem, final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + activeItem + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //rather than removing the item from the list i want to add a strikethrough version of the text
                ingredientList.remove(position);
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

    public static void storeIngredientsList(ArrayList currentList, Context context)
    {
        Set writeList = new HashSet(currentList);
        SharedPreferences putPreferences = context.getSharedPreferences("theArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor = putPreferences.edit();
        preferenceEditor.putStringSet("theArrayList", writeList);
        preferenceEditor.commit();
    }

    public static ArrayList getIngredientsList(Context stored)
    {
        SharedPreferences getPreferences = stored.getSharedPreferences("theArrayValues", Activity.MODE_PRIVATE);
        Set temporarySet = new HashSet();
        temporarySet = getPreferences.getStringSet("theArrayList", temporarySet);
        return new ArrayList<>(temporarySet);
    }

    public static String standardCase(String input)
    {
        if(input.isEmpty())
        {
            return input;
        }

        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

}
