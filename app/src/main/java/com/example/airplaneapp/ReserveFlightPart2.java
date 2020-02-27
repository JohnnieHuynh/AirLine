package com.example.airplaneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;
import com.example.airplaneapp.Database.ReserveLogDAO;
import com.example.airplaneapp.Database.TicketLogDAO;
import com.example.airplaneapp.Database.TransLogDAO;

import java.util.ArrayList;
import java.util.List;

public class ReserveFlightPart2 extends AppCompatActivity {

    //Table Objects
    TicketLogDAO ticketObj;
    FlightLogDAO flightObj;
    TransLogDAO transObj;
    ReserveLogDAO reserveObj;

    //List
    List<FlightLogs> possibleFlights = new ArrayList<>();

    //Transferred variables
    String sentDepart;
    String sentArrive;
    String sentTicketNumber;

    //TextView
    TextView mainDisp;
    EditText flightName;
    EditText numberOfTickets;

    //Create Buttons
    Button enterDataButt;
    Button backButt;
    Button finishButt;

    //PopUp Message
    Toast toast;

    //Shared Pref
    SharedPreferences sharedPref;

    //Condition
    int proceed = 1;
    double totalPrice = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_flight_part2);

        //Shared Pref
        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //Create Instances
        ticketObj = AppDatabase.getInstanceDatabase(ReserveFlightPart2.this).getTicketLogDAO();
        flightObj = AppDatabase.getInstanceDatabase(ReserveFlightPart2.this).getFlightLogDAO();
        transObj = AppDatabase.getInstanceDatabase(ReserveFlightPart2.this).getTransLogDAO();
        reserveObj = AppDatabase.getInstanceDatabase(ReserveFlightPart2.this).getReserveLogDAO();

        //Connect TextView
        mainDisp = (TextView) findViewById(R.id.mainDisplay);
        flightName = (EditText) findViewById(R.id.flightNumEdit);
        numberOfTickets = (EditText) findViewById(R.id.takenFlightsEdit);

        //Connect Buttons
        enterDataButt = (Button) findViewById(R.id.findButton);
        backButt = (Button) findViewById(R.id.signOutButton);
        finishButt = (Button) findViewById(R.id.sendTicketButton);


        //Get Extras
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            sentDepart = extras.getString("departValue");
            sentArrive = extras.getString("arrivalValue");
            sentTicketNumber = extras.getString("ticketNum");
        }

        //Turn Ticket#Str into Int
        int convertedTicketNum = Integer.parseInt(sentTicketNumber);

        //Check Database
        checkFlights(sentDepart, sentArrive, convertedTicketNum);

        //Print List to screen
        StringBuilder stringBuilder = new StringBuilder();
        for (FlightLogs presentedFlights: possibleFlights){
            stringBuilder.append(("Flight Name: " + presentedFlights.getFlightNumber() + "\n"
                    + "Departure Location: " + presentedFlights.getFlightDepart() + "\n"
                    + "Arrival Location: " + presentedFlights.getFlightArrival() + "\n"
                    + "Departure Time: " + presentedFlights.getFlightDepartTime() + "\n"
                    + "Number of Seats: " + presentedFlights.getFlightCapacity() + "\n"
                    + "Ticket Price: " + presentedFlights.getFlightPrice() + "\n"
                    + "\n" ));
        }

        //Scrolling TextView
        mainDisp.setMovementMethod(new ScrollingMovementMethod());

        //Set StringBuilder in TextView
        mainDisp.setText(stringBuilder.toString());

        //Back Button CLICKED
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRe2ToRe1 = new Intent(ReserveFlightPart2.this, ReserveFlight.class);
                startActivity(intentRe2ToRe1);
            }
        });

        //When Enter Button CLICKED
        enterDataButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Reset
                proceed = 1;

                //Get Data from Fields
                String tempFlightName = flightName.getText().toString();
                String convertStringNumber = (numberOfTickets.getText().toString());
                int tempNumTickets = Integer.parseInt(convertStringNumber);

                //Get Selected Flight
                FlightLogs tempFlight = flightObj.getFlightStr(tempFlightName);

                //Check if 7 tickets or more
                if (tempNumTickets > 7){
                    //Don't Go On
                    proceed = 4;
                    //Display Error Message through toast
                    toast = Toast.makeText(getApplicationContext(), "Limit of 7 ticket reserves", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

                //Check if there are available tickets
                if (tempNumTickets <= tempFlight.getFlightCapacity()){
                    proceed = 1;

                    //Calculate total price
                    double initialPrice = tempFlight.getFlightPrice();
                    totalPrice = initialPrice * tempNumTickets;
                }else{
                    proceed = 0;
                }

                //Confirm with User to continue
                if (proceed == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReserveFlightPart2.this);
                    builder.setCancelable(true);
                    builder.setTitle("Confirm Order?");
                    builder.setMessage("Flight Name: " + tempFlightName + "\n"
                            + "Departure Location: " + tempFlight.getFlightDepart() + "\n"
                            + "Arrival Location: " + tempFlight.getFlightArrival() + "\n"
                            + "Departure Time: " + tempFlight.getFlightDepartTime() + "\n"
                            + "Number of Tickets: " + tempNumTickets + "\n"
                            + "Total Price: $" + String.format("%.2f", totalPrice) + "\n"
                            + "To complete Transaction, Press 'Finish' After Acknowledging"
                            );
                    builder.setPositiveButton("Acknowledge",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

            }
        });

        //Finish Button Clicked
        finishButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset
                proceed = 1;

                //Get Data from Fields
                String tempFlightName = flightName.getText().toString();
                int tempNumTickets = Integer.parseInt(numberOfTickets.getText().toString());

                //Get Selected Flight
                FlightLogs tempFlight = flightObj.getFlightStr(tempFlightName);

                //Check if 7 tickets or more
                if (tempNumTickets > 7){
                    //Don't Go On
                    proceed = 4;
                    //Display Error Message through toast
                    toast = Toast.makeText(getApplicationContext(), "Limit of 7 ticket reserves", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

                //Check if there are available tickets
                if (tempNumTickets <= tempFlight.getFlightCapacity()){
                    proceed = 1;

                    //Remove num of tickets
                    tempFlight.setFlightCapacity(tempFlight.getFlightCapacity()-tempNumTickets);
                    flightObj.updateFli(tempFlight);

                    //Calculate total price
                    double initialPrice = tempFlight.getFlightPrice();
                    totalPrice = initialPrice * tempNumTickets;

                    //Popup message
                    toast = Toast.makeText(getApplicationContext(), "Tickets Booked!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else{
                    proceed = 0;
                }

                //Get Login Username from Shared Pref
                String activeUser = sharedPref.getString("username","");

                //Insert Each individual Reservation into reservation table by username
                for (int x = 0; x < tempNumTickets; x++){
                    ReserveLogs tempResHolder = new ReserveLogs( activeUser, tempFlight.getFlightNumber());
                    reserveObj.insertRes(tempResHolder);


                    //Insert into trans logs
                    transObj.insertTrans(new TransLogs(activeUser,
                            tempResHolder.getReserveID(),
                            tempFlight.getFlightNumber(),
                            tempFlight.getFlightDepart(),
                            tempFlight.getFlightArrival(),
                            tempFlight.getFlightDepartTime(),
                            tempNumTickets,
                            "Reservation",
                            totalPrice
                            ));
                }

                //Not Enough Tickets
                if (proceed == 0){
                    toast = Toast.makeText(getApplicationContext(), "Not Enough tickets", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }

            }
        });
    }

    //Check Database to find matching flights
    public void checkFlights(String depart, String arrive, int numTickets){

        //Get all flights
        List<FlightLogs> flight = flightObj.getFlightLogs();

        //Lopo through all flights
        for (FlightLogs tempFlight: flight){
            //Check if flight matches user input
            if (tempFlight.getFlightDepart().equals(depart)){
                //Check for arrival destination
                if (tempFlight.getFlightArrival().equals(arrive)){
                    //Check if number of tickets are capable
                    if (tempFlight.getFlightCapacity() >= numTickets){
                        possibleFlights.add(tempFlight);
                    }
                }
            }
        }
    }
}
