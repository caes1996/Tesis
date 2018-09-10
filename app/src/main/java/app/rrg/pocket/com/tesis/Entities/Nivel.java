package app.rrg.pocket.com.tesis.Entities;

public class Nivel {

    private int id;
    private String nombre;
    private int bloqueado;
    private int vocabulario;

    public Nivel(){}

    public Nivel(String nombre, int bloqueado, int vocabulario) {
        this.nombre = nombre;
        this.bloqueado = bloqueado;
        this.vocabulario = vocabulario;
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

    public int getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(int vocabulario) {
        this.vocabulario = vocabulario;
    }


    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", bloqueado='" + bloqueado + '\'' +
                ", vocabulario='" + vocabulario + '\'' +
                '}';
    }

}
