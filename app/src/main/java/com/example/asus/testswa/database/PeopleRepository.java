package com.example.asus.testswa.database;

import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

public class PeopleRepository implements DatabaseHelper {

    private DatabaseHelper helper;

    private PeopleRepository(DatabaseHelper helper) {
        this.helper = helper;
    }

    private static PeopleRepository mInstance;

    public static PeopleRepository getInstance(DatabaseHelper helper){
        if (mInstance == null){
            mInstance = new PeopleRepository(helper);
        }
        return mInstance;
    }

    @Override
    public Flowable<List<People>> getAllPeople() {
        return helper.getAllPeople();
    }

    @Override
    public void insertPeople(People... people) {
        helper.insertPeople(people);
    }
}
