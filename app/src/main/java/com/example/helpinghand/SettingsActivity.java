package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(findViewById(R.id.settings_fragment_container) != null) {
            if(savedInstanceState!=null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.settings_fragment_container, new SettingsFragment()).commit();
        }
    }
}
