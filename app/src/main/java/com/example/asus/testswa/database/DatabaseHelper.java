package com.example.asus.testswa.database;

import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

public interface DatabaseHelper {

    Flowable<List<People>> getAllPeople();
    void insertPeople(People... people);

}
