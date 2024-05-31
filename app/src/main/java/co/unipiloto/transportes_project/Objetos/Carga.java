package co.unipiloto.transportes_project.Objetos;

import java.io.Serializable;

public class Carga implements Serializable {

    private int id;
    private String nombreDeCarga;
    private int peso;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String estadoDeCarga;
    private String creadorDeCarga;

    public Carga(int id, String nombreDeCarga, int peso, String ciudadOrigen, String ciudadDestino, String estadoDeCarga, String creadorDeCarga) {
        this.id = id;
        this.nombreDeCarga = nombreDeCarga;
        this.peso = peso;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.estadoDeCarga = estadoDeCarga;
        this.creadorDeCarga = creadorDeCarga;
    }

    // Getters y setters para cada campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDeCarga() {
        return nombreDeCarga;
    }

    public void setNombreDeCarga(String nombreDeCarga) {
        this.nombreDeCarga = nombreDeCarga;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getEstadoDeCarga() {
        return estadoDeCarga;
    }

    public void setEstadoDeCarga(String estadoDeCarga) {
        this.estadoDeCarga = estadoDeCarga;
    }

    public String getCreadorDeCarga() {
        return creadorDeCarga;
    }

    public void setCreadorDeCarga(String creadorDeCarga) {
        this.creadorDeCarga = creadorDeCarga;
    }

    @Override
    public String toString() {
        return "Carga{" +
                "id=" + id +
                ", nombreDeCarga='" + nombreDeCarga + '\'' +
                ", peso=" + peso +
                ", ciudadOrigen='" + ciudadOrigen + '\'' +
                ", ciudadDestino='" + ciudadDestino + '\'' +
                ", estadoDeCarga='" + estadoDeCarga + '\'' +
                ", creadorDeCarga='" + creadorDeCarga + '\'' +
                '}';
    }
}
