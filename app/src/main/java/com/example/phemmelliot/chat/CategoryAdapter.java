package com.example.phemmelliot.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by phemmelliot on 7/1/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    ArrayList<String> list = new ArrayList<>();
    String category;
    Context context;
    PayMent payMent;

    public CategoryAdapter(ArrayList<String> list, String category){
        this.list = list;
        this.category = category;
        Integer len = list.size();
        Log.d("Length2", len.toString());
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View categoryView = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
        holder.text.setText(list.get(position));
        //holder.itemView.setTag();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You have clicked me", Toast.LENGTH_LONG).show();
                if(category.equals("mentee")){
                    Toast.makeText(context, "You have clicked me", Toast.LENGTH_LONG).show();
                    initiatePay("mentee", list.get(position));
                }else if(category.equals("mentor")){
                    initiatePay("mentor", list.get(position));
                }
            }
        });
        Integer pos = position;
        Log.d("Text", pos.toString());
    }

    private void initiatePay(String mentee, String s) {
        payMent = (PayMent) context;
        payMent.pay(mentee, s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView text;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            text = itemView.findViewById(R.id.category_text);
        }

        public void setName(String name){
            TextView textView = mView.findViewById(R.id.category_text);
            textView.setText(name);

        }
    }

    public interface PayMent{
        void pay(String mentee, String s);
    }
}
