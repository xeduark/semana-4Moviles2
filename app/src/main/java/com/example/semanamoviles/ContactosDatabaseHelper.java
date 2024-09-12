package com.example.semanamoviles;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


    public class ContactosDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "contactos.db";
        private static final int DATABASE_VERSION = 1;

        public static final String TABLE_CONTACTOS = "contactos";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_DIRECCION = "direccion";
        public static final String COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";

        private static final String TABLE_CREATE =
                "CREATE TABLE " + TABLE_CONTACTOS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NOMBRE + " TEXT, " +
                        COLUMN_TELEFONO + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_DIRECCION + " TEXT, " +
                        COLUMN_FECHA_NACIMIENTO + " TEXT" +
                        ");";

        public ContactosDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
            onCreate(db);
        }
    }

