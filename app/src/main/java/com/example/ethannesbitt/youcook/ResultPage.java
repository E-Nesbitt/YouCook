package com.example.ethannesbitt.youcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ResultPage extends AppCompatActivity
{
    private TextView title, publisher, link, ingredientsList;
    private ImageView image;
    private Button viewRecipe;
    private WebView webView;
    private ArrayList<String> ingredientList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        //webview used to display full recipe
        webView = findViewById(R.id.web_view_display);

        title = findViewById(R.id.recipe_title);
        publisher = findViewById(R.id.recipe_publisher);
        link = findViewById(R.id.recipe_link);
        image = findViewById(R.id.image_result_single);
        ingredientsList = findViewById(R.id.recipe_ingredient_list);

        viewRecipe = findViewById(R.id.view_full_recipe);

        //initialising shopping list
        ingredientList = getIngredientsList(getApplicationContext());

        Bundle resultBundle = getIntent().getExtras();

        if(resultBundle != null)
        {
            //getting all recipe details passed over from the previous activity on the list item click
            String theTitle = resultBundle.getString("title");
            String thePublisher = resultBundle.getString("publisher");
            String theLink = resultBundle.getString("source");
            String imageUrl = resultBundle.getString("image");
            String id = resultBundle.getString("id");
            new ResultPage.IngredientRetrieval().execute("http://food2fork.com/api/get?key=51bc38640178924d013b85854b8d7a52&rId=" + id);

            //using a check to make the publisher a bit more human readable, splits the string to only return the name of the source rather than the whole website link
            if(thePublisher.contains("www."))
            {
                int index = thePublisher.indexOf("w.") + 2;
                int index2 = thePublisher.indexOf(".co");
                thePublisher = thePublisher.substring(index, index2);
                thePublisher = thePublisher.substring(0, 1).toUpperCase() + thePublisher.substring(1);
            }
            else
            {
                int index = thePublisher.lastIndexOf("/") + 1;
                int index2 = thePublisher.indexOf(".co");
                thePublisher = thePublisher.substring(index, index2);
                thePublisher = thePublisher.substring(0, 1).toUpperCase() + thePublisher.substring(1);
            }

            //setting text views to have the information from the selected recipe in the DB
            title.setText(theTitle);
            publisher.setText(thePublisher);
            link.setText(theLink);

            //use the picasso library to load the image from its url in the returned json
            Picasso.with(image.getContext()).load(imageUrl).into(image);
        }
        else
        {
            //Error handling if something goes wrong when retrieving the data from the list
            title.setText("Error Loading Recipe!");
            Toast.makeText(this, "LOADING ERROR!", Toast.LENGTH_SHORT).show();
        }

        //view the full recipe within the app using a webview
        viewRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.loadUrl(link.getText().toString());
                webView.setWebViewClient(new WebViewClient());
            }
        });

        //copy the link so that you can easily send it to a friend to checkout
        link.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClipboardManager cManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("link", link.getText());
                cManager.setPrimaryClip(cData);
                Toast.makeText(ResultPage.this, "Link has been copied!", Toast.LENGTH_SHORT).show();
            }
        });

        ingredientsList.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view)
            {
                //Creating an alert prompt to ask user if item has been got
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultPage.this);
                builder.setTitle("Add all ingredients to the shopping list?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String itemsList = ingredientsList.getText().toString();
                        String[] items = itemsList.split("\n");

                        for(int j = 0; j < items.length ; j++)
                        {
                            ingredientList.add(items[j]);
                        }

                        storeIngredientsList(ingredientList, getApplicationContext());

                        Toast.makeText(ResultPage.this, "Ingredients Added Successfully to your Shopping List!", Toast.LENGTH_LONG).show();
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
        });
    }

    //stores the shopping list in shared preferences so it can be accessed when app is reopened
    public static void storeIngredientsList (ArrayList currentList, Context context)
    {
        Set writeList = new HashSet(currentList);
        SharedPreferences putPreferences=context.getSharedPreferences("shoppingListValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor preferenceEditor=putPreferences.edit();
        preferenceEditor.putStringSet("theShoppingList", writeList);
        preferenceEditor.apply();
    }

    //opens the shopping list stored in shared preferences when app is opened
    public static ArrayList getIngredientsList(Context stored)
    {
        SharedPreferences getPreferences = stored.getSharedPreferences("shoppingListValues", Activity.MODE_PRIVATE);
        Set temporarySet = new HashSet();
        temporarySet = getPreferences.getStringSet("theShoppingList", temporarySet);
        return new ArrayList<>(temporarySet);
    }

    @Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    public class IngredientRetrieval extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... url)
        {
            String ingredients = null;

            HttpURLConnection apiGetConnection = null;
            BufferedReader dataGetReader = null;
            try
            {
                URL apiGetUrl = new URL(url[0]);
                apiGetConnection = (HttpURLConnection) apiGetUrl.openConnection();
                apiGetConnection.connect();

                InputStream jsonGetStream = apiGetConnection.getInputStream();

                dataGetReader = new BufferedReader(new InputStreamReader(jsonGetStream));
                StringBuilder builder = new StringBuilder();


                String line = "";
                while ((line = dataGetReader.readLine()) != null) {
                    builder.append((line));
                }

                String returnedJson = builder.toString();

                JSONObject recipe = new JSONObject(returnedJson).getJSONObject("recipe");
                ingredients = recipe.getString("ingredients");

                return ingredients;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (apiGetConnection != null) {
                    apiGetConnection.disconnect();
                }
                try {
                    if (dataGetReader != null) {
                        dataGetReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return ingredients;
            }
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
            if (result == null)
            {
                result = "Error getting ingredients";
            }
            ingredientsList.setText("");

            String[] ingredientsArray;
            try
            {
                result = result.replaceAll("\"", "\"");
                result = result.replace(",", "");
                result = result.replaceAll("\\[", "");
                result = result.replaceAll("\\]", "");
                result = result.replaceAll("(?m)^\\s", "");

                ingredientsArray = result.split("\"");

                for(int i = 0; i< ingredientsArray.length; i++)
                {
                    if(ingredientsArray[i].equals(""))
                    {
                        //removes the blank lines from the returned json
                    }
                    else
                    {
                        ingredientsList.append(ingredientsArray[i] + "\n");
                    }
                }

            }catch (NullPointerException e)
            {
                Toast.makeText(ResultPage.this, "null pointer", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
