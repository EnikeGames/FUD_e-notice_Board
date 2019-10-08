package com.example.e_noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminLog extends AppCompatActivity {

    TextView Admin;
    EditText dash, password;
    Button LoginButton;
    AdminLog admin ;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProgressDialog bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log);
        admin = new AdminLog();

        bar = new ProgressDialog(this);
        LoginButton= findViewById(R.id.button);
        dash = findViewById(R.id.etReg);
        password = findViewById(R.id.etpass);
        Admin = findViewById(R.id.tvadmin);



        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Intent = new Intent(AdminLog.this, LoginActivity.class);
                startActivity(Intent);

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.show();
                bar.setTitle("Admin Logging in....");
                bar.setMessage("Please wait, while we are checking your credentials. follow me on instagram  @ _ _Enike");
                bar.setCanceledOnTouchOutside(false);
                bar.show();
                final String REGNO = dash.getText().toString();
                final String PASS = password.getText().toString();

                if (REGNO.isEmpty())
                {
                    Toast.makeText(AdminLog.this, "Please input admin id ...", Toast.LENGTH_SHORT).show();
                    bar.dismiss();

                }
                else if(PASS.isEmpty()){
                    Toast.makeText(AdminLog.this, "Please input Password ...", Toast.LENGTH_SHORT).show();
                    bar.dismiss();
                }
                else
                    {
                        ref = FirebaseDatabase.getInstance().getReference().child("Admins");
                        ref.child(REGNO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                AdminsOnly admin = dataSnapshot.getValue(AdminsOnly.class);
                                try {
                                    if (PASS.equals(admin.getPassword()))
                                    {
                                        Toast.makeText(AdminLog.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            bar.dismiss();

                                            Intent intent = new Intent(AdminLog.this, AdminPost.class);
                                            startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(AdminLog.this, "incorrect password...", Toast.LENGTH_SHORT).show();
                                        bar.dismiss();

                                    }
                                }

                                catch(Exception e) {
                                    Toast.makeText(AdminLog.this, "Admin ID with " + REGNO + " does not exists.", Toast.LENGTH_SHORT).show();
                                    bar.dismiss();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


            }



        });
}

}
