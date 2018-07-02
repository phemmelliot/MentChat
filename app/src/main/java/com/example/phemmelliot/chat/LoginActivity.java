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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout mEmail;
    TextInputLayout mPassword;
    private FirebaseAuth mAuth;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        mEmail = findViewById(R.id.login_email_et);
        mPassword = findViewById(R.id.login_password_et);
        coordinatorLayout = findViewById(R.id.login_layout);

        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if(validate(email, password))
                    logInUser(email, password);
                else
                    Toast.makeText(LoginActivity.this, "Inputs cannot be empty", Toast.LENGTH_LONG).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.login_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void logInUser(final String email, final String password) {
        mProgress.setMessage("Logging in...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgress.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else{
                   // Toast.makeText(LoginActivity.this, "Login Failed, try again", Toast.LENGTH_LONG).show();
                    mProgress.hide();
                    try {
                        throw task.getException();
                    }catch(FirebaseNetworkException e){
                        Snackbar.make(coordinatorLayout, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        logInUser(email,password);
                                    }
                                }).show();
                    } catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("LoginActivity", e.getMessage());
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

    public boolean validate( String email, String password)
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
