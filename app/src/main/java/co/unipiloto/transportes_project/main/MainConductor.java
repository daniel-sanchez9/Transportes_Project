package co.unipiloto.transportes_project.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.Registros.*;
import co.unipiloto.transportes_project.VerSolicitudesAceptadas;
import co.unipiloto.transportes_project.VerVehiculosConductor;
import co.unipiloto.transportes_project.database.DatabaseHelper;
import co.unipiloto.transportes_project.informeDeLlegada;


public class MainConductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_conductor);

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
    public void abrir_registrar_viaje(View view){
        startActivity(new Intent(getApplicationContext(), RegistrarViaje.class));
    }
    public void abrir_registros_de_viajes(View view){
        startActivity(new Intent(getApplicationContext(), VerRegistrosViajes.class));
    }
    public void abrir_Ver_solicitudes_aceptadas (View view){
        startActivity(new Intent(getApplicationContext(), VerSolicitudesAceptadas.class));
    }
    public void abrir_Informar_Llegada (View view){
        startActivity(new Intent(getApplicationContext(), informeDeLlegada.class));
    }
    public void abrir_ver_vehiculos (View view){
        startActivity(new Intent(getApplicationContext(), VerVehiculosConductor.class));
    }
}