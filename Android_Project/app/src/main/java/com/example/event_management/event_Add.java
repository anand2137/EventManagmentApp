package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class event_Add extends AppCompatActivity {

    EditText ename, evenue, edate, edisc, eduration;
    Button bt;
    DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        final String cid=intent.getStringExtra("CID");
        final String club_name=intent.getStringExtra("CLUBNAME");
        setTitle(club_name);


        setContentView(R.layout.activity_event__add);
        ename=findViewById(R.id.add_ename_et);
        evenue=findViewById(R.id.add_evenue_et);
        edate=findViewById(R.id.add_edate_et);
        edisc=findViewById(R.id.add_edisc_et);
        eduration=findViewById(R.id.add_eduration_et);
        bt=findViewById(R.id.add_submit_bt);
        edate.setInputType(InputType.TYPE_NULL);
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(edate);
            }
        });
        dataref= FirebaseDatabase.getInstance().getReference("Events");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String event_name=ename.getText().toString();
                String event_venue=evenue.getText().toString();
                String event_disc=edisc.getText().toString();
                String event_dur=eduration.getText().toString();
                String event_date=edate.getText().toString();
                long event_sec = 0;
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                try {
                    Date date = simpleDateFormat.parse(event_date);
                    event_sec =date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String event_id=dataref.push().getKey();
                Event e=new Event(event_id, event_name, event_date, event_sec, event_dur, cid, event_venue, event_disc);
                dataref.child(event_id).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(event_Add.this, event_name+" Added Sucessfully", Toast.LENGTH_LONG).show();
                        event_Add.this.finish();
                    }
                });
            }
        });

    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm a");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(event_Add.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(event_Add.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

}
