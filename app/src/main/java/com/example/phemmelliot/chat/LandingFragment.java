package com.example.phemmelliot.chat;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {
    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dashView = inflater.inflate(R.layout.fragment_landing, container, false);

        Button mentorBtn = dashView.findViewById(R.id.dash_mentor);
        mentorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("version", "mentor");
                startActivity(intent);
            }
        });

        Button menteeBtn = dashView.findViewById(R.id.dash_mentee);
        menteeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("version", "mentee");
                startActivity(intent);
            }
        });

        Button postBlog = dashView.findViewById(R.id.dash_blog);
        postBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("version", "post-blog");
            }
        });

        Button tutorials = dashView.findViewById(R.id.dash_tutorial);
        tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("version", "tutorials");
            }
        });


        return dashView;
    }

}
