package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessagingActivity extends AppCompatActivity {

    String userID;
    String clientID;
    int nextCheck = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        getID();
        Timer timer = new Timer();
        Checkmessage msg = new Checkmessage();
        timer.schedule(msg,5000,nextCheck);
    }

    void getID()
    {
        //sets variables to needed values
    }


    //checks for messages
    public class Checkmessage extends TimerTask
    {
        @Override
        public void run()
        {
            OkHttpClient client = new OkHttpClient();
            String url = "https://lamp.ms.wits.ac.za/home/s2241186/ChatCheck.php";
            HttpUrl.Builder urlbuilder = HttpUrl.parse(url).newBuilder();

            urlbuilder.addQueryParameter("UserID",userID);
            urlbuilder.addQueryParameter("ClientID",clientID);

            String queryurl = urlbuilder.build().toString();
            final Request request = new Request.Builder().url(queryurl).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                {
                    if(response.isSuccessful())
                    {
                        final String result = response.body().string();

                        if(!result.equalsIgnoreCase(""))
                        {
                            MessagingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AddClientText(result);
                                }
                            });
                        }
                    }
                }
            });


        }
    }


    void AddClientText(String text)
    {
        //adds what other person sent to UI
    }

    public void btnSend()
    {
        //get text from edittext
        String text = "";

        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2241186/ChatAdd.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        urlBuilder.addQueryParameter("UserID",userID);
        urlBuilder.addQueryParameter("ClientID",clientID);
        urlBuilder.addQueryParameter("Text",text);

        String queryurl = urlBuilder.build().toString();
        Request request = new Request.Builder().url(queryurl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.isSuccessful())
                {
                    MessagingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //clear edittext
                        }
                    });
                }
            }
        });

    }

}