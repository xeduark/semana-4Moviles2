package com.example.semanamoviles;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ContactosDataSource {

    private SQLiteDatabase database;
    private ContactosDatabaseHelper dbHelper;

    public ContactosDataSource(Context context) {
        dbHelper = new ContactosDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contacto createContacto(String nombre, String telefono, String email, String direccion, String fechaNacimiento) {
        ContentValues values = new ContentValues();
        values.put(ContactosDatabaseHelper.COLUMN_NOMBRE, nombre);
        values.put(ContactosDatabaseHelper.COLUMN_TELEFONO, telefono);
        values.put(ContactosDatabaseHelper.COLUMN_EMAIL, email);
        values.put(ContactosDatabaseHelper.COLUMN_DIRECCION, direccion);
        values.put(ContactosDatabaseHelper.COLUMN_FECHA_NACIMIENTO, fechaNacimiento);

        long insertId = database.insert(ContactosDatabaseHelper.TABLE_CONTACTOS, null, values);
        Cursor cursor = database.query(ContactosDatabaseHelper.TABLE_CONTACTOS,
                null, ContactosDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(insertId)},
                null, null, null);
        cursor.moveToFirst();
        Contacto newContacto = cursorToContacto(cursor);
        cursor.close();
        return newContacto;
    }

    // Método para eliminar contacto
    public void deleteContacto(long id) {
        database.delete(ContactosDatabaseHelper.TABLE_CONTACTOS,
                ContactosDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // Eliminar contacto dentro del modal
    public void setEliminarContactoListener(Button buttonEliminar, final Contacto contacto, final Dialog dialog) {
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacto(contacto.getId());  // Elimina el contacto usando el ID
                dialog.dismiss();  // Cierra el modal
                // Si es necesario, se debe implementar la lógica de actualización de la lista de contactos aquí
            }
        });
    }

    public List<Contacto> getAllContactos() {
        List<Contacto> contactos = new ArrayList<>();
        Cursor cursor = database.query(ContactosDatabaseHelper.TABLE_CONTACTOS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contacto contacto = cursorToContacto(cursor);
            contactos.add(contacto);
            cursor.moveToNext();
        }
        cursor.close();
        return contactos;
    }

    public Contacto getContactoByName(String nombre) {
        Cursor cursor = database.query(ContactosDatabaseHelper.TABLE_CONTACTOS,
                null, ContactosDatabaseHelper.COLUMN_NOMBRE + " = ?", new String[]{nombre},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Contacto contacto = cursorToContacto(cursor);
            cursor.close();
            return contacto;
        }
        return null;
    }

    public List<Contacto> getContactosByPhonePrefix(String prefix) {
        List<Contacto> contactos = new ArrayList<>();
        Cursor cursor = database.query(ContactosDatabaseHelper.TABLE_CONTACTOS,
                null, ContactosDatabaseHelper.COLUMN_TELEFONO + " LIKE ?", new String[]{prefix + "%"},
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contacto contacto = cursorToContacto(cursor);
            contactos.add(contacto);
            cursor.moveToNext();
        }
        cursor.close();
        return contactos;
    }

    public List<Contacto> getContactosOrderedByDate() {
        List<Contacto> contactos = new ArrayList<>();
        Cursor cursor = database.query(ContactosDatabaseHelper.TABLE_CONTACTOS,
                null, null, null, null, null, ContactosDatabaseHelper.COLUMN_FECHA_NACIMIENTO + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contacto contacto = cursorToContacto(cursor);
            contactos.add(contacto);
            cursor.moveToNext();
        }
        cursor.close();
        return contactos;
    }

    private Contacto cursorToContacto(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_ID);
        int nombreIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_NOMBRE);
        int telefonoIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_TELEFONO);
        int emailIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_EMAIL);
        int direccionIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_DIRECCION);
        int fechaNacimientoIndex = cursor.getColumnIndex(ContactosDatabaseHelper.COLUMN_FECHA_NACIMIENTO);

        if (idIndex == -1 || nombreIndex == -1 || telefonoIndex == -1 ||
                emailIndex == -1 || direccionIndex == -1 || fechaNacimientoIndex == -1) {
            throw new IllegalArgumentException("One or more column names are incorrect");
        }

        return new Contacto(
                cursor.getLong(idIndex),  // Usar getLong para columnas INTEGER
                cursor.getString(nombreIndex),
                cursor.getString(telefonoIndex),
                cursor.getString(emailIndex),
                cursor.getString(direccionIndex),
                cursor.getString(fechaNacimientoIndex)
        );
    }

    public void updateContacto(Contacto contacto) {
        ContentValues values = new ContentValues();
        values.put(ContactosDatabaseHelper.COLUMN_NOMBRE, contacto.getNombre());
        values.put(ContactosDatabaseHelper.COLUMN_TELEFONO, contacto.getTelefono());
        values.put(ContactosDatabaseHelper.COLUMN_EMAIL, contacto.getEmail());
        values.put(ContactosDatabaseHelper.COLUMN_DIRECCION, contacto.getDireccion());
        values.put(ContactosDatabaseHelper.COLUMN_FECHA_NACIMIENTO, contacto.getFechaNacimiento());

        database.update(ContactosDatabaseHelper.TABLE_CONTACTOS, values,
                ContactosDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(contacto.getId())});
    }
}

