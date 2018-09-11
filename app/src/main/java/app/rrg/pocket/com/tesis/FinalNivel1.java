package app.rrg.pocket.com.tesis;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.R;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class FinalNivel1 extends AppCompatActivity {

    String idPalabra;
    private PalabraDB dbP;
    private UsuarioDB db;
    Usuario usuario;
    Palabra palabra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recibirId();
        dbP = new PalabraDB(FinalNivel1.this);
        db = new UsuarioDB(FinalNivel1.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));

        setContentView(R.layout.activity_finalnivel1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();
        configuracion();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //Toast.makeText(this, "Hola", Toast.LENGTH_SHORT).show();
        }
    }

    private void recibirId(){
        Bundle extras = getIntent().getExtras();
        idPalabra = extras.getString("idPalabra");
        Log.d("FinalN1Activity -> ", "Id palabra: " + idPalabra);
    }

    private void configuracion(){
        TextView textView = (TextView) findViewById(R.id.textViewFN1);

        textView.setText(palabra.getNombre());

        if(usuario.getTamano().equals("pequeno")){
            textView.setTextSize(36);
        } else if (usuario.getTamano().equals("mediano")) {
            textView.setTextSize(40);
        }else{
            textView.setTextSize(44);
        }

        setImg(palabra.getNombre());
    }

    public void setImg(String palabra){

        ImageView  img = (ImageView) findViewById(R.id.imageViewFinalNivel1);

        switch (palabra){
            case "Acuarela":
                img.setImageResource(R.drawable.arte_acuarela);
                break;
            case "Arte":
                img.setImageResource(R.drawable.arte_arte);
                break;
            case "Boceto":
                img.setImageResource(R.drawable.arte_boceto);
                break;
            case "Clásico":
                img.setImageResource(R.drawable.arte_clasico);
                break;
            case "Dibujo":
                img.setImageResource(R.drawable.arte_dibujo);
                break;
            case "Estatua":
                img.setImageResource(R.drawable.arte_estatua);
                break;
            case "Lápiz":
                img.setImageResource(R.drawable.arte_lapiz);
                break;
            case "Mural":
                img.setImageResource(R.drawable.arte_mural);
                break;
            case "Plastilina":
                img.setImageResource(R.drawable.arte_plastilina);
                break;
            case "Yeso":
                img.setImageResource(R.drawable.arte_yeso);
                break;
            case "Rojo":
                img.setImageResource(R.drawable.colores_rojo);
                break;
            case "Verde":
                img.setImageResource(R.drawable.colores_verde);
                break;
            case "Azul":
                img.setImageResource(R.drawable.colores_azul);
                break;
            case "Amarillo":
                img.setImageResource(R.drawable.colores_amarillo);
                break;
            case "Negro":
                img.setImageResource(R.drawable.colores_negro);
                break;
            case "Blanco":
                img.setImageResource(R.drawable.colores_blanco);
                break;
            case "Gris":
                img.setImageResource(R.drawable.colores_gris);
                break;
            case "Café":
                img.setImageResource(R.drawable.colores_cafe);
                break;
            case "Morado":
                img.setImageResource(R.drawable.colores_morado);
                break;
            case "Rosado":
                img.setImageResource(R.drawable.colores_rosado);
                break;
            case "Salmón":
                img.setImageResource(R.drawable.colores_salmon);
                break;
            case "Durazno":
                img.setImageResource(R.drawable.colores_durazno);
                break;
            case "Fucsia":
                img.setImageResource(R.drawable.colores_fucsia);
                break;
            case "Plateado":
                img.setImageResource(R.drawable.colores_plateado);
                break;
            case "Dorado":
                img.setImageResource(R.drawable.colores_dorado);
                break;
            case "Violeta":
                img.setImageResource(R.drawable.colores_violeta);
                break;
            case "Púrpura":
                img.setImageResource(R.drawable.colores_violeta);
                break;
            case "Hamburguesa":
                img.setImageResource(R.drawable.comida_hamburguesa);
                break;
            case "Salchipapa":
                img.setImageResource(R.drawable.comida_salchipapa);
                break;
            case "Pizza":
                img.setImageResource(R.drawable.comida_pizza);
                break;
            case "Agenda":
                img.setImageResource(R.drawable.comunicacion_agenda);
                break;
            case "Prensa":
                img.setImageResource(R.drawable.comunicacion_prensa);
                break;
            case "Mensaje":
                img.setImageResource(R.drawable.comunicacion_mensaje);
                break;
            case "Opinión":
                img.setImageResource(R.drawable.comunicacion_opinion);
                break;
            case "Reportaje":
                img.setImageResource(R.drawable.comunicacion_reportaje);
                break;
            case "Caja":
                img.setImageResource(R.drawable.contaduria_caja);
                break;
            case "Contable":
                img.setImageResource(R.drawable.contaduria_contable);
                break;
            case "Contador":
                img.setImageResource(R.drawable.contaduria_contador);
                break;
            case "Factura":
                img.setImageResource(R.drawable.contaduria_factura);
                break;
            case "Inventario":
                img.setImageResource(R.drawable.contaduria_inventario);
                break;
            case "Iva":
                img.setImageResource(R.drawable.contaduria_iva);
                break;
            case "Proveedor":
                img.setImageResource(R.drawable.contaduria_proveedor);
                break;

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
