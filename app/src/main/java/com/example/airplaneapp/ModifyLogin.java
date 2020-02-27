package com.example.airplaneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_login);

        //Create EditTexts
        final EditText userEdit;
        final EditText passEdit;

        //Create Buttons
        Button signButt;

        //Connect Text Fields
        userEdit = (EditText) findViewById(R.id.usernameEdit);
        passEdit = (EditText) findViewById(R.id.passwordEdit);

        //Connect Buttons
        signButt = (Button) findViewById(R.id.adminSignInButton);

        //When Button Clicked
        signButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get texts from fields
                String givenUsername = userEdit.getText().toString();
                String givenPassword = passEdit.getText().toString();

                if (givenUsername.equals("admin2") && (givenPassword.equals("admin2"))){
                    //Change screen
                    Intent intentModLoginToModAcc = new Intent ( ModifyLogin.this, ModifyAccounts.class);
                    startActivity(intentModLoginToModAcc);
                }else{
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "Incorrect Login Info", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
