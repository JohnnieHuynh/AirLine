package com.example.airplaneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;
import com.example.airplaneapp.Database.ReserveLogDAO;
import com.example.airplaneapp.Database.TicketLogDAO;
import com.example.airplaneapp.Database.TransLogDAO;

import java.util.ArrayList;
import java.util.List;

public class ReserveFlight extends AppCompatActivity {

    //Table Objects
    TicketLogDAO ticketObj;
    FlightLogDAO flightObj;
    TransLogDAO transObj;
    ReserveLogDAO reserveObj;

    //Create Text Fields
    EditText departSearch;
    EditText arrivalSearch;
    EditText numTicketsSearch;
    TextView flightDisplay;

    //Create Button
    Button searchButt;
    Button backButt;

    //List
    List<FlightLogs> possibleFlights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_flight);

        //Create Instances of tables and DB
        ticketObj = AppDatabase.getInstanceDatabase(ReserveFlight.this).getTicketLogDAO();
        flightObj = AppDatabase.getInstanceDatabase(ReserveFlight.this).getFlightLogDAO();
        transObj = AppDatabase.getInstanceDatabase(ReserveFlight.this).getTransLogDAO();
        reserveObj = AppDatabase.getInstanceDatabase(ReserveFlight.this).getReserveLogDAO();


        //Connect TextEdits
        departSearch = (EditText) findViewById(R.id.departLocationEdit);
        arrivalSearch = (EditText) findViewById(R.id.arrivalLocationEdit);
        numTicketsSearch = (EditText) findViewById(R.id.numTicketsEdit);

        //Connect Buttons
        searchButt = (Button) findViewById(R.id.searchButton);
        backButt = (Button) findViewById(R.id.signOutButton);

        //Back Button CLICKED
        backButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResToMenu = new Intent(ReserveFlight.this, Menu.class);
                startActivity(intentResToMenu);
            }
        });

        //Search Button CLICKED
        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempDepart = departSearch.getText().toString();
                String tempArrival = arrivalSearch.getText().toString();
                String tempTicketNum = numTicketsSearch.getText().toString();

                Intent intentResToRes2 = new Intent ( ReserveFlight.this, ReserveFlightPart2.class );

                //Send Data to Next Screen
                intentResToRes2.putExtra("departValue", tempDepart);
                intentResToRes2.putExtra("arrivalValue", tempArrival);
                intentResToRes2.putExtra("ticketNum", tempTicketNum);

                startActivity(intentResToRes2);

            }
        });
    }

    public void connectField(){
        String tempDepart = departSearch.getText().toString();
        String tempArrival = arrivalSearch.getText().toString();
        int tempTicketNum = Integer.parseInt(numTicketsSearch.getText().toString());

        //Check
        checkFlights(tempDepart, tempArrival, tempTicketNum);
    }

    //Check for flights
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

