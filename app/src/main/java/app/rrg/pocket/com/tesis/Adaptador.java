package app.rrg.pocket.com.tesis;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Reto;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;

public class Adaptador extends BaseAdapter{
    private Context context;
    private ArrayList<Reto> listItems;
    private CategoriaDB dbC;
    private Categoria categoria;

    public Adaptador(Context context, ArrayList<Reto> listItems){
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Reto reto = (Reto) getItem(i);

        if (reto.getPalabra() != reto.getNpalabra()) {
            int imagen = 0;

            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);

            ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
            TextView titulo = (TextView) convertView.findViewById(R.id.textViewTituloReto);
            TextView contenido = (TextView) convertView.findViewById(R.id.textViewContenidoReto);
            TextView estado = (TextView) convertView.findViewById(R.id.textViewEstadoReto);

            if (reto.getVariable() == 0) {
                imagen = (R.drawable.money);
                imgFoto.setImageResource(imagen);
                titulo.setText("Compra en la tienda. " + reto.getPuntos() + " pts");
                contenido.setText("Compra " + reto.getPalabra() + " palabras en la tienda");
                estado.setText(reto.getNpalabra() + "/" + reto.getPalabra());
            } else {
                imagen = (R.drawable.mic);
                imgFoto.setImageResource(imagen);
                titulo.setText("Di palabras. " + reto.getPuntos() + " pts");
                contenido.setText("Di " + reto.getPalabra() + " palabras de la categoría " +
                        reto.getCategoria() + " del " + reto.getNivel());
                estado.setText(reto.getNpalabra() + "/" + reto.getPalabra());
            }

            return convertView;

        } else{

            return null;

        }
    }
}
