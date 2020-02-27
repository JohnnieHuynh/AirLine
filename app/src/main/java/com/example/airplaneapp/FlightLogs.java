package com.example.airplaneapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airplaneapp.Database.AppDatabase;

@Entity(tableName = AppDatabase.FLIGHT_TABLE)
public class FlightLogs {

    @PrimaryKey(autoGenerate = true)
    public int flightKeyID;

    @ColumnInfo(name = "FlightNumber")
    private String flightNumber;

    @ColumnInfo(name = "DepartureLocation")
    private String flightDepart;

    @ColumnInfo(name = "ArrivalLocation")
    private String flightArrival;

    @ColumnInfo(name = "DepartureTime")
    private String flightDepartTime;

    @ColumnInfo(name = "FlightCapacity")
    private int flightCapacity;

    @ColumnInfo(name = "FlightPrice")
    private double flightPrice;

    //Constructor
    public FlightLogs ( String flightNumber, String flightDepart, String flightArrival, String flightDepartTime, int flightCapacity, double flightPrice ) {
        this.flightNumber = flightNumber;
        this.flightDepart = flightDepart;
        this.flightArrival = flightArrival;
        this.flightDepartTime = flightDepartTime;
        this.flightCapacity = flightCapacity;
        this.flightPrice = flightPrice;
    }

    //Setters
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setFlightDepart(String flightDepart) {
        this.flightDepart = flightDepart;
    }

    public void setFlightArrival(String flightArrival) {
        this.flightArrival = flightArrival;
    }

    public void setFlightDepartTime(String flightDepartTime) {
        this.flightDepartTime = flightDepartTime;
    }

    public void setFlightCapacity(int flightCapacity) {
        this.flightCapacity = flightCapacity;
    }

    public void setFlightPrice(double flightPrice) {
        this.flightPrice = flightPrice;
    }

    //Getters
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightDepart() {
        return flightDepart;
    }

    public String getFlightArrival() {
        return flightArrival;
    }

    public String getFlightDepartTime() {
        return flightDepartTime;
    }

    public int getFlightCapacity() {
        return flightCapacity;
    }

    public double getFlightPrice() {
        return flightPrice;
    }
}
