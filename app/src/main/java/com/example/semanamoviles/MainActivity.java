package com.example.semanamoviles;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
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

    void mostrarDialogoContacto(final Contacto contacto) {
        // Inflar el layout del dialogo
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_contacto, null);

        final EditText editTextModalNombre = dialogView.findViewById(R.id.editTextModalNombre);
        final EditText editTextModalTelefono = dialogView.findViewById(R.id.editTextModalTelefono);
        final EditText editTextModalEmail = dialogView.findViewById(R.id.editTextModalEmail);
        final EditText editTextModalDireccion = dialogView.findViewById(R.id.editTextModalDireccion);
        final EditText editTextModalFechaNacimiento = dialogView.findViewById(R.id.editTextModalFechaNacimiento);

        // Llenar los campos con la información del contacto
        editTextModalNombre.setText(contacto.getNombre());
        editTextModalTelefono.setText(contacto.getTelefono());
        editTextModalEmail.setText(contacto.getEmail());
        editTextModalDireccion.setText(contacto.getDireccion());
        editTextModalFechaNacimiento.setText(contacto.getFechaNacimiento());

        // Crear el dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Configurar el botón "Modificar"
        Button buttonModificar = dialogView.findViewById(R.id.buttonModificar);
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualizar el contacto en la base de datos
                contacto.setNombre(editTextModalNombre.getText().toString());
                contacto.setTelefono(editTextModalTelefono.getText().toString());
                contacto.setEmail(editTextModalEmail.getText().toString());
                contacto.setDireccion(editTextModalDireccion.getText().toString());
                contacto.setFechaNacimiento(editTextModalFechaNacimiento.getText().toString());

                dataSource.updateContacto(contacto);
                loadContactos(); // Recargar la lista
                dialog.dismiss(); // Cerrar el diálogo
            }
        });

        // Configurar el botón "Eliminar"
        Button buttonEliminar = dialogView.findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.deleteContacto(contacto.getId()); // Eliminar el contacto
                loadContactos(); // Recargar la lista
                dialog.dismiss(); // Cerrar el diálogo
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the data source when the activity is destroyed
        dataSource.close();
    }

}
