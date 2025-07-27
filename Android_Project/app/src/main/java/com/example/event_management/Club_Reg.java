package com.example.event_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Club_Reg extends AppCompatActivity {

    EditText uname, uemail, uregno, cfacad, upass;
    Button bt;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__reg);
        uname=findViewById(R.id.club_name);
        uemail=findViewById(R.id.club_email);
        uregno=findViewById(R.id.club_regno);
        cfacad=findViewById(R.id.club_facad);
        upass=findViewById(R.id.club_pass);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.add_club).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=uname.getText().toString().trim();
                final String email=uemail.getText().toString().trim();
                final String regno=uregno.getText().toString().trim();
                String password=upass.getText().toString().trim();
                final String facad=cfacad.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() ||password.isEmpty() || regno.isEmpty()||facad.isEmpty())
                    return;

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    final String key=mAuth.getCurrentUser().getUid();
                                    //Club(String cId, String cName, String cRegNo, String cEmail, String cFacAd)
                                    Club user = new Club(key, name, regno, email, facad);

                                    FirebaseDatabase.getInstance().getReference("Club")
                                            .child(key)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(Club_Reg.this, name+" registered successfully", Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent(Club_Reg.this, Club_Main.class);
                                                intent.putExtra("CID", key);
                                                intent.putExtra("CLUBNAME", name);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(Club_Reg.this, "Try Again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(Club_Reg.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
