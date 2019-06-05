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

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Reto;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.RetoDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class FinalNivel1 extends AppCompatActivity implements TextToSpeech.OnInitListener, edu.cmu.pocketsphinx.RecognitionListener {

    String idPalabra;
    private PalabraDB dbP;
    private UsuarioDB db;
    private RetoDB dbR;
    private CategoriaDB dbC;
    private NivelDB dbN;
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

        dbP = new PalabraDB(FinalNivel1.this);
        db = new UsuarioDB(FinalNivel1.this);
        dbR = new RetoDB(FinalNivel1.this);
        dbC = new CategoriaDB(FinalNivel1.this);
        dbN = new NivelDB(FinalNivel1.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));
        acierto=false;
        try {
            listar();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_finalnivel1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setupActionBar();
        configuracion();

        findViewById(R.id.buttonFN1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textViewEscucha = (TextView)findViewById(R.id.textView_escuchaN1);
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
        }
    }

    private void recibirId(){
        Bundle extras = getIntent().getExtras();
        idPalabra = extras.getString("idPalabra");
        Log.d("FinalN1Activity -> ", "Id palabra: " + idPalabra);
    }

    private void configuracion(){
        TextView textView = (TextView) findViewById(R.id.textViewFN1);
        ImageView  img = (ImageView) findViewById(R.id.imageViewFinalNivel1);
        textView.setText(palabra.getNombre());

        TextView textViewTusPuntos = (TextView) findViewById(R.id.textViewPuntajeJugadorN1);
        TextView textViewAGanar = (TextView) findViewById(R.id.textViewPuntosAGanarPalabraN1);

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
            case "Auxiliar":
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
            case "Fuerza":
                img.setImageResource(R.drawable.deporte_fuerza);
                break;
            case "Ajedrez":
                img.setImageResource(R.drawable.deporte_ajedrez);
                break;
            case "Árbitro":
                img.setImageResource(R.drawable.deporte_arbitro);
                break;
            case "Carrera":
                img.setImageResource(R.drawable.deporte_carrera);
                break;
            case "Ciclismo":
                img.setImageResource(R.drawable.deporte_ciclismo);
                break;
            case "Clavado":
                img.setImageResource(R.drawable.deporte_clavado);
                break;
            case "Deporte":
                img.setImageResource(R.drawable.deporte_deporte);
                break;
            case "Entrenar":
                img.setImageResource(R.drawable.deporte_entrenar);
                break;
            case "Esfuerzo":
                img.setImageResource(R.drawable.deporte_esfuerzo);
                break;
            case "Fútbol":
                img.setImageResource(R.drawable.deporte_futbol);
                break;
            case "Lateral":
                img.setImageResource(R.drawable.deporte_formacion);
                break;
            case "Central":
                img.setImageResource(R.drawable.deporte_formacion);
                break;
            case "Volante":
                img.setImageResource(R.drawable.deporte_formacion);
                break;
            case "Delantero":
                img.setImageResource(R.drawable.deporte_formacion);
                break;
            case "Gimnasio":
                img.setImageResource(R.drawable.deporte_gimnasio);
                break;
            case "Mariposa":
                img.setImageResource(R.drawable.deporte_mariposa);
                break;
            case "Natación":
                img.setImageResource(R.drawable.deporte_natacion);
                break;
            case "Olímpico":
                img.setImageResource(R.drawable.deporte_olimpico);
                break;
            case "Patinaje":
                img.setImageResource(R.drawable.deporte_patinaje);
                break;
            case "Pesas":
                img.setImageResource(R.drawable.deporte_pesas);
                break;
            case "Técnico":
                img.setImageResource(R.drawable.deporte_tecnico);
                break;
            case "Tenis":
                img.setImageResource(R.drawable.deporte_tenis);
                break;
            case "Cuerpo":
                img.setImageResource(R.drawable.deporte_gimnasio);
                break;
            case "Deportista":
                img.setImageResource(R.drawable.deporte_deporte);
                break;
            case "Ley":
                img.setImageResource(R.drawable.derecho_ley);
                break;
            case "Juez":
                img.setImageResource(R.drawable.derecho_juez);
                break;
            case "Abogado":
                img.setImageResource(R.drawable.derecho_abogado);
                break;
            case "Jurado":
                img.setImageResource(R.drawable.derecho_jurado);
                break;
            case "Fiscalía":
                img.setImageResource(R.drawable.derecho_fiscalia);
                break;
            case "Constitución":
                img.setImageResource(R.drawable.derecho_constitucion);
                break;
            case "Derecho":
                img.setImageResource(R.drawable.derecho_derecho);
                break;
            case "Bienes":
                img.setImageResource(R.drawable.derecho_bienes);
                break;
            case "Herencia":
                img.setImageResource(R.drawable.derecho_herencia);
                break;
            case "Folleto":
                img.setImageResource(R.drawable.diseno_folleto);
                break;
            case "Ícono":
                img.setImageResource(R.drawable.diseno_icono);
                break;
            case "Lienzo":
                img.setImageResource(R.drawable.diseno_lienzo);
                break;
            case "Mapa":
                img.setImageResource(R.drawable.diseno_mapa);
                break;
            case "Marca":
                img.setImageResource(R.drawable.diseno_marca);
                break;
            case "Publicidad":
                img.setImageResource(R.drawable.diseno_publicidad);
                break;
            case "Tinta":
                img.setImageResource(R.drawable.diseno_tinta);
                break;
            case "Símbolo":
                img.setImageResource(R.drawable.diseno_simbolo);
                break;
            case "Sombra":
                img.setImageResource(R.drawable.diseno_sombra);
                break;
            case "Textura":
                img.setImageResource(R.drawable.diseno_textura);
                break;
            case "Utiles":
                img.setImageResource(R.drawable.docencia_utiles);
                break;
            case "Tarea":
                img.setImageResource(R.drawable.docencia_tarea);
                break;
            case "Salón":
                img.setImageResource(R.drawable.docencia_salon);
                break;
            case "Educación":
                img.setImageResource(R.drawable.docencia_ensenanza);
                break;
            case "Enseñanza":
                img.setImageResource(R.drawable.docencia_ensenanza);
                break;
            case "Familia":
                img.setImageResource(R.drawable.docencia_familia);
                break;
            case "Cuaderno":
                img.setImageResource(R.drawable.docencia_utiles);
                break;
            case "Vacaciones":
                img.setImageResource(R.drawable.deporte_clavado);
                break;
            case "Materia":
                img.setImageResource(R.drawable.docencia_utiles);
                break;
            case "Amistad":
                img.setImageResource(R.drawable.docencia_salon);
                break;
            case "Rector":
                img.setImageResource(R.drawable.docencia_educacion);
                break;
            case "Profesor":
                img.setImageResource(R.drawable.docencia_educacion);
                break;
            case "Maestro":
                img.setImageResource(R.drawable.docencia_educacion);
                break;
            case "Notas":
                img.setImageResource(R.drawable.docencia_tarea);
                break;
            case "Atención":
                img.setImageResource(R.drawable.docencia_ensenanza);
                break;
            case "Docente":
                img.setImageResource(R.drawable.docencia_educacion);
                break;
            case "Estudiante":
                img.setImageResource(R.drawable.docencia_salon);
                break;
            case "Ética":
                img.setImageResource(R.drawable.docencia_etica);
                break;
            case "Física":
                img.setImageResource(R.drawable.docencia_fisica);
                break;
            case "Química":
                img.setImageResource(R.drawable.docencia_quimica);
                break;
            case "Cálculo":
                img.setImageResource(R.drawable.docencia_calculo);
                break;
            case "Borrador":
                img.setImageResource(R.drawable.docencia_borrador);
                break;
            case "Tablero":
                img.setImageResource(R.drawable.docencia_tablero);
                break;
            case "Balanza":
                img.setImageResource(R.drawable.docencia_etica);
                break;
            case "Moneda":
                img.setImageResource(R.drawable.money);
                break;
            case "Población":
                img.setImageResource(R.drawable.docencia_familia);
                break;
            case "Interés":
                img.setImageResource(R.drawable.derecho_herencia);
                break;
            case "Bolsa":
                img.setImageResource(R.drawable.money);
                break;
            case "Salario":
                img.setImageResource(R.drawable.economia_capital);
                break;
            case "Capital":
                img.setImageResource(R.drawable.economia_capital);
                break;
            case "Ahorro":
                img.setImageResource(R.drawable.economia_ahorro);
                break;
            case "Mercado":
                img.setImageResource(R.drawable.economia_mercado);
                break;
            case "Oferta":
                img.setImageResource(R.drawable.economia_oferta);
                break;
            case "Demanda":
                img.setImageResource(R.drawable.economia_oferta);
                break;
            case "Manzana":
                img.setImageResource(R.drawable.frutas_manzana);
                break;
            case "Pera":
                img.setImageResource(R.drawable.frutas_pera);
                break;
            case "Sandía":
                img.setImageResource(R.drawable.frutas_sandia);
                break;
            case "Guayaba":
                img.setImageResource(R.drawable.frutas_guayaba);
                break;
            case "Maracuyá":
                img.setImageResource(R.drawable.frutas_maracuya);
                break;
            case "Banano":
                img.setImageResource(R.drawable.frutas_banano);
                break;
            case "Granadilla":
                img.setImageResource(R.drawable.frutas_granadilla);
                break;
            case "Lulo":
                img.setImageResource(R.drawable.frutas_lulo);
                break;
            case "Mango":
                img.setImageResource(R.drawable.frutas_mango);
                break;
            case "Mora":
                img.setImageResource(R.drawable.frutas_mora);
                break;
            case "Fresa":
                img.setImageResource(R.drawable.frutas_fresa);
                break;
            case "Aguacate":
                img.setImageResource(R.drawable.frutas_aguacate);
                break;
            case "Zapote":
                img.setImageResource(R.drawable.frutas_zapote);
                break;
            case "Guanábana":
                img.setImageResource(R.drawable.frutas_guanabana);
                break;
            case "Papaya":
                img.setImageResource(R.drawable.frutas_papaya);
                break;
            case "Piña":
                img.setImageResource(R.drawable.frutas_pina);
                break;
            case "Tomate":
                img.setImageResource(R.drawable.frutas_tomate);
                break;
            case "Uva":
                img.setImageResource(R.drawable.frutas_uva);
                break;
            case "Curuba":
                img.setImageResource(R.drawable.frutas_curuba);
                break;
            case "Borojó":
                img.setImageResource(R.drawable.frutas_borojo);
                break;
            case "Carambola":
                img.setImageResource(R.drawable.frutas_carambola);
                break;
            case "Escoba":
                img.setImageResource(R.drawable.general_escoba);
                break;
            case "Trapeador":
                img.setImageResource(R.drawable.general_trapeador);
                break;
            case "Recogedor":
                img.setImageResource(R.drawable.general_recogedor);
                break;
            case "Lavadora":
                img.setImageResource(R.drawable.general_lavadora);
                break;
            case "Balde":
                img.setImageResource(R.drawable.general_balde);
                break;
            case "Cepillo":
                img.setImageResource(R.drawable.implementospersonal_cepillo);
                break;
            case "Jabón":
                img.setImageResource(R.drawable.implementospersonal_jabon);
                break;
            case "Shampoo":
                img.setImageResource(R.drawable.implementospersonal_shampoo);
                break;
            case "Toalla":
                img.setImageResource(R.drawable.implementospersonal_toalla);
                break;
            case "Peineta":
                img.setImageResource(R.drawable.implementospersonal_peineta);
                break;
            case "Diseño":
                img.setImageResource(R.drawable.diseno_publicidad);
                break;
            case "Civil":
                img.setImageResource(R.drawable.ingenieria_civil);
                break;
            case "Sistema":
                img.setImageResource(R.drawable.ingenieria_sistemas);
                break;
            case "Desarrollo":
                img.setImageResource(R.drawable.ingenieria_desarrollo);
                break;
            case "Análisis":
                img.setImageResource(R.drawable.ingenieria_analisis);
                break;
            case "Calidad":
                img.setImageResource(R.drawable.ingenieria_calidad);
                break;
            case "Prototipo":
                img.setImageResource(R.drawable.ingenieria_prototipo);
                break;
            case "Ingenio":
                img.setImageResource(R.drawable.ingenieria_ingenio);
                break;
            case "Idea":
                img.setImageResource(R.drawable.ingenieria_idea);
                break;
            case "Página":
                img.setImageResource(R.drawable.ingenieria_pagina);
                break;
            case "Computador":
                img.setImageResource(R.drawable.ingenieria_computador);
                break;
            case "Portátil":
                img.setImageResource(R.drawable.ingenieria_portatil);
                break;
            case "Redes":
                img.setImageResource(R.drawable.ingenieria_redes);
                break;
            case "Fractura":
                img.setImageResource(R.drawable.medicina_fractura);
                break;
            case "Huesos":
                img.setImageResource(R.drawable.medicina_huesos);
                break;
            case "Tendones":
                img.setImageResource(R.drawable.medicina_tendon);
                break;
            case "Nervio":
                img.setImageResource(R.drawable.medicina_nervio);
                break;
            case "Músculo":
                img.setImageResource(R.drawable.medicina_musculo);
                break;
            case "Sutura":
                img.setImageResource(R.drawable.medicina_sutura);
                break;
            case "Tijeras":
                img.setImageResource(R.drawable.medicina_tijeras);
                break;
            case "Gasas":
                img.setImageResource(R.drawable.medicina_gasas);
                break;
            case "Sonda":
                img.setImageResource(R.drawable.medicina_sonda);
                break;
            case "Embarazo":
                img.setImageResource(R.drawable.medicina_embarazo);
                break;
            case "Doctor":
                img.setImageResource(R.drawable.medicina_doctor);
                break;
            case "Medicina":
                img.setImageResource(R.drawable.medicina_medicina);
                break;
            case "Paciente":
                img.setImageResource(R.drawable.medicina_fractura);
                break;
            case "Bicicleta":
                img.setImageResource(R.drawable.medios_transporte_bicicleta);
                break;
            case "Carro":
                img.setImageResource(R.drawable.medios_transporte_carro);
                break;
            case "Tren":
                img.setImageResource(R.drawable.medios_transporte_tren);
                break;
            case "Avión":
                img.setImageResource(R.drawable.medios_transporte_avion);
                break;
            case "Cero":
                img.setImageResource(R.drawable.numeros_cero);
                break;
            case "Uno":
                img.setImageResource(R.drawable.numeros_uno);
                break;
            case "Dos":
                img.setImageResource(R.drawable.numeros_dos);
                break;
            case "Tres":
                img.setImageResource(R.drawable.numeros_tres);
                break;
            case "Cuatro":
                img.setImageResource(R.drawable.numeros_cuatro);
                break;
            case "Cinco":
                img.setImageResource(R.drawable.numeros_cinco);
                break;
            case "Seis":
                img.setImageResource(R.drawable.numeros_seis);
                break;
            case "Siete":
                img.setImageResource(R.drawable.numeros_siete);
                break;
            case "Ocho":
                img.setImageResource(R.drawable.numeros_ocho);
                break;
            case "Nueve":
                img.setImageResource(R.drawable.numeros_nueve);
                break;
            case "Diez":
                img.setImageResource(R.drawable.numeros_diez);
                break;
            case "Once":
                img.setImageResource(R.drawable.numeros_once);
                break;
            case "Doce":
                img.setImageResource(R.drawable.numeros_doce);
                break;
            case "Trece":
                img.setImageResource(R.drawable.numeros_trece);
                break;
            case "Catorce":
                img.setImageResource(R.drawable.numeros_catorce);
                break;
            case "Quince":
                img.setImageResource(R.drawable.numeros_quince);
                break;
            case "Veinte":
                img.setImageResource(R.drawable.numeros_veinte);
                break;
            case "Treinta":
                img.setImageResource(R.drawable.numeros_treinta);
                break;
            case "Cuarenta":
                img.setImageResource(R.drawable.numeros_cuarenta);
                break;
            case "Cincuenta":
                img.setImageResource(R.drawable.numeros_cincuenta);
                break;
            case "Sesenta":
                img.setImageResource(R.drawable.numeros_sesenta);
                break;
            case "Setenta":
                img.setImageResource(R.drawable.numeros_setenta);
                break;
            case "Ochenta":
                img.setImageResource(R.drawable.numeros_ochenta);
                break;
            case "Noventa":
                img.setImageResource(R.drawable.numeros_noventa);
                break;
            case "Cien":
                img.setImageResource(R.drawable.numeros_cien);
                break;
            case "Mil":
                img.setImageResource(R.drawable.numeros_mil);
                break;
            case "Sala":
                img.setImageResource(R.drawable.partes_casa_sala);
                break;
            case "Baño":
                img.setImageResource(R.drawable.partes_casa_bano);
                break;
            case "Habitación":
                img.setImageResource(R.drawable.partes_casa_habitacion);
                break;
            case "Cocina":
                img.setImageResource(R.drawable.partes_casa_cocina);
                break;
            case "Terraza":
                img.setImageResource(R.drawable.partes_casa_terraza);
                break;
            case "Balcón":
                img.setImageResource(R.drawable.partes_casa_balcon);
                break;
            case "Comedor":
                img.setImageResource(R.drawable.partes_casa_comedor);
                break;
            case "Ventana":
                img.setImageResource(R.drawable.partes_casa_ventana);
                break;
            case "Puerta":
                img.setImageResource(R.drawable.partes_casa_puerta);
                break;
            case "Cajón":
                img.setImageResource(R.drawable.partes_casa_cajon);
                break;
            case "Armario":
                img.setImageResource(R.drawable.partes_casa_armario);
                break;
            case "Lavamanos":
                img.setImageResource(R.drawable.partes_casa_lavamanos);
                break;
            case "Sanitario":
                img.setImageResource(R.drawable.partes_casa_sanitario);
                break;
            case "Dedo":
                img.setImageResource(R.drawable.partes_cuerpo_dedo);
                break;
            case "Diente":
                img.setImageResource(R.drawable.partes_cuerpo_diente);
                break;
            case "Labios":
                img.setImageResource(R.drawable.partes_cuerpo_labios);
                break;
            case "Nariz":
                img.setImageResource(R.drawable.partes_cuerpo_nariz);
                break;
            case "Cabello":
                img.setImageResource(R.drawable.partes_cuerpo_cabello);
                break;
            case "Uña":
                img.setImageResource(R.drawable.partes_cuerpo_una);
                break;
            case "Cejas":
                img.setImageResource(R.drawable.partes_cuerpo_cejas);
                break;
            case "Barba":
                img.setImageResource(R.drawable.partes_cuerpo_barba);
                break;
            case "Tobillo":
                img.setImageResource(R.drawable.partes_cuerpo_tobillo);
                break;
            case "Talón":
                img.setImageResource(R.drawable.partes_cuerpo_tobillo);
                break;
            case "Pestaña":
                img.setImageResource(R.drawable.partes_cuerpo_pestana);
                break;
            case "Mano":
                img.setImageResource(R.drawable.partes_cuerpo_dedo);
                break;
            case "Pie":
                img.setImageResource(R.drawable.partes_cuerpo_tobillo);
                break;
            case "Rodilla":
                img.setImageResource(R.drawable.partes_cuerpo_rodilla);
                break;
            case "Codo":
                img.setImageResource(R.drawable.partes_cuerpo_codo);
                break;
            case "Cabeza":
                img.setImageResource(R.drawable.partes_cuerpo_cabeza);
                break;
            case "Espalda":
                img.setImageResource(R.drawable.partes_cuerpo_espalda);
                break;
            case "Pierna":
                img.setImageResource(R.drawable.partes_cuerpo_pierna);
                break;
            case "Hombro":
                img.setImageResource(R.drawable.partes_cuerpo_hombro);
                break;
            case "Pecho":
                img.setImageResource(R.drawable.partes_cuerpo_pecho);
                break;
            case "Brazo":
                img.setImageResource(R.drawable.partes_cuerpo_brazo);
                break;
            case "Cadera":
                img.setImageResource(R.drawable.partes_cuerpo_cadera);
                break;
            case "Orejas":
                img.setImageResource(R.drawable.partes_cuerpo_oreja);
                break;
            case "Lengua":
                img.setImageResource(R.drawable.partes_cuerpo_lengua);
                break;
            case "Ojos":
                img.setImageResource(R.drawable.partes_cuerpo_ojos);
                break;
            case "Cuello":
                img.setImageResource(R.drawable.partes_cuerpo_cuello);
                break;
            case "Sancocho":
                img.setImageResource(R.drawable.platos_sancocho);
                break;
            case "Ajiaco":
                img.setImageResource(R.drawable.platos_ajiaco);
                break;
            case "Frijoles":
                img.setImageResource(R.drawable.platos_frijoles);
                break;
            case "Lentejas":
                img.setImageResource(R.drawable.platos_lentejas);
                break;
            case "Arroz":
                img.setImageResource(R.drawable.platos_arroz);
                break;
            case "Chuleta":
                img.setImageResource(R.drawable.platos_chuleta);
                break;
            case "Pollo":
                img.setImageResource(R.drawable.platos_pollo);
                break;
            case "Hígado":
                img.setImageResource(R.drawable.platos_higado);
                break;
            case "Cerdo":
                img.setImageResource(R.drawable.platos_cerdo);
                break;
            case "Costilla":
                img.setImageResource(R.drawable.platos_costilla);
                break;
            case "Pescado":
                img.setImageResource(R.drawable.platos_pescado);
                break;
            case "Patacón":
                img.setImageResource(R.drawable.platos_patacon);
                break;
            case "Pastel":
                img.setImageResource(R.drawable.platos_pastel);
                break;
            case "Arepa":
                img.setImageResource(R.drawable.platos_arepa);
                break;
            case "Papa":
                img.setImageResource(R.drawable.platos_papa);
                break;
            case "Tamal":
                img.setImageResource(R.drawable.platos_tamal);
                break;
            case "Sopa":
                img.setImageResource(R.drawable.platos_sopa);
                break;
            case "Arma":
                img.setImageResource(R.drawable.policia_arma);
                break;
            case "Uniforme":
                img.setImageResource(R.drawable.policia_uniforme);
                break;
            case "Crimen":
                img.setImageResource(R.drawable.policia_crimen);
                break;
            case "Oficial":
                img.setImageResource(R.drawable.policia_ejercito);
                break;
            case "Coronel":
                img.setImageResource(R.drawable.policia_ejercito);
                break;
            case "Comandante":
                img.setImageResource(R.drawable.policia_ejercito);
                break;
            case "Sargento":
                img.setImageResource(R.drawable.policia_ejercito);
                break;
            case "Captura":
                img.setImageResource(R.drawable.policia_captura);
                break;
            case "Autoridad":
                img.setImageResource(R.drawable.policia_policia);
                break;
            case "Policía":
                img.setImageResource(R.drawable.policia_policia);
                break;
            case "Zapatos":
                img.setImageResource(R.drawable.prendas_zapatos);
                break;
            case "Pantalón":
                img.setImageResource(R.drawable.prendas_pantalon);
                break;
            case "Buzo":
                img.setImageResource(R.drawable.prendas_buzo);
                break;
            case "Camisa":
                img.setImageResource(R.drawable.prendas_camisa);
                break;
            case "Camiseta":
                img.setImageResource(R.drawable.prendas_buzo);
                break;
            case "Corbata":
                img.setImageResource(R.drawable.prendas_corbata);
                break;
            case "Sombrero":
                img.setImageResource(R.drawable.prendas_sombrero);
                break;
            case "Bermuda":
                img.setImageResource(R.drawable.prendas_bermuda);
                break;
            case "Medias":
                img.setImageResource(R.drawable.prendas_medias);
                break;
            case "Falda":
                img.setImageResource(R.drawable.prendas_falda);
                break;
            case "Chaqueta":
                img.setImageResource(R.drawable.prendas_chaqueta);
                break;
            case "Saco":
                img.setImageResource(R.drawable.prendas_chaqueta);
                break;
            case "Calzón":
                img.setImageResource(R.drawable.prendas_calzon);
                break;
            case "Bóxer":
                img.setImageResource(R.drawable.prendas_boxer);
                break;
            case "Anillo":
                img.setImageResource(R.drawable.prendas_anillo);
                break;
            case "Collar":
                img.setImageResource(R.drawable.prendas_collar);
                break;
            case "Bufanda":
                img.setImageResource(R.drawable.prendas_bufanda);
                break;
            case "Ruana":
                img.setImageResource(R.drawable.prendas_ruana);
                break;
            case "Chaleco":
                img.setImageResource(R.drawable.prendas_chaleco);
                break;
            case "Blusa":
                img.setImageResource(R.drawable.prendas_blusa);
                break;
            case "Sandalia":
                img.setImageResource(R.drawable.prendas_sandalia);
                break;
            case "Brasier":
                img.setImageResource(R.drawable.prendas_brasier);
                break;
            case "Top":
                img.setImageResource(R.drawable.prendas_top);
                break;
            case "Tanga":
                img.setImageResource(R.drawable.prendas_tanga);
                break;
            case "Arete":
                img.setImageResource(R.drawable.prendas_arete);
                break;
            case "Diadema":
                img.setImageResource(R.drawable.prendas_diadema);
                break;
            case "Moño":
                img.setImageResource(R.drawable.prendas_mono);
                break;
            case "Pulsera":
                img.setImageResource(R.drawable.prendas_pulsera);
                break;
            case "Aros":
                img.setImageResource(R.drawable.prendas_aros);
                break;
            case "Cadena":
                img.setImageResource(R.drawable.prendas_cadena);
                break;
            case "Correa":
                img.setImageResource(R.drawable.prendas_correa);
                break;
            case "Gafas":
                img.setImageResource(R.drawable.prendas_gafas);
                break;
            case "Parque":
                img.setImageResource(R.drawable.sitios_parque);
                break;
            case "Piscina":
                img.setImageResource(R.drawable.sitios_piscina);
                break;
            case "Escuela":
                img.setImageResource(R.drawable.sitios_escuela);
                break;
            case "Colegio":
                img.setImageResource(R.drawable.sitios_escuela);
                break;
            case "Ciudad":
                img.setImageResource(R.drawable.sitios_ciudad);
                break;
            case "Pueblo":
                img.setImageResource(R.drawable.sitios_pueblo);
                break;
            case "Lago":
                img.setImageResource(R.drawable.sitios_lago);
                break;
            case "Estadio":
                img.setImageResource(R.drawable.sitios_estadio);
                break;
            case "Iglesia":
                img.setImageResource(R.drawable.sitios_iglesia);
                break;
            case "Banco":
                img.setImageResource(R.drawable.sitios_banco);
                break;
            case "Hospital":
                img.setImageResource(R.drawable.sitios_hospital);
                break;
            case "Oficina":
                img.setImageResource(R.drawable.sitios_oficina);
                break;
            case "Zanahoria":
                img.setImageResource(R.drawable.verduras_zanahoria);
                break;
            case "Remolacha":
                img.setImageResource(R.drawable.verduras_remolacha);
                break;
            case "Repollo":
                img.setImageResource(R.drawable.verduras_repollo);
                break;
            case "Cebolla":
                img.setImageResource(R.drawable.verduras_cebolla);
                break;
            case "Brócoli":
                img.setImageResource(R.drawable.verduras_brocoli);
                break;
            case "Zapallo":
                img.setImageResource(R.drawable.verduras_zapallo);
                break;
            case "Habichuela":
                img.setImageResource(R.drawable.verduras_habichuela);
                break;
            case "Coliflor":
                img.setImageResource(R.drawable.verduras_coliflor);
                break;
            case "Pepino":
                img.setImageResource(R.drawable.verduras_pepino);
                break;
            case "Espinaca":
                img.setImageResource(R.drawable.verduras_espinaca);
                break;
            case "Lechuga":
                img.setImageResource(R.drawable.verduras_lechuga);
                break;
            case "Pimentón":
                img.setImageResource(R.drawable.verduras_pimenton);
                break;
            case "Ajo":
                img.setImageResource(R.drawable.verduras_ajo);
                break;
            case "Arveja":
                img.setImageResource(R.drawable.verduras_arveja);
                break;
            case "Guitarra":
                img.setImageResource(R.drawable.instrumentos_guitarra);
                break;
            case "Acordeón":
                img.setImageResource(R.drawable.instrumentos_acordeon);
                break;
            case "Piano":
                img.setImageResource(R.drawable.instrumentos_piano);
                break;
            case "Violín":
                img.setImageResource(R.drawable.instrumentos_violin);
                break;
            case "Batería":
                img.setImageResource(R.drawable.instrumentos_bateria);
                break;
            case "Flauta":
                img.setImageResource(R.drawable.instrumentos_flauta);
                break;
            case "Empresa":
                img.setImageResource(R.drawable.administracion_empresa);
                break;
            case "Equipo":
                img.setImageResource(R.drawable.administracion_equipo);
                break;
        }

        Button sonido = (Button) findViewById(R.id.buttonSound);
        sonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut();
            }
        });
    }

    @Override
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

        InputStream is = this.getResources().openRawResource(R.raw.nivel1);
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

        TextView textView = (TextView) findViewById(R.id.textView_escuchaN1);
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
        File keysGrammar = new File(assetsDir, "nivel1.gram");
        recognizer.addKeywordSearch("frases",keysGrammar);
    }

    private void resetRecognizer(){
        if(recognizer!=null) {
            recognizer.cancel();
            recognizer.startListening("frases");
        }
    }

    public void palabraReconocida(){
        TextView textViewTusPuntos = (TextView) findViewById(R.id.textViewPuntajeJugadorN1);
        usuario.setPuntaje(palabra.getPuntaje() + usuario.getPuntaje());
        db.updateUsuario(usuario);
        textViewTusPuntos.setText("Tienes: " + usuario.getPuntaje() +", Pts");

        ArrayList<Reto> retos = dbR.loadReto();

        for (int i = 0; i < retos.size(); i++){
            if(retos.get(i).getVariable() == 1){
                Categoria categoria = dbC.buscarCategoria(palabra.getCategoria());
                if(retos.get(i).getCategoria().equals(categoria.getNombre())){

                    retos.get(i).setNpalabra(1);
                    dbR.updateReto(retos.get(i));

                    if(retos.get(i).getNpalabra() == retos.get(i).getPalabra()){
                        usuario.setPuntaje(usuario.getPuntaje() + retos.get(i).getPuntos());
                        db.updateUsuario(usuario);
                        dbR.deleteReto(retos.get(i).getId());
                        Toast.makeText(this, "¡¡Cumpliste un reto!!", Toast.LENGTH_SHORT).show();
                        textViewTusPuntos.setText("Tienes: " + usuario.getPuntaje() +", Pts");
                    }
                }
            }
        }

        if(usuario.getPuntaje() >= 1000){
            Nivel nivel = dbN.buscarNivel("Nivel 2");
            if(nivel.getBloqueado() == 1){
                Toast.makeText(this, "¡Nivel 2 desbloqueado!", Toast.LENGTH_SHORT).show();
            }
            nivel.setBloqueado(0);
            dbN.updateNivel(nivel);
        }

    }
}