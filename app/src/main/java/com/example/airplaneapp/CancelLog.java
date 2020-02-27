package com.example.airplaneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CancelLog extends AppCompatActivity {

    //Tables
    TicketLogDAO ticketObj;
    FlightLogDAO flightObj;
    TransLogDAO transObj;
    ReserveLogDAO reserveObj;

    //Shared Pref
    SharedPreferences sharedPref;
    String activeUser;

    //TextView - Display
    TextView cancelDisplay;

    //List of reserves
    List <ReserveLogs> usersReserves = new ArrayList<>();
    List <TransLogs> usersTrans = new ArrayList<>();

    //Create EditText
    EditText enterIDEdit;

    //Create Button
    Button findButt;

    //Var for reserve that user wants to cancel
    int targetCancel;
    int targetID;

    enum ANSWER {
        YES,
        NO,
        ERROR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_log);

        //Shared Pref
        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        //Create Instances
        transObj = AppDatabase.getInstanceDatabase(CancelLog.this).getTransLogDAO();
        reserveObj = AppDatabase.getInstanceDatabase(CancelLog.this).getReserveLogDAO();

        //Connect TextViews
        cancelDisplay = (TextView) findViewById(R.id.displayCancel);
        enterIDEdit = (EditText) findViewById(R.id.IDEdit);

        //Connect Button
        findButt = (Button) findViewById(R.id.findButton);

        //Get user info from shared Pref AKA the active user
        activeUser = sharedPref.getString("username","");

        //Check Reserve flights for users tickets
        checkRes(activeUser);

        //Match flight names from reserves to flight names in flightslog
        checkFNames();

        //Create StringBuilder
        int x = 20;
        int y = 1;
        StringBuilder strBuilder = new StringBuilder();
        for (TransLogs presentedFlights : usersTrans){

            if (x == 20){
                x = presentedFlights.getId();
            }else{
                if (x != presentedFlights.getId()){
                    y = 1;
                }else{
                    y = 0;
                }
            }


            if (y == 1) {
                strBuilder.append(
                        "Reservation Number: " + presentedFlights.getId() + "\n"
                                + "Flight Number: " + presentedFlights.getFlightNumber() + "\n"
                                + "Departure Location: " + presentedFlights.getFlightDepart() + "\n"
                                + "Arrival Location: " + presentedFlights.getFlightArrival() + "\n"
                                + "Depart Time: " + presentedFlights.getFlightDepartTime() + "\n"
                                + "\n"
                );
            }

            //Increment x
            if (x == 20){
                x++;
            }else{
                x = presentedFlights.getId();
            }
        }

        //Scrolling TextView
        cancelDisplay.setMovementMethod(new ScrollingMovementMethod());

        //Set StrBuilding in Display
        cancelDisplay.setText(strBuilder.toString());


        //Find button clicked
        findButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get ID from Text
                targetID = Integer.parseInt(enterIDEdit.getText().toString());

                //Match correct transaction to the matching reservation in the already created userslist
                for (ReserveLogs targetRes : usersReserves){
                    for (TransLogs targetNum : usersTrans){
                        if (targetRes.getReserveID() == targetNum.getReservationNum()){
                            if (targetID == targetRes.getReserveID()){
                                targetCancel = targetRes.getReserveID();
                            }
                        }
                    }
                }

                //Ask user to confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(CancelLog.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Cancel?");
                builder.setMessage("Would you like to officially cancel this reservation?"
                );
                builder.setPositiveButton("Acknowledge",
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

    //Check reserve flights
    public void checkRes(String username){
        //Get All reservations
        List <ReserveLogs> allReserves = reserveObj.getReserveLogs();

        //Loop through reserves
        for (ReserveLogs tempRes : allReserves){
            //Check for matching username
            if ((tempRes.getUsername()).equals(username)){
                //Add to list of users reserves
                usersReserves.add(tempRes);
            }
        }
    }

    //Check for matching flight names
    public void checkFNames(){
        //Get all Flights
        List <TransLogs> allTrans = transObj.getTransLogs();

        //Loop through all flights
        for (TransLogs tempFlight : allTrans){
            //Loop through all User Reserves
            for (ReserveLogs tempReserve : usersReserves){
                //Find matching flight names to get matching flight info
                if (tempFlight.getUsername().equals(tempReserve.getUsername())){
                    //Add flights to users flights
                    usersTrans.add(tempFlight);
                }
            }
        }
    }

    //User choice to confirm or not
    public void userChoice(ANSWER choice){
        if (choice == ANSWER.YES){
            //Back to Menu popup Msg
            Toast.makeText(CancelLog.this, "Reservation Deleted, Going back to Menu", Toast.LENGTH_LONG).show();

            //Delete actual
            for (ReserveLogs resDel : usersReserves){
                if (resDel.getUsername() == activeUser){
                        ReserveLogs deleteOffical = reserveObj.getUserStr(activeUser);
                        reserveObj.deleteRes(deleteOffical);
                        usersReserves.remove(resDel.getReserveID());
                }
            }

            //Delete the display ones
            /*
            List<TransLogs> tempTList = transObj.getTransLogs();
            for (TransLogs delTrans : tempTList){
                if (delTrans.getUsername().equals(activeUser)) {
                    if (delTrans.getReservationNum() == targetCancel) {
                        usersTrans.remove(targetCancel);
                        transObj.deleteTrans(delTrans);
                    }
                }
            }

             */

            //
            List<TransLogs> tempList = transObj.getTransLogs();
            for (TransLogs delT : tempList){
                if (delT.getUsername().equals(activeUser)) {
                    if (delT.getReservationNum() == targetCancel) {
                        transObj.deleteTrans(delT);
                    }
                }
            }

            //Go back to Menu
            Intent intentCancelToMenu = new Intent (CancelLog.this, Menu.class);
            startActivity(intentCancelToMenu);

        }else if (choice == ANSWER.NO) {
            //Send back to Menu
            Toast.makeText(CancelLog.this, "Back to Menu", Toast.LENGTH_LONG).show();

            //Go back to Menu
            Intent intentCancelToMenu = new Intent (CancelLog.this, Menu.class);
            startActivity(intentCancelToMenu);
        }
    }
}
