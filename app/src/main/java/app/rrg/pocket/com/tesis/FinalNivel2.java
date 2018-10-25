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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
    List<String> listado;

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
        try {
            listar();
        } catch (IOException e) {
            e.printStackTrace();
        }        

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

        TextView textViewTusPuntos = (TextView) findViewById(R.id.textViewPuntajeJugadorN2);
        TextView textViewAGanar = (TextView) findViewById(R.id.textViewPuntosAGanarPalabraN2);

        textViewTusPuntos.setText("Tienes: " + usuario.getPuntaje() +", Pts");
        textViewAGanar.setText("Otorga: " + palabra.getPuntaje() +", Pts");

        if(usuario.getTamano().equals("pequeno")){
            textView.setTextSize(36);
            textViewTusPuntos.setTextSize(22);
            textViewAGanar.setTextSize(22);
        } else if (usuario.getTamano().equals("mediano")) {
            textView.setTextSize(40);
            textViewTusPuntos.setTextSize(23);
            textViewAGanar.setTextSize(23);
        }else{
            textView.setTextSize(44);
            textViewTusPuntos.setTextSize(24);
            textViewAGanar.setTextSize(24);
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

        Button sonido = (Button) findViewById(R.id.buttonSound2);
        sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void listar()throws IOException {
        listado = new ArrayList<String>();
        String linea;

        InputStream is = this.getResources().openRawResource(R.raw.nivel2);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if(is!=null){
            while ((linea = reader.readLine()) != null){
                listado.add(linea);
            }
        }
        is.close();
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if(text != null){
            for(int i=0;i<listado.size();i++){
                if(palabra.getNombre().contains(listado.get(i).split(";")[1])) {
                    if (text.contains(listado.get(i).split(";")[0]))
                        acierto = true;
                }
            }
        }else {
            acierto = false;
        }

        TextView textView = (TextView) findViewById(R.id.textView_escuchaN2);
        if(acierto){
            textView.setText("¡¡Dijiste la palabra!!");
            palabraReconocida();
        }else{
            textView.setText("¡Vuelve a intentarlo!");
        }
        text=null;
        acierto=false;
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
        TextView textViewTusPuntos = (TextView) findViewById(R.id.textViewPuntajeJugadorN2);
        usuario.setPuntaje(palabra.getPuntaje() + usuario.getPuntaje());
        db.updateUsuario(usuario);
        textViewTusPuntos.setText("Tienes: " + usuario.getPuntaje() +", Pts");
    }
}
