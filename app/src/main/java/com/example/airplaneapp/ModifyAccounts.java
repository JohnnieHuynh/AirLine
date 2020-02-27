package com.example.airplaneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.TransLogDAO;

import java.util.List;

public class ModifyAccounts extends AppCompatActivity {


    //Create Text View
    TextView mainScreen;

    //Create Buttons
    Button promptButt;
    Button backButt;

    //Table Objects
    TransLogDAO transObj;

    //List of Transactions
    List<TransLogs> listTrans;

    //User Choice
    enum ANSWER {
        YES,
        NO,
        ERROR
    };

    static ANSWER choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_accounts);

        //Connect TextView
        mainScreen = (TextView) findViewById(R.id.mainDisplayModAccScreen);

        //Connect Buttons
        promptButt = (Button) findViewById(R.id.promptButton);
        backButt = (Button) findViewById(R.id.backButton);

        //Create Instances
        transObj = AppDatabase.getInstanceDatabase(ModifyAccounts.this).getTransLogDAO();

        //Get list of tranactions
        listTrans = transObj.getTransLogs();


        //Create StringBuilder and put flights in
        StringBuilder stringBuilder = new StringBuilder();
        for (TransLogs tempTransaction: listTrans){
            stringBuilder.append(
                    "Username: " + tempTransaction.getUsername() + "\n"
                    + "Reservation Number: " + tempTransaction.getReservationNum() + "\n"
                    + "Flight Number: " + tempTransaction.getFlightNumber() + "\n"
                    + "Depart Location: " + tempTransaction.getFlightDepart() + "\n"
                    + "Arrival Location: " + tempTransaction.getFlightArrival() + "\n"
                    + "Departure Time: " + tempTransaction.getFlightDepartTime() + "\n"
                    + "Number of Tickets: " + tempTransaction.getNumTickets() + "\n"
                    + "Transaction Type: " + tempTransaction.getTransType() + "\n"
                    + "Total Price: " + tempTransaction.getTotalPrice() + "\n"
                    + "\n"
            );
        }

        //Scrolling TextView
        mainScreen.setMovementMethod(new ScrollingMovementMethod());

        //Set StringBuilder Text in TextView
        mainScreen.setText(stringBuilder.toString());

        //Prompt Button Clicked, and prompts with AlertBuilder
        promptButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyAccounts.this);
                builder.setCancelable(true);
                builder.setTitle("New Info");
                builder.setMessage("Would you like to enter a new flight?"
                );
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userChoice(ANSWER.YES);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userChoice(ANSWER.NO);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Back Button Clicked
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back to the Menu Screen!
                Intent intentModAccToMenu = new Intent(ModifyAccounts.this, Menu.class);
                startActivity(intentModAccToMenu);
            }
        });
    }

    //Enter New Flight Choice Function
    public void userChoice(ANSWER choice){
        if (choice == (ANSWER.YES)){
            //Change screen to new flight form
            Intent intentModAccToNewFli = new Intent(ModifyAccounts.this, NewFlightForm.class);
            startActivity(intentModAccToNewFli);

        }else if (choice == (ANSWER.NO)){
            //Back to Menu popup Msg
            Toast.makeText(ModifyAccounts.this, "Back to Menu", Toast.LENGTH_LONG).show();

            //Sent Back to Menu
            Intent intentModAccToMenu = new Intent(ModifyAccounts.this, Menu.class);
            startActivity(intentModAccToMenu);
        }
    }

}
