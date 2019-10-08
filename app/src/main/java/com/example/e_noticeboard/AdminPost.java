package com.example.e_noticeboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class AdminPost extends AppCompatActivity {

    EditText description;
    ImageView postImg;
    Button postbutton;
    private Uri postimageuri = null;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        description = findViewById(R.id.etdesc);
        postImg = findViewById(R.id.ivpost);
        postbutton = findViewById(R.id.btnpost);


        postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(AdminPost.this);

            }
        });

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Dsc = description.getText().toString();

                if(!TextUtils.isEmpty(Dsc)&& postimageuri!=null)
                {
                    String RandomName = FieldValue.serverTimestamp().toString();
                    StorageReference filePath = storageReference.child("post_images").child(RandomName + ".jpg");
                    filePath.putFile(postimageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful())
                            {
                                String downloadUri = task.getResult().getUploadSessionUri().toString();

                                Map<String, Object> postmap = new HashMap<>();

                                postmap.put("image Url",downloadUri);
                                postmap.put("description", description);
                                postmap.put("timestamp", FieldValue.serverTimestamp());

                                firebaseFirestore.collection("posts").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(AdminPost.this, "Post Made Successfully...", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(AdminPost.this,post.class);
                                            startActivity(intent);

                                        }
                                        else
                                        {
                                            Toast.makeText(AdminPost.this, "error 2...", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                            }
                            else
                            {
                                Toast.makeText(AdminPost.this, "Error1...", Toast.LENGTH_SHORT).show();


                            }


                        }
                    });

                }
                else{
                    Toast.makeText(AdminPost.this, "please insert both picture and description..", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                postimageuri = result.getUri();
                postImg.setImageURI(postimageuri);


            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}
