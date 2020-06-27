package com.example.helpinghand;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestingVolunteer extends AppCompatActivity {

    String userID;
    String userEmail;
    String json = "";
    ListView orderbox;
    boolean success1 = true;
    boolean success2;
    JSONArray ja;
    ArrayList<String> orders = new ArrayList<>();

    //private orders adapter = new orders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_accept_request);

        userEmail = getIntent().getStringExtra("email");
        userID = getIntent().getStringExtra("ID");

        orderbox = (ListView)findViewById(R.id.orderBox);
        json = getIntent().getStringExtra("userdata");
        getItems();

        orderbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {
                    JSONObject item = ja.getJSONObject(position);
                    moveToNext(item);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void moveToNext(JSONObject data)
    {
        UpdateOrder(data);
        CreateChat(data);

        if(success1 == true && success2==true)
        {
            try
            {
                String clientEmail = data.getString("PATIENT_EMAIL");
                String clientID = data.getString("PATIENT_ID");
                String order = data.getString("ORDER_RECEIPT");

                Intent i  = new Intent(TestingVolunteer.this,VolunteerAcceptOrder.class);
                i.putExtra("userID",userID);
                i.putExtra("userEmail",userEmail);
                i.putExtra("clientID",clientID);
                i.putExtra("clientEmail",clientEmail);
                i.putExtra("order",order);
                startActivity(i);


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void UpdateOrder(JSONObject data)
    {
        try
        {
            String clientEmail = data.getString("PATIENT_EMAIL");
            String date =  LocalDate.now().toString();

            OkHttpClient client = new OkHttpClient();
            String url  = "https://lamp.ms.wits.ac.za/home/s2241186/updateOrder.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder.addQueryParameter("volEmail",userEmail);
            urlBuilder.addQueryParameter("volID",userID);
            urlBuilder.addQueryParameter("patEmail",clientEmail);
            urlBuilder.addQueryParameter("date",date);
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
                        String result  = response.body().string();

                        if(result.equalsIgnoreCase("error"))
                        {
                            success2 = false;
                        }
                        else
                        {
                            success2 = true;
                        }
                    }
                }
            });
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    void CreateChat(JSONObject data)
    {
        try
        {
            String clientID = data.getString("PATIENT_ID");
            OkHttpClient client = new OkHttpClient();
            String url  = "https://lamp.ms.wits.ac.za/home/s2241186/createChat.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder.addQueryParameter("volID",userID);
            urlBuilder.addQueryParameter("patID",clientID);
            String queryurl = urlBuilder.build().toString();
            Request request = new Request.Builder().url(queryurl).build();

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
                        String result = response.body().string();
                        success1 = true;
                    }
                }
            });

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    void getItems()
    {
        OkHttpClient client = new OkHttpClient();
        String url  = "https://lamp.ms.wits.ac.za/home/s2241186/pendingOrder.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        String queryurl = urlBuilder.build().toString();
        Request request = new Request.Builder().url(queryurl).build();

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

                    TestingVolunteer.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            AddtoList(result);
                        }
                    });
                }
            }
        });
    }

    void AddtoList(String data)
    {

        try
        {
            ja = new JSONArray(data);

            for(int i = 0;i<ja.length();i++)
            {
                JSONObject item = ja.getJSONObject(i);
                String semail = item.getString("PATIENT_EMAIL");
                orders.add(semail);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,orders);
            orderbox.setAdapter(adapter);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }




}