package com.example.helpinghand;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestingPatient extends AppCompatActivity {

    String ID = "";
    String email = "";
    String json;
    private itemAdapter adapter = new itemAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_new_request);
        json = getIntent().getStringExtra("data")+"}";
        ListView orderitems = (ListView)findViewById(R.id.patlist);
        orderitems.setAdapter(adapter);
        getInfo();
    }

    void getInfo()
    {
        try
        {
           JSONObject item = new JSONObject(json);
            ID = item.getString("PATIENT_ID");
            email = item.getString("PATIENT_EMAIL");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    void moveToViewOrder()
    {
        Toast.makeText(TestingPatient.this,"Works",Toast.LENGTH_SHORT).show();
    }

    public void PRAdd(View view)
    {
        TextView edtitemname = (TextView)findViewById(R.id.edtpatNameItem);
        TextView edtitemnum = (TextView)findViewById(R.id.edtpatQuantity);
        String sent  = edtitemname.getText().toString();
        String quantity = edtitemnum.getText().toString();
        int num = Integer.parseInt(quantity);

        if(num>0)
        {
            if(!sent.equalsIgnoreCase(""))
            {
                JSONObject item = new JSONObject();

                try
                {
                    item.put("name",sent);
                    item.put("#",quantity);
                    adapter.addItem(item);
                    edtitemname.setText("");
                    edtitemnum.setText("");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(TestingPatient.this,"Enter a valid name for item",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(TestingPatient.this,"Enter a valid quantity",Toast.LENGTH_SHORT).show();
        }


    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void CompleteOrder(View view)
    {
        String data = adapter.getJSONArray();
        String date =  LocalDate.now().toString();

        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2241186/createOrder.php";
        HttpUrl.Builder urlbuilder = HttpUrl.parse(url).newBuilder();

        urlbuilder.addQueryParameter("id",ID);
        urlbuilder.addQueryParameter("email",email);
        urlbuilder.addQueryParameter("order",data);
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

                    TestingPatient.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(result.equalsIgnoreCase("done"))
                            {
                                moveToViewOrder();
                            }
                            else if(result.equalsIgnoreCase("exists"))
                            {
                                Toast.makeText(TestingPatient.this,"An order can only be placed once a day",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(TestingPatient.this,"error adding order",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    public class itemAdapter extends BaseAdapter
    {
        List<JSONObject> itemList = new ArrayList<>();

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int i) {
            return itemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {

            if(view == null)
            {
                view  = getLayoutInflater().inflate(R.layout.item_patient_request,parent,false);
            }

            JSONObject item = itemList.get(i);
            TextView edtname  = view.findViewById(R.id.pat_item_name);
            TextView edtquant = view.findViewById(R.id.pat_item_quant);

            try
            {
                edtname.setText(item.getString("name"));
                edtquant.setText("qty: "+item.getString("#"));
                edtname.setVisibility(View.VISIBLE);
                edtquant.setVisibility(View.VISIBLE);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return view;
        }

        public void addItem(JSONObject item)
        {
            itemList.add(item);
            notifyDataSetChanged();
        }

        public String getJSONArray()
        {
            JSONArray ja = new JSONArray();

            for(int i = 0;i<itemList.size();i++)
            {
                ja.put(itemList.get(i).toString());
            }

            return ja.toString();
        }
    }
}