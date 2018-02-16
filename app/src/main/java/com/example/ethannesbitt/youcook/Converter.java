package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;

public class Converter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;

    private FirebaseAuth mAuth;

    private EditText inputValue;
    private EditText outputValue;
    private Button convertButton;
    private Button resetButton;
    private Spinner inputType;
    private Spinner outputType;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        //getting user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //initialising drawer menu
        drawerMenu = findViewById(R.id.converter);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.converterdnav);
        navigationView.setNavigationItemSelectedListener(this);

        //conversion editTexts and Buttons set up
        inputValue = findViewById(R.id.conversionInput);
        outputValue = findViewById(R.id.conversionOutput);
        inputType = findViewById(R.id.conversionInputType);
        outputType = findViewById(R.id.conversionOutputType);

        //convert button and on click to call correct methods at correct time
        convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    double input = Double.parseDouble(String.valueOf(inputValue.getText()));

                    if(inputType.getSelectedItem().equals("Kilogram - Kg") && outputType.getSelectedItem().equals("Kilogram - Kg"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Kilogram");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Kilograms");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Gram - g") && outputType.getSelectedItem().equals("Gram - g"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Gram");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Grams");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Pound - lb") && outputType.getSelectedItem().equals("Pound - lb"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Pound");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Pounds");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Ounce - oz") && outputType.getSelectedItem().equals("Ounce - oz"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Ounce");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Ounces");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Kilogram - Kg") && outputType.getSelectedItem().equals("Gram - g"))
                    {
                        kiloToGram(input);
                    }
                    else if(inputType.getSelectedItem().equals("Kilogram - Kg") && outputType.getSelectedItem().equals("Pound - lb"))
                    {
                        kiloToPound(input);
                    }
                    else if(inputType.getSelectedItem().equals("Kilogram - Kg") && outputType.getSelectedItem().equals("Ounce - oz"))
                    {
                        kiloToOunce(input);
                    }
                    else if(inputType.getSelectedItem().equals("Gram - g") && outputType.getSelectedItem().equals("Kilogram - Kg"))
                    {
                        gramToKilo(input);
                    }
                    else if(inputType.getSelectedItem().equals("Gram - g") && outputType.getSelectedItem().equals("Pound - lb"))
                    {
                        gramToPound(input);
                    }
                    else if(inputType.getSelectedItem().equals("Gram - g") && outputType.getSelectedItem().equals("Ounce - oz"))
                    {
                        gramToOunce(input);
                    }
                    else if(inputType.getSelectedItem().equals("Ounce - oz") && outputType.getSelectedItem().equals("Kilogram - Kg"))
                    {
                        ounceToKilo(input);
                    }
                    else if(inputType.getSelectedItem().equals("Ounce - oz") && outputType.getSelectedItem().equals("Gram - g"))
                    {
                        ounceToGram(input);
                    }
                    else if(inputType.getSelectedItem().equals("Ounce - oz") && outputType.getSelectedItem().equals("Pound - lb"))
                    {
                        ounceToPound(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pound - lb") && outputType.getSelectedItem().equals("Kilogram - Kg"))
                    {
                        poundToKilo(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pound - lb") && outputType.getSelectedItem().equals("Gram - g"))
                    {
                        poundToGram(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pound - lb") && outputType.getSelectedItem().equals("Ounce - oz"))
                    {
                        poundToOunce(input);
                    }

                }
                catch (NumberFormatException e)
                {
                    Toast.makeText(Converter.this, "Enter a value first!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //reset button and on click to reset the values back to default
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //calls reset method to return all to default state
                reset();
            }
        });
    }

    //allows drawer menu to be opened via a button in title bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            default:
                break;
        }
        return false;
    }

    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    //conversion methods 'to Grams'
    private String kiloToGram (double k)
    {
        double result = k * 1000;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Gram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Grams"));
        }
        return finalResult;
    }

    private String poundToGram (double p)
    {
        double result = p * 453.59237;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Gram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Grams"));
        }
        return finalResult;
    }

    private String ounceToGram (double o)
    {
        double result = o * 28.34952;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Gram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Grams"));
        }
        return finalResult;
    }



    //conversion methods 'to Kilos'
    private String gramToKilo (double g)
    {
        DecimalFormat decimalFormat2 = new DecimalFormat("00.000");
        double result = g * 0.001;
        String finalResult = decimalFormat2.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Kilogram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Kilograms"));
        }
        return finalResult;
    }

    private String poundToKilo (double p)
    {
        double result = p * 0.45359237;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Kilogram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Kilograms"));
        }
        return finalResult;
    }

    private String ounceToKilo (double o)
    {
        double result = o * 0.02834952;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Kilogram"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Kilograms"));
        }
        return finalResult;
    }



    //conversion methods 'to Pounds'
    private String kiloToPound (double k)
    {
        double result = k * 2.20462;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Pound"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Pounds"));
        }
        return finalResult;
    }

    private String gramToPound (double g)
    {
        double result = g * 0.00220462;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Pound"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Pounds"));
        }
        return finalResult;
    }

    private String ounceToPound (double o)
    {
        double result = o * 0.0625;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Pound"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Pounds"));
        }
        return finalResult;
    }



    //conversion methods 'to Ounces'
    private String kiloToOunce (double k)
    {
        double result = k * 35.2739619;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Ounce"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Ounces"));
        }
        return finalResult;
    }

    private String gramToOunce (double g)
    {
        double result = g * 0.0352739619;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Ounce"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Ounces"));
        }
        return finalResult;
    }

    private String poundToOunce (double p)
    {
        double result = p * 16;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Ounce"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Ounces"));
        }
        return finalResult;
    }

    //method used to reset all values back to their default values so a new conversion can be carried out quickly
    private void reset()
    {
        inputValue.setText("");
        outputValue.setText("");
        inputType.setSelection(0);
        outputType.setSelection(0);
    }

    //sign out method to allow user to sign out of account/app
    private void signOut()
    {
        mAuth.signOut();
    }

}
