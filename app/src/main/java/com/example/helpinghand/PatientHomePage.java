package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientHomePage extends AppCompatActivity {


    int ID;
    String name;
    String email;
    String surname;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);
        String json = getIntent().getStringExtra("JSON");
        JSONdata(json);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    void JSONdata(String json)
    {
        try
        {
            JSONArray ja = new JSONArray(json);
            JSONObject item = ja.getJSONObject(0);

            ID = item.getInt("PATIENT_ID");
            name = item.getString("PATIENT_NAME");
            email = item.getString("PATIENT_EMAIL");
            surname = item.getString("PATIENT_SURNAME");
            contact = item.getString("PATIENT_CONTACT");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}