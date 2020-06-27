package com.example.helpinghand;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PatientProcessOrder extends AppCompatActivity implements View.OnClickListener {

    private CardView MessagesCard ;
    String id;
    String email;
    String date;
    String volID;
    String volEmail;
    Timer timer = new Timer();
    int nextCheck = 10000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_proccess_order);

        id = getIntent().getStringExtra("ID");
        email = getIntent().getStringExtra("email");
        date =  LocalDate.now().toString();

        MessagesCard = findViewById(R.id.MessagesCard);
        MessagesCard.setOnClickListener(this);
        MessagesCard.setVisibility(View.INVISIBLE);

        checkOrder check = new checkOrder();
        timer.schedule(check,5000,nextCheck);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.MessagesCard:
                MoveToMessage();
                break;
        }
    }

    void MoveToMessage()
    {
        Intent i = new Intent(PatientProcessOrder.this,MessagingActivity.class);
        i.putExtra("userID",id);
        i.putExtra("userEmail",email);
        i.putExtra("clientID",volID);
        i.putExtra("clientEmail",volEmail);
        i.putExtra("type","V");
        startActivity(i);
    }

    void AcceptOrder(String data)
    {
        TextView edit = (TextView)findViewById(R.id.ppo_text);
        edit.setText("Order accepted");
        MessagesCard.setVisibility(View.VISIBLE);
        timer.cancel();
        timer.purge();

        try
        {
            JSONObject item = new JSONObject(data);
            volID = item.getString("VOLUNTEER_ID");
            volEmail = item.getString("VOLUNTEER_EMAIL");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    public class checkOrder extends TimerTask
    {
        @Override
        public void run() {

            OkHttpClient client = new OkHttpClient();
            String url = "https://lamp.ms.wits.ac.za/home/s2241186/checkOrder.php";
            HttpUrl.Builder urlbuilder = HttpUrl.parse(url).newBuilder();
            urlbuilder.addQueryParameter("id",id);
            urlbuilder.addQueryParameter("date",date);

            String queryurl = urlbuilder.build().toString();
            final Request request = new Request.Builder().url(queryurl).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    if(response.isSuccessful())
                    {
                        final String result = response.body().string();

                        if(!result.equalsIgnoreCase("no")&&!result.equalsIgnoreCase("error"))
                        {
                           PatientProcessOrder.this.runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   AcceptOrder(result);
                               }
                           });
                        }
                    }
                }
            });

        }
    }
}