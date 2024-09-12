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

    private Context context;
    private List<Contacto> contactos;
    private ContactosDataSource dataSource;

    public ContactoAdapter(Context context, List<Contacto> contactos, ContactosDataSource dataSource) {
        super(context, R.layout.list_item_contacto, contactos);
        this.context = context;
        this.contactos = contactos;
        this.dataSource = dataSource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_contacto, parent, false);
        }

        Contacto contacto = contactos.get(position);

        TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
        Button buttonVer = convertView.findViewById(R.id.buttonVer);

        textViewNombre.setText(contacto.getNombre());

        buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).mostrarDialogoContacto(contacto);
            }
        });

        return convertView;
    }
}
