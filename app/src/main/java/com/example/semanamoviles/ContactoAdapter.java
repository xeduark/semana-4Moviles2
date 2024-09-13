package com.example.semanamoviles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    public interface OnVerClickListener {
        void onVerClick(Contacto contacto);
    }

    private OnVerClickListener verClickListener;

    public ContactoAdapter(Context context, List<Contacto> contactos, OnVerClickListener verClickListener) {
        super(context, 0, contactos);
        this.verClickListener = verClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutiliza la vista si es posible
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contacto, parent, false);
        }

        // Obtén la instancia del Contacto para esta posición
        Contacto contacto = getItem(position);

        // Configura los elementos de la vista
        TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
        Button buttonVer = convertView.findViewById(R.id.buttonVer);

        textViewNombre.setText(contacto.getNombre());

        // Configura el botón de ver con un listener
        buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verClickListener != null) {
                    verClickListener.onVerClick(contacto);
                }
            }
        });

        return convertView;
    }
}
