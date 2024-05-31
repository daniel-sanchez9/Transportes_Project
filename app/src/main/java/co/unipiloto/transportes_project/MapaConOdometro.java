package co.unipiloto.transportes_project;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import co.unipiloto.transportes_project.OdometerService;
import co.unipiloto.transportes_project.R;

public class MapaConOdometro extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private OdometerService odometerService;
    private boolean bound = false;
    private TextView distanceTextView;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OdometerService.OdometerBinder binder = (OdometerService.OdometerBinder) service;
            odometerService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    private LatLng point1 = new LatLng(37.421755, -122.085163); // Coordenadas punto 1
    private LatLng point2 = new LatLng(37.428632, -121.899414); // Coordenadas punto 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_con_odometro);

        distanceTextView = findViewById(R.id.distanceTextView);

        // Iniciar el mapa de Google Maps de manera asíncrona
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Agregar marcadores y dibujar ruta entre los puntos
        addMarkers();
        drawRoute();

        // Actualizar la distancia en el TextView
        if (bound) {
            updateDistance();
        }
    }

    private void addMarkers() {
        mMap.addMarker(new MarkerOptions().position(point1).title("Point 1"));
        mMap.addMarker(new MarkerOptions().position(point2).title("Point 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point1, 5));
    }

    private void drawRoute() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(point1)
                .add(point2);
        Polyline polyline = mMap.addPolyline(polylineOptions);
        polyline.setWidth(5); // Ancho de la línea de la ruta
        polyline.setColor(getResources().getColor(R.color.colorPrimary));
    }

    private void updateDistance() {
        if (bound && odometerService != null) {
            double distance = odometerService.getDistance();
            distanceTextView.setText(String.format("%.2f meters", distance));
        }
    }
}
