package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class User_Main extends AppCompatActivity {

    ListView lv;
    Button bt;
    FirebaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main);

        Intent intent=getIntent();
        final String username=intent.getStringExtra("USERNAME");
        final String uid= intent.getStringExtra("UID");
        setTitle(username);
//      bt=findViewById(R.id.bt);
        lv=findViewById(R.id.lv);

//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(User_Main.this, Intrested_Events.class);
//                intent.putExtra("USERNAME", username);
//                intent.putExtra("UID", uid);
//                startActivity(intent);
//            }
//        });

        Query query= FirebaseDatabase.getInstance().getReference().child("Events")
                .orderByChild("eSecond").startAt(System.currentTimeMillis());
        FirebaseListOptions<Event> options=new FirebaseListOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .setLayout(R.layout.user_event_list)
                .build();
//
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView ename=(TextView) v.findViewById(R.id.ulist_ename);
                TextView edate=(TextView) v.findViewById(R.id.ulist_edate);
                final TextView eclub=(TextView) v.findViewById(R.id.ulist_eclub);
                TextView evenue=(TextView) v.findViewById(R.id.ulist_evenue);
                final Event e=(Event) model;
                ename.setText(e.geteName());
                edate.setText(e.geteDate());
                evenue.setText(e.geteVenue());
                FirebaseDatabase.getInstance().getReference("Club").orderByKey().equalTo(e.getClub()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            eclub.setText(dataSnapshot.child(e.getClub()).child("cName").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent i=new Intent(User_Main.this, User_Event_Details.class);
                final Event e=(Event) parent.getItemAtPosition(position);
                FirebaseDatabase.getInstance().getReference("Club").orderByKey().equalTo(e.getClub()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                                i.putExtra("EVENT_CLUB", dataSnapshot.child(e.getClub()).child("cName").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                i.putExtra("USERNAME", username);
                i.putExtra("UID", uid);
                i.putExtra("EVENT_NAME", e.geteName());
                i.putExtra("EVENT_VENUE", e.geteVenue());
                i.putExtra("EVENT_DATE", e.geteDate());
                i.putExtra("EVENT_DURATION", e.geteDuration());
                i.putExtra("EVENT_DISC", e.geteDisc());
                i.putExtra("EVENT_ID", e.geteId());
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
