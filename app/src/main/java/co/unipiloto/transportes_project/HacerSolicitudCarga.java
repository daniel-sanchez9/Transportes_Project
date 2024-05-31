package co.unipiloto.transportes_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.database.DatabaseHelper;

public class HacerSolicitudCarga extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_solicitud_carga);

        dbHelper = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int cargaId = extras.getInt("cargaId");

            Button enviarSolicitudButton = findViewById(R.id.enviar_solicitud_button);
            enviarSolicitudButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.actualizarEstadoCarga(cargaId, "Solicitada");

                    // Inserta la solicitud de carga y activa el servicio para enviar la notificación
                    dbHelper.insertarSolicitudCarga(cargaId, "Solicitada", HacerSolicitudCarga.this);

                    Toast.makeText(HacerSolicitudCarga.this, "Solicitud enviada para la carga con ID: " + cargaId, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}


