package com.example.airplaneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;
import com.example.airplaneapp.Database.TicketLogDAO;
import com.example.airplaneapp.Database.TransLogDAO;

import org.w3c.dom.Text;

import java.util.List;

public class CreateAccount extends AppCompatActivity {

    //Create text boxes
    TextView createUserEdit;
    TextView createPassEdit;

    //Create Buttons
    Button startNewAccButton;

    //Conditions
    int proceed = 1;
    int checkDB = 1;
    int cond = 1;

    //Create toast to print pop-up messages
    Toast toast;

    //TicketLog Obj
    TicketLogDAO ticketObj;
    List<String> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Connect Text Edits
        createUserEdit = (TextView) findViewById(R.id.newUserEdit);
        createPassEdit = (TextView) findViewById(R.id.newPassEdit);

        //Connect Buttons
        startNewAccButton = (Button) findViewById(R.id.createButton);

        //Read values from Username and Password fields
        startNewAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Read Username
                String tempUsername = createUserEdit.getText().toString();

                //Read Password
                String tempPassword = createPassEdit.getText().toString();

                //Check if username is admin2
                if (!tempUsername.equals("admin2")){
                    proceed = 0;
                }

                //Check username and password if correct input
                if (checkInput(tempUsername) == false){
                    proceed = 0;
                }
                if (!checkInput(tempPassword) == false){
                    proceed = 0;
                }

                //Alternate Solution
                checkNum(tempUsername);

                //Get Database
                ticketObj = AppDatabase.getInstanceDatabase(CreateAccount.this).getTicketLogDAO();

                //Check DATABASE for user
                checkDatabaseForStr(tempUsername, tempPassword);

                //Outcomes
                if (proceed == 0){

                    //Input is bad format
                    toast = Toast.makeText(getApplicationContext(), "Input Error", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else if (proceed == 2){

                    //Username already in database
                    toast = Toast.makeText(getApplicationContext(), "Username already in Use", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else{
                    //Store data
                    ticketObj.insertAcc(new TicketLogs(tempUsername, tempPassword));

                    //Pop-up msg
                    toast = Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();

                    //Change to menu screen
                    Intent intentCreateToLogin = new Intent ( CreateAccount.this, LoginScreen.class );
                    startActivity(intentCreateToLogin);
                }


            }
        });
    }

    //check Database
    public void checkDatabaseForStr (String user, String pass) {
        if( (ticketObj.getUserStr(user)) != null ){
            proceed = 2;
        }else{
            proceed = 1;
        }

    }

    public void checkNum(String in){
        int count = 0;
        char[] chars = in.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                sb.append(c);
                count++;
            }
        }
        if (count < 1){
            proceed = 0;
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
            proceed = 0;
            return false;
        }

        //else
        return true;
    }
}
