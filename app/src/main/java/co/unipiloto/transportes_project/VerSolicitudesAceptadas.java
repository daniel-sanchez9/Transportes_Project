package co.unipiloto.transportes_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import co.unipiloto.transportes_project.Localizacion.Navegacion;
import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.Objetos.CargaAdapter;
import co.unipiloto.transportes_project.Registros.VerRegistrosCarga;
import co.unipiloto.transportes_project.database.DatabaseHelper;
import co.unipiloto.transportes_project.R;

public class VerSolicitudesAceptadas extends AppCompatActivity {

        private ListView listViewSolicitudesAceptadas;
        private List<Carga> cargasAceptadas;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ver_solicitudes_aceptadas);

            listViewSolicitudesAceptadas = findViewById(R.id.listViewSolicitudesAceptadas);

            // Obtener las cargas aceptadas desde la base de datos
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            cargasAceptadas = dbHelper.obtenerCargasAceptadas();

            // Crear un adaptador y establecerlo en el ListView
            CargaAdapter adapter = new CargaAdapter(this, cargasAceptadas);
            listViewSolicitudesAceptadas.setAdapter(adapter);

            // Registrar el ListView para el menú contextual
            registerForContextMenu(listViewSolicitudesAceptadas);

            // Establecer un listener para clics en los elementos del ListView
            listViewSolicitudesAceptadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listViewSolicitudesAceptadas.showContextMenuForChild(view);
                }
            });
        }

        // Inflar el menú contextual
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menuconductor, menu);
        }

        // Manejar las selecciones de elementos del menú
        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            Carga cargaSeleccionada = cargasAceptadas.get(position);

            if (item.getItemId() == R.id.action_conducir) {

                Intent intent2 = new Intent(VerSolicitudesAceptadas.this, Navegacion.class);

                // Agregar los datos de la carga seleccionada como extras al intent
                intent2.putExtra("cargaId", cargaSeleccionada.getId());
                intent2.putExtra("nombreDeCarga", cargaSeleccionada.getNombreDeCarga());
                intent2.putExtra("peso", cargaSeleccionada.getPeso());
                intent2.putExtra("ciudadOrigen", cargaSeleccionada.getCiudadOrigen());
                intent2.putExtra("ciudadDestino", cargaSeleccionada.getCiudadDestino());
                intent2.putExtra("estado", cargaSeleccionada.getEstadoDeCarga());
                intent2.putExtra("creadorCarga", cargaSeleccionada.getCreadorDeCarga());

                // Iniciar la actividad Navegacion
                startActivity(intent2);
                return true;
            } else if (item.getItemId() == R.id.action_odometro){
                Intent intent = new Intent(VerSolicitudesAceptadas.this, MapaConOdometro.class);
                startActivity(intent);
                return true;
            }
                return super.onContextItemSelected(item);
            }
        }
