package com.example.ethannesbitt.youcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Ethan Nesbitt 19/11/2017
 *
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent startLogin = new Intent(getApplicationContext(), Login.class);
                    startActivity(startLogin);
                    finish();
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start();

    }
}
