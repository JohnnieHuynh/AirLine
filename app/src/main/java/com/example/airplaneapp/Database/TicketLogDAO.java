package com.example.airplaneapp.Database;

import com.example.airplaneapp.TicketLogs;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TicketLogDAO {
    @Insert
    void insertAcc(TicketLogs... ticketLogs);

    @Update
    void updateAcc(TicketLogs... ticketLogs);

    @Delete
    void deleteAcc(TicketLogs ticketLog);

    @Query("DELETE  FROM " + AppDatabase.TICKETLOG_TABLE)
    void nukeTable();

    @Query("SELECT * FROM " + AppDatabase.TICKETLOG_TABLE)
    //TicketLogs getTicketLogs();
    List<TicketLogs> getTicketLogs();

    @Query("SELECT * FROM " +  AppDatabase.TICKETLOG_TABLE + " WHERE accountID = :logID")
    TicketLogs getID(int logID);

    @Query("SELECT * FROM " +  AppDatabase.TICKETLOG_TABLE + " WHERE username = :userStr")
    TicketLogs getUserStr(String userStr);

    @Query("SELECT * FROM " +  AppDatabase.TICKETLOG_TABLE + " WHERE password = :passStr")
    TicketLogs getPassStr(String passStr);
}
