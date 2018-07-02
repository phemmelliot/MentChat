package com.example.phemmelliot.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreate;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = findViewById(R.id.register_tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);

        mDisplayName = findViewById(R.id.tv_display_name);
        mEmail = findViewById(R.id.email_tv);
        mPassword = findViewById(R.id.password_tv);
        mCreate = findViewById(R.id.create_btn);
        coordinatorLayout = findViewById(R.id.register_layout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String username = mDisplayName.getEditText().getText().toString();
                if(validate(username, email, password))
                  createUser(username, email, password);
            }
        });



    }

    private void createUser(final String username, final String email, final String password) {
        mProgress.setMessage("Registering Account...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    HashMap<String, String> user = new HashMap<>();
                    user.put("name", username);
                    user.put("status", "Hey there, I am using mentChat");
                    user.put("image", "default");
                    user.put("thumb_image", "default");

                    String userId = mAuth.getCurrentUser().getUid();

                    mDatabase.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mProgress.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

                }else{
                    mProgress.hide();
                    try {
                        throw task.getException();
                    } catch (FirebaseNetworkException e){
                        Snackbar.make(coordinatorLayout, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        createUser(username,email,password);
                                    }
                                }).show();
                    } catch (FirebaseAuthEmailException | FirebaseAuthUserCollisionException e){
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
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

    public boolean validate(String username, String email, String password)
    {
        boolean valid = true;
        if(TextUtils.isEmpty(email))
        {
            mEmail.setError("Required");
            valid = false;
        }
        else if(!validateEmail(email)){
            mEmail.setError("Invalid Email");
            valid = false;
        }
        else
            mEmail.setError(null);

        if(TextUtils.isEmpty(password))
        {
            mPassword.setError("Required");
            valid = false;
        }
        else if(!validatePass(password)){
            mPassword.setError("Password length should be greater than 5");
            valid = false;
        }
        else
            mPassword.setError(null);

        if(TextUtils.isEmpty(username))
        {
            mDisplayName.setError("Required");
            valid = false;
        }
        else
            mDisplayName.setError(null);

        return valid;
    }

    public boolean validateEmail(String email)
    {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePass(String password)
    {
        return password.length()>5;
    }
}
