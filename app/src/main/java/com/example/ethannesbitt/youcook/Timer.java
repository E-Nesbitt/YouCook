package com.example.ethannesbitt.youcook;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener
{

    //declaring variables for the timer
    private DrawerLayout drawerMenu;
    private ActionBarDrawerToggle menuToggle;
    private Handler timeHandler;
    private long time;
    private TextView countDownTime;
    private ToggleButton startStopToggle;
    private Button userInputButton;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //getting user data (initialising user)
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        //Drawer Navigation Menu set up
        drawerMenu = findViewById(R.id.timer);
        menuToggle = new ActionBarDrawerToggle(this, drawerMenu, R.string.open, R.string.close);
        drawerMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.timerdnav);
        navigationView.setNavigationItemSelectedListener(this);

        //initialising countdown time text view
        countDownTime = findViewById(R.id.timecount);

        //initialising toggle button and setting it to have onclick
        startStopToggle = findViewById(R.id.startstop);
        startStopToggle.setOnCheckedChangeListener(this);

        //setting up the time
        timeHandler = new Handler();
        time = 0;

        //user input button with alert dialogue to prompt user to enter time
        userInputButton = findViewById(R.id.userinputbutton);
        userInputButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final EditText userInput = new EditText(view.getContext());
                userInput.setInputType(InputType.TYPE_CLASS_NUMBER);//setting the pop up dialog user input to only accept numbers

                //Building the alert dialog using the user input edit text initialised above
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Enter the time in Minutes");
                builder.setView(userInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //error handling for user input, has to be a number value and cannot be null
                        try
                        {
                            time = Long.valueOf(userInput.getText().toString());
                        }
                        catch (NumberFormatException e)
                        {
                            Toast.makeText(getApplicationContext(), "No Value Entered!", Toast.LENGTH_SHORT).show();
                            dialogInterface.cancel();
                        }
                        if(time > 0)
                        {
                            //setting minutes entered and converting it to milliseconds
                            time = time * 60 * 1000;
                            countDownTime.setText(Long.valueOf(time / 1000 / 60).toString() + ":00");
                        }
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
            }
        });
    }

    @Override //toggle the navigation drawer menu
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override //on click for toggle so that it knows what to do when checked or unchecked i.e. start or stop
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
    {
        if(checked)
        {
            //if user has input a time take user input and start the running of the task
            if(time > 0)
            {
                //timer task to reduce time entered by 1 second each second and stop once it reaches 0
                final Runnable timerTask = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time = time - 1000;
                        if(time >= 0)
                        {
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(time)%60;
                            String mins = Long.valueOf(minutes).toString();
                            String secs = Long.valueOf(seconds).toString();
                            Log.d("Timermin", mins + " Minutes, " +  secs + " Seconds");
                            if(seconds >= 10)
                            {
                                countDownTime.setText(mins + ":" + secs);

                            }
                            else
                            {
                                countDownTime.setText(mins + ":0" + secs);
                            }
                            timeHandler.postDelayed(this, 1000);
                        }
                        else
                        {
                            startStopToggle.toggle();
                            countDownTime.setText("00:00");
                            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();

                            //new notification, sets off the sound notification and adds a notification to the notification bar with a stop button attached
                            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                            Intent intent = new Intent(Timer.this, Timer.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Timer.this, 0, intent, 0);

                            Notification notification = new Notification.Builder(Timer.this)
                                    .setSound(sound)
                                    .setSmallIcon(R.mipmap.youcookic_launcher)
                                    .setContentTitle("Timer Complete!")
                                    .setContentText("Check your food!")
                                    .addAction(R.mipmap.youcookic_launcher, "Open", pendingIntent)
                                    .addAction(0, "Stop", pendingIntent).build();

                            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify(0, notification);
                        }
                    }
                };
                timeHandler.postDelayed(timerTask, 1000);
                Toast.makeText(getApplicationContext(), "Timer Started!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                startStopToggle.toggle();
                if(time == 0)
                {
                    Toast.makeText(getApplicationContext(), "No Time Value Entered", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            //stop task and revert timer value back to zero
            time = 0;
            countDownTime.setText("00:00");
        }
    }

    @Override //on clicks for the different items in the navigation drawer menu
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

    //sign out method to allow user to sign out of account/app
    private void signOut()
    {
        mAuth.signOut();
    }
}
