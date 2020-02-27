package com.example.airplaneapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airplaneapp.FlightLogs;
import com.example.airplaneapp.TicketLogs;

import java.util.List;

@Dao
public interface FlightLogDAO {
    @Insert
    void insertFli(FlightLogs... flightLogs);

    @Update
    void updateFli(FlightLogs... flightLogs);

    @Delete
    void deleteFli(FlightLogs flightLog);

    @Query("DELETE FROM flights WHERE flightKeyID = :id")
    int delete(int id);

    @Query("SELECT * FROM " + AppDatabase.FLIGHT_TABLE)
    List<FlightLogs> getFlightLogs();

    @Query("SELECT * FROM " +  AppDatabase.FLIGHT_TABLE + " WHERE flightKeyID = :flightID")
    FlightLogs getflightID(int flightID);

    @Query("SELECT * FROM " +  AppDatabase.FLIGHT_TABLE + " WHERE FlightNumber = :flightNum")
    FlightLogs getFlightStr(String flightNum);

    @Query("SELECT * FROM " +  AppDatabase.FLIGHT_TABLE + " WHERE FlightNumber = :flightName")
    List<FlightLogs> getFlightListStr(String flightName);

    @Query("SELECT * FROM " +  AppDatabase.FLIGHT_TABLE + " WHERE DepartureLocation = :flightDep")
    FlightLogs getFlightDepStr(String flightDep);

}