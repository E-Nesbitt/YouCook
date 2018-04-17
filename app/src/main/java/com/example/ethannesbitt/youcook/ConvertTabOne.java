package com.example.ethannesbitt.youcook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;


public class ConvertTabOne extends Fragment
{
    //variables for conversions
    private EditText inputValue, outputValue;
    private Button convertButton, resetButton;
    private Spinner inputType, outputType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.fragment_convert_tab_one, container,false);

        //conversion editTexts and Buttons set up
        inputValue = view.findViewById(R.id.conversionInput);
        outputValue = view.findViewById(R.id.conversionOutput);
        inputType = view.findViewById(R.id.conversionInputType);
        outputType = view.findViewById(R.id.conversionOutputType);

        //convert button and on click to call correct methods at correct time
        convertButton = view.findViewById(R.id.convertButton);
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
                    if(inputValue.getText().toString().isEmpty())
                    {
                        inputValue.setError("Enter a value");
                        Toast.makeText(getActivity(), "Enter a value first!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //reset button and on click to reset the values back to default
        resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //calls reset method to return all to default state
                reset();
            }
        });

        return view;
    }

    //set the output format to be two decimal places
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
}