package com.example.airplaneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.airplaneapp.Database.AppDatabase;
import com.example.airplaneapp.Database.FlightLogDAO;
import com.example.airplaneapp.Database.TicketLogDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Create Main Screen Button
    Button mainButt;

    //Create Account Tables
    TicketLogDAO ticketObj;

    //Create Flight Table
    FlightLogDAO flightObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Default Values
        //Instance of Database
        ticketObj = AppDatabase.getInstanceDatabase(MainActivity.this).getTicketLogDAO();
        flightObj = AppDatabase.getInstanceDatabase(MainActivity.this).getFlightLogDAO();


        //Default Accounts
        ticketObj.insertAcc(new TicketLogs("admin2", "admin2"));
        ticketObj.insertAcc(new TicketLogs("alice5", "csumb100"));
        ticketObj.insertAcc(new TicketLogs("brian77", "123ABC"));
        ticketObj.insertAcc(new TicketLogs("chris21", "CHRIS21"));

        //Default Flights
        flightObj.insertFli(new FlightLogs("Otter101", "Monterey", "Los Angeles", "10:00(AM)", 10, 150.00));
        flightObj.insertFli(new FlightLogs("Otter102", "Los Angeles", "Monterey", "1:00(PM)", 10, 150.00));
        flightObj.insertFli(new FlightLogs("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.50));
        flightObj.insertFli(new FlightLogs("Otter205", "Monterey", "Seattle", "3:00(PM)", 15, 150.00));
        flightObj.insertFli(new FlightLogs("Otter202", "Seattle", "Monterey", "2:00(PM)", 5, 200.50));

        //Connect Button
        mainButt = (Button) findViewById(R.id.buttMain);

        //Change screen to login screen
        mainButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLogInScreen = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intentToLogInScreen);
            }
        });
    }
}
