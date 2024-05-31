package co.unipiloto.transportes_project;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NuevoServicioSolicitudCarga extends IntentService {
    private static final String CHANNEL_ID = "CargoRequestChannel";

    public NuevoServicioSolicitudCarga() {
        super("NuevoServicioSolicitudCarga");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Simula la verificación de nuevas solicitudes de carga
        boolean hayNuevaSolicitud = verificarNuevaSolicitudCarga();

        if (hayNuevaSolicitud) {
            enviarNotificacion("Nueva Solicitud de Carga", "Tienes una nueva solicitud de carga.");
        }
    }

    private boolean verificarNuevaSolicitudCarga() {
        return true;
    }

    private void enviarNotificacion(String titulo, String mensaje) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Solicitudes de Carga",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("NUEVA_SOLICITUD_CARGA");
        sendBroadcast(broadcastIntent);
    }
}

