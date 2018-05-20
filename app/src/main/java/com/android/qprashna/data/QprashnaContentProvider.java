package com.android.qprashna.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.android.qprashna.data.QprashnaContract.ProfileEntry.CONTENT_URI;
import static com.android.qprashna.data.QprashnaContract.ProfileEntry.TABLE_NAME;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;

public class QprashnaContentProvider extends ContentProvider {
    public static final int PROFILE = 100;
    private QprashnaDBHelper mQprashnaDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public QprashnaContentProvider() {
    }

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(QprashnaContract.CONTENT_AUTHORITY, QprashnaContract.PATH_TASKS, PROFILE);

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mQprashnaDbHelper.getWritableDatabase();

        Log.d("Content Provider", "delete when sign out "+ uri.getPathSegments() );
        int match = sUriMatcher.match(uri);
        Log.d("Content Provider", "delete when sign out "+ match );
        // Keep track of the number of deleted tasks
        int rowsDeleted; // starts as 0

        switch (match) {
            case PROFILE:
                String id = String.valueOf(getUserIdFromSharedPreferences(getContext()));
                rowsDeleted = db.delete(TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0 && getContext()!=null) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mQprashnaDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case PROFILE:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (getContext()!=null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mQprashnaDbHelper = new QprashnaDBHelper(context);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mQprashnaDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case PROFILE:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        final SQLiteDatabase db = mQprashnaDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of updated tasks
        int rowsUpdated; // starts as 0

        switch (match) {
            case PROFILE:
                String id = String.valueOf(getUserIdFromSharedPreferences(getContext()));
                rowsUpdated = db.update(TABLE_NAME, values,null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0 && getContext()!=null) {
            // A task was updated, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
