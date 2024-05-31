package co.unipiloto.transportes_project.Objetos;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import co.unipiloto.transportes_project.R;

public class CargaAdapter extends ArrayAdapter<Carga> {

    public CargaAdapter(Context context, List<Carga> cargas) {
        super(context, 0, cargas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Carga carga = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_carga, parent, false);
        }

        TextView tvNombreDeCarga = convertView.findViewById(R.id.tvNombreDeCarga);
        TextView tvPeso = convertView.findViewById(R.id.tvPeso);
        TextView tvCiudadOrigen = convertView.findViewById(R.id.tvCiudadOrigen);
        TextView tvCiudadDestino = convertView.findViewById(R.id.tvCiudadDestino);

        if (carga != null) {
            tvNombreDeCarga.setText(carga.getNombreDeCarga());
            tvPeso.setText("Peso: " + carga.getPeso() + " kg");
            tvCiudadOrigen.setText("Origen: " + carga.getCiudadOrigen());
            tvCiudadDestino.setText("Destino: " + carga.getCiudadDestino());
        }

        return convertView;
    }
}

