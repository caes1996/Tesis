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

public class FinalNivel2 extends AppCompatActivity implements TextToSpeech.OnInitListener, edu.cmu.pocketsphinx.RecognitionListener {

    String idPalabra;
    private PalabraDB dbP;
    private UsuarioDB db;
    Usuario usuario;
    Palabra palabra;
    TextToSpeech tts;
    private SpeechRecognizer recognizer;
    String text;
    boolean acierto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this,this);

        recibirId();

        dbP = new PalabraDB(FinalNivel2.this);
        db = new UsuarioDB(FinalNivel2.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));
        acierto=false;

        setContentView(R.layout.activity_finalnivel2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();
        configuracion();

        findViewById(R.id.buttonFN2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textViewEscucha = (TextView)findViewById(R.id.textView_escuchaN2);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        recognizer.stop();
                        textViewEscucha.setText("");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        try {
                            Assets assets = new Assets(getApplicationContext());
                            File assetDir = assets.syncAssets();
                            setupRecognizer(assetDir);
                            recognizer.startListening("frases");
                            textViewEscucha.setText("Escuchando...");
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
            case "Estadistica":
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
        if(text != null){
            switch (palabra.getNombre()) {
                case "Estadistica":
                    if(text.contains("estadistica"))
                        acierto=true;
                    break;
                case "Caricatura":
                    if(text.contains("caricatura"))
                        acierto=true;
                    break;
                case "Aguamarina":
                    if(text.contains("aguamarina"))
                        acierto=true;
                    break;
                case "Comunicación":
                    if(text.contains("comunicacion"))
                        acierto=true;
                    break;
                case "Contaduría":
                    if(text.contains("contaduria"))
                        acierto=true;
                    break;
                case "Musculatura":
                    if(text.contains("musculatura"))
                        acierto=true;
                    break;
                case "Creatividad":
                    if(text.contains("creatividad"))
                        acierto=true;
                    break;
                case "Calificación":
                    if(text.contains("calificacion"))
                        acierto=true;
                    break;
                case "Aprendizaje":
                    if(text.contains("aprendizaje"))
                        acierto=true;
                    break;
                case "Matemática":
                    if(text.contains("matematica"))
                        acierto=true;
                    break;
                case "Economía":
                    if(text.contains("economia"))
                        acierto=true;
                    break;
                case "Aspiradora":
                    if(text.contains("aspiradora"))
                        acierto=true;
                    break;
                case "Investigación":
                    if(text.contains("investigacion"))
                        acierto=true;
                    break;
                case "Experimento":
                    if(text.contains("experimento"))
                        acierto=true;
                    break;
                case "Probabilidad":
                    if(text.contains("probabilidad"))
                        acierto=true;
                    break;
                case "Ingeniería":
                    if(text.contains("ingenieria"))
                        acierto=true;
                    break;
                case "Radiografía":
                    if(text.contains("radiografia"))
                        acierto=true;
                    break;
                case "Ecografía":
                    if(text.contains("ecografia"))
                        acierto=true;
                    break;
                case "Articulaciones":
                    if(text.contains("articulaciones"))
                        acierto=true;
                    break;
                case "Aborrajado":
                    if(text.contains("aborrajado"))
                        acierto=true;
                    break;
                case "Universidad":
                    if(text.contains("universidad"))
                        acierto=true;
                    break;
            }
        }else {
            acierto=false;
        }
        text=null;

        TextView textView = (TextView) findViewById(R.id.textView_escuchaN2);
        if(acierto){
            textView.setText("Dijiste la palabra!!");
            palabraReconocida();
        }else{
            textView.setText("Lo lamento, no entiendo.");
        }
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
        File keysGrammar = new File(assetsDir, "nivel2.gram");
        recognizer.addKeywordSearch("frases",keysGrammar);
    }

    private void resetRecognizer(){
        if(recognizer!=null) {
            recognizer.cancel();
            recognizer.startListening("frases");
        }
    }

    public void palabraReconocida(){

    }
}
