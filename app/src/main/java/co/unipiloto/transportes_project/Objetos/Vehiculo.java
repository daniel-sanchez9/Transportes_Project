package co.unipiloto.transportes_project.Objetos;

public class Vehiculo {
    private int id;
    private String Marca;
    private int Modelo;
    private String Placas;
    private int Capacidad_de_carga;
    private String Color;
    private String Estado;

    public Vehiculo(int id, String Marca, int Modelo, String Placas, int Capacidad_de_carga, String Color, String Estado) {
        this.id = id;
        this.Marca = Marca;
        this.Modelo = Modelo;
        this.Placas = Placas;
        this.Capacidad_de_carga = Capacidad_de_carga;
        this.Color = Color;
        this.Estado = Estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public int getModelo() {
        return Modelo;
    }

    public void setModelo(int modelo) {
        Modelo = modelo;
    }

    public String getPlacas() {
        return Placas;
    }

    public void setPlacas(String placas) {
        Placas = placas;
    }

    public int getCapacidad_de_carga() {
        return Capacidad_de_carga;
    }

    public void setCapacidad_de_carga(int capacidad_de_carga) {
        Capacidad_de_carga = capacidad_de_carga;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", Marca='" + Marca + '\'' +
                ", Modelo=" + Modelo +
                ", Placas='" + Placas + '\'' +
                ", Capacidad_de_carga=" + Capacidad_de_carga +
                ", Color='" + Color + '\'' +
                ", Estado='" + Estado + '\'' +
                '}';
    }
}
