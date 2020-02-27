package com.example.airplaneapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;
import com.example.airplaneapp.Database.TicketLogDAO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class LoginScreen extends AppCompatActivity {

    //Tags
    private static final String UTAG = "UsernameTAG";
    private static final String PTAG = "PasswordTAG";

    //Create Plain Text Var
    TextView usernameText;
    TextView passwordText;

    //Create Buttons
    Button loginButt;
    Button createAccButt;

    //Create Account Tables
    TicketLogDAO ticketObj;
    List<String> accounts;

    //Create Flight Table
    FlightLogDAO flightObj;

    //Create Transactions Table

    //Initial Condition
    int proceed = 1;
    int checkDB = 1;

    //Create Message to screen
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //Connect Text Boxes
        usernameText = (EditText) findViewById(R.id.usernameEdit);
        passwordText = (EditText) findViewById(R.id.passEdit);

        //Connect Buttons
        loginButt = (Button) findViewById(R.id.loginButton);
        createAccButt = (Button) findViewById(R.id.createAcc);

        //Instance of Database
        ticketObj = AppDatabase.getInstanceDatabase(LoginScreen.this).getTicketLogDAO();
        flightObj = AppDatabase.getInstanceDatabase(LoginScreen.this).getFlightLogDAO();


        //Read Values from username and password text fields
        loginButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Text from username field
                String userInput = usernameText.getText().toString();
                //Get Text from password field
                String passInput = passwordText.getText().toString();


                //Check if admin2
                if (userInput.equals("admin2") && passInput.equals("admin2")) {
                    proceed = 1;
                    checkDB = 0;
                }

                //Check if valid input: can only be >=1 num and >=3 letters
                if (!checkInput(userInput)){
                    toast = Toast.makeText(getApplicationContext(), "Bad Input for Username", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    checkDB = 0;
                    proceed = 0;
                }
                if (!checkInput(passInput)){
                    toast = Toast.makeText(getApplicationContext(), "Bad Input for Password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    checkDB = 0;
                    proceed = 0;
                }

                //If not admin check database for past users
                if (checkDB == 1){
                    //Check user input for validation
                    checkDatabaseForStr(userInput, passInput);
                }

                //Shared Pref Startup
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                //Save to Shared Pref for login
                if (proceed == 1){

                    editor.remove("username");
                    editor.remove("password");
                    editor.commit();

                    //This is to check if empty,but i didnt check for null -> will change later if there is time
                    editor.putString("username", userInput);
                    editor.putString("password", passInput);
                    editor.apply();
                }


                //Change to menu screen after validating
                if (proceed == 1){
                    Intent intentLoginToMenu = new Intent ( LoginScreen.this, Menu.class );
                    startActivity(intentLoginToMenu);
                }

            }
        });

        //Change to Create Account Activity Screen
        createAccButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoginToCreate = new Intent(LoginScreen.this, CreateAccount.class);
                startActivity(intentLoginToCreate);
            }
        });

    }

    //check Database
    public void checkDatabaseForStr (String user, String pass) {
        if( (ticketObj.getUserStr(user)) != null ){
            proceed = 1;
            //If username exists, check password
            if (((ticketObj.getUserStr(user)).getTicketPass()).equals(pass)){
                proceed = 1;
            }else{
                proceed = 0;
                toast = Toast.makeText(getApplicationContext(), "Account not found", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        }else{
            proceed = 0;
            toast = Toast.makeText(getApplicationContext(), "Account not found", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

    }

    //Check for valid username
    public boolean checkInput(String input){

        //Get size of username
        int size = input.length();

        //Create counters for letters and numbers
        int strCount = 0;
        int numCount = 0;

        //Check for number of letters and numbers //ASK TEACH
        for (int x = 0; x < size; x++){
            //Create a temp char var for each letter of a string
            char tempChar = input.charAt(x);

            //Check if Char is digit or letter
            if (Character.isDigit(tempChar)){
                numCount++;
            }else if (Character.isLetter(tempChar)){
                strCount++;
            }else{
                //This is an error, as the input is neither letter or digit
            }
        }

        //Password must have min 1 digit and min 3 letters
        if (numCount < 1 || strCount < 3){
            return false;
        }

        //else
        return true;
    }

}
