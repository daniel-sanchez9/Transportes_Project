package co.unipiloto.transportes_project.Registros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.model.LatLng;

import co.unipiloto.transportes_project.Localizacion.Mapas;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class RegistrarCarga extends AppCompatActivity {

    private Button buttonOrigenDireccion, buttonDestinoDireccion, buttonRegistrarCarga;
    private String ubicacionOrigen, ubicacionDestino, nombreDeCarga, estado, creadorCarga;
    private EditText editTextNombreCarga, editTextPeso;

    private Integer peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_carga);

        editTextNombreCarga = findViewById(R.id.editTextNombreCarga);
        editTextPeso = findViewById(R.id.editTextPeso);
        buttonOrigenDireccion = findViewById(R.id.buttonOrigenDireccion);
        buttonDestinoDireccion = findViewById(R.id.buttonDestinoDireccion);
        buttonRegistrarCarga = findViewById(R.id.buttonRegistrarCarga);

        DatabaseHelper database = new DatabaseHelper(this);

        buttonOrigenDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de mapas y permitir al usuario seleccionar la ubicación de origen
                Intent intent = new Intent(RegistrarCarga.this, Mapas.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonDestinoDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de mapas y permitir al usuario seleccionar la ubicación de destino
                Intent intent = new Intent(RegistrarCarga.this, Mapas.class);
                startActivityForResult(intent, 2);
            }
        });

        buttonRegistrarCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreDeCarga = editTextNombreCarga.getText().toString();
                estado = "Disponible"; // Por ejemplo, asigna un estado por defecto



                // Reemplaza "correo@example.com" con el correo del usuario logueado
                String emailUsuarioLogueado ="p@p.com";


                creadorCarga = obtenerNombreUsuario(database, emailUsuarioLogueado);

                if (creadorCarga == null) {
                    Toast.makeText(getApplicationContext(), "Error: No se pudo obtener el nombre del creador de la carga", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    peso = Integer.parseInt(editTextPeso.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "El peso debe ser un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ubicacionOrigen == null || ubicacionDestino == null) {
                    Toast.makeText(getApplicationContext(), "Por favor, selecciona las ubicaciones de origen y destino", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realizar la inserción en la base de datos con las ubicaciones seleccionadas
                database.insertarCarga(nombreDeCarga, peso, ubicacionOrigen, ubicacionDestino, estado, creadorCarga);
                // Ejemplo: Mostrar un mensaje de éxito
                Toast.makeText(RegistrarCarga.this, "Carga registrada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // Seleccionar ubicación de origen
                LatLng ubicacion = data.getParcelableExtra("ubicacion");
                ubicacionOrigen = ubicacion.toString();
                Toast.makeText(getApplicationContext(), "Ubicación de origen seleccionada: " + ubicacionOrigen, Toast.LENGTH_SHORT).show();
            } else if (requestCode == 2) {
                // Seleccionar ubicación de destino
                LatLng ubicacion = data.getParcelableExtra("ubicacion");
                ubicacionDestino = ubicacion.toString();
                Toast.makeText(getApplicationContext(), "Ubicación de destino seleccionada: " + ubicacionDestino, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método obtener el nombre del usuario desde la base de datos
    private String obtenerNombreUsuario(DatabaseHelper database, String username) {
        String nombreUsuario = database.getUsername(username);
        return nombreUsuario;
    }
}


