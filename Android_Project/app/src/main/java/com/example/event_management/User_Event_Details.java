package com.example.event_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class User_Event_Details extends AppCompatActivity {

    TextView ename, eclub, evenue, edate, edur, edisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__event__details);

        Intent intent=getIntent();
        final String username=intent.getStringExtra("USERNAME");
        final String uid= intent.getStringExtra("UID");
        setTitle(username);

        ename= (TextView) findViewById(R.id.name);
        evenue= (TextView)findViewById(R.id.venue);
        edate= (TextView)findViewById(R.id.date);
        edur= (TextView)findViewById(R.id.duratin);
        edisc= (TextView)findViewById(R.id.disc);
        eclub= (TextView)findViewById(R.id.kclub);
        ename.setText(intent.getStringExtra("EVENT_NAME").toString());
        evenue.setText(intent.getStringExtra("EVENT_VENUE"));
        edate.setText(intent.getStringExtra("EVENT_DATE"));
        eclub.setText(intent.getStringExtra("EVENT_CLUB"));
        edisc.setText("\nEvent Description\n"+intent.getStringExtra("EVENT_DISC"));
        edur.setText(intent.getStringExtra("EVENT_DURATION")+" hr");


    }
}
