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

public class User_Reg extends AppCompatActivity {

    EditText uname, uemail, uregno, umob, upass;
    Button bt;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__reg);

        uname=findViewById(R.id.user_name);
        uemail=findViewById(R.id.user_email);
        uregno=findViewById(R.id.user_regno);
        umob=findViewById(R.id.user_mob);
        upass=findViewById(R.id.user_pass);
        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=uname.getText().toString().trim();
                final String email=uemail.getText().toString().trim();
                final String regno=uregno.getText().toString().trim();
                String password=upass.getText().toString().trim();
                final String mob=umob.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() ||password.isEmpty() || regno.isEmpty()||mob.isEmpty())
                    return;

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    final String key=mAuth.getCurrentUser().getUid();
                                    User user = new User(key, regno, name, mob);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(key)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(User_Reg.this, name+" registered successfully", Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent(User_Reg.this, User_Main.class);
                                                intent.putExtra("UID", key);
                                                intent.putExtra("USERNAME", name);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(User_Reg.this, "Try Again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(User_Reg.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }
}
