package app.rrg.pocket.com.tesis;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;
import app.rrg.pocket.com.tesis.Utilidades.Utilidades;

public class PerfilActivity extends AppCompatActivity{

    EditText nombre;
    EditText edad;
    private UsuarioDB db;
    DBHelper conexion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setTheme(R.style.CursorColor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        conexion = new DBHelper(this);

        db = new UsuarioDB(PerfilActivity.this);

        //Variables del usuario
        nombre = (EditText) findViewById(R.id.editTextNombre);
        edad = (EditText) findViewById(R.id.editTextEdad);
        cargarUsuario();

        Button mGuardar = (Button) findViewById(R.id.buttonGuardarPerfil);
        mGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarUsuario();
            }
        });

        setupActionBar();
    }

    public void cargarUsuario(){
        Usuario usuario = db.buscarUsuarios(1);
        nombre.setText(usuario.getNombre());
        edad.setText(usuario.getEdad());
    }

    public void modificarUsuario(){
        Usuario usuario = db.buscarUsuarios(1);
        usuario.setNombre(nombre.getText().toString());
        usuario.setEdad(edad.getText().toString());
        db.updateUsuario(usuario);
        Toast.makeText(this, "Éxito al guardar", Toast.LENGTH_SHORT).show();
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
