package co.unipiloto.transportes_project.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.AceptarSolicitudes;
import co.unipiloto.transportes_project.R;
import co.unipiloto.transportes_project.Registros.*;
import co.unipiloto.transportes_project.database.DatabaseHelper;

public class mainPropietarioVehiculo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_propietario_vehiculo);

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
    public void abrirRegistrarVehiculo(View view){
        startActivity(new Intent(getApplicationContext(), RegistrarVehiculo.class));
    }
    public void abrirRegistrosVehiculo(View view){
        startActivity(new Intent(getApplicationContext(), VerRegistrosVehiculos.class));
    }
    public void abrirVerSolicitudes(View view){
        startActivity(new Intent(getApplicationContext(), AceptarSolicitudes.class));
    }
}
