package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class VolunteerHomePage extends AppCompatActivity {

    int ID;
    String name;
    String email;
    String surname;
    String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home_page);

        String json = getIntent().getStringExtra("JSON");

        JSONdata(json);

    }


    void JSONdata(String json)
    {
        try
        {
            JSONArray ja = new JSONArray(json);
            JSONObject item = ja.getJSONObject(0);

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
