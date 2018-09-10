package app.rrg.pocket.com.tesis.Entities;

public class Tienda {

    private int id;
    private int usuario;

    public Tienda(){}

    public Tienda(int usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int vocabulario) {
        this.usuario = usuario;
    }


    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                '}';
    }

}
