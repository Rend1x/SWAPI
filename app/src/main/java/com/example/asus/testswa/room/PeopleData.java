package com.example.asus.testswa.room;

import com.example.asus.testswa.database.DatabaseHelper;
import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

public class PeopleData implements DatabaseHelper {

    private PeopleDAO peopleDAO;
    private static PeopleData mInstance;

    private PeopleData(PeopleDAO helper) {
        this.peopleDAO = helper;
    }

    public static PeopleData getInstance(PeopleDAO peopleDAO){

        if (mInstance == null){
            mInstance = new PeopleData(peopleDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<List<People>> getAllPeople() {
        return peopleDAO.getAllPeople();
    }

    @Override
    public void insertPeople(People... people) {
        peopleDAO.insertPeople(people);
    }
}
