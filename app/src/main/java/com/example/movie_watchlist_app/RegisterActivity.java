package com.example.movie_watchlist_app;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}
