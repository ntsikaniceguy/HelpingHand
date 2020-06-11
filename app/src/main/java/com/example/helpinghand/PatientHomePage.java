package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientHomePage extends AppCompatActivity {

    TextView mTV;

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

        mTV = (TextView)findViewById(R.id.welcomeMessageTextId);
        mTV.setText(name);
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
