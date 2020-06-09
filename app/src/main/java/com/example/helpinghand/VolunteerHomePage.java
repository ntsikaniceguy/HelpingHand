package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class VolunteerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home_page);

        String json = getIntent().getStringExtra("JSON");
    }
}
