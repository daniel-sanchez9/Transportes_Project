package co.unipiloto.transportes_project.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.unipiloto.transportes_project.NuevoServicioSolicitudCarga;
import co.unipiloto.transportes_project.Objetos.Carga;
import co.unipiloto.transportes_project.Objetos.Vehiculo;
import co.unipiloto.transportes_project.Objetos.Viaje;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "convergente";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "USER TEXT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT," +
                "FECHA TEXT," +
                "GENERO TEXT," +
                "TIPO_USUARIO TEXT);");

        db.execSQL("CREATE TABLE CARGAS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NOMBRE_DE_CARGA TEXT, "
                + "PESO INTEGER, "
                + "CIUDAD_ORIGEN TEXT, "
                + "CIUDAD_DESTINO TEXT, "
                + "ESTADO_DE_CARGA TEXT, "
                + "CREADOR_DE_CARGA TEXT);");

        db.execSQL("CREATE TABLE VEHICULO (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "MARCA TEXT,"
                + "MODELO INTEGER,"
                + "PLACAS TEXT,"
                + "CAPACIDAD_DE_CARGA INTEGER,"
                + "COLOR TEXT,"
                + "ESTADO TEXT);");

        db.execSQL("CREATE TABLE VIAJE (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "VEHICULO TEXT,"
                + "CIUDAD_ORIGEN TEXT,"
                + "DIRECCION_ORIGEN TEXT,"
                + "CIUDAD_DESTINO TEXT,"
                + "DIRECCION_DESTINO TEXT);");

        db.execSQL("CREATE TABLE SOLICITUDES_CARGA (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CARGA_ID INTEGER,"
                + "ESTADO_SOLICITUD TEXT,"
                + "FOREIGN KEY(CARGA_ID) REFERENCES CARGAS(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Si la versión antigua de la base de datos es menor que 2,
            // significa que es necesario realizar la actualización
            db.execSQL("ALTER TABLE CARGAS ADD COLUMN NUEVA_COLUMNA TEXT;");
        }
    }

    public void insertarUsuario(SQLiteDatabase db, String name, String user, String email, String password, String fecha, String genero, String tipoUsuario) {
        ContentValues datos = new ContentValues();
        datos.put("NAME", name);
        datos.put("USER", user);
        datos.put("EMAIL", email);
        datos.put("PASSWORD", password);
        datos.put("FECHA", fecha);
        datos.put("GENERO", genero);
        datos.put("TIPO_USUARIO", tipoUsuario);
        db.insert("USUARIOS", null, datos);
        db.close();
    }

    public void insertarCarga(String nombreDeCarga, int peso, String ciudadOrigen, String ciudadDestino, String estado, String creadorCarga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_DE_CARGA", nombreDeCarga);
        values.put("PESO", peso);
        values.put("CIUDAD_ORIGEN", ciudadOrigen);
        values.put("CIUDAD_DESTINO", ciudadDestino);
        values.put("ESTADO_DE_CARGA", estado);
        values.put("CREADOR_DE_CARGA", creadorCarga);
        db.insert("CARGAS", null, values);
        db.close();
    }


    public void insertarVehiculo(String marca, int modelo, String placas, int capacidadDeCarga, String color, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("MARCA", marca);
        valores.put("MODELO", modelo);
        valores.put("PLACAS", placas);
        valores.put("CAPACIDAD_DE_CARGA", capacidadDeCarga);
        valores.put("COLOR", color);
        valores.put("ESTADO", estado);
        db.insert("VEHICULO", null, valores);
        db.close();
    }


    public void insertarViaje(String vehiculo, String ciudadOrigen, String direccionOrigen, String ciudadDestino, String direccionDestino) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valor = new ContentValues();
        valor.put("VEHICULO", vehiculo);
        valor.put("CIUDAD_ORIGEN", ciudadOrigen);
        valor.put("DIRECCION_ORIGEN", direccionOrigen);
        valor.put("CIUDAD_DESTINO", ciudadDestino);
        valor.put("DIRECCION_DESTINO", direccionDestino);
        db.insert("VIAJE", null, valor);
        db.close();
    }
    public void insertarSolicitudCarga(int cargaId, String estadoSolicitud, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CARGA_ID", cargaId);
        values.put("ESTADO_SOLICITUD", estadoSolicitud);
        db.insert("SOLICITUDES_CARGA", null, values);
        db.close();

        // Después de insertar la solicitud, iniciar el servicio para enviar la notificación
        Intent intent = new Intent(context, NuevoServicioSolicitudCarga.class);
        context.startService(intent);
    }


    public void actualizarEstadoSolicitud(int solicitudId, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ESTADO_SOLICITUD", nuevoEstado);
        db.update("SOLICITUDES_CARGA", values, "_id = ?", new String[]{String.valueOf(solicitudId)});
        db.close();
    }

    public List<Carga> obtenerSolicitudesPendientes() {
        List<Carga> listaCargas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT CARGAS._id, NOMBRE_DE_CARGA, PESO, CIUDAD_ORIGEN, CIUDAD_DESTINO, ESTADO_DE_CARGA, CREADOR_DE_CARGA "
                + "FROM CARGAS INNER JOIN SOLICITUDES_CARGA ON CARGAS._id = SOLICITUDES_CARGA.CARGA_ID "
                + "WHERE SOLICITUDES_CARGA.ESTADO_SOLICITUD = 'Pendiente'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int cargaId = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
                @SuppressLint("Range") int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                @SuppressLint("Range") String estadoCarga = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
                @SuppressLint("Range") String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

                Carga carga = new Carga(cargaId, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estadoCarga, creadorDeCarga);
                listaCargas.add(carga);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaCargas;
    }



    public List<Carga> obtenerTodasLasCargas() {
        List<Carga> listaCargas = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("CARGAS", // Tabla de cargas
                null, // Todas las columnas
                null, // Sin cláusula WHERE
                null, // Sin argumentos para la cláusula WHERE
                null, // Sin agrupamiento
                null, // Sin condición de agrupamiento
                null); // Sin orden

        // Iterar sobre el cursor para obtener las cargas
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de cada carga del cursor
                @SuppressLint("Range") int cargaId = cursor.getInt(cursor.getColumnIndex("_id")); // Utiliza "_id" en lugar de "cargaId"
                @SuppressLint("Range") String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
                @SuppressLint("Range") int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
                @SuppressLint("Range") String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

                // Crear un nuevo objeto Carga y agregarlo a la lista
                Carga carga = new Carga(cargaId, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorDeCarga);
                listaCargas.add(carga);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        // Devolver la lista de cargas
        return listaCargas;
    }
    public List<Carga> obtenerCargasSolicitadas() {
        List<Carga> listaCargas = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {"Solicitada"};
        Cursor cursor = db.query("CARGAS", // Tabla de cargas
                null, // Todas las columnas
                "ESTADO_DE_CARGA = ?", // Cláusula WHERE
                selectionArgs, // Argumentos para la cláusula WHERE
                null, // Sin agrupamiento
                null, // Sin condición de agrupamiento
                null); // Sin orden

        // Iterar sobre el cursor para obtener las cargas
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int cargaId = cursor.getInt(cursor.getColumnIndex("_id")); // Utiliza "_id" en lugar de "cargaId"
                @SuppressLint("Range") String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
                @SuppressLint("Range") int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
                @SuppressLint("Range") String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

                // Crear un nuevo objeto Carga y agregarlo a la lista
                Carga carga = new Carga(cargaId, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorDeCarga);
                listaCargas.add(carga);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        // Devolver la lista de cargas
        return listaCargas;
    }

    public List<Carga> obtenerCargasAceptadas() {
        List<co.unipiloto.transportes_project.Objetos.Carga> listaCargas = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {"Aceptada"};
        Cursor cursor = db.query("CARGAS", // Tabla de cargas
                null, // Todas las columnas
                "ESTADO_DE_CARGA = ?", // Cláusula WHERE
                selectionArgs, // Argumentos para la cláusula WHERE
                null, // Sin agrupamiento
                null, // Sin condición de agrupamiento
                null); // Sin orden

        // Iterar sobre el cursor para obtener las cargas
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int cargaId = cursor.getInt(cursor.getColumnIndex("_id")); // Utiliza "_id" en lugar de "cargaId"
                @SuppressLint("Range") String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
                @SuppressLint("Range") int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
                @SuppressLint("Range") String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

                // Crear un nuevo objeto Carga y agregarlo a la lista
                co.unipiloto.transportes_project.Objetos.Carga carga = new co.unipiloto.transportes_project.Objetos.Carga(cargaId, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorDeCarga);
                listaCargas.add(carga);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        // Devolver la lista de cargas
        return listaCargas;
    }



    public void eliminarCarga(Carga carga) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CARGAS", "_id=?", new String[]{String.valueOf(carga.getId())});
        db.close();
    }
    public void eliminarVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("VEHICULO", "_id=?", new String[]{String.valueOf(vehiculo.getId())});
        db.close();
    }
    public void eliminarViaje(Viaje viaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("VIAJE", "_id=?", new String[]{String.valueOf(viaje.getId())});
        db.close();
    }

    public void actualizarCarga(Carga carga) {
        // Obtener una instancia de la base de datos en modo de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Crear un objeto ContentValues para almacenar los nuevos valores de la carga
        ContentValues values = new ContentValues();
        values.put("NOMBRE_DE_CARGA", carga.getNombreDeCarga());
        values.put("PESO", carga.getPeso());
        values.put("CIUDAD_ORIGEN", carga.getCiudadOrigen());
        values.put("CIUDAD_DESTINO", carga.getCiudadDestino());
        values.put("ESTADO_DE_CARGA", carga.getEstadoDeCarga());

        // Definir la cláusula WHERE para identificar la carga que se actualizará
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(carga.getId())};

        // Ejecutar el método update de la base de datos para actualizar el registro
        db.update("CARGAS", values, whereClause, whereArgs);

        // Cerrar la conexión de la base de datos
        db.close();
    }
    public void actualizarEstadoCarga(int cargaId, String nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ESTADO_DE_CARGA", nuevoEstado);

        db.update("CARGAS", values, "_id=?", new String[]{String.valueOf(cargaId)});
        db.close();
    }
    public void actualizarVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("MARCA", vehiculo.getMarca());
        valores.put("MODELO", vehiculo.getModelo());
        valores.put("PLACAS", vehiculo.getPlacas());
        valores.put("CAPACIDAD_DE_CARGA", vehiculo.getCapacidad_de_carga());
        valores.put("COLOR", vehiculo.getColor());
        valores.put("ESTADO", vehiculo.getEstado());
        db.update("VEHICULO", valores, "_id=?", new String[]{String.valueOf(vehiculo.getId())});
        db.close();
    }




    public List<Vehiculo> obtenerTodosLosVehiculos() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("VEHICULO", null, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int vehiculoId = cursor.getInt(cursor.getColumnIndex("_id"));
                    @SuppressLint("Range") String marca = cursor.getString(cursor.getColumnIndex("MARCA"));
                    @SuppressLint("Range") int modelo = cursor.getInt(cursor.getColumnIndex("MODELO"));
                    @SuppressLint("Range") String placas = cursor.getString(cursor.getColumnIndex("PLACAS"));
                    @SuppressLint("Range") int capacidadDeCarga = cursor.getInt(cursor.getColumnIndex("CAPACIDAD_DE_CARGA"));
                    @SuppressLint("Range") String color = cursor.getString(cursor.getColumnIndex("COLOR"));
                    @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("ESTADO"));

                    Vehiculo vehiculo = new Vehiculo(vehiculoId, marca, modelo, placas, capacidadDeCarga, color, estado);
                    listaVehiculos.add(vehiculo);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaVehiculos;
    }

    public List<Viaje> obtenerTodosLosViajes() {
        List<Viaje> listaViajes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("VIAJE", null, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int viajeId = cursor.getInt(cursor.getColumnIndex("_id"));
                    @SuppressLint("Range") String vehiculo = cursor.getString(cursor.getColumnIndex("VEHICULO"));
                    @SuppressLint("Range") String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                    @SuppressLint("Range") String direccionOrigen = cursor.getString(cursor.getColumnIndex("DIRECCION_ORIGEN"));
                    @SuppressLint("Range") String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                    @SuppressLint("Range") String direccionDestino = cursor.getString(cursor.getColumnIndex("DIRECCION_DESTINO"));

                    Viaje viaje = new Viaje(viajeId, vehiculo, ciudadOrigen, direccionOrigen, ciudadDestino, direccionDestino);
                    listaViajes.add(viaje);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaViajes;
    }

    public boolean verificarUsuario(String email, String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("USUARIOS",
                new String[]{"_id"},
                "EMAIL=? OR USER=?",
                new String[]{email, user},
                null, null, null);

        boolean verificarUsuario = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return verificarUsuario;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {"_id"};
            String selection = "EMAIL=? AND PASSWORD=?";
            String[] selectionArgs = {email, password};

            cursor = db.query("USUARIOS", projection, selection, selectionArgs, null, null, null);
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASSWORD", newPassword);
        int rowsAffected = db.update("USUARIOS", values, "EMAIL = ?", new String[]{email});
        db.close();
        return rowsAffected > 0;
    }

    @SuppressLint("Range")
    public String getUserType(String emailOrUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TIPO_USUARIO FROM USUARIOS WHERE EMAIL = ? OR USER = ?";
        Cursor cursor = db.rawQuery(query, new String[]{emailOrUsername, emailOrUsername});

        String userType = null;
        if (cursor.moveToFirst()) {
            userType = cursor.getString(cursor.getColumnIndex("TIPO_USUARIO"));
        }

        cursor.close();
        db.close();

        return userType;
    }

    @SuppressLint("Range")
    public String getUsername(String emailOrUsername) {
        if (emailOrUsername == null) {
            throw new IllegalArgumentException("The email or username cannot be null");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT USER FROM USUARIOS WHERE EMAIL = ? OR USER = ?";
        Cursor cursor = null;
        String username = null;

        try {
            cursor = db.rawQuery(query, new String[]{emailOrUsername, emailOrUsername});
            if (cursor != null && cursor.moveToFirst()) {
                int userIndex = cursor.getColumnIndex("USER");
                if (userIndex != -1) {
                    username = cursor.getString(userIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return username;
    }

}
