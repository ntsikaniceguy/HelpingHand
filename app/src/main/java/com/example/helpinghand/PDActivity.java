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

public class PDActivity extends AppCompatActivity {

    int ID;
    String name;
    String email;
    String surname;
    String contact;
    String json;

    private TextView NameView;

    private CardView NameCard;
    private CardView SurnameCard;
    private CardView PhoneNumberCard;
    private CardView EmailCard;
    private CardView PasswordCard;

    TextView textView;
    AlertDialog dialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d);

        json = getIntent().getStringExtra("JSON")+"}";
        JSONdata(json);

        TextView volunteerName = findViewById(R.id.personNameText);
        volunteerName.setText(name);

        TextView VolunteerSurname = findViewById(R.id.personSurnameText);
        VolunteerSurname.setText(surname);

        TextView volunteerEmail = findViewById(R.id.EmailText);
        volunteerEmail.setText(email);

        TextView volunteerContact = findViewById(R.id.PhoneText);
        volunteerContact.setText(contact);

        //cards
        NameCard = findViewById(R.id.NameDetailCard);
        SurnameCard = findViewById(R.id.SurnameDetailCard);
        PhoneNumberCard = findViewById(R.id.PhoneNumberCard);
        EmailCard = findViewById(R.id.EmailCard);
        PasswordCard = findViewById(R.id.PasswordCard);


        /*
        editDetails(NameText);
        editDetails(SurnameText);
        editDetails(PhoneText);
        editDetails(EmailText);
        editDetails(PasswordText);
         */
    }

    void JSONdata(String json)
    {
        try
        {
            JSONArray ja = new JSONArray(json);
            JSONObject item = ja.getJSONObject(0);

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

        textView = findViewById(view.getId());
        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edit the text");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(editText.getText());
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(textView.getText());
                dialog.show();
            }
        });
    }

}