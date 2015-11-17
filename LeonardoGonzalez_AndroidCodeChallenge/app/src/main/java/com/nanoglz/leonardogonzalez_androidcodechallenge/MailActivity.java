package com.nanoglz.leonardogonzalez_androidcodechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {
    EditText editTextTo, editTextCC, editTextSubject, editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTo = (EditText) findViewById(R.id.editTextTo);
        editTextCC = (EditText) findViewById(R.id.editTextCC);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        editText.setText(intent.getStringExtra("title"));

        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        Button buttonNew = (Button) findViewById(R.id.buttonNew);
        Button buttonSend = (Button) findViewById(R.id.buttonSend);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "functionality not implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, editTextTo.getText());
                intent.putExtra(Intent.EXTRA_CC, editTextCC.getText());
                intent.putExtra(Intent.EXTRA_SUBJECT, editTextSubject.getText());
                intent.putExtra(Intent.EXTRA_TEXT, editText.getText());
                startActivity(intent);
            }
        });

    }

}
