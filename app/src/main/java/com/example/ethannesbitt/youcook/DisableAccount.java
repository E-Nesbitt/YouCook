package com.example.ethannesbitt.youcook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class DisableAccount extends AppCompatActivity
{
    //firebase variables
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    //delete button variable
    private Button deleteButton, exportButton;

    //variables for database
    private DatabaseReference databaseReference;
    private static final String RECIPES_FILE = "Recipes.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disable_account);

        //initialise firebase and the user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        //initialising database
        databaseReference = FirebaseDatabase.getInstance().getReference("recipes").child(uid);

        //initialising delete button
        deleteButton = findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are you sure you want to delete your account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deleteAccount();
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

        //initialising export button
        exportButton = findViewById(R.id.export_recipes_button);
        exportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                exportRecipes();
            }
        });
    }

    //method to delete account from the application, displays toast message on success or failure
    public void deleteAccount()
    {
        if(user != null)
        {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(DisableAccount.this, "Account has been deleted!",Toast.LENGTH_LONG).show();
                                finish();
                                Intent login = new Intent(DisableAccount.this, Login.class);
                                startActivity(login);
                            }
                            else
                            {
                                Toast.makeText(DisableAccount.this, "Something went wrong, account could not be deleted!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    //method used to export recipes to a txt file on the users phone, this needs to be implemented to external storage for real use however for demonstration purposes on the emulator it has been structured like this,
    //for real phones it would use the line Environment.getExternalStorageDirectory.getAbsolutePath(), a new file called "Recipes.txt" would then be created there
    private void exportRecipes() {
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //count used for toast message
                int i = 0;

                //default strings for the txt file so that the information displayed is meaningful
                String blank = "\n";
                String recipeName = "Recipe Name:\n";
                String pTime = "Prep Time:\n";
                String cTime = "Cook Time:\n";
                String type = "Course:\n";
                String ingredients = "Ingredients:\n";

                //Output text to the Recipes file called Recipes.txt, set to mode append so that each recipe can be added to the one file
                FileOutputStream fileOutput = null;
                try {
                    fileOutput = openFileOutput(RECIPES_FILE, MODE_APPEND);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //retrieve all data from the recipes database under the users specific user id
                for(DataSnapshot recipeSnapshot: dataSnapshot.getChildren())
                {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                    if(recipe != null)
                    {
                        i++;
                        //retrieving the information for the recipe
                        String name = recipe.getRecipeName();
                        String prepTime = recipe.getRecipePrepTime();
                        String cookTime = recipe.getRecipeCookTime();
                        String course = recipe.getRecipeType();
                        String ingredientOne = recipe.getIngredientOne();
                        String ingredientTwo = recipe.getIngredientTwo();
                        String ingredientThree = recipe.getIngredientThree();
                        String ingredientFour = recipe.getIngredientFour();
                        String ingredientFive = recipe.getIngredientFive();
                        String ingredientSix = recipe.getIngredientSix();
                        String ingredientSeven = recipe.getIngredientSeven();
                        String ingredientEight = recipe.getIngredientEight();
                        String ingredientNine = recipe.getIngredientNine();
                        String ingredientTen = recipe.getIngredientTen();
                        String ingredientEleven = recipe.getIngredientEleven();
                        String ingredientTwelve = recipe.getIngredientTwelve();
                        String ingredientThirteen = recipe.getIngredientThirteen();
                        String ingredientFourteen = recipe.getIngredientFourteen();
                        String ingredientFifteen = recipe.getIngredientFifteen();
                        String ingredientSixteen = recipe.getIngredientSixteen();
                        String ingredientSeventeen = recipe.getIngredientSeventeen();
                        String ingredientEighteen = recipe.getIngredientEighteen();
                        String ingredientNineteen = recipe.getIngredientNineteen();
                        String ingredientTwenty = recipe.getIngredientTwenty();

                        //carrying out check, if ingredient was left blank i.e. String = "  Unit:" do not add it to the txt file
                        String[] ingredientsList = new String[] { ingredientOne, ingredientTwo, ingredientThree, ingredientFour, ingredientFive, ingredientSix, ingredientSeven, ingredientEight,
                                ingredientNine, ingredientTen, ingredientEleven, ingredientTwelve, ingredientThirteen, ingredientFourteen, ingredientFifteen,
                                ingredientSixteen, ingredientSeventeen, ingredientEighteen, ingredientNineteen, ingredientTwenty};

                        try {
                            fileOutput = openFileOutput(RECIPES_FILE, MODE_APPEND);
                            fileOutput.write(blank.getBytes());
                            fileOutput.write(recipeName.getBytes());
                            fileOutput.write(name.getBytes());
                            fileOutput.write(blank.getBytes());
                            fileOutput.write(pTime.getBytes());
                            fileOutput.write(prepTime.getBytes());
                            fileOutput.write(blank.getBytes());
                            fileOutput.write(cTime.getBytes());
                            fileOutput.write(cookTime.getBytes());
                            fileOutput.write(blank.getBytes());
                            fileOutput.write(type.getBytes());
                            fileOutput.write(course.getBytes());
                            fileOutput.write(blank.getBytes());
                            fileOutput.write(ingredients.getBytes());
                            for(int j = 0; j <=19 ; j++)
                            {
                                if (ingredientsList[j].equals("  Unit:"))
                                {
                                    //do nothing
                                } else
                                    {
                                        try
                                        {
                                            fileOutput.write(ingredientsList[j].getBytes());
                                            fileOutput.write("\n".getBytes());
                                        } catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                            }
                            Toast.makeText(DisableAccount.this, "Recipe " + i + " " + name + ", is Saved!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }finally
                        {
                            try
                            {
                                if(fileOutput != null)
                                {
                                    fileOutput.close();
                                }
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                Toast.makeText(DisableAccount.this, "All Recipes Saved!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
