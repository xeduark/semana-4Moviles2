package com.example.semanamoviles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {
    Button irLogin, registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Inicializar botones
        irLogin = findViewById(R.id.btnLogin);
        registrar = findViewById(R.id.btnRegister);

        // Configurar botón para redirigir al login
        irLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, Login.class);
                startActivity(intent);

                // Opcional: Finalizar la actividad actual si no deseas que el usuario regrese a ella
                finish();
            }
        });

        // Configurar botón para registrar usuario
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iría la lógica para registrar el usuario

                // Mostrar un Toast indicando que el usuario ha sido creado correctamente
                Toast.makeText(register.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();

                // Redirigir a la pantalla de inicio de sesión
                Intent intent = new Intent(register.this, Login.class);
                startActivity(intent);

                // Opcional: Finalizar la actividad actual si no deseas que el usuario regrese a ella
                finish();
            }
        });
    }
}
