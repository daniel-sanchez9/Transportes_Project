package co.unipiloto.transportes_project.Registros;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import co.unipiloto.transportes_project.Objetos.*;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class VerRegistrosViajes extends AppCompatActivity {

    private List<Viaje> listaViaje;
    private ArrayAdapter<Viaje> adapter;
    private ListView listViewViaje;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_viajes);

        listViewViaje = findViewById(R.id.listViewViajes);
        listaViaje = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaViaje);
        listViewViaje.setAdapter(adapter);

        database = new DatabaseHelper(this);
        cargarViaje();
        registerForContextMenu(listViewViaje);
    }
    private void cargarViaje (){
        List<Viaje> viajes = database.obtenerTodosLosViajes();
        listaViaje.clear();
        listaViaje.addAll(viajes);
        adapter.notifyDataSetChanged();
    }
}