package com.example.e_noticeboard;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {
    EditText Name, Reg, Password, Email, Phone, Department,dash;
    Button Register;
    TextView Login;
    private CircleImageView  Profilepix;
    private Uri  Mainimage = null;
    private ProgressDialog bar;
    private StudentInfo students;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        UI();
        students = new StudentInfo();


        Profilepix = findViewById(R.id.profile_image);
        bar = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Users");


        Profilepix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
                {
                    if (ContextCompat.checkSelfPermission(Registration.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

                    {
                        ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    }
                    else
                    {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(Registration.this);

                    }

                }

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent Intent = new Intent(Registration.this,LoginActivity.class);
                       startActivity(Intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                students.setName(Name.getText().toString());
                students.setRegno(Reg.getText().toString());
                students.setPassword(Password.getText().toString());
                students.setEmail(Email.getText().toString());
                students.setPhone(Phone.getText().toString());
                students.setDepartment(Department.getText().toString());
                students.setDash(dash.getText().toString());

                CreateAccount();


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Mainimage = result.getUri();
                Profilepix.setImageURI(Mainimage);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

    private void UI(){
        Name = findViewById(R.id.etname);
        Reg = findViewById(R.id.etRegno);
        Password = findViewById(R.id.etPassword);
        Email = findViewById(R.id.etEmail);
        Phone = findViewById(R.id.etPhone);
        Department = findViewById(R.id.etDept);
        dash = findViewById(R.id.etdash);

        Login = findViewById(R.id.tvSignin);
        Register = findViewById(R.id.btnRegister);

    }
    private void CreateAccount()
    {

        String UEmail = Email.getText().toString().trim();
        String UPassword = Password.getText().toString().trim();
        String URegNo = Reg.getText().toString().trim();
        String UName = Name.getText().toString().trim();
        String UPhone_Number = Phone.getText().toString().trim();
        String UDepartment = Department.getText().toString().trim();
        String Udash = dash.getText().toString().trim();




        if (TextUtils.isEmpty(UName))
        {
            Toast.makeText(Registration.this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(UPassword))
        {
            Toast.makeText(Registration.this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(UPhone_Number))
        {
            Toast.makeText(Registration.this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(UEmail))
        {
            Toast.makeText(Registration.this, "Please write your email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(URegNo))
        {
            Toast.makeText(Registration.this, "Please write your Registration number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(UDepartment))
        {
            Toast.makeText(Registration.this, "Please write your Department...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Udash))
        {
            Toast.makeText(Registration.this, "Please fill the last field...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            bar.setTitle("Registering....");
            bar.setMessage("Please wait, while we are checking your credentials. follow me on instagram  @ _ _Enike");
            bar.setCanceledOnTouchOutside(false);
            bar.show();

            ref.child(students.getDash()).setValue(students).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(Registration.this, "Registration Successfully...", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Registration.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{

                        Toast.makeText(Registration.this, "No Internet Connection...", Toast.LENGTH_SHORT).show();
                        bar.dismiss();

                    }
                }
            });


        }


    }


}