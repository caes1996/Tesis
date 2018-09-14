package app.rrg.pocket.com.tesis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class FinalNivel2 extends AppCompatActivity implements TextToSpeech.OnInitListener{

    String idPalabra;
    private PalabraDB dbP;
    private UsuarioDB db;
    Usuario usuario;
    Palabra palabra;
    TextToSpeech tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this,this);

        recibirId();

        dbP = new PalabraDB(FinalNivel2.this);
        db = new UsuarioDB(FinalNivel2.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));

        setContentView(R.layout.activity_finalnivel2);
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
        Log.d("FinalN2Activity -> ", "Id palabra: " + idPalabra);
    }

    private void configuracion(){
        TextView textView = (TextView) findViewById(R.id.textViewFN2);
        ImageView img = (ImageView) findViewById(R.id.imageViewFinalNivel2);
        textView.setText(palabra.getNombre());

        if(usuario.getTamano().equals("pequeno")){
            textView.setTextSize(36);
        } else if (usuario.getTamano().equals("mediano")) {
            textView.setTextSize(40);
        }else{
            textView.setTextSize(44);
        }

        setImg(palabra.getNombre());
        if(img.getDrawable() == null){
            img.setImageResource(R.drawable.sonido);
        }
    }

    public void setImg(final String palabra){

        ImageView  img = (ImageView) findViewById(R.id.imageViewFinalNivel2);

        switch (palabra) {
            case "Estadística":
                img.setImageResource(R.drawable.administracion_estadistica);
                break;
            case "Caricatura":
                img.setImageResource(R.drawable.arte_caricatura);
                break;
            case "Aguamarina":
                img.setImageResource(R.drawable.color_aguamarina);
                break;
            case "Comunicación":
                img.setImageResource(R.drawable.comunicacion_comunicacion);
                break;
            case "Contaduría":
                img.setImageResource(R.drawable.contaduria_contaduria);
                break;
            case "Musculatura":
                img.setImageResource(R.drawable.deporte_gimnasio);
                break;
            case "Creatividad":
                img.setImageResource(R.drawable.diseno_creatividad);
                break;
            case "Calificación":
                img.setImageResource(R.drawable.docencia_calificacion);
                break;
            case "Aprendizaje":
                img.setImageResource(R.drawable.docencia_aprendizaje);
                break;
            case "Matemática":
                img.setImageResource(R.drawable.docencia_calculo);
                break;
            case "Economía":
                img.setImageResource(R.drawable.economia_economia);
                break;
            case "Aspiradora":
                img.setImageResource(R.drawable.general_aspiradora);
                break;
            case "Investigación":
                img.setImageResource(R.drawable.ingenieria_investigacion);
                break;
            case "Experimento":
                img.setImageResource(R.drawable.ingenieria_experimento);
                break;
            case "Probabilidad":
                img.setImageResource(R.drawable.ingenieria_probabilidad);
                break;
            case "Ingeniería":
                img.setImageResource(R.drawable.ingenieria_ingenio);
                break;
            case "Radiografía":
                img.setImageResource(R.drawable.medicina_radiografia);
                break;
            case "Ecografía":
                img.setImageResource(R.drawable.medicina_ecografia);
                break;
            case "Articulaciones":
                img.setImageResource(R.drawable.medicina_articulaciones);
                break;
            case "Aborrajado":
                img.setImageResource(R.drawable.platos_aborrajado);
                break;
            case "Universidad":
                img.setImageResource(R.drawable.sitios_universidad);
                break;


        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });

    }

    public void onInit(int status){

        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.getDefault());
            if(result == TextToSpeech.LANG_NOT_SUPPORTED ||
                    result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS", "Este lenguaje no es soportado");
            }else{
                //speakOut();
            }
        }else{
            Log.e("TTS", "Inicialización del lenguaje fallida");
        }

    }

    public void speakOut(){
        tts.speak(palabra.getNombre(), TextToSpeech.QUEUE_FLUSH, null);
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
