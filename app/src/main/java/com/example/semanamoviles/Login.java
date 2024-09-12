package com.example.semanamoviles;
import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {


    private EditText txtUser, txtPass;
    private Button btnIngresar;

    // Predefined credentials
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        txtUser = findViewById(R.id.user);
        txtPass = findViewById(R.id.Pass);
        btnIngresar = findViewById(R.id.btnIngresar);

        // Set up the button click listener
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCredenciales();
            }
        });
    }

    private void verificarCredenciales() {
        // Get the input from EditTexts
        String username = txtUser.getText().toString();
        String password = txtPass.getText().toString();

        // Check if the input matches the predefined credentials
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Display success message
            Toast.makeText(Login.this, "Ingreso exitoso", Toast.LENGTH_SHORT).show();

            // Create an Intent to start MainActivity
            Intent intent = new Intent(Login.this, MainActivity.class);

            // Start MainActivity
            startActivity(intent);

            // Optionally, finish the current activity to prevent going back to it
            finish();
        } else {
            // Display failed login message
            Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}
