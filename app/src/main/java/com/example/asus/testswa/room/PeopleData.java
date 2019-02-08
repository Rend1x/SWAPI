package com.example.asus.testswa.room;

import com.example.asus.testswa.database.DatabaseHelper;
import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

public class PeopleData implements DatabaseHelper {

    private PeopleDAO mPeopleDAO;
    private static PeopleData sInstance;

    private PeopleData(PeopleDAO helper) {
        this.mPeopleDAO = helper;
    }

    public static PeopleData getInstance(PeopleDAO peopleDAO) {

        if (sInstance == null) {
            sInstance = new PeopleData(peopleDAO);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<People>> getAllPeople() {
        return mPeopleDAO.getAllPeople();
    }

    @Override
    public void insertPeople(People... people) {
        mPeopleDAO.insertPeople(people);
    }
}
