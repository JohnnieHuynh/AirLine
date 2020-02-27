package com.example.airplaneapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airplaneapp.Database.AppDatabase;

@Entity(tableName = AppDatabase.RESERVE_TABLE)
public class ReserveLogs {

    @PrimaryKey(autoGenerate = true)
    public int reserveID;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "flightName")
    private String flightName;

    //Constructor
    public ReserveLogs(String username, String flightName) {
        this.username = username;
        this.flightName = flightName;
    }

    //Setters

    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    //Getters


    public int getReserveID() {
        return reserveID;
    }

    public String getUsername() {
        return username;
    }

    public String getFlightName() {
        return flightName;
    }

}
