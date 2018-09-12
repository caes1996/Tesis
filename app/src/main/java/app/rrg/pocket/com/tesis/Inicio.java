package app.rrg.pocket.com.tesis;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import java.util.Random;

import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Reto;
import app.rrg.pocket.com.tesis.Entities.Tienda;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Entities.Vocabulario;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.RetoDB;
import app.rrg.pocket.com.tesis.Utilidades.TiendaDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;
import app.rrg.pocket.com.tesis.Utilidades.VocabularioDB;

public class Inicio extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private  SectionsPageAdapter mSectionsPageAdapter;
    private  ViewPager mViewPager;
    private UsuarioDB db;
    private TiendaDB dbT;
    private VocabularioDB dbV;
    private NivelDB dbN;
    private RetoDB dbR;
    DBHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        conexion = new DBHelper(this);

        String nombre = "Nombre completo";
        String edad = "Edad";

        db = new UsuarioDB(Inicio.this);
        dbT = new TiendaDB(Inicio.this);
        dbV = new VocabularioDB(Inicio.this);
        dbN = new NivelDB(Inicio.this);
        dbR = new RetoDB(Inicio.this);

        if(db.loadUsuarios().size() == 0){
            Usuario usuario = new Usuario(nombre, edad, 0, "pequeno");
            db.insertUsuario(usuario);

            Reto retoFijo1 = new Reto(0, "", "", 5, 0, 120, usuario.getId());
            dbR.insertReto(retoFijo1);

            Reto retoFijo2 = new Reto(0, "", "", 10, 0, 220, usuario.getId());
            dbR.insertReto(retoFijo2);

            Tienda tienda = new Tienda(usuario.getId());
            dbT.insertTienda(tienda);

            Vocabulario vocabulario = new Vocabulario(usuario.getId());
            dbV.insertVocabulario(vocabulario);

            Nivel nivel1 = new Nivel("Nivel 1", 0, vocabulario.getId());
            Nivel nivel2 = new Nivel("Nivel 2", 1 , vocabulario.getId());
            Nivel nivel3 = new Nivel("Nivel 3", 1 , vocabulario.getId());

            dbN.insertNivel(nivel1);
            dbN.insertNivel(nivel2);
            dbN.insertNivel(nivel3);

            Toast.makeText(this, "Usuario creado", Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate: Starting.");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //Set up the ViewPager whit the sections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(Inicio.this, PerfilActivity.class);
            startActivity(i);
            return true;
            //Toast toast = Toast.makeText(getApplicationContext(), "Iré a perfil", Toast.LENGTH_SHORT);
            //toast.show();
        }else if(id == R.id.action_configuracion){
            Intent i = new Intent(Inicio.this, ConfiguracionActivity.class);
            startActivity(i);
            return true;
        }else if(id == R.id.action_recomendaciones){
            Intent i = new Intent(Inicio.this, RecomendacionesActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabInicioFragment(), "Inicio");
        adapter.addFragment(new TabRetosFragment(), "Retos");
        adapter.addFragment(new TabTiendaFragment(), "Tienda");
        viewPager.setAdapter(adapter);
    }
}
