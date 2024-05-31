package co.unipiloto.transportes_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.Objetos.Vehiculo;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class EditarVehiculo extends AppCompatActivity {
    private DatabaseHelper database;
    private Vehiculo vehiculoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vehiculo);
        database = new DatabaseHelper(this);
        int vehiculoId = getIntent().getIntExtra("vehiculoId", -1);

        // Aquí puedes obtener el objeto Carga seleccionado de los extras del intent
        vehiculoSeleccionado = obtenerVehiculoPorId(vehiculoId);

        // Obtener referencias a tus EditText y Spinner en el layout XML
        EditText editTextMarca = findViewById(R.id.editTextMarca);
        EditText editTextModelo = findViewById(R.id.editTextModelo);
        EditText editTextPlacas = findViewById(R.id.editTextPlacas);
        EditText editTextCapacidad = findViewById(R.id.editTextCapacidad);
        EditText editTextColor = findViewById(R.id.editTextColor);
        Spinner spinnerEstado = findViewById(R.id.spinnerEstado);

        // Mostrar la información de la carga en los EditText y Spinner
        editTextMarca.setText(vehiculoSeleccionado.getMarca());
        editTextModelo.setText(String.valueOf(vehiculoSeleccionado.getModelo()));
        editTextPlacas.setText(vehiculoSeleccionado.getPlacas());
        editTextCapacidad.setText(String.valueOf(vehiculoSeleccionado.getCapacidad_de_carga()));
        editTextColor.setText(vehiculoSeleccionado.getColor());

        // Configurar el Spinner para el estado de carga
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
        spinnerEstado.setSelection(adapter.getPosition(vehiculoSeleccionado.getEstado()));

        // Permitir al usuario editar la información y guardar los cambios
        Button buttonGuardar = findViewById(R.id.buttonRegistrarCambios);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores de los EditText y Spinner
                String nuevaMarca = editTextMarca.getText().toString();
                int nuevoModelo = Integer.parseInt(editTextModelo.getText().toString());
                String nuevasPlacas = editTextPlacas.getText().toString();
                int nuevaCapacidad = Integer.parseInt(editTextCapacidad.getText().toString());
                String NuevoColor = editTextColor.getText().toString();
                String nuevoEstadoCarga = spinnerEstado.getSelectedItem().toString();

                // Actualizar la carga con los nuevos valores
                vehiculoSeleccionado.setMarca(nuevaMarca);
                vehiculoSeleccionado.setModelo(nuevoModelo);
                vehiculoSeleccionado.setPlacas(nuevasPlacas);
                vehiculoSeleccionado.setCapacidad_de_carga(nuevaCapacidad);
                vehiculoSeleccionado.setColor(NuevoColor);
                vehiculoSeleccionado.setEstado(nuevoEstadoCarga);

                // Actualizar la carga en la base de datos
                database.actualizarVehiculo(vehiculoSeleccionado);

                // Indicar al usuario que los cambios han sido guardados
                Toast.makeText(EditarVehiculo.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Vehiculo obtenerVehiculoPorId(int id) {
        // Obtener una instancia de la base de datos
        DatabaseHelper database = new DatabaseHelper(this);

        // Consultar la base de datos para obtener la carga por su ID
        Vehiculo vehiculo = null;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.query("VEHICULO",
                null,
                "_id=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        // Verificar si se encontró una carga con el ID dado
        if (cursor != null && cursor.moveToFirst()) {
            // Obtener los datos del cursor
            @SuppressLint("Range") String marca = cursor.getString(cursor.getColumnIndex("MARCA"));
            @SuppressLint("Range") int modelo = cursor.getInt(cursor.getColumnIndex("MODELO"));
            @SuppressLint("Range") String placas = cursor.getString(cursor.getColumnIndex("PLACAS"));
            @SuppressLint("Range") int capacidadDeCarga = cursor.getInt(cursor.getColumnIndex("CAPACIDAD_DE_CARGA"));
            @SuppressLint("Range") String color = cursor.getString(cursor.getColumnIndex("COLOR"));
            @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO"));

            // Construir el objeto Carga
            vehiculo = new Vehiculo(id, marca, modelo, placas, capacidadDeCarga, color, estado);

            // Cerrar el cursor
            cursor.close();
        }

        // Cerrar la conexión de la base de datos
        db.close();

        // Devolver la carga encontrada o null si no se encontró ninguna
        return vehiculo;
    }
}
