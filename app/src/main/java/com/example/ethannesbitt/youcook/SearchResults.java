package com.example.ethannesbitt.youcook;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchResults extends AppCompatActivity
{
    private String userInput;
    private TextView searchDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchDisplay = findViewById(R.id.search_display);

        Bundle searchResultsBundle = getIntent().getExtras();

        if(searchResultsBundle != null)
        {
            //getting user input from search passed over from the previous activity on the search click
            String input = searchResultsBundle.getString("User Input");

            userInput = input;
        }
        else
        {
            Toast.makeText(this, "SEARCH ERROR!", Toast.LENGTH_SHORT).show();
        }

        new SearchResults.JSONSearch().execute("http://food2fork.com/api/search?key=51bc38640178924d013b85854b8d7a52&q=" + userInput + "");


    }

    public class JSONSearch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            HttpURLConnection apiConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(urls[0]);
                apiConnection = (HttpURLConnection) url.openConnection();
                apiConnection.connect();

                InputStream stream = apiConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();


                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append((line));

                }

                //complete json received here at this stage
                String returnedJson = buffer.toString();

                JSONObject parent = new JSONObject(returnedJson);
                JSONArray pArray = parent.getJSONArray("recipes");

                StringBuffer allDataBuffer = new StringBuffer();
//                for (int i = 0; i < pArray.length(); i++)
                for (int i = 0; i < 5; i++)
                {
                    JSONObject finalObj = pArray.getJSONObject(i);


                    String recipeTitle = finalObj.getString("title");
                    String recipeLink = finalObj.getString("source_url");

                    allDataBuffer.append(recipeTitle + " - " + recipeLink + "\n");
                }
                JSONObject finalObj = pArray.getJSONObject(0);

                return allDataBuffer.toString();

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (apiConnection != null)
                {
                    apiConnection.disconnect();
                }
                try
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            searchDisplay.setText(result);
        }
    }
}
