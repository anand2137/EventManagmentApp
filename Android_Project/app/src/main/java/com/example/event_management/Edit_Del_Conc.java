package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Edit_Del_Conc extends AppCompatActivity {

    Button edit, del, conc, save_edit, cancel, save_conc;
    LinearLayout e,d,c;
    DatabaseReference dataref;
    TextView conctx, deltx;
    EditText name, venue, date, dur, disc, summary, expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__del__conc);
        Intent i=getIntent();
        final String club_name=i.getStringExtra("CLUBNAME");
        setTitle(club_name);

        edit=(Button)findViewById(R.id.edit_top_bt);
        del=(Button)findViewById(R.id.delete_top_bt);
        conc=(Button)findViewById(R.id.conclude_top_bt);
        save_edit=findViewById(R.id.edit_save);
        cancel=findViewById(R.id.cancel_bt);
        save_conc=findViewById(R.id.conclude_bt);

        conctx=(TextView) findViewById(R.id.conc_name);
        deltx=findViewById(R.id.del_name);

        name=findViewById(R.id.edit_ename_et);
        venue=findViewById(R.id.edit_evenue_et);
        date=findViewById(R.id.edit_edate_et);
        dur=findViewById(R.id.edit_eduration_et);
        disc=findViewById(R.id.edit_edisc_et);
        summary=findViewById(R.id.summary);
        expenses=findViewById(R.id.expenses);

        conctx.setText(i.getStringExtra("EVENT_NAME"));
        deltx.setText(i.getStringExtra("EVENT_NAME"));
        name.setText(i.getStringExtra("EVENT_NAME"));
        venue.setText(i.getStringExtra("EVENT_VENUE"));
        date.setText(i.getStringExtra("EVENT_DATE"));
        dur.setText(i.getStringExtra("EVENT_DURATION"));
        disc.setText(i.getStringExtra("EVENT_DISC"));
        final String cid= i.getStringExtra("CID");
        final String eid=i.getStringExtra("EVENT_ID");
        dataref= FirebaseDatabase.getInstance().getReference("Events");

        e=findViewById(R.id.edit_view);
        d=findViewById(R.id.del_view);
        c=findViewById(R.id.conc_view);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setVisibility(View.VISIBLE);
                d.setVisibility(View.GONE);
                c.setVisibility(View.GONE);

                save_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataref.child(eid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("eName").setValue(name.getText().toString());
                                dataSnapshot.getRef().child("eVenue").setValue(venue.getText().toString());
                                dataSnapshot.getRef().child("eDate").setValue(date.getText().toString());
                                dataSnapshot.getRef().child("eDuration").setValue(dur.getText().toString());
                                dataSnapshot.getRef().child("eDisc").setValue(disc.getText().toString());
                                long event_sec = 0;
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                                try {
                                    Date dt = simpleDateFormat.parse(date.getText().toString());
                                    event_sec =dt.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dataSnapshot.getRef().child("eSecond").setValue(event_sec);
                                Toast.makeText(Edit_Del_Conc.this, "Data updated Sucessfully...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });



            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setVisibility(View.GONE);
                d.setVisibility(View.VISIBLE);
                c.setVisibility(View.GONE);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataref.child(eid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Edit_Del_Conc.this, name+" is deleted", Toast.LENGTH_SHORT).show();
                                    Edit_Del_Conc.this.finish();
                                }
                                else
                                    Toast.makeText(Edit_Del_Conc.this, name+" not yet deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


        conc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.setVisibility(View.GONE);
                d.setVisibility(View.GONE);
                c.setVisibility(View.VISIBLE);

                save_conc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String exp=expenses.getText().toString();
                        final String sum=summary.getText().toString();
                        final DatabaseReference concref=FirebaseDatabase.getInstance().getReference("Concluded Events").child(eid);
                        final DatabaseReference fromref=dataref.child(eid);

                        fromref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                concref.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            concref.child("eExpences").setValue(exp);
                                            concref.child("eSummary").setValue(sum);
                                            fromref.setValue(null);
                                        }
                                        else {

                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Edit_Del_Conc.this.finish();
                    }
                });



            }
        });

    }

}
