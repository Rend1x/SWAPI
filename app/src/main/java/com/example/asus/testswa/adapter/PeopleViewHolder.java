package com.example.asus.testswa.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.asus.testswa.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PeopleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.birth)
    TextView mBirth;
    @BindView(R.id.card_view)
    CardView mCardView;

    public PeopleViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
