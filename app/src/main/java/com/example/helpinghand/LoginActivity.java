package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doCreateAccountIntent(View view){
        Intent createAccountIntent = new Intent(LoginActivity.this,CreateAccount.class);
        startActivity(createAccountIntent);
    }

    public void doPatientHomePageIntent(View view){
        Intent patientHomePageIntent = new Intent(LoginActivity.this, PatientHomePage.class);
        startActivity(patientHomePageIntent);
    }
}
