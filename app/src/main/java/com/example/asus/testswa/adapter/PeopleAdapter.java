package com.example.asus.testswa.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.testswa.model.People;
import com.example.asus.testswa.R;
import com.example.asus.testswa.sqlite.DatabaseHelper;

import java.util.List;
import java.util.Objects;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleViewHolder> {

    private Context context;
    private List<People> people;
    private Dialog mDialog;
    private DatabaseHelper databaseHelper;

    public PeopleAdapter(Context context, List<People> people) {
        this.context = context;
        this.people = people;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.people_layout,viewGroup,false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

        holder.name.setText(people.get(pos).getName());
        holder.birth.setText(people.get(pos).getBirth_year());
        holder.gender.setText(people.get(pos).getGender());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(context);
                databaseHelper = new DatabaseHelper(context);
                mDialog.setContentView(R.layout.dialog_people);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView name = mDialog.findViewById(R.id.dialog_name);
                TextView birth = mDialog.findViewById(R.id.dialog_birth);
                TextView gender = mDialog.findViewById(R.id.dialog_gender);
                TextView height = mDialog.findViewById(R.id.dialog_height);
                TextView mass = mDialog.findViewById(R.id.dialog_mass);
                TextView hair = mDialog.findViewById(R.id.dialog_hair);
                TextView eye = mDialog.findViewById(R.id.dialog_eye);
                final Button save = mDialog.findViewById(R.id.dialog_save);

                name.setText(people.get(pos).getName());
                birth.setText(people.get(pos).getBirth_year());
                gender.setText(people.get(pos).getGender());
                height.setText(people.get(pos).getHeight());
                mass.setText(people.get(pos).getMass());
                hair.setText(people.get(pos).getHair_color());
                eye.setText(people.get(pos).getEye_color());

                if (databaseHelper.checkName(people.get(pos).getName())){
                    save.setVisibility(View.GONE);
                }else {
                    save.setVisibility(View.VISIBLE);
                }

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (databaseHelper.checkName(people.get(pos).getName())){
                            Toast.makeText(context,R.string.saved_people_1,Toast.LENGTH_SHORT).show();
                        }else {
                            databaseHelper.addUser(people.get(pos));
                            Toast.makeText(context, R.string.saved_people_2,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
