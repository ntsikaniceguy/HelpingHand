package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    String type ;

    int nextCheck = 10000;

    private MessageAdapter adapter = new MessageAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        ListView messagelist  = (ListView)findViewById(R.id.messageList);
        messagelist.setAdapter(adapter);

        TextView name = (TextView)findViewById(R.id.edtNameMess);
        getID();
        name.setText(getIntent().getStringExtra("clientEmail"));
        Timer timer = new Timer();
        Checkmessage msg = new Checkmessage();
        timer.schedule(msg,5000,nextCheck);
    }

    void getID()
    {
        //sets variables to needed values
        userID = getIntent().getStringExtra("userID");
        clientID = getIntent().getStringExtra("clientID");
        type = getIntent().getStringExtra("type");
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
            urlbuilder.addQueryParameter("type",type);

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
        JSONObject item = new JSONObject();

        try
        {
            item.put("msg",text);
            item.put("user",false);
            adapter.Additem(item);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(View view)
    {
        final EditText msgBox = (EditText)findViewById(R.id.messageBox);
        final String text = msgBox.getText().toString();

        if(!text.equalsIgnoreCase(""))
        {
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
                            public void run()
                            {

                                JSONObject item = new JSONObject();

                                try
                                {
                                    msgBox.setText("");
                                    item.put("msg",text);
                                    item.put("user",true);
                                    adapter.Additem(item);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }
            });
        }

    }


    public class MessageAdapter extends BaseAdapter
    {
        List<JSONObject> messageList = new ArrayList<>();

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int i) {
            return messageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {

            if(view == null)
            {
                view  = getLayoutInflater().inflate(R.layout.message_list_item,parent,false);
            }


            TextView getMsg = view.findViewById(R.id.msg_recieved);
            TextView sendMsg = view.findViewById(R.id.msg_sent);

            JSONObject item  = messageList.get(i);

            try
            {

                if(item.getBoolean("user"))
                {
                    sendMsg.setText(item.getString("msg"));
                    sendMsg.setVisibility(View.VISIBLE);
                    getMsg.setVisibility(View.INVISIBLE);
                }
                else
                {
                    getMsg.setText(item.getString("msg"));
                    getMsg.setVisibility(View.VISIBLE);
                    sendMsg.setVisibility(View.INVISIBLE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return view;
        }

        public void Additem(JSONObject item)
        {
            messageList.add(item);
            notifyDataSetChanged();
        }
    }

}