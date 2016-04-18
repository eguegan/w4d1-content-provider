package com.example.admin.w4d1contentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.admin.w4d1contentprovider.database.DatabaseContract;
import com.example.admin.w4d1contentprovider.database.DatabaseHelper;

public class SimpleProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.admin.w4d1contentprovider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DatabaseHelper mDatabaseHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.PATH_USERS, MATCH_USERS);
        uriMatcher.addURI(AUTHORITY, DatabaseContract.PATH_COMPANIES, MATCH_COMPANIES);

        return uriMatcher;
    }

    private static final int MATCH_USERS = 100;
    private static final int MATCH_COMPANIES = 200;

    public SimpleProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MATCH_USERS:
                return DatabaseContract.UsersEntry.CONTENT_TYPE;
            case MATCH_COMPANIES:
                return DatabaseContract.CompaniesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        long resultId;

        Uri returnUri;

        switch (match) {
            case MATCH_USERS:
                resultId = sqLiteDatabase.insert(DatabaseContract.UsersEntry.TABLE_NAME, null, values);
                if (resultId > 0) {
                    returnUri = ContentUris.withAppendedId(DatabaseContract.UsersEntry.CONTENT_URI, resultId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case MATCH_COMPANIES:
                resultId = sqLiteDatabase.insert(DatabaseContract.CompaniesEntry.TABLE_NAME, null, values);
                if (resultId > 0) {
                    returnUri = ContentUris.withAppendedId(DatabaseContract.CompaniesEntry.CONTENT_URI, resultId);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        String tableName;

        switch (match) {
            case MATCH_USERS:
                tableName = DatabaseContract.UsersEntry.TABLE_NAME;
                break;
            case MATCH_COMPANIES:
                tableName = DatabaseContract.CompaniesEntry.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return sqLiteDatabase.query(tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
