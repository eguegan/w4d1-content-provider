package com.example.admin.w4d1contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.admin.w4d1contentprovider.database.DatabaseContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addPerson(View view) {
        Uri usersUri = DatabaseContract.UsersEntry.CONTENT_URI;
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseContract.UsersEntry.KEY_NAME, "Etienne");
        contentValues.put(DatabaseContract.UsersEntry.KEY_AGE, 17);

        Uri resultUri = getContentResolver().insert(usersUri, contentValues);

        Log.d("TAG", "addPerson: " + resultUri);
    }

    public void listAll(View view) {
        Uri usersUri = DatabaseContract.UsersEntry.CONTENT_URI;

        Cursor resultCursor = getContentResolver().query(usersUri, null, null, null,null);
        resultCursor.moveToFirst();

        do {
            Log.d("TAG", "listAll: " + resultCursor.getString(resultCursor.getColumnIndex(DatabaseContract.UsersEntry.KEY_NAME)));
            Log.d("TAG", "listAll: " + resultCursor.getString(resultCursor.getColumnIndex(DatabaseContract.UsersEntry.KEY_AGE)));
        }while (resultCursor.moveToNext());
    }
}
