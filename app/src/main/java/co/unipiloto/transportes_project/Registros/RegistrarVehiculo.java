package co.unipiloto.transportes_project.Registros;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class RegistrarVehiculo extends AppCompatActivity {

    private EditText editTextMarca, editTextModelo, editTextPlacas, editTextCapacidad_de_carga, editTextColor;
    private Spinner spinnerEstado;
    private Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);

        editTextMarca = findViewById(R.id.editTextMarca);
        editTextModelo = findViewById(R.id.editTextModelo);
        editTextPlacas = findViewById(R.id.editTextPlacas);
        editTextCapacidad_de_carga = findViewById(R.id.editTextCapacidad);
        editTextColor = findViewById(R.id.editTextColor);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        buttonRegistrar = findViewById(R.id.buttonRegistrarCarga);
        DatabaseHelper database = new DatabaseHelper(this);
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Marca = editTextMarca.getText().toString();
                int Modelo = Integer.parseInt(editTextModelo.getText().toString());
                String Placas = editTextPlacas.getText().toString();
                int Capacidad_De_Carga = Integer.parseInt(editTextCapacidad_de_carga.getText().toString());
                String Color = editTextColor.getText().toString();
                String estadoSeleccionado = spinnerEstado.getSelectedItem().toString();

                database.insertarVehiculo(Marca, Modelo, Placas, Capacidad_De_Carga, Color, estadoSeleccionado);
                Toast.makeText(RegistrarVehiculo.this, "Registro exitoso: " + Marca, Toast.LENGTH_SHORT).show();
            }
        });
    }
}