package com.example.asus.testswa.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.testswa.model.People;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "PeopleList.db";

    private final static String[] columnsAdapter = new String[] {

            PeopleList.PeopleListEntry.COLUMN_NAME,
            PeopleList.PeopleListEntry.COLUMN_HEIGHT,
            PeopleList.PeopleListEntry.COLUMN_MASS,
            PeopleList.PeopleListEntry.COLUMN_HAIR_COLOR,
            PeopleList.PeopleListEntry.COLUMN_EYE_COLOR,
            PeopleList.PeopleListEntry.COLUMN_BIRTH_YEAR,
            PeopleList.PeopleListEntry.COLUMN_GENDER,

    };


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private final String SQL_TABLE_PEOPLE = "CREATE TABLE " + PeopleList.PeopleListEntry.TABLE_NAME + " (" +
            PeopleList.PeopleListEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            PeopleList.PeopleListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            PeopleList.PeopleListEntry.COLUMN_HEIGHT + " TEXT NOT NULL UNIQUE, " +
            PeopleList.PeopleListEntry.COLUMN_MASS + " TEXT, " +
            PeopleList.PeopleListEntry.COLUMN_HAIR_COLOR + " TEXT, " +
            PeopleList.PeopleListEntry.COLUMN_EYE_COLOR + " TEXT, " +
            PeopleList.PeopleListEntry.COLUMN_BIRTH_YEAR + " TEXT, " +
            PeopleList.PeopleListEntry.COLUMN_GENDER + " TEXT, " +
            "UNIQUE (" + PeopleList.PeopleListEntry.COLUMN_NAME + ") ON CONFLICT IGNORE " +
            "); ";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_PEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_BENEFICIARY_TABLE =
                "DROP TABLE IF EXISTS " + PeopleList.PeopleListEntry.TABLE_NAME;

        db.execSQL(DROP_BENEFICIARY_TABLE);

        onCreate(db);

    }

    public ArrayList<People> getAllBeneficiary() {

        String sortOrder =
                PeopleList.PeopleListEntry.COLUMN_NAME + " ASC";

        ArrayList<People> peopleList = new ArrayList<>();

        database = this.getReadableDatabase();


        Cursor cursor = database.query(PeopleList.PeopleListEntry.TABLE_NAME,
                columnsAdapter,
                null,
                null,
                null,
                null,
                sortOrder);


        if (cursor.moveToFirst()) {
            do {
                People people = new People();

                people.setName(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_NAME)));
                people.setHeight(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_HEIGHT)));
                people.setMass(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_MASS)));
                people.setHair_color(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_HAIR_COLOR)));
                people.setEye_color(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_EYE_COLOR)));
                people.setGender(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_GENDER)));
                people.setBirth_year(cursor.getString(cursor.getColumnIndex(PeopleList.PeopleListEntry.COLUMN_BIRTH_YEAR)));

                peopleList.add(people);

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return peopleList;

    }

    public void addUser(People people) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PeopleList.PeopleListEntry.COLUMN_NAME,people.getName());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_HEIGHT,people.getHeight());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_MASS,people.getMass());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_HAIR_COLOR,people.getHair_color());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_EYE_COLOR,people.getEye_color());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_BIRTH_YEAR,people.getBirth_year());
        contentValues.put(PeopleList.PeopleListEntry.COLUMN_GENDER,people.getGender());

        db.insert(PeopleList.PeopleListEntry.TABLE_NAME, null, contentValues);

    }

    public boolean checkName(String name) {

        SQLiteDatabase database = getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + PeopleList.PeopleListEntry.TABLE_NAME + " WHERE "
                + PeopleList.PeopleListEntry.COLUMN_NAME + " =? ";
        Cursor cursor = database.rawQuery(selectQuery, new String[]{name});
        boolean checkPeople = false;
        if (cursor.moveToFirst()) {
            checkPeople = true;
            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
        }
        cursor.close();
        database.close();
        return checkPeople;
    }
}
