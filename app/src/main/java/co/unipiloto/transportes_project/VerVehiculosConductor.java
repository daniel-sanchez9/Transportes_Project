package co.unipiloto.transportes_project;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import co.unipiloto.transportes_project.Objetos.Vehiculo;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class VerVehiculosConductor extends AppCompatActivity {
    private List<Vehiculo> listaVehiculos;
    private ArrayAdapter<Vehiculo> adapter;
    private ListView listViewVehiculosConductor;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_vehiculos_conductor);

        listViewVehiculosConductor = findViewById(R.id.listViewVehiculosConductor);
        listaVehiculos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewVehiculosConductor.setAdapter(adapter);

        database = new DatabaseHelper(this);
        cargarVehiculos();
        registerForContextMenu(listViewVehiculosConductor);
    }

    private void cargarVehiculos() {
        List<Vehiculo> vehiculos = database.obtenerTodosLosVehiculos();
        listaVehiculos.clear();
        listaVehiculos.addAll(vehiculos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menuvehiculo, menu);
    }

}