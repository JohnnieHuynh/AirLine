package com.example.airplaneapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.airplaneapp.FlightLogs;
import com.example.airplaneapp.ReserveLogs;
import com.example.airplaneapp.TicketLogs;
import com.example.airplaneapp.TransLogs;

@Database(entities = {

        //Table for username and passwords
        TicketLogs.class,
        //Table for Flights
        FlightLogs.class,
        //Table for Logging transactions
        TransLogs.class,
        //Table for reservations
        ReserveLogs.class

        }, version = 1
)
//@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DBNAME = "db-ticketlog";
    public static final String TICKETLOG_TABLE = "ticketlog";
    public static final String FLIGHT_TABLE = "flights";
    public static final String TRANS_TABLE = "transactions";
    public static final String RESERVE_TABLE = "reservations";

    public abstract TicketLogDAO getTicketLogDAO();
    public abstract FlightLogDAO getFlightLogDAO();
    public abstract TransLogDAO getTransLogDAO();
    public abstract ReserveLogDAO getReserveLogDAO();

    //Creating Single DB
    private static AppDatabase INSTANCE;
    public static AppDatabase getInstanceDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "db-ticketlog")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    //Get Ticket Table Instance (WAIT, i dont need this? Ask someone later)
    private static TicketLogDAO ticketInstance;
    public static TicketLogDAO getInstanceTicketTable(Context context){
        return ticketInstance;
    }

    public void cleanUp() {
        INSTANCE = null;
    }


}
