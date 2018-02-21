package com.example.ethannesbitt.youcook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ResultPage extends AppCompatActivity
{
    private TextView title, id, publisher, link;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        title = findViewById(R.id.recipe_title);
        id = findViewById(R.id.recipe_id);
        publisher = findViewById(R.id.recipe_publisher);
        link = findViewById(R.id.recipe_link);
        image = findViewById(R.id.image_result_single);

        Bundle resultBundle = getIntent().getExtras();

        if(resultBundle != null)
        {
            //getting all recipe details passed over from the previous activity on the list item click
            String theTitle = resultBundle.getString("title");
            String theId = resultBundle.getString("id");
            String thePublisher = resultBundle.getString("publisher");
            String theLink = resultBundle.getString("source");
            String imageUrl = resultBundle.getString("image");

            //setting text views to have the information from the selected recipe in the DB
            title.setText(theTitle);
            id.setText(theId);
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


    }
}
