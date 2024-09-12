package com.example.semanamoviles;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ContactosDataSource dataSource;
    private EditText editTextNombre, editTextTelefono, editTextEmail, editTextDireccion, editTextFechaNacimiento;
    private ListView listViewContactos;
    private ArrayAdapter<Contacto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data source
        dataSource = new ContactosDataSource(this);
        dataSource.open();

        // Initialize UI elements
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        listViewContactos = findViewById(R.id.listViewContactos);

        // Set up the button click listener
        Button buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarContacto();
            }
        });

        // Load contacts into the ListView
        loadContactos();
    }

    private void agregarContacto() {
        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString().trim();

        // Ensure no fields are empty (optional)
        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechaNacimiento.isEmpty()) {
            // Handle the case where one or more fields are empty
            return;
        }

        // Create a new contact in the database
        dataSource.createContacto(nombre, telefono, email, direccion, fechaNacimiento);

        // Reload contacts to update the ListView
        loadContactos();
    }

    private void loadContactos() {
        // Retrieve all contacts from the database
        List<Contacto> contactos = dataSource.getAllContactos();

        // Create an ArrayAdapter to display the contacts in the ListView
        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactos);
            listViewContactos.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(contactos);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the data source when the activity is destroyed
        dataSource.close();
    }
}
