package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

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
    String json;
    ListView orderbox;
    JSONArray ja;
    ArrayList<String> orders = new ArrayList<>();

    //private orders adapter = new orders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_accept_request);

        orderbox = (ListView)findViewById(R.id.orderBox);
        //orderbox.setAdapter(adapter);
        json = getIntent().getStringExtra("userdata");
        getItems();
        getUserInfo(json);

        orderbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestingVolunteer.this,"selected item",Toast.LENGTH_SHORT).show();
            }
        });

       /* orderbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id)
            {
                String item = adapter.getItem(i).toString();

                try
                {
                    JSONObject data = new JSONObject(item);
                    //moveToNext(data);
                    Toast.makeText(TestingVolunteer.this,"works",Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });*/
    }

    public void getUserInfo(String data)
    {
        try
        {
            JSONObject item = new JSONObject(data);
            userID = item.getString("VOLUNTEER_ID");
            userEmail  = item.getString("VOLUNTEER_EMAIL");

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void moveToNext(JSONObject data)
    {

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
        /*try
        {
            JSONArray ja = new JSONArray(data);

            if(ja.length()!=0)
            {
                for(int i = 0;i<ja.length();i++)
                {
                    JSONObject item = ja.getJSONObject(i);
                    adapter.addItem(item);
                }

                adapter.finalize();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }*/

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



   /* public class orders extends BaseAdapter {
        List<JSONObject> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            if (view == null) {
                getLayoutInflater().inflate(R.layout.item_volunteer_accept, parent, false);
            }

            TextView email = view.findViewById(R.id.itemedtemail);
            JSONObject item = items.get(i);

            try {
                email.setText(item.getString("PATIENT_EMAIL"));
                email.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }

        public void addItem(JSONObject item) {
            items.add(item);
        }

        public void finalize()
        {
            notifyDataSetChanged();
        }

    }*/
}