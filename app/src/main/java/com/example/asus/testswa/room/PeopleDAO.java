package com.example.asus.testswa.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.asus.testswa.model.People;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PeopleDAO {

    @Query("SELECT * FROM people")
    Flowable<List<People>> getAllPeople();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPeople(People... people);
}
