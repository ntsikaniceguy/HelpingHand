package com.example.helpinghand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class VolunteerHomePage extends AppCompatActivity implements View.OnClickListener {

    int ID;
    String name;
    String email;
    String surname;
    String contact;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home_page);
        json = getIntent().getStringExtra("JSON")+"}";
        JSONdata(json);

        String welcomeMessage = "Welcome, " + name;
        TextView textView = findViewById(R.id.nameViewVolunteer);
        textView.setText(welcomeMessage);

        //defining cards
        CardView NewRequestCard = findViewById(R.id.AcceptRequestCard);
        CardView LocationUpdateCard = findViewById(R.id.UpdateLocationCardVolunteer);
        CardView messagesCard = findViewById(R.id.MessagesCardVolunteer);
        CardView PersonalDetailsCard = findViewById(R.id.PersonalDetailsCardVolunteer);
        CardView SettingsCard = findViewById(R.id.SettingsCardVolunteer);
        CardView LogoutCard = findViewById(R.id.LogoutCardVolunteer);

        //adding click listeners to the cards
        NewRequestCard.setOnClickListener(this);
        LocationUpdateCard.setOnClickListener(this);
        messagesCard.setOnClickListener(this);
        PersonalDetailsCard.setOnClickListener(this);
        SettingsCard.setOnClickListener(this);
        LogoutCard.setOnClickListener(this);
    }

    public void moveToMessageActivity(){
        Intent i = new Intent(VolunteerHomePage.this, MessagingActivity.class);
        startActivity(i);
    }

    public void volunteerToRequestActivity() {
        JSONdata(json);
        Intent i = new Intent(VolunteerHomePage.this,TestingVolunteer.class);
        i.putExtra("email",email);
        i.putExtra("ID",ID);
        startActivity(i);
    }


    public void moveToPDVolunteer(){
        Intent intent = new Intent(this, PDVolunteer.class);
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

    public void moveToLocationActivity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AcceptRequestCard:
                volunteerToRequestActivity();
                break;

            case R.id.UpdateLocationCardVolunteer:
                moveToLocationActivity();
                break;

            case R.id.MessagesCardVolunteer:
                moveToMessageActivity();
                break;

            case R.id.PersonalDetailsCardVolunteer:
                moveToPDVolunteer();
                break;

            case R.id.SettingsCardVolunteer:
                moveToSettingsActivity();
                break;

            case R.id.LogoutCardVolunteer:
                logoutActivity();
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
            JSONObject item = new JSONObject(json);

            ID = item.getInt("VOLUNTEER_ID");
            name = item.getString("VOLUNTEER_NAME");
            email = item.getString("VOLUNTEER_EMAIL");
            surname = item.getString("VOLUNTEER_SURNAME");
            contact = item.getString("VOLUNTEER_CONTACT");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }



}