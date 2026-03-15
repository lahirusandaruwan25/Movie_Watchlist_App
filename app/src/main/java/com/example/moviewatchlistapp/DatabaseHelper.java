package com.example.moviewatchlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDB";
    private static final int DATABASE_VERSION = 4; // Incremented version for user table

    private static final String TABLE_MOVIES = "movies";
    private static final String TABLE_USERS = "users";

    // Movie Table Columns
    private static final String COL_ID = "id";
    private static final String COL_USER_EMAIL = "user_email";
    private static final String COL_NAME = "name";
    private static final String COL_GENRE = "genre";
    private static final String COL_RATING = "rating";
    private static final String COL_REVIEW = "review";
    private static final String COL_IMAGE_URL = "image_url";

    // User Table Columns
    private static final String COL_U_ID = "uid";
    private static final String COL_U_EMAIL = "email";
    private static final String COL_U_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieTable = "CREATE TABLE " + TABLE_MOVIES + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_USER_EMAIL + " TEXT, "
                + COL_NAME + " TEXT, "
                + COL_GENRE + " TEXT, "
                + COL_RATING + " REAL, "
                + COL_REVIEW + " TEXT, "
                + COL_IMAGE_URL + " TEXT"
                + ")";
        db.execSQL(createMovieTable);

        String createUserTable = "CREATE TABLE " + TABLE_USERS + "("
                + COL_U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_U_EMAIL + " TEXT UNIQUE, "
                + COL_U_PASSWORD + " TEXT"
                + ")";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // User Methods
    public boolean registerUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_U_EMAIL, email);
        cv.put(COL_U_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, cv);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_U_ID},
                COL_U_EMAIL + "=? AND " + COL_U_PASSWORD + "=?", new String[]{email, password}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Movie Methods
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
