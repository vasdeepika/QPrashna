package com.android.qprashna.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QprashnaDBHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "qprashna.db";

    //Database schema version
    private static final int VERSION = 1;

    public QprashnaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_PROFILE_TABLE = "CREATE TABLE "  + QprashnaContract.ProfileEntry.TABLE_NAME + " (" +
                QprashnaContract.ProfileEntry.DESIGNATION + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.FIRSTNAME + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.LASTNAME + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.EMAIL + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.GENDER + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.USERID + " INTEGER NOT NULL, " +
                QprashnaContract.ProfileEntry.PROFILEPIC + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.DOB + " BIGINT, " +
                QprashnaContract.ProfileEntry.COUNTRY + " TEXT NOT NULL, " +
                QprashnaContract.ProfileEntry.STATE + " TEXT NOT NULL);";

        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}