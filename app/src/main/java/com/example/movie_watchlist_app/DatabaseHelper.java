package com.example.movie_watchlist_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "movieDB";
    private static final int DATABASE_VERSION = 1;

    // Table Info
    private static final String TABLE_MOVIES = "movies";

    // Columns
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_GENRE = "genre";
    private static final String COL_RATING = "rating";
    private static final String COL_REVIEW = "review";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_MOVIES + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_GENRE + " TEXT, "
                + COL_RATING + " REAL, "
                + COL_REVIEW + " TEXT"
                + ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // ✅ Insert Movie
    public boolean insertMovie(String name, String genre, float rating, String review) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_GENRE, genre);
        cv.put(COL_RATING, rating);
        cv.put(COL_REVIEW, review);

        long result = db.insert(TABLE_MOVIES, null, cv);

        db.close();

        return result != -1;
    }

    // ✅ Get All Movies
    public Cursor getMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);
    }
}