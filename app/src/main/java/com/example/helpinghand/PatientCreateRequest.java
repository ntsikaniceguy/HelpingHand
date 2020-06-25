package com.example.helpinghand;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class PatientCreateRequest extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_new_request);


    }

    public void Add()
    {
        TextView edtitemname = (TextView)findViewById(R.id.edtpatNameItem);
        TextView edtitemnum = (TextView)findViewById(R.id.edtpatQuantity);




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
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
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
                ja.put(itemList.get(i));
            }

            return ja.toString();
        }
    }
}
