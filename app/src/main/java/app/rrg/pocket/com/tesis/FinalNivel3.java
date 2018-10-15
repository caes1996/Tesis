package app.rrg.pocket.com.tesis;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class FinalNivel3 extends AppCompatActivity implements TextToSpeech.OnInitListener, edu.cmu.pocketsphinx.RecognitionListener {

    String idPalabra;
    private PalabraDB dbP;
    private UsuarioDB db;
    Usuario usuario;
    Palabra palabra;
    TextToSpeech tts;
    private SpeechRecognizer recognizer;
    String text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this,this);

        recibirId();

        dbP = new PalabraDB(FinalNivel3.this);
        db = new UsuarioDB(FinalNivel3.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));

        setContentView(R.layout.activity_finalnivel3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();
        configuracion();

        findViewById(R.id.buttonFN3).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        //when the user removed the finger
                        //editText.setHint("You will see input here");
                        Toast.makeText(getBaseContext(), "Entendi: " + text, Toast.LENGTH_SHORT).show();
                        if(palabra.getNombre().toLowerCase().equals(text)) {
                            Toast.makeText(getBaseContext(), "Dijiste la palabra!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getBaseContext(), "Por favor, repite de nuevo", Toast.LENGTH_SHORT).show();
                        }
                        recognizer.stop();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        //finger is on the button
                        //editText.setText("");
                        //editText.setHint("Listening...");
                        try {
                            Assets assets = new Assets(getApplicationContext());
                            File assetDir = assets.syncAssets();
                            setupRecognizer(assetDir);
                            Toast.makeText(getBaseContext(), "Escuchando...",Toast.LENGTH_SHORT).show();
                            recognizer.startListening("frases");
                        }catch (IOException e){
                            Toast.makeText(getBaseContext(),"Failed to init recognizer " + e,Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                return false;
            }
        });
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
        Log.d("FinalN3Activity -> ", "Id palabra: " + idPalabra);
    }

    private void configuracion(){
        TextView textView = (TextView) findViewById(R.id.textViewFN3);
        ImageView img = (ImageView) findViewById(R.id.imageViewFinalNivel3);
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
        ImageView img = (ImageView) findViewById(R.id.imageViewFinalNivel3);

        switch (palabra) {
            case "Trabajo en grupo":
                img.setImageResource(R.drawable.administracion_trabajoengrupo);
                break;
            case "Perro caliente":
                img.setImageResource(R.drawable.comida_perrocaliente);
                break;
            case "Tenis de mesa":
                img.setImageResource(R.drawable.deporte_tenisdemesa);
                break;
            case "Ciencias sociales":
                img.setImageResource(R.drawable.docencia_cienciassociales);
                break;
            case "Tomate de árbol":
                img.setImageResource(R.drawable.frutas_tomatedearbol);
                break;
            case "Hilo dental":
                img.setImageResource(R.drawable.implementospersonal_hilo);
                break;
            case "Crema dental":
                img.setImageResource(R.drawable.implementospersonal_crema);
                break;
            case "Arroz con pollo":
                img.setImageResource(R.drawable.platos_arrozconpollo);
                break;
            case "Bandeja paisa":
                img.setImageResource(R.drawable.platos_bandejapaisa);
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

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if(hypothesis == null)
            return;

        //Obtenemos el String de la Hypothesiss

        text = hypothesis.getHypstr();

        //Reiniciamos el reconocedor, de esta forma reconoce voz de forma continua y limpia el buffer
        resetRecognizer();
    }

    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        //Esta es la configuración básica, donde se le indican las bibliotecas con las palabras en español
        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "es-ptm"))
                .setDictionary(new File(assetsDir, "es.dict"))
                .getRecognizer();
        recognizer.addListener(this);

        //Aquí indicamos el archivo que contiene las palabras clave que queremos reconocer
        // para realizar diferentes acciones. En este caso yo creo un archivo llamado "keys.gram"
        File keysGrammar = new File(assetsDir, "nivel3.gram");
        recognizer.addKeywordSearch("frases",keysGrammar);
    }

    private void resetRecognizer(){
        if(recognizer!=null) {
            recognizer.cancel();
            recognizer.startListening("frases");
        }
    }
}
