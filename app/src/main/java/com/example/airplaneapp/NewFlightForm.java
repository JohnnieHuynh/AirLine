package com.example.airplaneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;

public class NewFlightForm extends AppCompatActivity {

    //Create EditTexts
    EditText flightNumEdit;
    EditText departEdit;
    EditText arrivalEdit;
    EditText departTimeEdit;
    EditText capacityEdit;
    EditText priceEdit;

    //Create Buttons
    Button enterButt;

    //Get Tables
    FlightLogDAO flightObj;

    //New FlightLog
    FlightLogs enteredFlight;

    //Enum
    enum ANSWER {
        YES,
        NO,
        ERROR
    }

    //Entered Values
    String name = "X";
    String depart = "X";
    String arrival = "X";
    String departTime = "X";
    int cap = 0;
    double price = 00.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight_form);

        //Get Instance
        flightObj = AppDatabase.getInstanceDatabase(NewFlightForm.this).getFlightLogDAO();

        //Connect EditTexts
        flightNumEdit = (EditText) findViewById(R.id.flightNumberEdit);
        departEdit = (EditText) findViewById(R.id.departLocEdit);
        arrivalEdit = (EditText) findViewById(R.id.arrivalEdit);
        departTimeEdit = (EditText) findViewById(R.id.departTimeEdit);
        capacityEdit = (EditText) findViewById(R.id.flightCapEdit);
        priceEdit = (EditText) findViewById(R.id.priceEdit);

        //Connect Buttons
        enterButt = (Button) findViewById(R.id.findButton);

        //Enter Button Pressed
        enterButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Text from fields
                name = flightNumEdit.getText().toString();
                depart = departEdit.getText().toString();
                arrival = arrivalEdit.getText().toString();
                departTime = departTimeEdit.getText().toString();
                cap = Integer.parseInt(capacityEdit.getText().toString());
                price = Double.parseDouble(priceEdit.getText().toString());

                //
                AlertDialog.Builder builder = new AlertDialog.Builder(NewFlightForm.this);
                builder.setCancelable(true);
                builder.setTitle("New Info");
                builder.setMessage(
                        //Display Flight Info
                        "Flight Name: " + name + "\n"
                        + "Departure Location: " + depart + "\n"
                        + "Arrival Location: " + arrival + "\n"
                        + "Departure Time: " + departTime + "\n"
                        + "Capacity: " + cap + "\n"
                        + "Price: " + String.format("%.2f", price) + "\n"
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
    }

    //Choice Function
    //Enter New Flight Choice Function
    public void userChoice(ANSWER choice){
        if (choice == (ANSWER.YES)){
            //Make new Flight
            enteredFlight = new FlightLogs(name, depart, arrival, departTime, cap, price);
            flightObj.insertFli(enteredFlight);

            //Popup Msg
            Toast.makeText(NewFlightForm.this, "Back to Menu", Toast.LENGTH_LONG).show();

            //Back to Menu
            Intent intentNewFliToMenu = new Intent(NewFlightForm.this, Menu.class);
            startActivity(intentNewFliToMenu);

        }else if (choice == (ANSWER.NO)){
            //Back to Menu popup Msg
            Toast.makeText(NewFlightForm.this, "Back to Menu", Toast.LENGTH_LONG).show();

            //Sent Back to Menu
            Intent intentNewFliToMenu = new Intent(NewFlightForm.this, Menu.class);
            startActivity(intentNewFliToMenu);
        }
    }
}
