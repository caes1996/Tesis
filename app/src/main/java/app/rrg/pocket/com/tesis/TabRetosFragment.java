package app.rrg.pocket.com.tesis;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Reto;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.RetoDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class TabRetosFragment  extends Fragment {
    private static final String TAG = "TabRetosFragment";
    private ListView lvItems;
    private Adaptador adaptador;
    private RetoDB dbR;
    private UsuarioDB db;
    private NivelDB dbN;
    private Usuario usuario;
    private CategoriaDB dbC;
    private PalabraDB dbP;
    private Nivel nivel1;
    private Nivel nivel2;
    private Nivel nivel3;
    private ArrayList<Categoria> categorias;
    private ArrayList<Palabra> palabras;
    Context thisContext;
    int contador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabretos_fragment, container,false);

        thisContext = container.getContext();
        contador = 0;

        dbR = new RetoDB(thisContext);
        db = new UsuarioDB(thisContext);
        dbN = new NivelDB(thisContext);
        dbC = new CategoriaDB(thisContext);
        dbP = new PalabraDB(thisContext);

        usuario = db.buscarUsuarios(1);
        nivel1 = dbN.buscarNivel("Nivel 1");
        nivel2 = dbN.buscarNivel("Nivel 2");
        nivel3 = dbN.buscarNivel("Nivel 3");

        ArrayList<Reto> retos = dbR.loadReto();
        ArrayList<Reto> retosAct = new ArrayList<>();

        if(masRetos(retos)){
            generarRetos(nivel1, nivel2, nivel3);
        }

        if(retosAct.size() > 0){
            adaptador = new Adaptador(getActivity(), retosAct);
        }else {
            adaptador = new Adaptador(getActivity(), retos);
        }

        lvItems = view.findViewById(R.id.listViewReto);

        lvItems.setAdapter(adaptador);

        return view;
    }

    public void generarRetos(Nivel nivel1, Nivel nivel2, Nivel nivel3){

        Random r = new Random();

        if(nivel2.getBloqueado() == 1){

            categorias = dbC.loadCategoria(nivel1.getId());

            if(categorias.size() != 0){
                Categoria categoria = dbC.buscarCategoria(r.nextInt(categorias.size()) + 1);

                palabras = dbP.loadTotalPalabras(categoria.getId());

                if(palabras.size() != 0){
                    int nPalabra = r.nextInt(palabras.size()) + 1;

                    Reto reto = new Reto(1, nivel1.getNombre(), categoria.getNombre(), nPalabra, 0, 220, usuario.getId());

                    ArrayList<Reto> retos = dbR.loadReto();

                    if(sePuedeAgregar(reto, retos)){
                        dbR.insertReto(reto);
                    }else {
                        if(contador < 100){
                            generarRetos(nivel1, nivel2, nivel3);
                            contador++;
                        }
                    }
                }
            }

        }else if(nivel3.getBloqueado() == 1){

            Nivel nivel = dbN.buscarNivelId(r.nextInt(2) + 1);

            categorias = dbC.loadCategoria(nivel.getId());

            if(categorias.size() != 0){
                Categoria categoria = dbC.buscarCategoria(r.nextInt(categorias.size()) + 1);

                palabras = dbP.loadTotalPalabras(categoria.getId());

                if(palabras.size() != 0){
                    int nPalabra = r.nextInt(palabras.size()) + 1;

                    Reto reto = new Reto(1, nivel.getNombre(), categoria.getNombre(), nPalabra, 0, 220, usuario.getId());

                    ArrayList<Reto> retos = dbR.loadReto();

                    if(sePuedeAgregar(reto, retos)){
                        dbR.insertReto(reto);
                    }else {
                        if(contador < 100){
                            generarRetos(nivel1, nivel2, nivel3);
                            contador++;
                        }
                    }
                }
            }

        }else{

            Nivel nivel = dbN.buscarNivelId(r.nextInt(3) + 1);

            categorias = dbC.loadCategoria(nivel.getId());

            if(categorias.size() != 0){
                Categoria categoria = dbC.buscarCategoria(r.nextInt(categorias.size()) + 1);

                palabras = dbP.loadTotalPalabras(categoria.getId());

                if(palabras.size() != 0){
                    int nPalabra = r.nextInt(palabras.size()) + 1;

                    Reto reto = new Reto(1, nivel.getNombre(), categoria.getNombre(), nPalabra, 0, 220, usuario.getId());

                    ArrayList<Reto> retos = dbR.loadReto();

                    if(sePuedeAgregar(reto, retos)){
                        dbR.insertReto(reto);
                    }else {
                        if(contador < 100){
                            generarRetos(nivel1, nivel2, nivel3);
                            contador++;
                        }
                    }
                }
            }

        }
    }

    public boolean sePuedeAgregar(Reto reto, ArrayList<Reto> retos){
        boolean flag = true;

        loop :
        {
            for (int i = 0; i < retos.size(); i++) {
                if (reto.getCategoria() == retos.get(i).getCategoria()){
                    if(reto.getPalabra() == retos.get(i).getPalabra()){
                        flag = false;
                        break loop;
                    }
                }
            }
        }

        return flag;
    }

    public boolean masRetos(ArrayList<Reto> retos){
        boolean flag = true;

        loop :{
            for (int i = 0; i < retos.size(); i++){
                if(retos.get(i).getVariable() == 1){
                    if(retos.get(i).getPalabra() != retos.get(i).getNpalabra()){
                        flag = false;
                        break loop;
                    }
                }
            }
        }

        return flag;
    }
}