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

public class FinalNivel1 extends AppCompatActivity implements TextToSpeech.OnInitListener, edu.cmu.pocketsphinx.RecognitionListener {

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

        dbP = new PalabraDB(FinalNivel1.this);
        db = new UsuarioDB(FinalNivel1.this);

        usuario = db.buscarUsuarios(1);
        palabra = dbP.buscarPalabra(Integer.parseInt(idPalabra));
        acierto=false;

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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public void onResult(Hypothesis hypothesis) {
        if(text != null){
            switch (palabra.getNombre()){
                case "Acuarela":
                    if(text.contains("acuarela"))
                        acierto=true;
                    break;
                case "Arte":
                    if(text.contains("arte"))
                        acierto=true;
                    break;
                case "Boceto":
                    if(text.contains("boceto"))
                        acierto=true;
                    break;
                case "Clásico":
                    if(text.contains("clasico"))
                        acierto=true;
                    break;
                case "Dibujo":
                    if(text.contains("dibujo"))
                        acierto=true;
                    break;
                case "Estatua":
                    if(text.contains("estatua"))
                        acierto=true;
                    break;
                case "Lápiz":
                    if(text.contains("lapiz"))
                        acierto=true;
                    break;
                case "Mural":
                    if(text.contains("mural"))
                        acierto=true;
                    break;
                case "Plastilina":
                    if(text.contains("plastilina"))
                        acierto=true;
                    break;
                case "Yeso":
                    if(text.contains("yeso"))
                        acierto=true;
                    break;
                case "Rojo":
                    if(text.contains("rojo"))
                        acierto=true;
                    break;
                case "Verde":
                    if(text.contains("verde"))
                        acierto=true;
                    break;
                case "Azul":
                    if(text.contains("azul"))
                        acierto=true;
                    break;
                case "Amarillo":
                    if(text.contains("amarillo"))
                        acierto=true;
                    break;
                case "Negro":
                    if(text.contains("negro"))
                        acierto=true;
                    break;
                case "Blanco":
                    if(text.contains("blanco"))
                        acierto=true;
                    break;
                case "Gris":
                    if(text.contains("gris"))
                        acierto=true;
                    break;
                case "Café":
                    if(text.contains("cafe"))
                        acierto=true;
                    break;
                case "Morado":
                    if(text.contains("morado"))
                        acierto=true;
                    break;
                case "Rosado":
                    if(text.contains("rosado"))
                        acierto=true;
                    break;
                case "Salmón":
                    if(text.contains("salmon"))
                        acierto=true;
                    break;
                case "Durazno":
                    if(text.contains("durazno"))
                        acierto=true;
                    break;
                case "Fucsia":
                    if(text.contains("fucsia"))
                        acierto=true;
                    break;
                case "Plateado":
                    if(text.contains("plateado"))
                        acierto=true;
                    break;
                case "Dorado":
                    if(text.contains("dorado"))
                        acierto=true;
                    break;
                case "Violeta":
                    if(text.contains("violeta"))
                        acierto=true;
                    break;
                case "Púrpura":
                    if(text.contains("purpura"))
                        acierto=true;
                    break;
                case "Hamburguesa":
                    if(text.contains("hamburguesa"))
                        acierto=true;
                    break;
                case "Salchipapa":
                    if(text.contains("salchipapa"))
                        acierto=true;
                    break;
                case "Pizza":
                    if(text.contains("pizza"))
                        acierto=true;
                    break;
                case "Agenda":
                    if(text.contains("agenda"))
                        acierto=true;
                    break;
                case "Prensa":
                    if(text.contains("prensa"))
                        acierto=true;
                    break;
                case "Mensaje":
                    if(text.contains("mensaje"))
                        acierto=true;
                    break;
                case "Opinión":
                    if(text.contains("opinion"))
                        acierto=true;
                    break;
                case "Reportaje":
                    if(text.contains("reportaje"))
                        acierto=true;
                    break;
                case "Caja":
                    if(text.contains("caja"))
                        acierto=true;
                    break;
                case "Contable":
                    if(text.contains("contable"))
                        acierto=true;
                    break;
                case "Contador":
                    if(text.contains("contador"))
                        acierto=true;
                    break;
                case "Auxiliar":
                    if(text.contains("auxiliar"))
                        acierto=true;
                    break;
                case "Factura":
                    if(text.contains("factura"))
                        acierto=true;
                    break;
                case "Inventario":
                    if(text.contains("inventario"))
                        acierto=true;
                    break;
                case "Iva":
                    if(text.contains("iva"))
                        acierto=true;
                    break;
                case "Proveedor":
                    if(text.contains("proveedor"))
                        acierto=true;
                    break;
                case "Fuerza":
                    if(text.contains("fuerza"))
                        acierto=true;
                    break;
                case "Ajedrez":
                    if(text.contains("ajedrez"))
                        acierto=true;
                    break;
                case "Árbitro":
                    if(text.contains("arbitro"))
                        acierto=true;
                    break;
                case "Carrera":
                    if(text.contains("carrera"))
                        acierto=true;
                    break;
                case "Ciclismo":
                    if(text.contains("ciclismo"))
                        acierto=true;
                    break;
                case "Clavado":
                    if(text.contains("clavado"))
                        acierto=true;
                    break;
                case "Deporte":
                    if(text.contains("deporte"))
                        acierto=true;
                    break;
                case "Entrenar":
                    if(text.contains("entrenar"))
                        acierto=true;
                    break;
                case "Esfuerzo":
                    if(text.contains("esfuerzo"))
                        acierto=true;
                    break;
                case "Fútbol":
                    if(text.contains("futbol"))
                        acierto=true;
                    break;
                case "Lateral":
                    if(text.contains("lateral"))
                        acierto=true;
                    break;
                case "Central":
                    if(text.contains("central"))
                        acierto=true;
                    break;
                case "Volante":
                    if(text.contains("colante"))
                        acierto=true;
                    break;
                case "Delantero":
                    if(text.contains("delantero"))
                        acierto=true;
                    break;
                case "Gimnasio":
                    if(text.contains("gimnasio"))
                        acierto=true;
                    break;
                case "Mariposa":
                    if(text.contains("mariposa"))
                        acierto=true;
                    break;
                case "Natación":
                    if(text.contains("natacion"))
                        acierto=true;
                    break;
                case "Olímpico":
                    if(text.contains("olimpico"))
                        acierto=true;
                    break;
                case "Patinaje":
                    if(text.contains("patinaje"))
                        acierto=true;
                    break;
                case "Pesas":
                    if(text.contains("pesas"))
                        acierto=true;
                    break;
                case "Técnico":
                    if(text.contains("tecnico"))
                        acierto=true;
                    break;
                case "Tenis":
                    if(text.contains("tenis"))
                        acierto=true;
                    break;
                case "Cuerpo":
                    if(text.contains("cuerpo"))
                        acierto=true;
                    break;
                case "Deportista":
                    if(text.contains("deportista"))
                        acierto=true;
                    break;
                case "Ley":
                    if(text.contains("ley"))
                        acierto=true;
                    break;
                case "Juez":
                    if(text.contains("juez"))
                        acierto=true;
                    break;
                case "Abogado":
                    if(text.contains("abogado"))
                        acierto=true;
                    break;
                case "Jurado":
                    if(text.contains("jurado"))
                        acierto=true;
                    break;
                case "Fiscalía":
                    if(text.contains("fiscalia"))
                        acierto=true;
                    break;
                case "Constitución":
                    if(text.contains("constitucion"))
                        acierto=true;
                    break;
                case "Derecho":
                    if(text.contains("derecho"))
                        acierto=true;
                    break;
                case "Bienes":
                    if(text.contains("bienes"))
                        acierto=true;
                    break;
                case "Herencia":
                    if(text.contains("herencia"))
                        acierto=true;
                    break;
                case "Folleto":
                    if(text.contains("folleto"))
                        acierto=true;
                    break;
                case "Ícono":
                    if(text.contains("icono"))
                        acierto=true;
                    break;
                case "Lienzo":
                    if(text.contains("lienzo"))
                        acierto=true;
                    break;
                case "Mapa":
                    if(text.contains("mapa"))
                        acierto=true;
                    break;
                case "Marca":
                    if(text.contains("marca"))
                        acierto=true;
                    break;
                case "Publicidad":
                    if(text.contains("publicidad"))
                        acierto=true;
                    break;
                case "Tinta":
                    if(text.contains("tinta"))
                        acierto=true;
                    break;
                case "Símbolo":
                    if(text.contains("simbolo"))
                        acierto=true;
                    break;
                case "Sombra":
                    if(text.contains("sombra"))
                        acierto=true;
                    break;
                case "Textura":
                    if(text.contains("textura"))
                        acierto=true;
                    break;
                case "Útiles":
                    if(text.contains("utiles"))
                        acierto=true;
                    break;
                case "Tarea":
                    if(text.contains("tarea"))
                        acierto=true;
                    break;
                case "Salón":
                    if(text.contains("salon"))
                        acierto=true;
                    break;
                case "Educación":
                    if(text.contains("educacion"))
                        acierto=true;
                    break;
                case "Enseñanza":
                    if(text.contains("enseñanza"))
                        acierto=true;
                    break;
                case "Familia":
                    if(text.contains("familia"))
                        acierto=true;
                    break;
                case "Cuaderno":
                    if(text.contains("cuaderno"))
                        acierto=true;
                    break;
                case "Vacaciones":
                    if(text.contains("vacaciones"))
                        acierto=true;
                    break;
                case "Materia":
                    if(text.contains("materia"))
                        acierto=true;
                    break;
                case "Amistad":
                    if(text.contains("amistad"))
                        acierto=true;
                    break;
                case "Rector":
                    if(text.contains("rector"))
                        acierto=true;
                    break;
                case "Profesor":
                    if(text.contains("profesor"))
                        acierto=true;
                    break;
                case "Maestro":
                    if(text.contains("maestro"))
                        acierto=true;
                    break;
                case "Notas":
                    if(text.contains("notas"))
                        acierto=true;
                    break;
                case "Atención":
                    if(text.contains("atencion"))
                        acierto=true;
                    break;
                case "Docente":
                    if(text.contains("docente"))
                        acierto=true;
                    break;
                case "Estudiante":
                    if(text.contains("estudiante"))
                        acierto=true;
                    break;
                case "Ética":
                    if(text.contains("etica"))
                        acierto=true;
                    break;
                case "Física":
                    if(text.contains("fisica"))
                        acierto=true;
                    break;
                case "Química":
                    if(text.contains("quimica"))
                        acierto=true;
                    break;
                case "Cálculo":
                    if(text.contains("calculo"))
                        acierto=true;
                    break;
                case "Borrador":
                    if(text.contains("borrador"))
                        acierto=true;
                    break;
                case "Tablero":
                    if(text.contains("tablero"))
                        acierto=true;
                    break;
                case "Balanza":
                    if(text.contains("alanza"))
                        acierto=true;
                    break;
                case "Moneda":
                    if(text.contains("moneda"))
                        acierto=true;
                    break;
                case "Población":
                    if(text.contains("poblacion"))
                        acierto=true;
                    break;
                case "Interés":
                    if(text.contains("interes"))
                        acierto=true;
                    break;
                case "Bolsa":
                    if(text.contains("bolsa"))
                        acierto=true;
                    break;
                case "Salario":
                    if(text.contains("salario"))
                        acierto=true;
                    break;
                case "Capital":
                    if(text.contains("capital"))
                        acierto=true;
                    break;
                case "Ahorro":
                    if(text.contains("ahorro"))
                        acierto=true;
                    break;
                case "Mercado":
                    if(text.contains("mercado"))
                        acierto=true;
                    break;
                case "Oferta":
                    if(text.contains("oferta"))
                        acierto=true;
                    break;
                case "Demanda":
                    if(text.contains("demanda"))
                        acierto=true;
                    break;
                case "Manzana":
                    if(text.contains("manzana"))
                        acierto=true;
                    break;
                case "Pera":
                    if(text.contains("pera"))
                        acierto=true;
                    break;
                case "Sandía":
                    if(text.contains("sandia"))
                        acierto=true;
                    break;
                case "Guayaba":
                    if(text.contains("guayaba"))
                        acierto=true;
                    break;
                case "Maracuyá":
                    if(text.contains("maracuya"))
                        acierto=true;
                    break;
                case "Banano":
                    if(text.contains("banano"))
                        acierto=true;
                    break;
                case "Granadilla":
                    if(text.contains("granadilla"))
                        acierto=true;
                    break;
                case "Lulo":
                    if(text.contains("lulo"))
                        acierto=true;
                    break;
                case "Mango":
                    if(text.contains("mango"))
                        acierto=true;
                    break;
                case "Mora":
                    if(text.contains("mora"))
                        acierto=true;
                    break;
                case "Fresa":
                    if(text.contains("fresa"))
                        acierto=true;
                    break;
                case "Aguacate":
                    if(text.contains("aguacate"))
                        acierto=true;
                    break;
                case "Zapote":
                    if(text.contains("zapote"))
                        acierto=true;
                    break;
                case "Guanábana":
                    if(text.contains("guanabana"))
                        acierto=true;
                    break;
                case "Papaya":
                    if(text.contains("papaya"))
                        acierto=true;
                    break;
                case "Piña":
                    if(text.contains("piña"))
                        acierto=true;
                    break;
                case "Tomate":
                    if(text.contains("tomate"))
                        acierto=true;
                    break;
                case "Uva":
                    if(text.contains("uva"))
                        acierto=true;
                    break;
                case "Curuba":
                    if(text.contains("curuba"))
                        acierto=true;
                    break;
                case "Borojó":
                    if(text.contains("borojo"))
                        acierto=true;
                    break;
                case "Carambola":
                    if(text.contains("carambola"))
                        acierto=true;
                    break;
                case "Escoba":
                    if(text.contains("escoba"))
                        acierto=true;
                    break;
                case "Trapeador":
                    if(text.contains("trapeador"))
                        acierto=true;
                    break;
                case "Recogedor":
                    if(text.contains("recogedor"))
                        acierto=true;
                    break;
                case "Lavadora":
                    if(text.contains("lavadora"))
                        acierto=true;
                    break;
                case "Balde":
                    if(text.contains("balde"))
                        acierto=true;
                    break;
                case "Cepillo":
                    if(text.contains("cepillo"))
                        acierto=true;
                    break;
                case "Jabón":
                    if(text.contains("jabon"))
                        acierto=true;
                    break;
                case "Shampoo":
                    if(text.contains("shampoo"))
                        acierto=true;
                    break;
                case "Toalla":
                    if(text.contains("toalla"))
                        acierto=true;
                    break;
                case "Peineta":
                    if(text.contains("peineta"))
                        acierto=true;
                    break;
                case "Diseño":
                    if(text.contains("diseño"))
                        acierto=true;
                    break;
                case "Civil":
                    if(text.contains("civil"))
                        acierto=true;
                    break;
                case "Sistema":
                    if(text.contains("sistema"))
                        acierto=true;
                    break;
                case "Desarrollo":
                    if(text.contains("desarrollo"))
                        acierto=true;
                    break;
                case "Análisis":
                    if(text.contains("analisis"))
                        acierto=true;
                    break;
                case "Calidad":
                    if(text.contains("calidad"))
                        acierto=true;
                    break;
                case "Prototipo":
                    if(text.contains("prototipo"))
                        acierto=true;
                    break;
                case "Ingenio":
                    if(text.contains("ingenio"))
                        acierto=true;
                    break;
                case "Idea":
                    if(text.contains("idea"))
                        acierto=true;
                    break;
                case "Página":
                    if(text.contains("pagina"))
                        acierto=true;
                    break;
                case "Computador":
                    if(text.contains("computador"))
                        acierto=true;
                    break;
                case "Portátil":
                    if(text.contains("portatil"))
                        acierto=true;
                    break;
                case "Redes":
                    if(text.contains("redes"))
                        acierto=true;
                    break;
                case "Fractura":
                    if(text.contains("fractura"))
                        acierto=true;
                    break;
                case "Huesos":
                    if(text.contains("huesos"))
                        acierto=true;
                    break;
                case "Tendones":
                    if(text.contains("tendones"))
                        acierto=true;
                    break;
                case "Nervio":
                    if(text.contains("nervio"))
                        acierto=true;
                    break;
                case "Músculo":
                    if(text.contains("musculo"))
                        acierto=true;
                    break;
                case "Sutura":
                    if(text.contains("sutura"))
                        acierto=true;
                    break;
                case "Tijeras":
                    if(text.contains("tijeras"))
                        acierto=true;
                    break;
                case "Gasas":
                    if(text.contains("gasas"))
                        acierto=true;
                    break;
                case "Sonda":
                    if(text.contains("sonda"))
                        acierto=true;
                    break;
                case "Embarazo":
                    if(text.contains("embarazo"))
                        acierto=true;
                    break;
                case "Doctor":
                    if(text.contains("doctor"))
                        acierto=true;
                    break;
                case "Medicina":
                    if(text.contains("medicina"))
                        acierto=true;
                    break;
                case "Paciente":
                    if(text.contains("paciente"))
                        acierto=true;
                    break;
                case "Bicicleta":
                    if(text.contains("bicicleta"))
                        acierto=true;
                    break;
                case "Carro":
                    if(text.contains("carro"))
                        acierto=true;
                    break;
                case "Tren":
                    if(text.contains("tren"))
                        acierto=true;
                    break;
                case "Avión":
                    if(text.contains("avion"))
                        acierto=true;
                    break;
                case "Cero":
                    if(text.contains("cero"))
                        acierto=true;
                    break;
                case "Uno":
                    if(text.contains("uno"))
                        acierto=true;
                    break;
                case "Dos":
                    if(text.contains("dos"))
                        acierto=true;
                    break;
                case "Tres":
                    if(text.contains("tres"))
                        acierto=true;
                    break;
                case "Cuatro":
                    if(text.contains("cuatro"))
                        acierto=true;
                    break;
                case "Cinco":
                    if(text.contains("cinco"))
                        acierto=true;
                    break;
                case "Seis":
                    if(text.contains("seis"))
                        acierto=true;
                    break;
                case "Siete":
                    if(text.contains("siete"))
                        acierto=true;
                    break;
                case "Ocho":
                    if(text.contains("ocho"))
                        acierto=true;
                    break;
                case "Nueve":
                    if(text.contains("nueve"))
                        acierto=true;
                    break;
                case "Diez":
                    if(text.contains("diez"))
                        acierto=true;
                    break;
                case "Once":
                    if(text.contains("once"))
                        acierto=true;
                    break;
                case "Doce":
                    if(text.contains("doce"))
                        acierto=true;
                    break;
                case "Trece":
                    if(text.contains("trece"))
                        acierto=true;
                    break;
                case "Catorce":
                    if(text.contains("catorce"))
                        acierto=true;
                    break;
                case "Quince":
                    if(text.contains("quince"))
                        acierto=true;
                    break;
                case "Veinte":
                    if(text.contains("veinte"))
                        acierto=true;
                    break;
                case "Treinta":
                    if(text.contains("treinta"))
                        acierto=true;
                    break;
                case "Cuarenta":
                    if(text.contains("cuarenta"))
                        acierto=true;
                    break;
                case "Cincuenta":
                    if(text.contains("cincuenta"))
                        acierto=true;
                    break;
                case "Sesenta":
                    if(text.contains("sesenta"))
                        acierto=true;
                    break;
                case "Setenta":
                    if(text.contains("setenta"))
                        acierto=true;
                    break;
                case "Ochenta":
                    if(text.contains("ochenta"))
                        acierto=true;
                    break;
                case "Noventa":
                    if(text.contains("noventa"))
                        acierto=true;
                    break;
                case "Cien":
                    if(text.contains("cien"))
                        acierto=true;
                    break;
                case "Mil":
                    if(text.contains("mil"))
                        acierto=true;
                    break;
                case "Sala":
                    if(text.contains("sala"))
                        acierto=true;
                    break;
                case "Baño":
                    if(text.contains("baño"))
                        acierto=true;
                    break;
                case "Habitación":
                    if(text.contains("habitacion"))
                        acierto=true;
                    break;
                case "Cocina":
                    if(text.contains("cocina"))
                        acierto=true;
                    break;
                case "Terraza":
                    if(text.contains("terraza"))
                        acierto=true;
                    break;
                case "Balcón":
                    if(text.contains("balcon"))
                        acierto=true;
                    break;
                case "Comedor":
                    if(text.contains("comedor"))
                        acierto=true;
                    break;
                case "Ventana":
                    if(text.contains("ventana"))
                        acierto=true;
                    break;
                case "Puerta":
                    if(text.contains("puerta"))
                        acierto=true;
                    break;
                case "Cajón":
                    if(text.contains("cajon"))
                        acierto=true;
                    break;
                case "Armario":
                    if(text.contains("armario"))
                        acierto=true;
                    break;
                case "Lavamanos":
                    if(text.contains("lavamanos"))
                        acierto=true;
                    break;
                case "Sanitario":
                    if(text.contains("sanitario"))
                        acierto=true;
                    break;
                case "Dedo":
                    if(text.contains("dedo"))
                        acierto=true;
                    break;
                case "Diente":
                    if(text.contains("diente"))
                        acierto=true;
                    break;
                case "Labios":
                    if(text.contains("labios"))
                        acierto=true;
                    break;
                case "Nariz":
                    if(text.contains("nariz"))
                        acierto=true;
                    break;
                case "Cabello":
                    if(text.contains("cabello"))
                        acierto=true;
                    break;
                case "Uña":
                    if(text.contains("uña"))
                        acierto=true;
                    break;
                case "Cejas":
                    if(text.contains("cejas"))
                        acierto=true;
                    break;
                case "Barba":
                    if(text.contains("barba"))
                        acierto=true;
                    break;
                case "Tobillo":
                    if(text.contains("tobillo"))
                        acierto=true;
                    break;
                case "Talón":
                    if(text.contains("talon"))
                        acierto=true;
                    break;
                case "Pestaña":
                    if(text.contains("pestaña"))
                        acierto=true;
                    break;
                case "Mano":
                    if(text.contains("mano"))
                        acierto=true;
                    break;
                case "Pie":
                    if(text.contains("pie"))
                        acierto=true;
                    break;
                case "Rodilla":
                    if(text.contains("rodilla"))
                        acierto=true;
                    break;
                case "Codo":
                    if(text.contains("codo"))
                        acierto=true;
                    break;
                case "Cabeza":
                    if(text.contains("cabeza"))
                        acierto=true;
                    break;
                case "Espalda":
                    if(text.contains("espalda"))
                        acierto=true;
                    break;
                case "Pierna":
                    if(text.contains("pierna"))
                        acierto=true;
                    break;
                case "Hombro":
                    if(text.contains("hombro"))
                        acierto=true;
                    break;
                case "Pecho":
                    if(text.contains("pecho"))
                        acierto=true;
                    break;
                case "Brazo":
                    if(text.contains("brazo"))
                        acierto=true;
                    break;
                case "Cadera":
                    if(text.contains("cadera"))
                        acierto=true;
                    break;
                case "Orejas":
                    if(text.contains("orejas"))
                        acierto=true;
                    break;
                case "Lengua":
                    if(text.contains("lengua"))
                        acierto=true;
                    break;
                case "Ojos":
                    if(text.contains("ojos"))
                        acierto=true;
                    break;
                case "Cuello":
                    if(text.contains("cuello"))
                        acierto=true;
                    break;
                case "Sancocho":
                    if(text.contains("sancocho"))
                        acierto=true;
                    break;
                case "Ajiaco":
                    if(text.contains("ajiaco"))
                        acierto=true;
                    break;
                case "Frijoles":
                    if(text.contains("frijoles"))
                        acierto=true;
                    break;
                case "Lentejas":
                    if(text.contains("lentejas"))
                        acierto=true;
                    break;
                case "Arroz":
                    if(text.contains("arroz"))
                        acierto=true;
                    break;
                case "Chuleta":
                    if(text.contains("chuleta"))
                        acierto=true;
                    break;
                case "Pollo":
                    if(text.contains("pollo"))
                        acierto=true;
                    break;
                case "Hígado":
                    if(text.contains("higado"))
                        acierto=true;
                    break;
                case "Cerdo":
                    if(text.contains("cerdo"))
                        acierto=true;
                    break;
                case "Costilla":
                    if(text.contains("costilla"))
                        acierto=true;
                    break;
                case "Pescado":
                    if(text.contains("pescado"))
                        acierto=true;
                    break;
                case "Patacón":
                    if(text.contains("patacon"))
                        acierto=true;
                    break;
                case "Pastel":
                    if(text.contains("pastel"))
                        acierto=true;
                    break;
                case "Arepa":
                    if(text.contains("arepa"))
                        acierto=true;
                    break;
                case "Papa":
                    if(text.contains("papa"))
                        acierto=true;
                    break;
                case "Tamal":
                    if(text.contains("tamal"))
                        acierto=true;
                    break;
                case "Sopa":
                    if(text.contains("sopa"))
                        acierto=true;
                    break;
                case "Arma":
                    if(text.contains("arma"))
                        acierto=true;
                    break;
                case "Uniforme":
                    if(text.contains("uniforme"))
                        acierto=true;
                    break;
                case "Crimen":
                    if(text.contains("crimen"))
                        acierto=true;
                    break;
                case "Oficial":
                    if(text.contains("oficial"))
                        acierto=true;
                    break;
                case "Coronel":
                    if(text.contains("coronel"))
                        acierto=true;
                    break;
                case "Comandante":
                    if(text.contains("comandante"))
                        acierto=true;
                    break;
                case "Sargento":
                    if(text.contains("sargento"))
                        acierto=true;
                    break;
                case "Captura":
                    if(text.contains("captura"))
                        acierto=true;
                    break;
                case "Autoridad":
                    if(text.contains("autoridad"))
                        acierto=true;
                    break;
                case "Policía":
                    if(text.contains("policia"))
                        acierto=true;
                    break;
                case "Zapatos":
                    if(text.contains("zapatos"))
                        acierto=true;
                    break;
                case "Pantalón":
                    if(text.contains("pantalon"))
                        acierto=true;
                    break;
                case "Buzo":
                    if(text.contains("buzo"))
                        acierto=true;
                    break;
                case "Camisa":
                    if(text.contains("camisa"))
                        acierto=true;
                    break;
                case "Camiseta":
                    if(text.contains("camiseta"))
                        acierto=true;
                    break;
                case "Corbata":
                    if(text.contains("corbata"))
                        acierto=true;
                    break;
                case "Sombrero":
                    if(text.contains("sombrero"))
                        acierto=true;
                    break;
                case "Bermuda":
                    if(text.contains("bermuda"))
                        acierto=true;
                    break;
                case "Medias":
                    if(text.contains("medias"))
                        acierto=true;
                    break;
                case "Falda":
                    if(text.contains("falda"))
                        acierto=true;
                    break;
                case "Chaqueta":
                    if(text.contains("chaqueta"))
                        acierto=true;
                    break;
                case "Saco":
                    if(text.contains("saco"))
                        acierto=true;
                    break;
                case "Calzón":
                    if(text.contains("calzon"))
                        acierto=true;
                    break;
                case "Bóxer":
                    if(text.contains("boxer"))
                        acierto=true;
                    break;
                case "Anillo":
                    if(text.contains("anillo"))
                        acierto=true;
                    break;
                case "Collar":
                    if(text.contains("collar"))
                        acierto=true;
                    break;
                case "Bufanda":
                    if(text.contains("bufanda"))
                        acierto=true;
                    break;
                case "Ruana":
                    if(text.contains("ruana"))
                        acierto=true;
                    break;
                case "Chaleco":
                    if(text.contains("chaleco"))
                        acierto=true;
                    break;
                case "Blusa":
                    if(text.contains("blusa"))
                        acierto=true;
                    break;
                case "Sandalia":
                    if(text.contains("sandalia"))
                        acierto=true;
                    break;
                case "Brasier":
                    if(text.contains("brasier"))
                        acierto=true;
                    break;
                case "Top":
                    if(text.contains("top"))
                        acierto=true;
                    break;
                case "Tanga":
                    if(text.contains("tanga"))
                        acierto=true;
                    break;
                case "Arete":
                    if(text.contains("arete"))
                        acierto=true;
                    break;
                case "Diadema":
                    if(text.contains("diadema"))
                        acierto=true;
                    break;
                case "Moño":
                    if(text.contains("moño"))
                        acierto=true;
                    break;
                case "Pulsera":
                    if(text.contains("pulsera"))
                        acierto=true;
                    break;
                case "Aros":
                    if(text.contains("aros"))
                        acierto=true;
                    break;
                case "Cadena":
                    if(text.contains("cadena"))
                        acierto=true;
                    break;
                case "Correa":
                    if(text.contains("correa"))
                        acierto=true;
                    break;
                case "Gafas":
                    if(text.contains("gafas"))
                        acierto=true;
                    break;
                case "Parque":
                    if(text.contains("parque"))
                        acierto=true;
                    break;
                case "Piscina":
                    if(text.contains("piscina"))
                        acierto=true;
                    break;
                case "Escuela":
                    if(text.contains("escuela"))
                        acierto=true;
                    break;
                case "Colegio":
                    if(text.contains("colegio"))
                        acierto=true;
                    break;
                case "Ciudad":
                    if(text.contains("ciudad"))
                        acierto=true;
                    break;
                case "Pueblo":
                    if(text.contains("pueblo"))
                        acierto=true;
                    break;
                case "Lago":
                    if(text.contains("lago"))
                        acierto=true;
                    break;
                case "Estadio":
                    if(text.contains("estadio"))
                        acierto=true;
                    break;
                case "Iglesia":
                    if(text.contains("iglesia"))
                        acierto=true;
                    break;
                case "Banco":
                    if(text.contains("banco"))
                        acierto=true;
                    break;
                case "Hospital":
                    if(text.contains("hospital"))
                        acierto=true;
                    break;
                case "Oficina":
                    if(text.contains("oficina"))
                        acierto=true;
                    break;
                case "Zanahoria":
                    if(text.contains("zanahoria"))
                        acierto=true;
                    break;
                case "Remolacha":
                    if(text.contains("remolacha"))
                        acierto=true;
                    break;
                case "Repollo":
                    if(text.contains("repollo"))
                        acierto=true;
                    break;
                case "Cebolla":
                    if(text.contains("cebolla"))
                        acierto=true;
                    break;
                case "Brócoli":
                    if(text.contains("brocoli"))
                        acierto=true;
                    break;
                case "Zapallo":
                    if(text.contains("zapallo"))
                        acierto=true;
                    break;
                case "Habichuela":
                    if(text.contains("habichuela"))
                        acierto=true;
                    break;
                case "Coliflor":
                    if(text.contains("coliflor"))
                        acierto=true;
                    break;
                case "Pepino":
                    if(text.contains("pepino"))
                        acierto=true;
                    break;
                case "Espinaca":
                    if(text.contains("espinaca"))
                        acierto=true;
                    break;
                case "Lechuga":
                    if(text.contains("lechuga"))
                        acierto=true;
                    break;
                case "Pimentón":
                    if(text.contains("pimenton"))
                        acierto=true;
                    break;
                case "Ajo":
                    if(text.contains("ajo"))
                        acierto=true;
                    break;
                case "Arveja":
                    if(text.contains("arveja"))
                        acierto=true;
                    break;
                case "Guitarra":
                    if(text.contains("guitarra"))
                        acierto=true;
                    break;
                case "Acordeón":
                    if(text.contains("acordeon"))
                        acierto=true;
                    break;
                case "Piano":
                    if(text.contains("piano"))
                        acierto=true;
                    break;
                case "Violín":
                    if(text.contains("violin"))
                        acierto=true;
                    break;
                case "Batería":
                    if(text.contains("bateria"))
                        acierto=true;
                    break;
                case "Flauta":
                    if(text.contains("flauta"))
                        acierto=true;
                    break;
                case "Empresa":
                    if(text.contains("empresa"))
                        acierto=true;
                    break;
                case "Equipo":
                    if(text.contains("equipo"))
                        acierto=true;
                    break;
            }
        }else {
            acierto=false;
        }
        text=null;

        TextView textView = (TextView) findViewById(R.id.textView_escuchaN1);
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

    }
}