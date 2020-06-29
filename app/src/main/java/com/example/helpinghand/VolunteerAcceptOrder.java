package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolunteerAcceptOrder extends AppCompatActivity implements View.OnClickListener{

    String userID;
    String userEmail;
    String clientID;
    String clientEmail;
    String data;
    ListView itemlist;
    JSONArray ja;
    ArrayList<String> items = new ArrayList<>();

    private CardView AcceptOrderMessagesCard ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_accept_order);

        getData();
        itemlist = (ListView) findViewById(R.id.patlist);
        addToList(data);
        AcceptOrderMessagesCard = findViewById(R.id.AcceptOrderMessagesCard);
        AcceptOrderMessagesCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AcceptOrderMessagesCard:
                MoveToMessage();
                break;
        }
    }

    void MoveToMessage()
    {
        Intent i = new Intent(VolunteerAcceptOrder.this,MessagingActivity.class);
        i.putExtra("userID",userID);
        i.putExtra("userEmail",userEmail);
        i.putExtra("clientID",clientID);
        i.putExtra("clientEmail",clientEmail);
        i.putExtra("type","V");
        startActivity(i);
    }


    void getData()
    {
        userID = getIntent().getStringExtra("userID");
        userEmail = getIntent().getStringExtra("userEmail");
        clientID = getIntent().getStringExtra("clientID");
        clientEmail = getIntent().getStringExtra("clientEmail");
        data = getIntent().getStringExtra("order");
    }

    void addToList(String json)
    {
        try
        {
            ja = new JSONArray(json);

            for(int i = 0;i<ja.length();i++)
            {
                String vp = ja.getString(i);
                JSONObject item = new JSONObject(vp);
                String sent  = item.getString("#")+" x "+item.getString("name");
                items.add(sent);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
            itemlist.setAdapter(adapter);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


}