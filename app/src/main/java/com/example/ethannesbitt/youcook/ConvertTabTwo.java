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

public class ConvertTabTwo extends Fragment
{
    //variables for conversions
    private EditText inputValue, outputValue;
    private Button convertButton, resetButton;
    private Spinner inputType, outputType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_convert_tab_two, container, false);

        //conversion editTexts and Buttons set up
        inputValue = view.findViewById(R.id.fluidInput);
        outputValue = view.findViewById(R.id.fluidOutput);
        inputType = view.findViewById(R.id.fluidInputType);
        outputType = view.findViewById(R.id.fluidOutputType);

        //convert button and on click to call correct methods at correct time
        convertButton = view.findViewById(R.id.fluidConvertButton);
        convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    double input = Double.parseDouble(String.valueOf(inputValue.getText()));

                    if(inputType.getSelectedItem().equals("Litres - l") && outputType.getSelectedItem().equals("Litres - l"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Litre");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Litres");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Millilitres - ml") && outputType.getSelectedItem().equals("Millilitres - ml"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Millilitre");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Millilitres");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Pints - pt") && outputType.getSelectedItem().equals("Pints - pt"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Pint");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Pints");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Fluid Ounces - fl oz") && outputType.getSelectedItem().equals("Fluid Ounces - fl oz"))
                    {
                        if(input == 1)
                        {
                            outputValue.setText(String.valueOf(input) + " Fluid Ounce");
                        }
                        else
                        {
                            outputValue.setText(String.valueOf(input) + " Fluid Ounces");
                        }
                    }
                    else if(inputType.getSelectedItem().equals("Litres - l") && outputType.getSelectedItem().equals("Millilitres - ml"))
                    {
                        litresToMillilitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Litres - l") && outputType.getSelectedItem().equals("Pints - pt"))
                    {
                        litresToPints(input);
                    }
                    else if(inputType.getSelectedItem().equals("Litres - l") && outputType.getSelectedItem().equals("Fluid Ounces - fl oz"))
                    {
                        litresToFluidOunces(input);
                    }
                    else if(inputType.getSelectedItem().equals("Millilitres - ml") && outputType.getSelectedItem().equals("Litres - l"))
                    {
                        millilitresToLitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Millilitres - ml") && outputType.getSelectedItem().equals("Pints - pt"))
                    {
                        millilitresToPints(input);
                    }
                    else if(inputType.getSelectedItem().equals("Millilitres - ml") && outputType.getSelectedItem().equals("Fluid Ounces - fl oz"))
                    {
                        millilitresToFluidOunces(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pints - pt") && outputType.getSelectedItem().equals("Litres - l"))
                    {
                        pintsToLitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pints - pt") && outputType.getSelectedItem().equals("Millilitres - ml"))
                    {
                        pintsToMillilitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Pints - pt") && outputType.getSelectedItem().equals("Fluid Ounces - fl oz"))
                    {
                        pintsToFluidOunces(input);
                    }
                    else if(inputType.getSelectedItem().equals("Fluid Ounces - fl oz") && outputType.getSelectedItem().equals("Litres - l"))
                    {
                        fluidOuncesToLitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Fluid Ounces - fl oz") && outputType.getSelectedItem().equals("Millilitres - ml"))
                    {
                        fluidOuncesToMillilitres(input);
                    }
                    else if(inputType.getSelectedItem().equals("Fluid Ounces - fl oz") && outputType.getSelectedItem().equals("Pints - pt"))
                    {
                        fluidOuncesToPints(input);
                    }

                }
                catch (NumberFormatException e)
                {
                    if(inputValue.getText().toString().isEmpty())
                    {
                        inputValue.setError("Enter a value");
                        Toast.makeText(getActivity(), "Enter a value first!", Toast.LENGTH_LONG).show();
                    }                }
            }
        });

        //reset button and on click to reset the values back to default
        resetButton = view.findViewById(R.id.fluidResetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calls reset method to return all to default state
                reset();
            }
        });

        return view;
    }

    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    //conversion methods 'to Millilitres'
    private String litresToMillilitres (double l)
    {
        double result = l * 1000;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Millilitre"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Millilitres"));
        }
        return finalResult;
    }

    private String pintsToMillilitres (double p)
    {
        double result = p * 568.2612;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Millilitres"));
        return finalResult;
    }

    private String fluidOuncesToMillilitres (double fL)
    {
        double result = fL * 28.41306;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Millilitres"));
        return finalResult;
    }

    //conversion methods 'to Litres'
    private String millilitresToLitres (double m)
    {
        double result = m * 0.001;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Litre"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Litres"));
        }
        return finalResult;
    }

    private String pintsToLitres (double p)
    {
        double result = p * 0.5682612;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Litres"));
        return finalResult;
    }

    private String fluidOuncesToLitres (double fL)
    {
        double result = fL * 0.02841306;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Litres"));
        return finalResult;
    }

    //conversion methods "to Pints"
    private String millilitresToPints (double m)
    {
        double result = m * 0.001759754;
        String finalResult = decimalFormat.format(result);
        if(result == 1)
        {
            outputValue.setText(String.valueOf(finalResult + " Pint"));
        }
        else
        {
            outputValue.setText(String.valueOf(finalResult + " Pints"));
        }
        return finalResult;
    }

    private String litresToPints (double l)
    {
        double result = l * 1.759754;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Pints"));
        return finalResult;
    }

    private String fluidOuncesToPints (double fL)
    {
        double result = fL * 0.05;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Pints"));
        return finalResult;
    }

    //conversion methods "to FluidOunces"
    private String millilitresToFluidOunces (double m)
    {
        double result = m * 0.03519508;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Fluid Ounces"));
        return finalResult;
    }

    private String litresToFluidOunces (double l)
    {
        double result = l * 35.19508;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Fluid Ounces"));
        return finalResult;
    }

    private String pintsToFluidOunces (double p)
    {
        double result = p * 20;
        String finalResult = decimalFormat.format(result);
        outputValue.setText(String.valueOf(finalResult + " Fluid Ounces"));
        return finalResult;
    }

    //method used to reset all values back to their default values so a new conversion can be carried out quickly
    private void reset() {
        inputValue.setText("");
        outputValue.setText("");
        inputType.setSelection(0);
        outputType.setSelection(0);
    }
}
