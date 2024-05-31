package co.unipiloto.transportes_project.Registros;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import co.unipiloto.transportes_project.EditarCargaActivity;
import co.unipiloto.transportes_project.HacerSolicitudCarga;
import co.unipiloto.transportes_project.Localizacion.Navegacion;
import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class VerRegistrosCarga extends AppCompatActivity {

    private List<Carga> listaCargas;
    private ArrayAdapter<Carga> adapter;
    private ListView listViewCargas;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registros_carga);
        listViewCargas = findViewById(R.id.listViewCargas);
        listaCargas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCargas);
        listViewCargas.setAdapter(adapter);

        database = new DatabaseHelper(this);
        cargarCargas();
        registerForContextMenu(listViewCargas);

        listViewCargas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Carga cargaSeleccionada = listaCargas.get(position);
                Intent intent = new Intent(VerRegistrosCarga.this, HacerSolicitudCarga.class);
                intent.putExtra("cargaSeleccionada", cargaSeleccionada);
                startActivity(intent);
            }
        });
    }

    private void cargarCargas() {
        List<Carga> cargas = database.obtenerTodasLasCargas();
        listaCargas.clear();
        listaCargas.addAll(cargas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Carga cargaSeleccionada = listaCargas.get(info.position);

        int itemId = item.getItemId();
        if(itemId == R.id.editar){
            // Aquí puedes abrir la actividad de edición de carga
            // Puedes pasar la carga seleccionada como extra en el intent
            Intent intent = new Intent(VerRegistrosCarga.this, EditarCargaActivity.class);
            intent.putExtra("cargaId", cargaSeleccionada.getId());
            startActivity(intent);
            return true;
        }else if (itemId == R.id.eliminar){
            // Mostrar un diálogo de confirmación antes de eliminar la carga
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar carga")
                    .setMessage("¿Estás seguro de que deseas eliminar esta carga?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Eliminar la carga de la base de datos y recargar la lista
                            database.eliminarCarga(cargaSeleccionada);
                            cargarCargas();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        }else if (itemId == R.id.solicitud){
            Intent intent3 = new Intent(VerRegistrosCarga.this, HacerSolicitudCarga.class);

            intent3.putExtra("cargaId", cargaSeleccionada.getId());
            intent3.putExtra("nombreDeCarga", cargaSeleccionada.getNombreDeCarga());
            intent3.putExtra("peso", cargaSeleccionada.getPeso());
            intent3.putExtra("ciudadOrigen", cargaSeleccionada.getCiudadOrigen());
            intent3.putExtra("ciudadDestino", cargaSeleccionada.getCiudadDestino());
            intent3.putExtra("estado", cargaSeleccionada.getEstadoDeCarga());
            intent3.putExtra("creadorCarga", cargaSeleccionada.getCreadorDeCarga());

            startActivity(intent3);
            return true;
        }
        return super.onContextItemSelected(item);
        }
    }


