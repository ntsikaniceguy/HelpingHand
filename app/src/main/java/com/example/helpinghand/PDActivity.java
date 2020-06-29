package com.example.helpinghand;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PDActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_p_d);

        json = getIntent().getStringExtra("JSON")+"}";
        PatientJSONdata(json);

        //initialise the textviews
        Name = findViewById(R.id.personNameText);
        Surname = findViewById(R.id.personSurnameText);
        Email = findViewById(R.id.EmailText);
        Phone = findViewById(R.id.PhoneText);
        Password = findViewById(R.id.PasswordText);

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

    void PatientJSONdata(String json)
    {
        try
        {
            JSONObject item = new JSONObject(json);

            ID = item.getInt("PATIENT_ID");
            name = item.getString("PATIENT_NAME");
            email = item.getString("PATIENT_EMAIL");
            surname = item.getString("PATIENT_SURNAME");
            contact = item.getString("PATIENT_CONTACT");
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