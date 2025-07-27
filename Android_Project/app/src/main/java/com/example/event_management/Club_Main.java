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
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Club_Main extends AppCompatActivity {

    Button bt, conc; ListView lv;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        final String cid=intent.getStringExtra("CID");
        final String club_name=intent.getStringExtra("CLUBNAME");
        setTitle(club_name);


        setContentView(R.layout.activity_club__main);
        lv=findViewById(R.id.clist);
        bt=findViewById(R.id.add_event);
        conc=findViewById(R.id.view_conc);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Club_Main.this, event_Add.class);
                i.putExtra("CID", cid);
                i.putExtra("CLUBNAME", club_name);
                startActivity(i);
            }
        });

        conc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Club_Main.this, ConcEventView.class);
                i.putExtra("CID", cid);
                i.putExtra("CLUBNAME", club_name);
                startActivity(i);
            }
        });
        Query query= FirebaseDatabase.getInstance().getReference().child("Events")
                .orderByChild("club").equalTo(cid);
        FirebaseListOptions<Event> options=new FirebaseListOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .setLayout(R.layout.club_event_list)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView ename=(TextView) v.findViewById(R.id.clist_ename);
                TextView edate=(TextView) v.findViewById(R.id.clist_date);
                Event event=(Event)model;
                ename.setText(event.geteName().toString());
                edate.setText(event.geteDate().toString());
            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(Club_Main.this, Edit_Del_Conc.class);
                Event e=(Event) parent.getItemAtPosition(position);
                i.putExtra("CLUBNAME", club_name);
                i.putExtra("CID", cid);
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
