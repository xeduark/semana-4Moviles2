package com.example.semanamoviles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactoAdapter.OnVerClickListener {

    private ContactosDataSource dataSource;
    private EditText editTextNombre, editTextTelefono, editTextEmail, editTextDireccion, editTextFechaNacimiento;
    private ListView listViewContactos;
    private ContactoAdapter adapter; // Adaptador personalizado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa la fuente de datos
        dataSource = new ContactosDataSource(this);
        dataSource.open();

        // Inicializa los elementos de la interfaz de usuario
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento);
        listViewContactos = findViewById(R.id.listViewContactos);

        // Configura el listener para el botón "Agregar"
        Button buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarContacto();
            }
        });

        // Carga los contactos en el ListView
        loadContactos();
    }

    @Override
    public void onVerClick(Contacto contacto) {
        mostrarDialogoContacto(contacto);
    }

    private void agregarContacto() {
        // Obtiene los datos ingresados por el usuario
        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();
        String fechaNacimiento = editTextFechaNacimiento.getText().toString().trim();

        // Verifica que no haya campos vacíos (opcional)
        if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechaNacimiento.isEmpty()) {
            // Maneja el caso en el que uno o más campos están vacíos
            return;
        }

        // Crea un nuevo contacto en la base de datos
        dataSource.createContacto(nombre, telefono, email, direccion, fechaNacimiento);

        // Recarga los contactos para actualizar el ListView
        loadContactos();
    }

    private void loadContactos() {
        // Obtiene todos los contactos de la base de datos
        List<Contacto> contactos = dataSource.getAllContactos();

        // Crea un ContactoAdapter para mostrar los contactos en el ListView
        if (adapter == null) {
            adapter = new ContactoAdapter(this, contactos, this); // Pasa `this` como el listener
            listViewContactos.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(contactos);
            adapter.notifyDataSetChanged();
        }
    }

    private void mostrarDialogoContacto(final Contacto contacto) {
        // Infla el layout del diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_contacto, null);

        // Encuentra los EditText del diálogo
        final EditText editTextModalNombre = dialogView.findViewById(R.id.editTextModalNombre);
        final EditText editTextModalTelefono = dialogView.findViewById(R.id.editTextModalTelefono);
        final EditText editTextModalEmail = dialogView.findViewById(R.id.editTextModalEmail);
        final EditText editTextModalDireccion = dialogView.findViewById(R.id.editTextModalDireccion);
        final EditText editTextModalFechaNacimiento = dialogView.findViewById(R.id.editTextModalFechaNacimiento);

        // Llena los campos con la información del contacto
        editTextModalNombre.setText(contacto.getNombre());
        editTextModalTelefono.setText(contacto.getTelefono());
        editTextModalEmail.setText(contacto.getEmail());
        editTextModalDireccion.setText(contacto.getDireccion());
        editTextModalFechaNacimiento.setText(contacto.getFechaNacimiento());

        // Crea el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Configura el botón "Modificar"
        Button buttonModificar = dialogView.findViewById(R.id.buttonModificar);
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualiza el contacto en la base de datos
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

        // Configura el botón "Eliminar"
        Button buttonEliminar = dialogView.findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmar Eliminación")
                        .setMessage("¿Estás seguro de que deseas eliminar este contacto?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dataSource.deleteContacto(contacto.getId()); // Eliminar el contacto
                                loadContactos(); // Recargar la lista
                                dialog.dismiss(); // Cerrar el diálogo
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Solo cierra el diálogo sin eliminar
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra la fuente de datos cuando la actividad se destruye
        dataSource.close();
    }
}
