package com.example.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doCreateAccountIntent(View view){
        Intent createAccountIntent = new Intent(MainActivity.this,CreateAccount.class);
        startActivity(createAccountIntent);
    }

    public void doLoginIntent(View view){
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
