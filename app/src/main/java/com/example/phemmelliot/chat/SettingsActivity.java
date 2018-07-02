package com.example.phemmelliot.chat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private TextView mDisplayName;
    private TextView mStatus;
    private Button mChangeImage;
    private Button mChangeStatus;
    private String newStatus;
    private ProgressDialog mProgress;
    private CircleImageView image;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayName = findViewById(R.id.setting_display_name);
        mStatus = findViewById(R.id.setting_status_tv);
        mChangeImage = findViewById(R.id.setting_change_image);
        mChangeStatus = findViewById(R.id.setting_change_status);
        image = findViewById(R.id.setting_image);

        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference("profile-images");

        userId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot == null)) {
                    mDisplayName.setText(dataSnapshot.child("name").getValue().toString());
                    mStatus.setText(dataSnapshot.child("status").getValue().toString());
                    final String imageFromInternet = dataSnapshot.child("image").toString();

                    //use glide to load image from firebase storage url

                    //Toast.makeText(SettingsActivity.this, dataSnapshot.child("image").toString(), Toast.LENGTH_LONG).show();
//                    Glide.with(SettingsActivity.this)
//                            .load(dataSnapshot.child("image").getValue().toString())
//                            .apply(new RequestOptions()
//                                    .placeholder(R.drawable.placeholder))
//                            .into(image);

                    Picasso.with(SettingsActivity.this).load(imageFromInternet).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.placeholder).into(image, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(SettingsActivity.this).load(imageFromInternet).placeholder(R.drawable.placeholder).into(image);

                        }
                    });
                } else {
                    Toast.makeText(SettingsActivity.this, "DataSnapshot is null", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        mChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(2, 2)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);
            }
        });

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Status");
        final EditText input = new EditText(this);
        //input.setPadding(16, 0, 16, 0);
        //input.setPaddingRelative(16, 0, 16, 0);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(mStatus.getText().toString());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newStatus = input.getText().toString();
                dialog.dismiss();
                changeStatus();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void changeStatus() {
        mProgress.setMessage("Updating Status...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        mDatabase.child("status").setValue(newStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mProgress.dismiss();
                    mStatus.setText(newStatus);
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseNetworkException | DatabaseException e) {
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("SettingsActivity", e.getMessage());
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProgress.setMessage("Uploading and Processing Image...");
                mProgress.setCanceledOnTouchOutside(false);
                mProgress.show();
                Uri resultUri = result.getUri();
                uploadImage(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                try {
                    throw error;
                } catch (Exception e) {
                    Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void uploadImage(final Uri resultUri) {
        StorageReference filepath = mStorage.child(mAuth.getCurrentUser().getUid() + ".jpg");
        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    File thumb_file = new File(resultUri.getPath());
                    Bitmap thumb_bitmap = null;
                    try {
                        thumb_bitmap = new Compressor(SettingsActivity.this)
                                .setMaxHeight(200)
                                .setMaxWidth(200)
                                .setQuality(75)
                                .compressToBitmap(thumb_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (thumb_bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        String download_url = task.getResult().getDownloadUrl().toString();
                        uploadCompressedBitmap(data, resultUri, download_url);
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseNetworkException | StorageException e) {
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("SettingsActivity", e.getMessage());
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void uploadCompressedBitmap(byte[] data, final Uri resultUri, final String downloadUrl) {
        StorageReference bitmapRef = mStorage.child("thumbs").child(userId + ".jpg");
        bitmapRef.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    String thumbUrl = task.getResult().getDownloadUrl().toString();
                    Map<String, Object> update = new HashMap<>();
                    update.put("image", downloadUrl);
                    update.put("thumb_image", thumbUrl);

                    mDatabase.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mProgress.hide();
                                image.setImageURI(resultUri);
                                Toast.makeText(SettingsActivity.this, "Image Uploading successful", Toast.LENGTH_LONG).show();

                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseNetworkException | DatabaseException e) {
                                    Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Log.d("SettingsActivity", e.getMessage());
                                    Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseNetworkException | StorageException e) {
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("SettingsActivity", e.getMessage());
                        Toast.makeText(SettingsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
