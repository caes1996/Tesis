package app.rrg.pocket.com.tesis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class RecomendacionesActivity extends AppCompatActivity {

    private UsuarioDB db;
    Usuario usuario;
    DBHelper conexion;
    TextView texto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        setTheme(R.style.CursorColor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        conexion = new DBHelper(this);

        db = new UsuarioDB(RecomendacionesActivity.this);

        cargarUsuario();

        texto = (TextView) findViewById(R.id.textViewRecomendaciones);

        texto.setText(
                "Esta aplicación es apta para todo público, sin embargo es de recalcar que fue desarrollada para pacientes quienes" +
                        " son diagnosticados con una Afasia de BROCA o TRANSCORTICAL MOTORA. Por lo tanto estas personas " +
                        "podrán obtener mejores resultados. \n" + "\n" +
                        "Teniendo en cuenta que la aplicación es orientada a personas adultas, se tuvo que considerar la " +
                        "opción de cambiar el tamaño de la fuente, para así poder brindar una mejor experiecia visual, por " +
                        "lo tanto es recomendable tener configurado su dispositivo móvil con un tamaño de pantalla relativamente bajo." +
                        "\n" + "\n" +
                        "De antemano muchas gracias por su uso. Daniel, Cristian y equipo de desarrollo .");

        if(usuario.getTamano().equals("pequeno")){
            texto.setTextSize(24);
        } else if (usuario.getTamano().equals("mediano")) {
            texto.setTextSize(28);
        }else{
            texto.setTextSize(32);
        }

        setupActionBar();
    }

    public void cargarUsuario(){
        usuario = db.buscarUsuarios(1);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
