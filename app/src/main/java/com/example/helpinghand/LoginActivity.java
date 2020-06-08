package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    public void moveToHomePage(View view){
        EditText emailText = findViewById(R.id.loginActivityEmail);
        String emailString = emailText.toString();

    }

    public void btnLogin()
    {
        TextView edtemail = (TextView)findViewById(R.id.loginActivityEmail);
        TextView edtpassword = (TextView)findViewById(R.id.loginActivityPassword);

        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Login(email,password);
        }
    }

    void Login(String email,String password)
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
                    final String data = response.body().toString();

                    if(result.equalsIgnoreCase("NULL"))
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
                        //move to next activity
                        //data is stored in String data
                    }
                }
            }
        });
    }

    void ForgotPassword(View view)
    {

    }
}
