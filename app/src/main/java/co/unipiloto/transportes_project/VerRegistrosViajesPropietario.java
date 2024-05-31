package co.unipiloto.transportes_project;

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

import co.unipiloto.transportes_project.Objetos.Viaje;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class VerRegistrosViajesPropietario extends AppCompatActivity {

    private List<Viaje> listaViaje;
    private ArrayAdapter<Viaje> adapter;
    private ListView listViewViajesPropietario;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_viajes_propietario);

        listViewViajesPropietario = findViewById(R.id.listViewViajesPropietario);
        listaViaje = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaViaje);
        listViewViajesPropietario.setAdapter(adapter);

        database = new DatabaseHelper(this);
        cargarViaje();
        registerForContextMenu(listViewViajesPropietario);
    }
    private void cargarViaje (){
        List<Viaje> viajes = database.obtenerTodosLosViajes();
        listaViaje.clear();
        listaViaje.addAll(viajes);
        adapter.notifyDataSetChanged();
    }
}