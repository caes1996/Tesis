package app.rrg.pocket.com.tesis.Entities;

public class Palabra {

    private int id;
    private String nombre;
    private int costo;
    private int puntaje;
    private int bloqueado;
    private int tienda;
    private int categoria;

    public Palabra(){}

    public Palabra(String nombre, int costo, int puntaje, int bloqueado, int tienda ,int categoria) {
        this.nombre = nombre;
        this.costo = costo;
        this.puntaje = puntaje;
        this.bloqueado = bloqueado;
        this.tienda = tienda;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getTienda() {
        return tienda;
    }

    public void setTienda(int tienda) {
        this.tienda = tienda;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", costo='" + costo + '\'' +
                ", puntaje='" + puntaje + '\'' +
                ", bloqueado='" + bloqueado + '\'' +
                ", tienda='" + tienda + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }

}
