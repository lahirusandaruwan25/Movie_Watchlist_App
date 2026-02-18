package com.example.movie_watchlist_app;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class WatchlistActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper db;
    ArrayList<String> movieList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        listView = findViewById(R.id.listViewMovies);
        db = new DatabaseHelper(this);
        movieList = new ArrayList<>();

        Cursor c = db.getMovies();
        while (c.moveToNext()) {
            movieList.add(c.getString(1) + " - " + c.getString(2));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieList);
        listView.setAdapter(adapter);
    }
}
