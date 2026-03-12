package com.example.moviewatchlistapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
            } else if (!isValidPassword(password)) {
                etPassword.setError("Password must be 8 chars, 1 capital letter, 1 number");
            } else if (email.isEmpty() || password.isEmpty()) {
                etEmail.setError("All fields required");
                etPassword.setError("All fields required");
            } else {
                // Save user email in SharedPreferences
                SharedPreferences pref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("email", email);
                editor.apply();

                // Validation OK → Go to MainActivity
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        });
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    // Email Validation
    public boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Password Validation
    public boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(passwordPattern);
    }
}
