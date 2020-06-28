package com.example.helpinghand;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PDVolunteer extends AppCompatActivity implements View.OnClickListener {

    int ID;
    String name;
    String email;
    String surname;
    String contact;
    String json;

    private TextView Name;
    private TextView Surname;
    private TextView Email;
    private TextView Phone;
    private TextView Password;

    AlertDialog dialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_volunteer);

        json = getIntent().getStringExtra("JSON")+"}";
        VolunteerJSONdata(json);

        //initialise the textviews
        Name = findViewById(R.id.VolunteerNameText);
        Surname = findViewById(R.id.VolunteerSurnameText);
        Email = findViewById(R.id.VolunteerEmailText);
        Phone = findViewById(R.id.VolunteerPhoneText);
        Password = findViewById(R.id.VolunteerPasswordText);

        //adding onclicks to the textviews:
        Name.setOnClickListener(this);
        Surname.setOnClickListener(this);
        Email.setOnClickListener(this);
        Phone.setOnClickListener(this);
        Password.setOnClickListener(this);

        //adding the person's details on each card
        Name.setText(name);
        Surname.setText(surname);
        Email.setText(email);
        Phone.setText(contact);
    }

    void VolunteerJSONdata(String json)
    {
        try
        {
            JSONObject item = new JSONObject(json);

            ID = item.getInt("VOLUNTEER_ID");
            name = item.getString("VOLUNTEER_NAME");
            email = item.getString("VOLUNTEER_EMAIL");
            surname = item.getString("VOLUNTEER_SURNAME");
            contact = item.getString("VOLUNTEER_CONTACT");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void editDetails(View view){
        TextView textView;
        textView = findViewById(view.getId());
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edit the text");
        dialog.setView(editText);

        final TextView finalTextView = textView;
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalTextView.setText(editText.getText());
            }
        });
        final TextView finalTextView1 = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(finalTextView1.getText());
                dialog.show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personNameText:
                editDetails(Name);
                break;

            case R.id.personSurnameText:
                editDetails(Surname);
                break;

            case R.id.PhoneText:
                editDetails(Phone);
                break;

            case R.id.EmailText:
                editDetails(Email);
                break;

            case R.id.PasswordText:
                editDetails(Password);
                break;
        }
    }
}