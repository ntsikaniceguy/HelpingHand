package com.example.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class VolunteerHomePage extends AppCompatActivity implements View.OnClickListener {

    String ID;
    String name;
    String email;
    String surname;
    String contact;
    String json;
    //  private DrawerLayout drawer;
    private CardView NewRequestCard, LocationUpdateCard, MessagesCard, PersonalDetailsCard, SettingsCard, LogoutCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home_page);

        json = getIntent().getStringExtra("JSON");
        JSONdata(json);

        //defining cards
        NewRequestCard = findViewById(R.id.AcceptRequestCard);
        LocationUpdateCard = findViewById(R.id.UpdateLocationCardVolunteer);
        MessagesCard = findViewById(R.id.MessagesCardVolunteer);
        PersonalDetailsCard = findViewById(R.id.PersonalDetailsCardVolunteer);
        SettingsCard = findViewById(R.id.SettingsCardVolunteer);
        LogoutCard = findViewById(R.id.LogoutCardVolunteer);

        //adding click listeners to the cards
        NewRequestCard.setOnClickListener(this);
        LocationUpdateCard.setOnClickListener(this);
        MessagesCard.setOnClickListener(this);
        PersonalDetailsCard.setOnClickListener(this);
        SettingsCard.setOnClickListener(this);
        LogoutCard.setOnClickListener(this);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


    /* unneeded
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
     */
    }

    public void moveToMessageActivity(){
        Intent i = new Intent(VolunteerHomePage.this, MessagingActivity.class);
        startActivity(i);
    }

    public void volunteerToRequestActivity() {
        Intent i = new Intent(VolunteerHomePage.this,TestingVolunteer.class);
        i.putExtra("email",email);
        i.putExtra("ID",ID);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AcceptRequestCard:
                volunteerToRequestActivity();
                break;

            case R.id.MessagesCardVolunteer:
                moveToMessageActivity();
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

    /* unneeded
    @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;

            case R.id.nav_requests:
                volunteerToRequestActivity(item);
                break;

            case R.id.nav_messages:
                moveToMessageActivity(item);
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


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
     */



    void JSONdata(String data)
    {
        try
        {
            JSONObject item = new JSONObject(data);

            ID = item.getString("VOLUNTEER_ID");
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