package com.example.lab07;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edUsername;
    private EditText edPassword;
    private Button btnLogin;
    private Button btnSignUp;

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(CREDENTIAL_SHARED_PREF, MODE_PRIVATE);
        String savedUsername = prefs.getString("Username", null);
        String savedPassword = prefs.getString("Password", null);

        if (savedUsername != null && savedPassword != null) {
            // Уже вошёл ранее — сразу в MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            String usernameInput = edUsername.getText().toString();
            String passwordInput = edPassword.getText().toString();

            SharedPreferences credentials = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
            String storedUsername = credentials.getString("Username", null);
            String storedPassword = credentials.getString("Password", null);

            if (storedUsername != null && storedUsername.equals(usernameInput) &&
                    storedPassword != null && storedPassword.equals(passwordInput)) {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

