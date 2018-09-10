package app.rrg.pocket.com.tesis.Entities;

public class Categoria {

    private int id;
    private String nombre;
    private int bloqueado;
    private int nivel;

    public Categoria(){}

    public Categoria(String nombre, int bloqueado, int nivel) {
        this.nombre = nombre;
        this.bloqueado = bloqueado;
        this.nivel = nivel;
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

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", bloqueado='" + bloqueado + '\'' +
                ", nivel='" + nivel + '\'' +
                '}';
    }
}
