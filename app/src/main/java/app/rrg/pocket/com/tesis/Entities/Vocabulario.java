package app.rrg.pocket.com.tesis.Entities;

public class Vocabulario {

        private int id;
        private int usuario;

        public Vocabulario(){}

        public Vocabulario(int usuario) {
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

        public void setUsuario(int usuario) {
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
