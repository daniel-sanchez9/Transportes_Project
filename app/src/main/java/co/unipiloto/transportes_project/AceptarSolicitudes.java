package co.unipiloto.transportes_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class AceptarSolicitudes extends AppCompatActivity {
    private ListView listViewCargasSolicitadas;
    private DatabaseHelper dbHelper;
    private Carga cargaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceptar_solicitudes);

        Intent intent = new Intent(this, NuevoServicioSolicitudCarga.class);
        startService(intent);

        listViewCargasSolicitadas = findViewById(R.id.list_view_aceptar_solicitudes);
        dbHelper = new DatabaseHelper(this);

        // Obtener las cargas solicitadas
        List<Carga> listaCargasSolicitadas = dbHelper.obtenerCargasSolicitadas();

        // Crear un adaptador para el ListView
        ArrayAdapter<Carga> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaCargasSolicitadas
        );
        listViewCargasSolicitadas.setAdapter(adapter);

        listViewCargasSolicitadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cargaSeleccionada = (Carga) parent.getItemAtPosition(position);
                showMenu();
            }
        });
    }

    private void showMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione una opción")
                .setItems(R.array.menu_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) { // Aceptar
                            cargaSeleccionada.setEstadoDeCarga("Aceptada");
                            dbHelper.actualizarCarga(cargaSeleccionada);
                            Toast.makeText(AceptarSolicitudes.this, "Carga aceptada", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) { // Rechazar
                            cargaSeleccionada.setEstadoDeCarga("Rechazada");
                            dbHelper.actualizarCarga(cargaSeleccionada);
                            Toast.makeText(AceptarSolicitudes.this, "Carga rechazada", Toast.LENGTH_SHORT).show();
                        }
                        ((ArrayAdapter<Carga>) listViewCargasSolicitadas.getAdapter()).notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_accept) {
            if (cargaSeleccionada != null) {
                cargaSeleccionada.setEstadoDeCarga("Aceptada");
                dbHelper.actualizarCarga(cargaSeleccionada);
                ((ArrayAdapter<Carga>) listViewCargasSolicitadas.getAdapter()).notifyDataSetChanged();
                Toast.makeText(this, "Carga aceptada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione una carga primero", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.menu_reject) {
            if (cargaSeleccionada != null) {
                cargaSeleccionada.setEstadoDeCarga("Rechazada");
                dbHelper.actualizarCarga(cargaSeleccionada);
                ((ArrayAdapter<Carga>) listViewCargasSolicitadas.getAdapter()).notifyDataSetChanged();
                Toast.makeText(this, "Carga rechazada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione una carga primero", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
