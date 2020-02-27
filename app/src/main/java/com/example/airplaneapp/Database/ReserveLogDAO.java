package com.example.airplaneapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airplaneapp.ReserveLogs;
import com.example.airplaneapp.TransLogs;

import java.util.List;

@Dao
public interface ReserveLogDAO {
    @Insert
    void insertRes(ReserveLogs... reserveLogs);

    @Update
    void updateRes(ReserveLogs... reserveLogs);

    @Delete
    void deleteRes(ReserveLogs reserveLog);

    @Query("SELECT * FROM " + AppDatabase.RESERVE_TABLE)
    List<ReserveLogs> getReserveLogs();

    @Query("SELECT * FROM " +  AppDatabase.RESERVE_TABLE + " WHERE username = :userStr")
    ReserveLogs getUserStr(String userStr);

    @Query("SELECT * FROM " +  AppDatabase.RESERVE_TABLE + " WHERE reserveID = :userID")
    ReserveLogs getResLogFromID(int userID);
}
