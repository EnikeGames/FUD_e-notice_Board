package com.example.e_noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ProgressDialog bar;
    ImageView BackButton;
    TextView Sign;
    TextView Admin;
    TextView Hello;
    Button LoginBtn;
    EditText RegNo;
    EditText Password;
    private String DbName = "Users";
    private StudentInfo student;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        student = new StudentInfo();

        bar = new ProgressDialog(this);

        Hello = findViewById(R.id.tvhello);
        Admin = findViewById(R.id.tvadmin);
        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Intent = new Intent(LoginActivity.this, AdminLog.class);
                startActivity(Intent);

            }
        });

        BackButton = findViewById(R.id.Backbtn);
        Sign = findViewById(R.id.tvSignup);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(LoginActivity.this, Registration.class);
                startActivity(Intent);

            }
        });


        LoginBtn = findViewById(R.id.button);
        RegNo = findViewById(R.id.etReg);
        Password = findViewById(R.id.etpass);

        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.show();
                bar.setTitle("Logging in....");
                bar.setMessage("Please wait, while we are checking your credentials. follow me on instagram  @ _ _Enike");
                bar.setCanceledOnTouchOutside(false);
                bar.show();

                final String REGNO = RegNo.getText().toString();
                final String PASS = Password.getText().toString();

                if (REGNO.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please input Username ...", Toast.LENGTH_SHORT).show();
                    bar.dismiss();

                }
                else if(PASS.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input Password ...", Toast.LENGTH_SHORT).show();
                    bar.dismiss();
                }
                else {
                        ref.child(REGNO).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                StudentInfo student = dataSnapshot.getValue(StudentInfo.class);

                                try {
                                    if (PASS.equals(student.getPassword()))
                                    {
                                            Toast.makeText(LoginActivity.this, "Welcome you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                            bar.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, post.class);
                                            startActivity(intent);


                                    }
                                  else {
                                            Toast.makeText(LoginActivity.this, "incorrect password...", Toast.LENGTH_SHORT).show();
                                            bar.dismiss();

                                        }
                                    }

                                catch(Exception e) {
                                    Toast.makeText(LoginActivity.this, "Account with " + REGNO + " does not exists.", Toast.LENGTH_SHORT).show();
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