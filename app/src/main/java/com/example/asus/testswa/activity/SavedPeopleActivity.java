package com.example.asus.testswa.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.testswa.adapter.PeopleAdapter;
import com.example.asus.testswa.model.People;
import com.example.asus.testswa.R;
import com.example.asus.testswa.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SavedPeopleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<People> peopleList;
    private DatabaseHelper databaseHelper;
    private PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_people);

        databaseHelper = new DatabaseHelper(this);
        peopleList = new ArrayList<>();
        getDataFromSQLite();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_saved);
        adapter = new PeopleAdapter(this,peopleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                peopleList.clear();
                peopleList.addAll(databaseHelper.getAllBeneficiary());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
