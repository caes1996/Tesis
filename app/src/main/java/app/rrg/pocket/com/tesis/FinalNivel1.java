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
