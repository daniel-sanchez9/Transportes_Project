package co.unipiloto.transportes_project.Registros;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import co.unipiloto.transportes_project.EditarVehiculo;
import co.unipiloto.transportes_project.Objetos.Vehiculo;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class VerRegistrosVehiculos extends AppCompatActivity {

    private List<Vehiculo> listaVehiculos;
    private ArrayAdapter<Vehiculo> adapter;
    private ListView listViewVehiculos;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_vehiculos);

        listViewVehiculos = findViewById(R.id.listViewVehiculos);
        listaVehiculos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewVehiculos.setAdapter(adapter);

        database = new DatabaseHelper(this);
        cargarVehiculos();
        registerForContextMenu(listViewVehiculos);
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Vehiculo vehiculoSeleccionado = listaVehiculos.get(info.position);

        int itemId = item.getItemId();
        if (itemId == R.id.editarVehiculo) {
                Intent intent = new Intent(VerRegistrosVehiculos.this, EditarVehiculo.class);
                intent.putExtra("vehiculoId", vehiculoSeleccionado.getId());
                startActivity(intent);
            return true;
        } else if (itemId == R.id.eliminarVehiculo) {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar vehiculo")
                    .setMessage("¿Estás seguro de que deseas eliminar este vehículo?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.eliminarVehiculo(vehiculoSeleccionado);
                            cargarVehiculos();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
