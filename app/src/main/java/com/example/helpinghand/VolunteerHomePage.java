package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class VolunteerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home_page);

        String json = getIntent().getStringExtra("JSON");
        TextView textView = (TextView)findViewById(R.id.volunteerHomePageJSONId);

        textView.setText("Welcome");
    }



}
