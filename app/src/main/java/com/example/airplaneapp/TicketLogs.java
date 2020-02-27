package com.example.airplaneapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.airplaneapp.Database.AppDatabase;

import java.util.List;

@Entity(tableName = AppDatabase.TICKETLOG_TABLE)
public class TicketLogs {

    //Ticket Account Information
    @PrimaryKey(autoGenerate = true)
    public int accountID;

    @ColumnInfo(name = "username")
    private String ticketUser;

    @ColumnInfo(name = "password")
    private String ticketPass;

    //Constructor
    public TicketLogs(String ticketUser, String ticketPass){
        this.ticketUser = ticketUser;
        this.ticketPass = ticketPass;
    }

    //Setters
    public void setTicketUser(String ticketUser) {
        this.ticketUser = ticketUser;
    }

    public void setTicketPass(String ticketPass) {
        this.ticketPass = ticketPass;
    }

    //Getters
    public String getTicketUser() {
        return ticketUser;
    }

    public String getTicketPass() {
        return ticketPass;
    }

}
