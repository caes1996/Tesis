package app.rrg.pocket.com.tesis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Tienda;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.TiendaDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class TabTiendaFragment  extends Fragment {
    private static final String TAG = "TabTiendaFragment";
    Context thisContext;
    private TiendaDB dbT;
    private UsuarioDB db;
    private PalabraDB dbP;
    Tienda tienda;
    Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabtienda_fragment, container,false);
        thisContext = container.getContext();

        db = new UsuarioDB(thisContext);
        dbT = new TiendaDB(thisContext);
        dbP = new PalabraDB(thisContext);

        usuario = db.buscarUsuarios(1);

        tienda = dbT.loadTienda().get(0);

        TextView puntos = (TextView) view.findViewById(R.id.textViewNumero);
        TextView puntaje = (TextView) view.findViewById(R.id.textViewPuntaje);
        puntos.setText(usuario.getPuntaje() + " puntos");
        TextView textView = (TextView) view.findViewById(R.id.textViewTienda);

        if(usuario.getTamano().equals("pequeno")){
            puntos.setTextSize(24);
            puntaje.setTextSize(24);
            textView.setTextSize(24);
        } else if (usuario.getTamano().equals("mediano")) {
            puntos.setTextSize(28);
            puntaje.setTextSize(28);
            textView.setTextSize(28);
        }else{
            puntos.setTextSize(32);
            puntaje.setTextSize(32);
            textView.setTextSize(32);
        }

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layoutPalabrasTienda);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        ArrayList<Palabra> list = dbP.loadTotalPalabras(tienda.getId());
        Log.d("TiendaActivity -> ", "tamano lista: " + list.size());

        if(list.size() > 0){
            boolean flag = false;
            for (int i = 0; i < list.size(); i++){
                if(list.get(i).getBloqueado() == 1){
                    Button palabra = new Button(thisContext);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        palabra.setBackground(thisContext.getDrawable(R.drawable.btn_default));
                    }

                    if(usuario.getTamano().equals("pequeno")){
                        palabra.setTextSize(16);//pequeño 16, mediano 20, grande 24
                    }else if(usuario.getTamano().equals("mediano")){
                        palabra.setTextSize(20);//pequeño 16, mediano 20, grande 24
                    }else{
                        palabra.setTextSize(24);//pequeño 16, mediano 20, grande 24
                    }

                    palabra.setLayoutParams(lp);
                    palabra.setText(list.get(i).getNombre());
                    palabra.setId(list.get(i).getId());
                    palabra.setOnClickListener(new ButtonsOnClickListener(thisContext, palabra));
                    layout.addView(palabra);
                    flag = true;
                }
            }

            if(flag){
                LinearLayout.LayoutParams lpTV = new LinearLayout.LayoutParams(0,
                        0 );
                textView.setLayoutParams(lpTV);
                textView.setVisibility(view.INVISIBLE);
            }
        }

        return view;
    }

    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        Button palabra;

        public ButtonsOnClickListener(Context context, Button palabra) {
            this.context = context;
            this.palabra = palabra;
        }

        @Override
        public void onClick(View v)
        {
            /*Lo primero es verificar si las palabras no sehan creado, de ser así se crean,
            de lo contrario se llama la vista de las palabras;
             */
            ArrayList<Palabra> listaPalabras = dbP.loadPalabra(palabra.getId());
            Log.d("TiendaActivity -> ", "tamano palabras: " + listaPalabras.size());

            launchIntent(context, palabra.getId());
        }

    };

    public void launchIntent(Context context, int idPalabra){
        //Intent i = new Intent(context, TiendaActivity.class);

        Intent intent = new Intent(getActivity(), TiendaActivity.class);
        intent.putExtra("idPalabra", "" + idPalabra);
        getActivity().startActivity(intent);

        /*i.putExtra("idPalabra", "" + idPalabra);
        startActivity(i);*/
    }
}
