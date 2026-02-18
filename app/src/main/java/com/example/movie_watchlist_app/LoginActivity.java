package com.example.movie_watchlist_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!isValidEmail(email)) {
                etEmail.setError("Invalid Email");
            }
            else if (!isValidPassword(password)) {
                etPassword.setError("Password must be 8 chars, 1 capital letter, 1 number");
            }
            else {
                // Validation OK → Go to MainActivity
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    // Email Validation
    public boolean isValidEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Password Validation
    public boolean isValidPassword(String password){
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(passwordPattern);
    }
}