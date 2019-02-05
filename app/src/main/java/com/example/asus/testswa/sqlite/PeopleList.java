package com.example.asus.testswa.sqlite;

import android.provider.BaseColumns;

public class PeopleList {

    public static final class PeopleListEntry implements BaseColumns {

        public static final String TABLE_NAME = "people_list";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_MASS = "mass";
        public static final String COLUMN_HAIR_COLOR = "hair";
        public static final String COLUMN_EYE_COLOR = "eye";
        public static final String COLUMN_BIRTH_YEAR = "birth";
        public static final String COLUMN_GENDER = "gender";
    }
}
