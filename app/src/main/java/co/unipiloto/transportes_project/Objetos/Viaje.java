package co.unipiloto.transportes_project.Objetos;

public class Viaje {
    private int id;
    private String vehiculo;
    private String ciudadOrigen;
    private String direccionOrigen;
    private String ciudadDestino;
    private String direccionDestino;

    public Viaje(int id, String vehiculo, String ciudadOrigen, String direccionOrigen, String ciudadDestino, String direccionDestino) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.ciudadOrigen = ciudadOrigen;
        this.direccionOrigen = direccionOrigen;
        this.ciudadDestino = ciudadDestino;
        this.direccionDestino = direccionDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    @Override
    public String toString() {
        return "Viaje{" +
                "id=" + id +
                ", vehiculo='" + vehiculo + '\'' +
                ", ciudadOrigen='" + ciudadOrigen + '\'' +
                ", direccionOrigen='" + direccionOrigen + '\'' +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", direccionDestino='" + direccionDestino + '\'' +
                '}';
    }
}
