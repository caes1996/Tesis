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

public class Nivel3Activity extends AppCompatActivity {

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

        dbN = new NivelDB(Nivel3Activity.this);
        dbP = new PalabraDB(Nivel3Activity.this);
        dbT = new TiendaDB(Nivel3Activity.this);
        db = new UsuarioDB(Nivel3Activity.this);

        usuario = db.buscarUsuarios(1);
        tienda = dbT.loadTienda().get(0);

        nivel = new Nivel();
        nivel = dbN.buscarNivel("Nivel 3");

        dbC = new CategoriaDB(Nivel3Activity.this);

        if(dbC.loadCategoria(nivel.getId()).size() == 0){
            crearCategorias();
        }

        setContentView(R.layout.activity_nivel3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutNivel3);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        ArrayList<Categoria> list = dbC.loadCategoria(nivel.getId());
        Log.d("Nivel3Activity -> ", "tamano lista: " + list.size());

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
            categoria.setOnClickListener(new Nivel3Activity.ButtonsOnClickListener(this, categoria));
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
            Log.d("Nivel3Activity -> ", "categoria: " + categoria.getText() + ", id-> " + categoria.getId());
            Log.d("Nivel3Activity -> ", "tamano palabras: " + listaPalabras.size());

            if(listaPalabras.size() == 0){
                switch (categoria.getText().toString()){
                    case "Administración":

                        Palabra TEG = new Palabra("Trabajo en grupo", 360, 120, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(TEG);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Comida chatarra":

                        Palabra PC = new Palabra("Perro caliente", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(PC);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Contaduría":

                        Palabra RC = new Palabra("Registro contable", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(RC);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Deporte":

                        Palabra TDM = new Palabra("Tenis de mesa", 360, 120, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(TDM);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Derecho":

                        Palabra NPE = new Palabra("Notificación por estado", 360, 120, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(NPE);

                        Palabra MC = new Palabra("Medidas cautelares", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(MC);

                        Palabra PN = new Palabra("Persona natural", 240, 800, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(PN);

                        Palabra PJ = new Palabra("Pruebas judiciales", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(PJ);

                        Palabra EP = new Palabra("Escritura pública", 240, 800, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(EP);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Docencia":

                        Palabra CS = new Palabra("Ciencias sociales", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(CS);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Frutas":

                        Palabra TDA = new Palabra("Tomate de árbol", 360, 120, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(TDA);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Implementos aseo personal":

                        Palabra HD = new Palabra("Hilo dental", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(HD);

                        Palabra CD = new Palabra("Crema dental", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(CD);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Medicina":

                        Palabra PA = new Palabra("Presión arterial", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(PA);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Platos":

                        Palabra ACP = new Palabra("Arroz con pollo", 360, 120, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ACP);

                        Palabra BP = new Palabra("Bandeja paisa", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(BP);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Saludos":

                        Palabra BD = new Palabra("Buen día", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(BD);

                        Palabra BT = new Palabra("Buena tarde", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(BT);

                        Palabra BN = new Palabra("Buena noche", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(BN);

                        Palabra HL = new Palabra("Hasta luego", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(HL);

                        Palabra CE = new Palabra("Cómo están", 240, 80, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(CE);

                        launchIntent(context, categoria.getId());

                        break;
                }
            }

            launchIntent(context, categoria.getId());
        }

    };

    public void launchIntent(Context context, int idCategoria){
        Intent i = new Intent(context, PalabraNivel3.class);
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
        Log.d("Nivel3Activity -> ", "agregando categorias");

        Categoria administracion = new Categoria();
        administracion.setNombre("Administración");
        administracion.setBloqueado(0);
        administracion.setNivel(nivel.getId());
        dbC.insertCategoria(administracion);

        Categoria comidaChatarra = new Categoria();
        comidaChatarra.setNombre("Comida chatarra");
        comidaChatarra.setBloqueado(0);
        comidaChatarra.setNivel(nivel.getId());
        dbC.insertCategoria(comidaChatarra);

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

        Categoria derecho = new Categoria();
        derecho.setNombre("Derecho");
        derecho.setBloqueado(0);
        derecho.setNivel(nivel.getId());
        dbC.insertCategoria(derecho);

        Categoria docencia = new Categoria();
        docencia.setNombre("Docencia");
        docencia.setBloqueado(0);
        docencia.setNivel(nivel.getId());
        dbC.insertCategoria(docencia);

        Categoria frutas = new Categoria();
        frutas.setNombre("Frutas");
        frutas.setBloqueado(0);
        frutas.setNivel(nivel.getId());
        dbC.insertCategoria(frutas);

        Categoria implementosAseoPersonal = new Categoria();
        implementosAseoPersonal.setNombre("Implementos aseo personal");
        implementosAseoPersonal.setBloqueado(0);
        implementosAseoPersonal.setNivel(nivel.getId());
        dbC.insertCategoria(implementosAseoPersonal);

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

        Categoria saludos = new Categoria();
        saludos.setNombre("Saludos");
        saludos.setBloqueado(0);
        saludos.setNivel(nivel.getId());
        dbC.insertCategoria(saludos);
    }
}
