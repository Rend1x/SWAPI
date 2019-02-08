package com.example.asus.testswa.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.asus.testswa.model.People;

import static com.example.asus.testswa.room.PeopleDatabase.DATABASE_VER;

@Database(entities = People.class, version = DATABASE_VER)
public abstract class PeopleDatabase extends RoomDatabase {
    static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "People-Database";

    public abstract PeopleDAO peopleDAO();

    private static PeopleDatabase mInstance;

    public static PeopleDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, PeopleDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}
