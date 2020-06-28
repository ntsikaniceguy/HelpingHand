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
        json = getIntent().getStringExtra("JSON") + "}";
        JSONdata(json);

        String welcomeMessage = "Welcome back, " + name;
        TextView WelcomeText = findViewById(R.id.nameView);
        WelcomeText.setText(welcomeMessage);

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
    }

    void patientHomeToRequest() {
        Intent i = new Intent(PatientHomePage.this,TestingPatient.class);
        i.putExtra("data",json);
        startActivity(i);
    }

    void patientToMapsActivity(){
        Intent i = new Intent(PatientHomePage.this, MapsActivity.class);
        startActivity(i);
    }

    public void moveToMessageActivity(){
        Intent intent = new Intent(PatientHomePage.this, MessagingActivity.class);
        startActivity(intent);
    }

    public void moveToPDActivity(){
        Intent intent = new Intent(PatientHomePage.this, PDActivity.class);
        intent.putExtra("JSON",json);
        startActivity(intent);
    }

    public void logoutActivity(){
        Toast.makeText(this, "Goodbye!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void moveToSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.NewRequestCard:
                patientHomeToRequest();
                break;

            case R.id.UpdateLocationCard:
                patientToMapsActivity();
                break;

            case R.id.MessagesCard:
                moveToMessageActivity();
                break;

            case R.id.PersonalDetailsCard:
                moveToPDActivity();
                break;

            case R.id.LogoutCard:
                logoutActivity();
                break;

            case R.id.SettingsCard:
                moveToSettingsActivity();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (false) {
            super.onBackPressed();
        } else {
            //disables back
        }
    }

    void JSONdata(String json)
    {
        try
        {
            // JSONArray ja = new JSONArray(json);
            JSONObject item = new JSONObject(json);

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