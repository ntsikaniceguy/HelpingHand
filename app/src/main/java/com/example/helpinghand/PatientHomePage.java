package com.example.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientHomePage extends AppCompatActivity implements View.OnClickListener {

    int ID;
    String name;
    String email;
    String surname;
    String contact;
    String json;
    private TextView NameView;

    private DrawerLayout drawer;
    private CardView NewRequestCard;
    private CardView LocationUpdateCard;
    private CardView PersonalDetailsCard;
    private CardView SettingsCard;
    private CardView LogoutCard;
    private CardView MessagesCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);
        json = getIntent().getStringExtra("JSON");
        JSONdata(json);

        NameView = findViewById(R.id.nameView);
        NameView.setText(name);

        //defining cards
        NewRequestCard = findViewById(R.id.NewRequestCard);
        LocationUpdateCard = findViewById(R.id.UpdateLocationCard);
        MessagesCard = findViewById(R.id.MessagesCard);
        PersonalDetailsCard = findViewById(R.id.PersonalDetailsCard);
        SettingsCard = findViewById(R.id.SettingsCard);
        LogoutCard = findViewById(R.id.LogoutCard);

        //adding click listeners to the cards
        NewRequestCard.setOnClickListener(this);
        LocationUpdateCard.setOnClickListener(this);
        MessagesCard.setOnClickListener(this);
        PersonalDetailsCard.setOnClickListener(this);
        SettingsCard.setOnClickListener(this);
        LogoutCard.setOnClickListener(this);

       /* probably won't use this
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        */


    }

    void patientHomeToRequest()
    {
        Intent i = new Intent(PatientHomePage.this,TestingPatient.class);
        i.putExtra("data",json);
        startActivity(i);
    }

    void patientToMapsActivity(){
        Intent i = new Intent(PatientHomePage.this, MapsActivityCurrentPlace.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.NewRequestCard:
                //getSupportFragmentManager().beginTransaction().replace(R.id.grid_layout, new RequestsFragment());
                patientHomeToRequest();
                break;

            case R.id.UpdateLocationCard:
                patientToMapsActivity();
                break;

            case R.id.MessagesCard:
                moveToMessageActivity();
                break;

        }
    }



    /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment());
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;

            case R.id.nav_requests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestsFragment()).commit();
                break;

            case R.id.nav_messages:
                moveToMessageActivity();
                break;

            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_help:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    */




    public void moveToMessageActivity(){
        Intent intent = new Intent(PatientHomePage.this, MessagingActivity.class);
        startActivity(intent);
    }

    /*
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    */

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