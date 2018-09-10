package app.rrg.pocket.com.tesis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Tienda;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.TiendaDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class Nivel2Activity extends AppCompatActivity {

    private NivelDB dbN;
    private CategoriaDB dbC;
    private PalabraDB dbP;
    private TiendaDB dbT;
    private UsuarioDB db;
    Usuario usuario;
    Tienda tienda;
    Nivel nivel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbN = new NivelDB(Nivel2Activity.this);
        dbP = new PalabraDB(Nivel2Activity.this);
        dbT = new TiendaDB(Nivel2Activity.this);
        db = new UsuarioDB(Nivel2Activity.this);

        usuario = db.buscarUsuarios(1);
        tienda = dbT.loadTienda().get(0);

        nivel = new Nivel();
        nivel = dbN.buscarNivel("Nivel 2");

        dbC = new CategoriaDB(Nivel2Activity.this);

        if(dbC.loadCategoria(nivel.getId()).size() == 0){
            crearCategorias();
        }

        setContentView(R.layout.activity_nivel2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutNivel2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        ArrayList<Categoria> list = dbC.loadCategoria(nivel.getId());
        Log.d("Nivel2Activity -> ", "tamano lista: " + list.size());

        for (int i = 0; i < list.size(); i++){
            Button categoria = new Button(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                categoria.setBackground(this.getDrawable(R.drawable.btn_default));
            }

            if(usuario.getTamano().equals("pequeno")){
                categoria.setTextSize(16);//pequeño 16, mediano 20, grande 24
            }else if(usuario.getTamano().equals("mediano")){
                categoria.setTextSize(20);//pequeño 16, mediano 20, grande 24
            }else{
                categoria.setTextSize(24);//pequeño 16, mediano 20, grande 24
            }

            categoria.setLayoutParams(lp);
            categoria.setText(list.get(i).getNombre());
            categoria.setId(list.get(i).getId());
            categoria.setOnClickListener(new Nivel2Activity.ButtonsOnClickListener(this, categoria));
            layout.addView(categoria);
        }
    }

    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        Button categoria;

        public ButtonsOnClickListener(Context context, Button categoria) {
            this.context = context;
            this.categoria = categoria;
        }

        @Override
        public void onClick(View v)
        {
            /*Lo primero es verificar si las palabras no sehan creado, de ser así se crean,
            de lo contrario se llama la vista de las palabras;
             */
            ArrayList<Palabra> listaPalabras = dbP.loadPalabra(categoria.getId());
            Log.d("Nivel2Activity -> ", "categoria: " + categoria.getText() + ", id-> " + categoria.getId());
            Log.d("Nivel2Activity -> ", "tamano palabras: " + listaPalabras.size());

            if(listaPalabras.size() == 0){
                switch (categoria.getText().toString()){
                    case "Arte":

                        Palabra caricatura = new Palabra("Caricatura", 0, 0, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(caricatura);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Ingeniería":

                        Log.d("Nivel2Activity -> ", "categoría id " + categoria.getId());

                        Palabra investigacion = new Palabra("Investigación", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(investigacion);

                        Palabra procedimiento = new Palabra("Procedimiento", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(procedimiento);

                        Palabra comunicacion = new Palabra("Comunicación", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(comunicacion);

                        Palabra experimento = new Palabra("Experimento", 150, 0, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(experimento);

                        Palabra probabilidad = new Palabra("Probabilidad", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(probabilidad);

                        Palabra ingenieria = new Palabra("Ingeniería", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ingenieria);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Deporte":

                        Palabra musculatura = new Palabra("Musculatura", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(musculatura);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Medicina":

                        Palabra radiografia = new Palabra("Radiografía", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(radiografia);

                        Palabra ecografia = new Palabra("Ecografía", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ecografia);

                        Palabra articulaciones = new Palabra("Articulaciones", 180, 60, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(articulaciones);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Contaduría":

                        Palabra contaduria = new Palabra("Contaduría", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(contaduria);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Economía":

                        Palabra economia = new Palabra("Economía", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(economia);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Sitios":

                        Palabra universidad = new Palabra("Universidad", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(universidad);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Diseño":

                        Palabra creatividad = new Palabra("Creatividad", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(creatividad);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Implementos aseo general":

                        Palabra aspiradora = new Palabra("Aspiradora", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aspiradora);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Policía":

                        Palabra operativo = new Palabra("Operativo", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(operativo);

                        Palabra capacitacion = new Palabra("Capacitación", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(capacitacion);

                        Palabra confiabilidad = new Palabra("Confiabilidad", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(confiabilidad);

                        Palabra problematica = new Palabra("Problemática", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(problematica);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Comunicación":

                        Palabra comunicacionC = new Palabra("Comunicación", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(comunicacionC);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Administración":

                        Palabra estadistica = new Palabra("Estadística", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estadistica);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Platos":

                        Palabra aborrajado = new Palabra("Aborrajado", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aborrajado);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Colores":

                        Palabra aguamarina = new Palabra("Aguamarina", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aguamarina);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Docencia":

                        Palabra calificacion = new Palabra("Calificación", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(calificacion);

                        Palabra aprendizaje = new Palabra("Aprendizaje", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aprendizaje);

                        Palabra matematica = new Palabra("Matemática", 150, 50, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(matematica);

                        launchIntent(context, categoria.getId());

                        break;
                }
            }

            launchIntent(context, categoria.getId());
        }

    };

    public void launchIntent(Context context, int idCategoria){
        Intent i = new Intent(context, PalabraNivel2.class);
        i.putExtra("idCategoria", "" + idCategoria);
        startActivity(i);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void crearCategorias(){
        Log.d("Nivel2Activity -> ", "agregando categorias");

        Categoria administracion = new Categoria();
        administracion.setNombre("Administración");
        administracion.setBloqueado(0);
        administracion.setNivel(nivel.getId());
        dbC.insertCategoria(administracion);

        Categoria arte = new Categoria();
        arte.setNombre("Arte");
        arte.setBloqueado(0);
        arte.setNivel(nivel.getId());
        dbC.insertCategoria(arte);

        Categoria colores = new Categoria();
        colores.setNombre("Colores");
        colores.setBloqueado(0);
        colores.setNivel(nivel.getId());
        dbC.insertCategoria(colores);

        Categoria comunicacion = new Categoria();
        comunicacion.setNombre("Comunicación");
        comunicacion.setBloqueado(0);
        comunicacion.setNivel(nivel.getId());
        dbC.insertCategoria(comunicacion);

        Categoria contaduria = new Categoria();
        contaduria.setNombre("Contaduría");
        contaduria.setBloqueado(0);
        contaduria.setNivel(nivel.getId());
        dbC.insertCategoria(contaduria);

        Categoria deporte = new Categoria();
        deporte.setNombre("Deporte");
        deporte.setBloqueado(0);
        deporte.setNivel(nivel.getId());
        dbC.insertCategoria(deporte);

        Categoria diseno = new Categoria();
        diseno.setNombre("Diseño");
        diseno.setBloqueado(0);
        diseno.setNivel(nivel.getId());
        dbC.insertCategoria(diseno);

        Categoria docencia = new Categoria();
        docencia.setNombre("Docencia");
        docencia.setBloqueado(0);
        docencia.setNivel(nivel.getId());
        dbC.insertCategoria(docencia);

        Categoria economia = new Categoria();
        economia.setNombre("Economía");
        economia.setBloqueado(0);
        economia.setNivel(nivel.getId());
        dbC.insertCategoria(economia);

        Categoria implementosAseoGeneral = new Categoria();
        implementosAseoGeneral.setNombre("Implementos aseo general");
        implementosAseoGeneral.setBloqueado(0);
        implementosAseoGeneral.setNivel(nivel.getId());
        dbC.insertCategoria(implementosAseoGeneral);

        Categoria ingenieria = new Categoria();
        ingenieria.setNombre("Ingeniería");
        ingenieria.setBloqueado(0);
        ingenieria.setNivel(nivel.getId());
        dbC.insertCategoria(ingenieria);

        Categoria medicina = new Categoria();
        medicina.setNombre("Medicina");
        medicina.setBloqueado(0);
        medicina.setNivel(nivel.getId());
        dbC.insertCategoria(medicina);

        Categoria platos = new Categoria();
        platos.setNombre("Platos");
        platos.setBloqueado(0);
        platos.setNivel(nivel.getId());
        dbC.insertCategoria(platos);

        Categoria policia = new Categoria();
        policia.setNombre("Policía");
        policia.setBloqueado(0);
        policia.setNivel(nivel.getId());
        dbC.insertCategoria(policia);

        Categoria sitios = new Categoria();
        sitios.setNombre("Sitios");
        sitios.setBloqueado(0);
        sitios.setNivel(nivel.getId());
        dbC.insertCategoria(sitios);
    }
}
