package com.example.e_noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class post extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressDialog bar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Logout:{
                Logout();
            }
        }

        return super.onOptionsItemSelected(item);
    }
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(post.this,LoginActivity.class));
        bar.show();
        bar.setTitle("Loging Out....");
        bar.setMessage("Please wait, Loging Out... . follow me on instagram  @ _ _Enike");
        bar.setCanceledOnTouchOutside(false);
        bar.show();

        Toast.makeText(post.this, "Please input Username ...", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        bar = new ProgressDialog(this);

        firebaseAuth = firebaseAuth.getInstance();




    }
}