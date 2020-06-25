package com.example.helpinghand;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class PatientCreateRequest extends AppCompatActivity {

    String ID = "";
    String email = "";

    private itemAdapter adapter = new itemAdapter();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_new_request);
        ListView orderitems = (ListView)findViewById(R.id.patlist);
        orderitems.setAdapter(adapter);
        getInfo();
    }

    void getInfo()
    {
        //get relevant info
    }

    public void PRAdd()
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
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
            else
            {
                Toast.makeText(PatientCreateRequest.this,"Enter a valid name for item",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(PatientCreateRequest.this,"Enter a valid quantity",Toast.LENGTH_SHORT).show();
        }


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
