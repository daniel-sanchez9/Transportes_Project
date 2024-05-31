package co.unipiloto.transportes_project.Localizacion;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.unipiloto.transportes_project.R;


public class Navegacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);

        // Obtener los datos de la carga seleccionada del Intent
        Intent intent = getIntent();
        String ciudadOrigen = intent.getStringExtra("ciudadOrigen");
        String ciudadDestino = intent.getStringExtra("ciudadDestino");

        // Extraer las coordenadas de las cadenas de texto
        double latOrigen = extractLatitude(ciudadOrigen);
        double lngOrigen = extractLongitude(ciudadOrigen);
        double latDestino = extractLatitude(ciudadDestino);
        double lngDestino = extractLongitude(ciudadDestino);

        // Verificar que las coordenadas sean válidas
        if (latOrigen == 0 || lngOrigen == 0 || latDestino == 0 || lngDestino == 0) {
            Toast.makeText(this, "Error: Coordenadas inválidas", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad si las coordenadas son inválidas
            return;
        }

        // Crear una URI con las coordenadas de origen y destino
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latDestino + "," + lngDestino + "&origin=" + latOrigen + "," + lngOrigen);

        // Crear un Intent para abrir Google Maps con las coordenadas especificadas
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Especificar que se utilice Google Maps

        // Verificar si hay una aplicación que pueda manejar el Intent
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent); // Abrir Google Maps
        } else {
            Toast.makeText(this, "Error: No se encontró Google Maps", Toast.LENGTH_SHORT).show();
        }

        // Finalizar la actividad después de iniciar Google Maps
        finish();
    }

    // Método para extraer la latitud de la cadena de texto
    private double extractLatitude(String coordinateString) {
        if (coordinateString != null && coordinateString.contains("lat/lng:")) {
            int startIndex = coordinateString.indexOf("(") + 1;
            int commaIndex = coordinateString.indexOf(",");
            String latitudeString = coordinateString.substring(startIndex, commaIndex);
            return Double.parseDouble(latitudeString);
        }
        return 0;
    }

    // Método para extraer la longitud de la cadena de texto
    private double extractLongitude(String coordinateString) {
        if (coordinateString != null && coordinateString.contains("lat/lng:")) {
            int commaIndex = coordinateString.indexOf(",") + 1;
            int endIndex = coordinateString.indexOf(")");
            String longitudeString = coordinateString.substring(commaIndex, endIndex);
            return Double.parseDouble(longitudeString);
        }
        return 0;
    }
}
