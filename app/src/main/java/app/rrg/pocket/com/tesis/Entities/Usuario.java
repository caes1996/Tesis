package app.rrg.pocket.com.tesis.Entities;

public class Usuario {
    private int id;
    private String nombre;
    private String edad;
    private int puntaje;
    private String tamano;

    public Usuario(){}

    public Usuario(String nombre, String edad, int puntaje, String tamano) {
        this.nombre = nombre;
        this.edad = edad;
        this.puntaje = puntaje;
        this.tamano = tamano;
    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEdad() {
        return edad;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getTamano(){ return tamano; }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public  void setPuntaje(int puntaje){
        this.puntaje = puntaje;
    }

    public void setTamano(String tamano){ this.tamano = tamano; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='"        + nombre        + '\'' +
                ", edad='"          + edad          + '\'' +
                ", puntaje='"       + puntaje       + '\'' +
                ", tamano='"        + tamano        + '\'' +
                '}';
    }
}