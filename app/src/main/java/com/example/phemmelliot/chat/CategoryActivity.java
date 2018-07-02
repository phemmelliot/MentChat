package com.example.phemmelliot.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phemmelliot.chat.card.SubmitCreditCardActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.PayMent{
    private String category;
    private RecyclerView recyclerView;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private HashMap<String, String> categories;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.category_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //categories = new HashMap<>();

        Intent intent  = getIntent();
        category = intent.getStringExtra("version");

        categories = new HashMap<>();

        mProgress = new ProgressDialog(this);



        recyclerView = findViewById(R.id.category_recycler_view);
        //recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase = FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabase.keepSynced(true);
        //ArrayList<String> categories = new ArrayList<>();

//        list = new ArrayList<>();
//
//        list.add( "Android");
//        list.add( "Java");
//        list.add("Python");
//        list.add("JavaScript");
//        list.add("Angular");
//        list.add("Php");
//        list.add("CodeIgniter");
//
//        setAdapter(list);


        mProgress.setMessage("Loading categories...");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Log.d("Category", dataSnapshot.toString());
                    String stringValue = ds.getValue(String.class);
                    //Log.d("Category", stringValue);
                    list.add(stringValue);

                    //categories = (ArrayList<String>)(ds.getValue());
                    //Log.i("Firebase", stringValue);
                }
                Integer len = list.size();
                setAdapter(list);
                Log.d("Length0", len.toString());

                mProgress.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgress.dismiss();
                Toast.makeText(CategoryActivity.this, "There was an error accessing the database", Toast.LENGTH_SHORT).show();
                Log.d("Category", databaseError.getDetails());
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setAdapter(ArrayList<String> list) {
        Toast.makeText(this, category, Toast.LENGTH_LONG).show();
        CategoryAdapter adapter = new CategoryAdapter(list, category);
        recyclerView.setAdapter(adapter);
        Integer len = list.size();
        Log.d("Length1", len.toString());
    }

    @Override
    public void pay(String mentee, String s) {
//        String publicKey = "FLWPUBK-b72ae1232f2241048f0ee502d91d9aea-X";
//        String secretKey = "FLWSECK-0bfab0cd61b86021342119e593178bf7-X";
//        new RavePayManager(this).setAmount(20.0)
//                .setCountry("NG")
//                .setCurrency("NGN")
//                .setEmail("davash001@gmail.com")
//                .setfName("David")
//                .setlName("Ashamu")
//                .setNarration("This is a test")
//                .setPublicKey(publicKey)
//                .setSecretKey(secretKey)
//                .setTxRef("12345")
//                .acceptAccountPayments(true)
//                        .acceptCardPayments(true)
//                        .onStagingEnv(true)
//                .initialize();
        if(mentee.equals("mentee")) {
            Intent intent = new Intent(this, SubmitCreditCardActivity.class);
            intent.putExtra("category", s);
            startActivity(intent);
        }else if(mentee.equals("mentor")){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("mentor");
           // String userId
           // ref.setValue()
            Intent intent = new Intent(this, MentorActivity.class);
            //intent.putExtra("category", s);
            startActivity(intent);
        }
    }
}



//        categories.put("1", "Android");
//        categories.put("2", "Java");
//        categories.put("3", "Python");
//        categories.put("4", "JavaScript");
//        categories.put("5", "Angular");
//        categories.put("6", "Php");
//        categories.put("7", "Codeigniter");
//
//        mDatabase.setValue(categories).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(CategoryActivity.this, "Uploaded categories successfully", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(CategoryActivity.this, "Uploading categories failed", Toast.LENGTH_LONG).show();            }
//            }
//        });
