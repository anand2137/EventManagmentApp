package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ConcEventView extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conc_event_view);
        Intent intent=getIntent();
        final String cid=intent.getStringExtra("CID");
        final String club_name=intent.getStringExtra("CLUBNAME");
        setTitle(club_name);
        lv=findViewById(R.id.vlist);
        Query query= FirebaseDatabase.getInstance().getReference().child("Concluded Events")
                .orderByChild("club").equalTo(cid);
        FirebaseListOptions<Conc_Event> options=new FirebaseListOptions.Builder<Conc_Event>()
                .setQuery(query, Conc_Event.class)
                .setLayout(R.layout.vlister)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView ename=(TextView) v.findViewById(R.id.vlist_ename);
                TextView edate=(TextView) v.findViewById(R.id.vlist_edate);
                TextView evenue=(TextView) v.findViewById(R.id.vlist_evenue);
                TextView esum=(TextView) v.findViewById(R.id.vlist_sum);
                TextView edisc=(TextView) v.findViewById(R.id.vlist_disc);
                TextView eexp=(TextView) v.findViewById(R.id.vlist_exp);

                Conc_Event event=(Conc_Event)model;
                ename.setText(event.geteName().toString());
                edate.setText(event.geteDate().toString());
                evenue.setText(event.geteVenue().toString());
                esum.setText(event.geteSummary().toString());
                edisc.setText(event.geteDisc().toString());
                //eexp.setText(String.valueOf(event.geteExpenses()));
            }
        };
        lv.setAdapter(adapter);

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
