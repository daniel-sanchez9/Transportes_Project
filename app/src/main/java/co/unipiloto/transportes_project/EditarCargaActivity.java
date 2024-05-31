package co.unipiloto.transportes_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.database.DatabaseHelper;


public class EditarCargaActivity extends AppCompatActivity {
    private DatabaseHelper database;
    private Carga cargaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_carga);
        database = new DatabaseHelper(this);
        int cargaId = getIntent().getIntExtra("cargaId", -1);

        // Aquí puedes obtener el objeto Carga seleccionado de los extras del intent
        cargaSeleccionada = obtenerCargaPorId(cargaId);

        // Obtener referencias a tus EditText y Spinner en el layout XML
        EditText editTextNombreCarga = findViewById(R.id.etNombreCarga);
        EditText editTextPeso = findViewById(R.id.etPeso);
        EditText editTextCiudadOrigen = findViewById(R.id.etCiudadOrigen);
        EditText editTextCiudadDestino = findViewById(R.id.etCiudadDestino);
        Spinner spinnerEstadoCarga = findViewById(R.id.spinnerEstadoCarga);

        // Mostrar la información de la carga en los EditText y Spinner
        editTextNombreCarga.setText(cargaSeleccionada.getNombreDeCarga());
        editTextPeso.setText(String.valueOf(cargaSeleccionada.getPeso()));
        editTextCiudadOrigen.setText(cargaSeleccionada.getCiudadOrigen());
        editTextCiudadDestino.setText(cargaSeleccionada.getCiudadDestino());

        // Configurar el Spinner para el estado de carga
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones_estado_carga, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstadoCarga.setAdapter(adapter);
        spinnerEstadoCarga.setSelection(adapter.getPosition(cargaSeleccionada.getEstadoDeCarga()));

        // Permitir al usuario editar la información y guardar los cambios
        Button buttonGuardar = findViewById(R.id.btn_Guardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores de los EditText y Spinner
                String nuevoNombreCarga = editTextNombreCarga.getText().toString();
                int nuevoPeso = Integer.parseInt(editTextPeso.getText().toString());
                String nuevaCiudadOrigen = editTextCiudadOrigen.getText().toString();
                String nuevaCiudadDestino = editTextCiudadDestino.getText().toString();
                String nuevoEstadoCarga = spinnerEstadoCarga.getSelectedItem().toString();

                // Actualizar la carga con los nuevos valores
                cargaSeleccionada.setNombreDeCarga(nuevoNombreCarga);
                cargaSeleccionada.setPeso(nuevoPeso);
                cargaSeleccionada.setCiudadOrigen(nuevaCiudadOrigen);
                cargaSeleccionada.setCiudadDestino(nuevaCiudadDestino);
                cargaSeleccionada.setEstadoDeCarga(nuevoEstadoCarga);

                // Actualizar la carga en la base de datos
                database.actualizarCarga(cargaSeleccionada);

                // Indicar al usuario que los cambios han sido guardados
                Toast.makeText(EditarCargaActivity.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Carga obtenerCargaPorId(int id) {
        // Obtener una instancia de la base de datos
        DatabaseHelper database = new DatabaseHelper(this);

        // Consultar la base de datos para obtener la carga por su ID
        Carga carga = null;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query("CARGAS",
                null,
                "_id=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        // Verificar si se encontró una carga con el ID dado
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los datos del cursor
            @SuppressLint("Range") String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
            @SuppressLint("Range") int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
            @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
            @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
            @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
            @SuppressLint("Range") String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

            // Construir el objeto Carga
            carga = new Carga(id, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorDeCarga);

            // Cerrar el cursor
            cursor.close();
        }

        // Cerrar la conexión de la base de datos
        db.close();

        // Devolver la carga encontrada o null si no se encontró ninguna
        return carga;
    }
}
