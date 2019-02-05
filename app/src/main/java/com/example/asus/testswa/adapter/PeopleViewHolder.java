package com.example.asus.testswa.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.testswa.R;


public class PeopleViewHolder extends RecyclerView.ViewHolder{

    TextView name,gender,birth;
    CardView cardView;

    public PeopleViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        gender = (TextView) itemView.findViewById(R.id.gender);
        birth = (TextView) itemView.findViewById(R.id.birth);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
    }
}
