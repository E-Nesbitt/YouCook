package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShoppingList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    ArrayList<String> ingredientList = null;
    ArrayAdapter<String> aAdapter = null;
    ListView lView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialising drawer navigation
        drawerMenu = findViewById(R.id.shoppinglist);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.shoppinglistdnav);
        navigationView.setNavigationItemSelectedListener(this);

        //initialising shopping list
        ingredientList = getIngredientsList(getApplicationContext());
        aAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientList);
        lView = findViewById(R.id.list);
        lView.setAdapter(aAdapter);

        //setting the on click for the shopping list items so they can be deleted
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long itemID)
            {
                String activeItem = ((TextView) view).getText().toString();
                if(activeItem.trim().equals(ingredientList.get(position).trim()))
                {
                    //call the delete method on the clicked item
                    deleteItem(activeItem, position);
                }
                else
                {
                    //error handling if the item wont delete
                    Toast.makeText(getApplicationContext(), "Error trying to delete item", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //allows drawer menu to be opened via a button in title bar
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
            builder.setView(newItem);//passing the edit text to the alert prompt view
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
            builder.setTitle("Do you want to clear the Shopping List?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    ingredientList.clear();
                    storeIngredientsList(ingredientList, getApplicationContext());//storing the now clear shopping list to the shared preferences
                    lView.setAdapter(aAdapter);//update the list by re setting the adapter
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
        preferenceEditor.apply();//was using commit before try using apply method and see if it still works
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
        else
        {
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        }
    }

    //drawer menu navigation, on clicks for each item in the menu, finishes current activity and starts the new activity
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Intent mOptions;

        switch (item.getItemId())
        {
            case R.id.home : mOptions = new Intent(this, MainActivity.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.search : mOptions = new Intent(this, Search.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.shoppinglist : mOptions = new Intent(this, ShoppingList.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.recipes : mOptions = new Intent(this, Recipes.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.timer : mOptions = new Intent(this, Timer.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.converter : mOptions = new Intent(this, Converter.class);
                finish();
                startActivity(mOptions);
                break;

            case R.id.signout :
                signOut();
                break;

            case R.id.delete_account : mOptions = new Intent(this, DisableAccount.class);
                finish();
                startActivity(mOptions);
                break;

            default:
                break;
        }
        return false;
    }

    private void signOut()
    {
        mAuth.signOut();
    }

}
