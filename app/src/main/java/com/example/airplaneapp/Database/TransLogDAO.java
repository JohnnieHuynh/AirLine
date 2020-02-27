package com.example.airplaneapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.airplaneapp.TransLogs;

import java.util.List;

@Dao
public interface TransLogDAO {
    @Insert
    void insertTrans(TransLogs... transLogs);

    @Update
    void updateTrans(TransLogs... transLogs);

    @Delete
    void deleteTrans(TransLogs transLog);

    @Query("SELECT * FROM " + AppDatabase.TRANS_TABLE)
    List<TransLogs> getTransLogs();

    @Query("SELECT * FROM " +  AppDatabase.TRANS_TABLE + " WHERE username = :userStr")
    TransLogs getUserStr(String userStr);
}
