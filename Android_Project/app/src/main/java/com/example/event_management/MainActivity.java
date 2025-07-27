package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button regUser, regClub, login_bt;
    EditText email, pass;
    DatabaseReference userref, clubref;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.login_email);
        pass=findViewById(R.id.login_password);
        login_bt=findViewById(R.id.login_bt);
        regUser=findViewById(R.id.register_user_bt);
        regClub=findViewById(R.id.register_club_bt);
        userref= FirebaseDatabase.getInstance().getReference("Users");
        clubref= FirebaseDatabase.getInstance().getReference("Club");
        mAuth=FirebaseAuth.getInstance();

        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail=email.getText().toString().trim();
                String password=pass.getText().toString().trim();
                if(uemail.isEmpty())
                {
                    email.setError("Enter Email");
                    email.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass.setError("Enter Password");
                    pass.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(uemail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            userref.orderByKey().equalTo(uid)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()) {
                                                String an=dataSnapshot.child(uid).child("uName").getValue().toString();
                                                Intent intent = new Intent(MainActivity.this, User_Main.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("UID", uid);
                                                intent.putExtra("USERNAME", "Hi "+an+",");
                                                startActivity(intent);
                                            }
                                            else {
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                            clubref.orderByKey().equalTo(uid)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){
                                                String an=dataSnapshot.child(uid).child("cName").getValue().toString();
                                                Intent intent = new Intent(MainActivity.this, Club_Main.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("CID", uid);
                                                intent.putExtra("CLUBNAME", an);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                        else
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });





        regUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, User_Reg.class);
                startActivity(i);
            }
        });

        regClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Club_Reg.class);
                startActivity(i);
            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(mAuth.getCurrentUser()!=null)
//        {
//            sdsdg;
//            ahbaghd;
//            asgs;
//        }
//    }
}
