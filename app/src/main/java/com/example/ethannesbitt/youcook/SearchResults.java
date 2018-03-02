package com.example.ethannesbitt.youcook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ethannesbitt.youcook.models.RecipeModel;
import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity
{
    //declaring variables needed for search results
    private String userInput;
    private ProgressDialog searchDialog;
    private ListView searchResults;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //testing setting up the back button in the app toolbar


        //initialising searching progress dialog to show user it is searching for recipes
        searchDialog = new ProgressDialog(this);
        searchDialog.setMessage("Searching...");
        searchDialog.show();

        //handler to show the progress dialog for 3000 milliseconds and then dismiss it
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable()
        {
            public void run()
            {
                searchDialog.dismiss();
            }
        }, 2000);

        //initialising list view for the results
        searchResults = findViewById(R.id.search_results);

        Bundle searchResultsBundle = getIntent().getExtras();

        if(searchResultsBundle != null)
        {
            //getting user input from search passed over from the previous activity on the search click
            String input = searchResultsBundle.getString("User Input");

            userInput = input;
            if(userInput.equals("&sort=t"))
            {
                this.setTitle("Trending Recipes");
            }
            else if(userInput.equals("&sort=r"))
            {
                this.setTitle("Highest Rated Recipes");
            }
            else
            {
                userInput = userInput.replaceAll(",\\s+", "%20");
            }
        }
        else
        {
            Toast.makeText(this, "SEARCH ERROR!", Toast.LENGTH_SHORT).show();
        }

        //running the JSONSearch using the food to fork api with users input placed into search query
        new SearchResults.JSONSearch().execute("http://food2fork.com/api/search?key=51bc38640178924d013b85854b8d7a52&q=" + userInput + "");
    }

    //trending api call
    //http://food2fork.com/api/search?key=51bc38640178924d013b85854b8d7a52&sort=t

    //highest rating api call
    //http://food2fork.com/api/search?key=51bc38640178924d013b85854b8d7a52&sort=r

    public class JSONSearch extends AsyncTask<String, String, List<RecipeModel>> {

        @Override
        protected List<RecipeModel> doInBackground(String... urls) {

            //initialising Url connection and BufferReader to be null, necessary for method to run successfully
            HttpURLConnection apiConnection = null;
            BufferedReader dataReader = null;
            try {
                URL apiUrl = new URL(urls[0]);
                apiConnection = (HttpURLConnection) apiUrl.openConnection();
                apiConnection.connect();

                InputStream jsonStream = apiConnection.getInputStream();

                dataReader = new BufferedReader(new InputStreamReader(jsonStream));
                StringBuilder builder = new StringBuilder();


                String line = "";
                while ((line = dataReader.readLine()) != null)
                {
                    builder.append((line));
                }

                //complete json received here at this stage
                String returnedJson = builder.toString();

                JSONObject count = new JSONObject(returnedJson);
                JSONArray recipes = count.getJSONArray("recipes");

                List<RecipeModel> recipeModelList = new ArrayList<>();

               for (int i = 0; i < recipes.length(); i++)
                {
                    JSONObject recipeObject = recipes.getJSONObject(i);
                    RecipeModel recipeModel = new RecipeModel();

                    //setting the recipes variables from the json object
                    recipeModel.setRecipeId(recipeObject.getString("recipe_id"));
                    recipeModel.setRecipeTitle(recipeObject.getString("title"));
                    recipeModel.setSourceUrl(recipeObject.getString("source_url"));
                    recipeModel.setPublisher(recipeObject.getString("publisher_url"));
                    recipeModel.setImageUrl(recipeObject.getString("image_url"));

                    //adding the json recipe object to the custom list
                    recipeModelList.add(recipeModel);
                }

                return recipeModelList;
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
                    if (dataReader != null)
                    {
                        dataReader.close();
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
        protected void onPostExecute(final List<RecipeModel> result)
        {
            super.onPostExecute(result);
            RecipeAdapter recipeAdapter = new RecipeAdapter(getApplicationContext(), R.layout.search_list_row, result);
            searchResults.setAdapter(recipeAdapter);
            //setting the json recipe data received from search to the custom list

            //code for setting up on click for list items
            searchResults.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    RecipeModel recipeModel = result.get(i);
                    Intent recipePage = new Intent(SearchResults.this, ResultPage.class);

                    //getting the clicked recipes details and setting them to be strings
                    String title = recipeModel.getRecipeTitle().toString();
                    String id = recipeModel.getRecipeId().toString();
                    String publisher = recipeModel.getPublisher().toString();
                    String source = recipeModel.getSourceUrl().toString();
                    String image = recipeModel.getImageUrl().toString();

                    //taking the string data for the recipe and passing it to the next activity using the bundle
                    recipePage.putExtra("title", title);
                    recipePage.putExtra("id", id);
                    recipePage.putExtra("publisher", publisher);
                    recipePage.putExtra("source", source);
                    recipePage.putExtra("image", image);

                    //start the activity with all recipe details added
                    startActivity(recipePage);
                }
            });
        }
    }

    //custom list adapter so that returned results can be displayed in a useful and easy to process manner
    public class RecipeAdapter extends ArrayAdapter
    {
        private LayoutInflater lInflater;
        private int resource;
        private List<RecipeModel> recipeModelList;

        public RecipeAdapter(@NonNull Context context, int resource, @NonNull List<RecipeModel> objects)
        {
            super(context, resource, objects);
            recipeModelList = objects;
            this.resource = resource;
            lInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            if(convertView == null)
            {
                convertView = lInflater.inflate(resource, null);
            }

            TextView recipeId;
            TextView publisher;
            TextView recipeTitle;
            //TextView sourceUrl;
            ImageView imageUrl;

            recipeId = convertView.findViewById(R.id.result_id);
            publisher = convertView.findViewById(R.id.result_publisher);
            recipeTitle = convertView.findViewById(R.id.result_title);
            //sourceUrl = convertView.findViewById(R.id.result_source);
            imageUrl = convertView.findViewById(R.id.result_image);

            //get the image url
            String image;
            image = recipeModelList.get(position).getImageUrl().toString();

            //use the picasso library to load the image from its url in the returned json
            Picasso.with(getContext()).load(image).into(imageUrl);

            recipeId.setText("Recipe ID: " + recipeModelList.get(position).getRecipeId());
            publisher.setText("Publisher: " + recipeModelList.get(position).getPublisher());
            recipeTitle.setText(recipeModelList.get(position).getRecipeTitle());
            //sourceUrl.setText("Source: " + recipeModelList.get(position).getSourceUrl());

            return convertView;
        }
    }

}