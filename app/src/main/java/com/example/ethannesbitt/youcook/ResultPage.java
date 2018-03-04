package com.example.ethannesbitt.youcook;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ResultPage extends AppCompatActivity
{
    private TextView title, publisher, link;
    private ImageView image;
    private Button viewRecipe;
    private WebView webView;

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

        viewRecipe = findViewById(R.id.view_full_recipe);

        Bundle resultBundle = getIntent().getExtras();

        if(resultBundle != null)
        {
            //getting all recipe details passed over from the previous activity on the list item click
            String theTitle = resultBundle.getString("title");
            String thePublisher = resultBundle.getString("publisher");
            String theLink = resultBundle.getString("source");
            String imageUrl = resultBundle.getString("image");

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
}
