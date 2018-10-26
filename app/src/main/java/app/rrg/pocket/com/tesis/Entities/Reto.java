package app.rrg.pocket.com.tesis.Entities;

public class Reto {

    private int id;
    private int variable;
    private String nivel;
    private String categoria;
    private int palabra;
    private int npalabra;
    private int usuario;
    private int puntos;

    public Reto(){}

    public Reto(int variable, String nivel, String categoria, int palabra, int npalabra ,int puntos, int usuario) {
        this.variable = variable;
        this.nivel = nivel;
        this.categoria = categoria;
        this.palabra = palabra;
        this.npalabra = npalabra;
        this.puntos = puntos;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPalabra() {
        return palabra;
    }

    public void setPalabra(int palabra) {
        this.palabra = palabra;
    }

    public int getNpalabra() {
        return npalabra;
    }

    public void setNpalabra(int npalabra) {
        this.npalabra += npalabra;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", variable='" + variable + '\'' +
                ", nivel='" + nivel + '\'' +
                ", categoria='" + categoria + '\'' +
                ", palabra='" + palabra + '\'' +
                ", npalabra='" + npalabra + '\'' +
                ", puntos='" + puntos + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }

}

