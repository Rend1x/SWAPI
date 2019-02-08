package com.example.asus.testswa.database;

import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

public class PeopleRepository implements DatabaseHelper {

    private DatabaseHelper mDBHelper;

    private PeopleRepository(DatabaseHelper mDBHelper) {
        this.mDBHelper = mDBHelper;
    }

    private static PeopleRepository sInstance;

    public static PeopleRepository getInstance(DatabaseHelper mDBHelper) {
        if (sInstance == null) {
            sInstance = new PeopleRepository(mDBHelper);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<People>> getAllPeople() {
        return mDBHelper.getAllPeople();
    }

    @Override
    public void insertPeople(People... people) {
        mDBHelper.insertPeople(people);
    }
}
