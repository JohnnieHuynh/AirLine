package com.example.airplaneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    //Create Buttons
    Button reserveButt;
    Button cancelButt;
    Button modifyButt;
    Button signoutButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Connect Buttons
        reserveButt = (Button) findViewById(R.id.reserveButton);
        cancelButt = (Button) findViewById(R.id.cancelButton);
        modifyButt = (Button) findViewById(R.id.modifyButton);
        signoutButt = (Button) findViewById(R.id.signOutButton);

        //Change screen to reserve activity
        reserveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenuToRes = new Intent ( Menu.this, ReserveFlight.class );
                startActivity(intentMenuToRes);
            }
        });

        //Change screen to cancel activity
        cancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenuToCan = new Intent ( Menu.this, CancelLog.class );
                startActivity(intentMenuToCan);
            }
        });

        //Change screen to modify activity
        modifyButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenuToModLogin = new Intent ( Menu.this, ModifyLogin.class );
                startActivity(intentMenuToModLogin);
            }
        });

        //Change screen to login activity
        signoutButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove login from Shared Pref
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.remove("username");
                editor.remove("password");
                editor.commit();

                //Change screen to login
                Intent intentMenuToLogin = new Intent ( Menu.this, LoginScreen.class );
                startActivity(intentMenuToLogin);
            }
        });
    }


}
