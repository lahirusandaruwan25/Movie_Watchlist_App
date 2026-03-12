package com.example.moviewatchlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDB";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_MOVIES = "movies";

    private static final String COL_ID = "id";
    private static final String COL_USER_EMAIL = "user_email";
    private static final String COL_NAME = "name";
    private static final String COL_GENRE = "genre";
    private static final String COL_RATING = "rating";
    private static final String COL_REVIEW = "review";
    private static final String COL_IMAGE_URL = "image_url";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_MOVIES + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USER_EMAIL + " TEXT, "
                + COL_NAME + " TEXT, "
                + COL_GENRE + " TEXT, "
                + COL_RATING + " REAL, "
                + COL_REVIEW + " TEXT, "
                + COL_IMAGE_URL + " TEXT"
                + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public boolean insertMovie(String userEmail, String name, String genre, float rating, String review, String imageUrl) {
        if (isMovieExists(userEmail, name)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, userEmail);
        cv.put(COL_NAME, name);
        cv.put(COL_GENRE, genre);
        cv.put(COL_RATING, rating);
        cv.put(COL_REVIEW, review);
        cv.put(COL_IMAGE_URL, imageUrl);

        long result = db.insert(TABLE_MOVIES, null, cv);
        db.close();
        return result != -1;
    }

    public boolean isMovieExists(String userEmail, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[]{COL_ID},
                COL_USER_EMAIL + "=? AND " + COL_NAME + "=?", new String[]{userEmail, name}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getMoviesByUser(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MOVIES, null, COL_USER_EMAIL + "=?", new String[]{userEmail}, null, null, null);
    }

    public boolean deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MOVIES, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
