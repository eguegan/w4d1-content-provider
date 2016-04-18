package com.example.admin.w4d1contentprovider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.w4d1contentprovider.database.DatabaseContract.CompaniesEntry;
import com.example.admin.w4d1contentprovider.database.DatabaseContract.UsersEntry;


/**
 * Created by evin on 4/16/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simpleDB";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersEntry.CREATE_TABLE_QUERY);
        db.execSQL(CompaniesEntry.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CompaniesEntry.TABLE_NAME);
        onCreate(db);
    }
}
