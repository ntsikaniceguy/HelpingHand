package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LoginToCreate(View view)
    {
        Intent createIntent = new Intent(LoginActivity.this,CreateAccount.class);
        startActivity(createIntent);
    }

    void LoginToPatient(String json)
    {
        Intent patientHomePage = new Intent(this, PatientHomePage.class);
        patientHomePage.putExtra("JSON", json);
        startActivity(patientHomePage);
    }

    void LoginToVolunteer(String json)
    {
        Intent volunteerHomePageIntent = new Intent(this, VolunteerHomePage.class);
        volunteerHomePageIntent.putExtra("JSON", json);
        startActivity(volunteerHomePageIntent);
    }

    public boolean RememberMe(CheckBox remember)
    {

        boolean RememberUser = false;
        remember = findViewById(R.id.checkBoxId);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            RememberUser = true;
        }else if(checkbox.equals("false")){
            RememberUser = false;
        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return RememberUser;
    }

    public void btnLogin(View view)
    {

        TextView edtemail = (TextView)findViewById(R.id.loginActivityEmail);
        TextView edtpassword = (TextView)findViewById(R.id.loginActivityPassword);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBoxId);


        String email = edtemail.getText().toString();
        edtemail.setText("");
        String password = edtpassword.getText().toString();
        edtpassword.setText("");
        Boolean remembered = RememberMe(checkBox);

        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else
            {
            Login(email,password, remembered);
        }
    }

    void Login(String email,String password, boolean remember)
    {



        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2241186/login.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("email",email);
        urlBuilder.addQueryParameter("password",password);

        String queryurl = urlBuilder.build().toString();
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

                    if(result.equalsIgnoreCase("NOTE"))
                    {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "email or password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        final String json = result.substring(1, result.length()-1);
                        final char type = result.charAt(0);

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(type == 'V')
                                {
                                    LoginToVolunteer(result);
                                }
                                else if (type == 'P')
                                {
                                    LoginToPatient(json);
                                }

                            }
                        });
                    }
                }
            }
        });
    }

    public void ForgotPassword(View view)
    {
        TextView edtemail = (TextView)findViewById(R.id.loginActivityEmail);
        String email = edtemail.getText().toString();

        if(email.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Fill in email", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FPConn(email);
        }
    }

    void FPConn(String email)
    {
        OkHttpClient client = new OkHttpClient();
        String url = "https://lamp.ms.wits.ac.za/home/s2241186/forgotemail.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("email",email);
        String queryurl = urlBuilder.build().toString();
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

                    if(result.equalsIgnoreCase("NE"))
                    {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "email does not exist.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

}