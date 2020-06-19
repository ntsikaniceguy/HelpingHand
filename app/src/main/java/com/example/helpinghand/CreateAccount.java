package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void doCreateAccount (View view) {

        TextView edtname = (TextView)findViewById(R.id.createAccountNameTextId);
        TextView edtsurname = (TextView)findViewById(R.id.createAccountSurnameTextId);
        TextView edtemail = (TextView)findViewById(R.id.createAccountEmailTextId);
        TextView edtphone = (TextView)findViewById(R.id.createAccountPhoneId);
        TextView edtpassword = (TextView)findViewById(R.id.createAccountPassword);
        final RadioGroup rgroup = (RadioGroup)findViewById(R.id.radioGroup);

        int rbuttonID = rgroup.getCheckedRadioButtonId();
        RadioButton rbutton = (RadioButton) rgroup.findViewById(rbuttonID);

        String name = edtname.getText().toString();
        edtname.setText("");
        String surname = edtsurname.getText().toString();
        edtsurname.setText("");
        String email = edtemail.getText().toString();
        edtemail.setText("");
        String phone = edtphone.getText().toString();
        edtphone.setText("");
        String password = edtpassword.getText().toString();
        edtpassword.setText("");

        String type;

        //please find a way to fix the readio thing i tried this doesnt work and im lazy
        if(rbutton.getText().toString().equalsIgnoreCase("Volunteer"))
        {
            type = "V";
        }
        else
        {
            type = "P";
        }


        if(name.isEmpty()||surname.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty())
        {
            Toast.makeText(CreateAccount.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Add(type,name,surname,email,phone,password);
        }


    }

    public void doLoginIntent(View view){
        Intent loginIntent = new Intent(CreateAccount.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void Add(String type,String name,String surname,String email,String phone,String password)
    {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2241186/signup.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("type",type);
        urlBuilder.addQueryParameter("name",name);
        urlBuilder.addQueryParameter("email",email);
        urlBuilder.addQueryParameter("surname",surname);
        urlBuilder.addQueryParameter("address",null);
        urlBuilder.addQueryParameter("password",password);
        urlBuilder.addQueryParameter("contact",phone);
        urlBuilder.addQueryParameter("image",null);

        String queryurl = urlBuilder.build().toString();
        Request request = new Request.Builder().url(queryurl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {

                if(response.isSuccessful())
                {
                    final String result = response.body().string();

                    CreateAccount.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(result.equalsIgnoreCase("EXI"))
                            {
                                Toast.makeText(CreateAccount.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                moveToSelfie();
                            }
                        }
                    });
                }
            }
        });
    }

    public void moveToSelfie()
    {
        Intent volunteerIntent = new Intent(CreateAccount.this, GetSelfieActivity.class);
        startActivity(volunteerIntent);
    }

}