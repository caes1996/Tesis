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
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class PalabraNivel3 extends AppCompatActivity {

    String idCategoria;
    private PalabraDB dbP;
    private CategoriaDB dbC;
    private UsuarioDB db;
    Usuario usuario;
    Categoria categoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recibirId();

        dbP = new PalabraDB(PalabraNivel3.this);
        dbC = new CategoriaDB(PalabraNivel3.this);
        db = new UsuarioDB(PalabraNivel3.this);

        usuario = db.buscarUsuarios(1);
        categoria = dbC.buscarCategoria(Integer.parseInt(idCategoria));

        setContentView(R.layout.activity_palabranivel3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutPalabrasNivel3);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        ArrayList<Palabra> list = dbP.loadPalabra(categoria.getId());
        Log.d("PalabraN3Activity -> ", "tamano lista: " + list.size());

        for (int i = 0; i < list.size(); i++){
            Button palabra = new Button(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                palabra.setBackground(this.getDrawable(R.drawable.btn_default));
            }

            if(usuario.getTamano().equals("pequeno")){
                palabra.setTextSize(16);//pequeño 16, mediano 20, grande 24
            }else if(usuario.getTamano().equals("mediano")){
                palabra.setTextSize(20);//pequeño 16, mediano 20, grande 24
            }else{
                palabra.setTextSize(24);//pequeño 16, mediano 20, grande 24
            }

            palabra.setLayoutParams(lp);
            palabra.setText(list.get(i).getNombre());
            palabra.setId(list.get(i).getId());
            palabra.setOnClickListener(new PalabraNivel3.ButtonsOnClickListener(this, palabra));
            layout.addView(palabra);
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
        idCategoria = extras.getString("idCategoria");
        Log.d("PalabraN3Activity -> ", "Id categoría: " + idCategoria);
    }

    class ButtonsOnClickListener implements View.OnClickListener {
        Context context;
        Button palabra;

        public ButtonsOnClickListener(Context context, Button palabra) {
            this.context = context;
            this.palabra = palabra;
        }

        @Override
        public void onClick(View v) {

            Intent i = new Intent(context, FinalNivel3.class);
            i.putExtra("idPalabra", "" + palabra.getId());
            startActivity(i);

        }
    }
}
