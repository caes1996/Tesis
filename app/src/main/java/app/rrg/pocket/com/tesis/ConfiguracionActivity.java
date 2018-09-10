package app.rrg.pocket.com.tesis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class ConfiguracionActivity extends AppCompatActivity {

    private UsuarioDB db;
    Usuario usuario;
    DBHelper conexion;
    SeekBar tamano;
    TextView texto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setTheme(R.style.CursorColor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        conexion = new DBHelper(this);

        db = new UsuarioDB(ConfiguracionActivity.this);

        cargarUsuario();

        tamano = (SeekBar) findViewById(R.id.seekBarTamanoLetra);
        tamano.setOnSeekBarChangeListener(new progreso());
        texto = (TextView) findViewById(R.id.textViewConfiguracion);

        if(usuario.getTamano().equals("pequeno")){
            texto.setTextSize(24);
            tamano.setProgress(0);
        } else if (usuario.getTamano().equals("mediano")) {
            texto.setTextSize(28);
            tamano.setProgress(1);
        }else{
            texto.setTextSize(32);
            tamano.setProgress(2);
        }

        Button mGuardar = (Button) findViewById(R.id.buttonGuardarConfiguracion);
        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificarUsuario();
            }
        });
        setupActionBar();
    }

    public void modificarUsuario(){
        db.updateUsuario(usuario);
        Toast.makeText(this, "Éxito al guardar", Toast.LENGTH_SHORT).show();
    }

    private class progreso implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            if(progress == 0){
                texto.setTextSize(24);
                usuario.setTamano("pequeno");
            }else if(progress == 1){
                texto.setTextSize(28);
                usuario.setTamano("mediano");
            }else{
                texto.setTextSize(32);
                usuario.setTamano("grande");
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

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
