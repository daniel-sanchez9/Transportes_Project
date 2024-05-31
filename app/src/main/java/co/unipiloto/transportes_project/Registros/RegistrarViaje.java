package co.unipiloto.transportes_project.Registros;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class RegistrarViaje extends AppCompatActivity {

    private EditText editTextVehiculo, editTextOrigenCiudad, editTextOrigenDireccion, editTextDestinoCiudad, editTextDestinoDireccion;
    private Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_viaje);

        editTextVehiculo = findViewById(R.id.editTextVehiculo);
        editTextOrigenCiudad = findViewById(R.id.editTextOrigenC);
        editTextOrigenDireccion = findViewById(R.id.editTextOrigenD);
        editTextDestinoCiudad = findViewById(R.id.editTextDestinoC);
        editTextDestinoDireccion = findViewById(R.id.editTextDestinoD);
        buttonRegistrar = findViewById(R.id.buttonRegistrarViaje);
        DatabaseHelper database = new DatabaseHelper(this);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehiculo = editTextVehiculo.getText().toString();
                String origenCiudad = editTextOrigenCiudad.getText().toString();
                String origenDireccion = editTextOrigenDireccion.getText().toString();
                String destinoCiudad = editTextDestinoCiudad.getText().toString();
                String destinoDireccion = editTextDestinoDireccion.getText().toString();

                database.insertarViaje(vehiculo, origenCiudad, origenDireccion,destinoCiudad, destinoDireccion);
                Toast.makeText(RegistrarViaje.this, "Registro exitoso: " + vehiculo, Toast.LENGTH_SHORT).show();
            }
        });
    }
}