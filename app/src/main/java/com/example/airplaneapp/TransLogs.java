package com.example.airplaneapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import com.example.airplaneapp.Database.AppDatabase;

//THERE ARE MULTIPLE DATE STUFF COMMENTED GO BACK AND FIX
@Entity(tableName = AppDatabase.TRANS_TABLE)
public class TransLogs {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "reservationNum")
    private int reservationNum;

    @ColumnInfo(name = "FlightNumber")
    private String flightNumber;

    @ColumnInfo(name = "DepartureLocation")
    private String flightDepart;

    @ColumnInfo(name = "ArrivalLocation")
    private String flightArrival;

    @ColumnInfo(name = "DepartureTime")
    private String flightDepartTime;

    @ColumnInfo(name = "NumberTickets")
    private int numTickets;

    @ColumnInfo(name = "TransactionType")
    private String transType;

    @ColumnInfo(name = "TotalAmount")
    private double totalPrice;

    //@ColumnInfo(name = "Date")
    //private Date date;

    //Constructor
    public TransLogs(String username, int reservationNum, String flightNumber, String flightDepart, String flightArrival, String flightDepartTime, int numTickets, String transType, double totalPrice){ //DATE date) {
        this.username = username;
        this.reservationNum = reservationNum;
        this.flightNumber = flightNumber;
        this.flightDepart = flightDepart;
        this.flightArrival = flightArrival;
        this.flightDepartTime = flightDepartTime;
        this.numTickets = numTickets;
        this.transType = transType;
        this.totalPrice = totalPrice;
        //this.date = date;
    }

    //Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setReservationNum(int reservationNum) {
        this.reservationNum = reservationNum;
    }

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

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setTotalPrice(double totalAmount) {
        this.totalPrice = totalAmount;
    }

    //Getters


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getReservationNum() {
        return reservationNum;
    }

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

    public int getNumTickets() {
        return numTickets;
    }

    public String getTransType() {
        return transType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /*
    public Date getDate() {
        return date;
    }

     */

}
