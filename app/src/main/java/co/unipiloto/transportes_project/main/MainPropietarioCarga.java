package co.unipiloto.transportes_project.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.HacerSolicitudCarga;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.Registros.*;
import co.unipiloto.transportes_project.VerRegistrosViajesPropietario;
import co.unipiloto.transportes_project.database.DatabaseHelper;


public class MainPropietarioCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper database = new DatabaseHelper(this);

        String email = getIntent().getStringExtra("email");
        String nombreUsuario = obtenerNombreUsuario(database, email);

        TextView textView = findViewById(R.id.Bienvenido_usuario);
        textView.setText("Bienvenido " + nombreUsuario);
    }
    private String obtenerNombreUsuario(DatabaseHelper database, String username) {
        String nombreUsuario = database.getUsername(username);
        return nombreUsuario;
    }
    public void abrir_registrar_carga(View view){
        startActivity(new Intent(getApplicationContext(), RegistrarCarga.class));

    }
    public void Abrir_registros_cargas(View view){
        startActivity(new Intent(getApplicationContext(), VerRegistrosCarga.class));
    }
    public void Abrir_registros_viajes(View view){
        startActivity(new Intent(getApplicationContext(), VerRegistrosViajesPropietario.class));
    }

}