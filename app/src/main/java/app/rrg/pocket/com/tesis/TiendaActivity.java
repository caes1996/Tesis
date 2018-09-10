package app.rrg.pocket.com.tesis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class TiendaActivity extends AppCompatActivity {

    String idPalabra;
    private NivelDB dbN;
    private CategoriaDB dbC;
    private PalabraDB dbP;
    private UsuarioDB db;
    Palabra palabra;
    Usuario usuario;
    Categoria categoria;
    Nivel nivel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recibirId();

        db = new UsuarioDB(TiendaActivity.this);
        dbP = new PalabraDB(TiendaActivity.this);
        dbN = new NivelDB(TiendaActivity.this);
        dbC = new CategoriaDB(TiendaActivity.this);

        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));
        usuario = db.buscarUsuarios(1);

        categoria = dbC.buscarCategoria(palabra.getCategoria());
        nivel = dbN.buscarNivelId(categoria.getNivel());

        Log.d("TiendaActivity -> ", "costo palabra: " + palabra.getCosto());
        Log.d("TiendaActivity -> ", "nivel: " + nivel.getNombre());
        Log.d("TiendaActivity -> ", "id categoría: " + categoria.getId());
        Log.d("TiendaActivity -> ", "categoría palabra: " + categoria.getNombre());

        setContentView(R.layout.activity_tienda);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();
        configuracion();

        Button mComprar = (Button) findViewById(R.id.buttonTienda);
        mComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprarPalabra();
            }
        });
    }

    public void comprarPalabra(){
        if(usuario.getPuntaje() >= palabra.getCosto()){
            usuario.setPuntaje(usuario.getPuntaje() - palabra.getCosto());
            palabra.setBloqueado(0);
            db.updateUsuario(usuario);
            dbP.updatePalabra(palabra);
            Toast.makeText(this, "¡Palabra desbloqueada!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Puntuación insuficiente, avanza más!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void recibirId(){
        Bundle extras = getIntent().getExtras();
        idPalabra = extras.getString("idPalabra");
        Log.d("TiendaActivity -> ", "Id palabra: " + idPalabra);
    }

    private void configuracion(){
        TextView textViewPalabra = (TextView) findViewById(R.id.textViewTienda);
        textViewPalabra.setText("Palabra: " + palabra.getNombre());

        TextView textViewNivel = (TextView) findViewById(R.id.textViewNivelTienda);
        textViewNivel.setText(nivel.getNombre() + "");

        TextView textViewCategoria = (TextView) findViewById(R.id.textViewCategoriaTienda);
        textViewCategoria.setText("Categoría: " + categoria.getNombre() + "");

        TextView textViewCosto = (TextView) findViewById(R.id.textViewCosto);
        textViewCosto.setText("Costo: " + palabra.getCosto() + " puntos");

        TextView textViewPuntos = (TextView) findViewById(R.id.textViewPuntos);
        textViewPuntos.setText("Tus puntos: " + usuario.getPuntaje() + " puntos");

        if(usuario.getTamano().equals("pequeno")){
            textViewPalabra.setTextSize(36);
            textViewCosto.setTextSize(34);
            textViewNivel.setTextSize(30);
            textViewCategoria.setTextSize(30);
            textViewPuntos.setTextSize(30);
        } else if (usuario.getTamano().equals("mediano")) {
            textViewPalabra.setTextSize(40);
            textViewCosto.setTextSize(40);
            textViewNivel.setTextSize(40);
            textViewCategoria.setTextSize(40);
            textViewPuntos.setTextSize(40);
        }else{
            textViewPalabra.setTextSize(44);
            textViewCosto.setTextSize(44);
            textViewNivel.setTextSize(44);
            textViewCategoria.setTextSize(44);
            textViewPuntos.setTextSize(44);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
