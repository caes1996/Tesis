package app.rrg.pocket.com.tesis;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.Entities.Categoria;
import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Tienda;
import app.rrg.pocket.com.tesis.Entities.Usuario;
import app.rrg.pocket.com.tesis.Utilidades.CategoriaDB;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;
import app.rrg.pocket.com.tesis.Utilidades.PalabraDB;
import app.rrg.pocket.com.tesis.Utilidades.TiendaDB;
import app.rrg.pocket.com.tesis.Utilidades.UsuarioDB;

public class Nivel1Activity  extends AppCompatActivity {

    private NivelDB dbN;
    private TiendaDB dbT;
    private CategoriaDB dbC;
    private PalabraDB dbP;
    private UsuarioDB db;
    Nivel nivel;
    Tienda tienda;
    Usuario usuario;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbN = new NivelDB(Nivel1Activity.this);
        dbP = new PalabraDB(Nivel1Activity.this);
        dbT = new TiendaDB(Nivel1Activity.this);
        db = new UsuarioDB(Nivel1Activity.this);

        tienda = dbT.loadTienda().get(0);

        nivel = new Nivel();
        nivel = dbN.buscarNivel("Nivel 1");

        usuario = db.buscarUsuarios(1);

        dbC = new CategoriaDB(Nivel1Activity.this);

        if(dbC.loadCategoria(nivel.getId()).size() == 0){
            crearCategorias();
        }

        setContentView(R.layout.activity_nivel1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupActionBar();

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutNivel1);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        ArrayList<Categoria> list = dbC.loadCategoria(nivel.getId());
        Log.d("Nivel1Activity -> ", "tamano lista: " + list.size());

        for (int i = 0; i < list.size(); i++){
            Button categoria = new Button(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                categoria.setBackground(this.getDrawable(R.drawable.btn_default));
            }

            if(usuario.getTamano().equals("pequeno")){
                categoria.setTextSize(16);//pequeño 16, mediano 20, grande 24
            }else if(usuario.getTamano().equals("mediano")){
                categoria.setTextSize(20);//pequeño 16, mediano 20, grande 24
            }else{
                categoria.setTextSize(24);//pequeño 16, mediano 20, grande 24
            }

            categoria.setLayoutParams(lp);
            categoria.setText(list.get(i).getNombre());
            categoria.setId(list.get(i).getId());
            categoria.setOnClickListener(new ButtonsOnClickListener(this, categoria));
            layout.addView(categoria);
        }
    }

    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        Button categoria;

        public ButtonsOnClickListener(Context context, Button categoria) {
            this.context = context;
            this.categoria = categoria;
        }

        @Override
        public void onClick(View v)
        {
            /*Lo primero es verificar si las palabras no sehan creado, de ser así se crean,
            de lo contrario se llama la vista de las palabras;
             */
            ArrayList<Palabra> listaPalabras = dbP.loadPalabra(categoria.getId());
            Log.d("Nivel1Activity -> ", "categoria: " + categoria.getText() + ", id-> " + categoria.getId());
            Log.d("Nivel1Activity -> ", "tamano palabras: " + listaPalabras.size());

            if(listaPalabras.size() == 0){
                switch (categoria.getText().toString()){
                    case "Ingeniería":

                        Palabra civil = new Palabra("Civil", 60, 20, 1, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(civil);

                        Palabra sistema = new Palabra("Sistema", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sistema);

                        Palabra desarrollo = new Palabra("Desarrollo", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(desarrollo);

                        Palabra problemas = new Palabra("Problemas", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(problemas);

                        Palabra solucion = new Palabra("Solución", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(solucion);

                        Palabra diseno = new Palabra("Diseño", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(diseno);

                        Palabra analisis = new Palabra("Análisis", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(analisis);

                        Palabra calidad = new Palabra("Calidad", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(calidad);

                        Palabra prototipo = new Palabra("Prototipo", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(prototipo);

                        Palabra ingenio = new Palabra("Ingenio", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ingenio);

                        Palabra idea = new Palabra("Idea", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(idea);

                        Palabra pagina = new Palabra("Página", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pagina);

                        Palabra tecnico = new Palabra("Técnico", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tecnico);

                        Palabra computador = new Palabra("Computador", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(computador);

                        Palabra portatil = new Palabra("Portátil", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(portatil);

                        Palabra redes = new Palabra("Redes", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(redes);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Frutas":

                        Palabra manzana = new Palabra("Manzana", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(manzana);

                        Palabra pera = new Palabra("Pera", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pera);

                        Palabra sandia = new Palabra("Sandía", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sandia);

                        Palabra guayaba = new Palabra("Guayaba", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(guayaba);

                        Palabra maracuya = new Palabra("Maracuyá", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(maracuya);

                        Palabra banano = new Palabra("Banano", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(banano);

                        Palabra granadilla = new Palabra("Granadilla", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(granadilla);

                        Palabra lulo = new Palabra("Lulo", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pera);

                        Palabra mango = new Palabra("Mango", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mango);

                        Palabra mora = new Palabra("Mora", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mora);

                        Palabra fresa = new Palabra("Fresa", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fresa);

                        Palabra aguacate = new Palabra("Aguacate", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aguacate);

                        Palabra zapote = new Palabra("Zapote", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(zapote);

                        Palabra guanabana = new Palabra("Guanábana", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(guanabana);

                        Palabra papaya = new Palabra("Papaya", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(papaya);

                        Palabra pina = new Palabra("Piña", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pina);

                        Palabra tomate = new Palabra("Tomate", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tomate);

                        Palabra uva = new Palabra("Uva", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(uva);

                        Palabra curuba = new Palabra("Curuba", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(curuba);

                        Palabra borojo = new Palabra("Borojó", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(borojo);

                        Palabra carambola = new Palabra("Carambola", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(carambola);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Partes del cuerpo":

                        Palabra dedo = new Palabra("Dedo", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(dedo);

                        Palabra diente = new Palabra("Diente", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(diente);

                        Palabra labios = new Palabra("Labios", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(labios);

                        Palabra nariz = new Palabra("Nariz", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nariz);

                        Palabra cabello = new Palabra("Cabello", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cabello);

                        Palabra una = new Palabra("Uña", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(una);

                        Palabra cejas = new Palabra("Cejas", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cejas);

                        Palabra barba = new Palabra("Barba", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(barba);

                        Palabra tobillo = new Palabra("Tobillo", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tobillo);

                        Palabra talon = new Palabra("Talón", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(talon);

                        Palabra pestana = new Palabra("Pestaña", 90 , 0, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pestana);

                        Palabra mano = new Palabra("Mano", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mano);

                        Palabra pie = new Palabra("Pie", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pie);

                        Palabra rodilla = new Palabra("Rodilla", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(rodilla);

                        Palabra codo = new Palabra("Codo", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(codo);

                        Palabra estomago = new Palabra("Estómago", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estomago);

                        Palabra cabeza = new Palabra("Cabeza", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cabeza);

                        Palabra espalda = new Palabra("Espalda", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(espalda);

                        Palabra pierna = new Palabra("Pierna", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pierna);

                        Palabra hombro = new Palabra("Hombro", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hombro);

                        Palabra pecho = new Palabra("Pecho", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pecho);

                        Palabra brazo = new Palabra("Brazo", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(brazo);

                        Palabra cadera = new Palabra("Cadera", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cadera);

                        Palabra orejas = new Palabra("Orejas", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(orejas);

                        Palabra lengua = new Palabra("Lengua", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lengua);

                        Palabra ojos = new Palabra("Ojos", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ojos);

                        Palabra cuello = new Palabra("Cuello", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cuello);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Deporte":

                        Palabra sacrificio = new Palabra("Sacrificio", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sacrificio);

                        Palabra deporte = new Palabra("Deporte", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(deporte);

                        Palabra dedicacion = new Palabra("Dedicación", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(dedicacion);

                        Palabra disciplina = new Palabra("Disciplina", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(disciplina);

                        Palabra esfuerzo = new Palabra("Esfuerzo", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(esfuerzo);

                        Palabra pesas = new Palabra("Pesas", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pesas);

                        Palabra gimnasio = new Palabra("Gimnasio", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gimnasio);

                        Palabra entrenar = new Palabra("Entrenar", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(entrenar);

                        Palabra futbol = new Palabra("Fútbol", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(futbol);

                        Palabra lateral = new Palabra("Lateral", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lateral);

                        Palabra central = new Palabra("Central", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(central);

                        Palabra volante = new Palabra("Volante", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(volante);

                        Palabra delantero = new Palabra("Delantero", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(delantero);

                        Palabra tecnicoD = new Palabra("Técnico", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tecnicoD);

                        Palabra cuerpo = new Palabra("Cuerpo", 60 , 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cuerpo);

                        Palabra arbitro = new Palabra("Árbitro", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arbitro);

                        Palabra natacion = new Palabra("Natación", 90 , 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(natacion);

                        Palabra mariposa = new Palabra("Mariposa", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mariposa);

                        Palabra clavado = new Palabra("Clavado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(clavado);

                        Palabra olimpico = new Palabra("Olímpico", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(olimpico);

                        Palabra ciclismo = new Palabra("Ciclismo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ciclismo);

                        Palabra patinaje = new Palabra("Patinaje", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(patinaje);

                        Palabra carrera = new Palabra("Carrera", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(carrera);

                        Palabra ajedrez = new Palabra("Ajedrez", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ajedrez);

                        Palabra tenis = new Palabra("Tenis", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tenis);

                        Palabra fuerza = new Palabra("Fuerza", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fuerza);

                        Palabra pasion = new Palabra("Pasión", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pasion);

                        Palabra deportista = new Palabra("Deportista", 120 , 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(deportista);

                        Palabra motivacion = new Palabra("Motivación", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(motivacion);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Medios de  transporte":

                        Palabra bicicleta = new Palabra("Bicicleta", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bicicleta);

                        Palabra carro = new Palabra("Carro", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(carro);

                        Palabra tren = new Palabra("Tren", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tren);

                        Palabra avion = new Palabra("Avión", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(avion);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Medicina":

                        Palabra fractura = new Palabra("Fractura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fractura);

                        Palabra huesos = new Palabra("Huesos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(huesos);

                        Palabra tendones = new Palabra("Tendones", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tendones);

                        Palabra nervio = new Palabra("Nervio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nervio);

                        Palabra musculo = new Palabra("Músculo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(musculo);

                        Palabra sutura = new Palabra("Sutura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sutura);

                        Palabra tijeras = new Palabra("Tijeras", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tijeras);

                        Palabra gasas = new Palabra("Gasas", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gasas);

                        Palabra sonda = new Palabra("Sonda", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sonda);

                        Palabra defensas = new Palabra("Defensas", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(defensas);

                        Palabra muestra = new Palabra("Muestra", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(muestra);

                        Palabra virus = new Palabra("Virus", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(virus);

                        Palabra hongos = new Palabra("Hongos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hongos);

                        Palabra embarazo = new Palabra("Embarazo", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(embarazo);

                        Palabra nutricion = new Palabra("Nutrición", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nutricion);

                        Palabra salud = new Palabra("Salud", 600, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(salud);

                        Palabra medicina = new Palabra("Medicina", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(medicina);

                        Palabra doctor = new Palabra("Doctor", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(doctor);

                        Palabra paciente = new Palabra("Paciente", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(paciente);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Verduras":

                        Palabra zanahoria = new Palabra("Zanahoria", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(zanahoria);

                        Palabra remolacha = new Palabra("Remolacha", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(remolacha);

                        Palabra repollo = new Palabra("Repollo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(repollo);

                        Palabra cebolla = new Palabra("Cebolla", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cebolla);

                        Palabra brocoli = new Palabra("Brócoli", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(brocoli);

                        Palabra zapallo = new Palabra("Zapallo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(zapallo);

                        Palabra habichuela = new Palabra("Habichuela", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(habichuela);

                        Palabra coliflor = new Palabra("Coliflor", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(coliflor);

                        Palabra pepino = new Palabra("Pepino", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pepino);

                        Palabra espinaca = new Palabra("Espinaca", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(espinaca);

                        Palabra lechuga = new Palabra("Lechuga", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lechuga);

                        Palabra pimenton = new Palabra("Pimentón", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pimenton);

                        Palabra ajo = new Palabra("Ajo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ajo);

                        Palabra arveja = new Palabra("Arveja", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arveja);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Días":

                        Palabra lunes = new Palabra("Lunes", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lunes);

                        Palabra martes = new Palabra("Martes", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(martes);

                        Palabra miercoles = new Palabra("Miércoles", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(miercoles);

                        Palabra jueves = new Palabra("Jueves", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(jueves);

                        Palabra viernes = new Palabra("Viernes", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(viernes);

                        Palabra sabado = new Palabra("Sábado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sabado);

                        Palabra domingo = new Palabra("Domingo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(domingo);

                        Palabra manana = new Palabra("Mañana", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(manana);

                        Palabra hoy = new Palabra("Hoy", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hoy);

                        Palabra ayer = new Palabra("Ayer", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ayer);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Números":

                        Palabra cero = new Palabra("Cero", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cero);

                        Palabra uno = new Palabra("Uno", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(uno);

                        Palabra dos = new Palabra("Dos", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(dos);

                        Palabra tres = new Palabra("Tres", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tres);

                        Palabra cuatro = new Palabra("Cuatro", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cuatro);

                        Palabra cinco = new Palabra("Cinco", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cinco);

                        Palabra seis = new Palabra("Seis", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(seis);

                        Palabra siete = new Palabra("Siete", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(siete);

                        Palabra ocho = new Palabra("Ocho", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ocho);

                        Palabra nueve = new Palabra("Nueve", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nueve);

                        Palabra diez = new Palabra("Diez", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(diez);

                        Palabra once = new Palabra("Once", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(once);

                        Palabra doce = new Palabra("Doce", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(doce);

                        Palabra trece = new Palabra("Trece", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(trece);

                        Palabra catorce = new Palabra("Catorce", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(catorce);

                        Palabra quince = new Palabra("Quince", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(quince);

                        Palabra veinte = new Palabra("Veinte", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(veinte);

                        Palabra treinta = new Palabra("Treinta", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(treinta);

                        Palabra cuarenta = new Palabra("Cuarenta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cuarenta);

                        Palabra cincuenta = new Palabra("Cincuenta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cincuenta);

                        Palabra sesenta = new Palabra("Sesenta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sesenta);

                        Palabra setenta = new Palabra("Setenta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(setenta);

                        Palabra ochenta = new Palabra("Ochenta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ochenta);

                        Palabra noventa = new Palabra("Noventa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(noventa);

                        Palabra cien = new Palabra("Cien", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cien);

                        Palabra mil = new Palabra("Mil", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mil);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Partes de la casa":

                        Palabra sala = new Palabra("Sala", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sala);

                        Palabra bano = new Palabra("Baño", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bano);

                        Palabra habitacion = new Palabra("Habitación", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(habitacion);

                        Palabra cocina = new Palabra("Cocina", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cocina);

                        Palabra patio = new Palabra("Patio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(patio);

                        Palabra anden = new Palabra("Andén", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(anden);

                        Palabra terraza = new Palabra("Terraza", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(terraza);

                        Palabra balcon = new Palabra("balcón", 90, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(balcon);

                        Palabra comedor = new Palabra("Comedor", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(comedor);

                        Palabra ventana = new Palabra("Ventana", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ventana);

                        Palabra puerta = new Palabra("Puerta", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(puerta);

                        Palabra cajon = new Palabra("Cajón", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cajon);

                        Palabra armario = new Palabra("Armario", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(armario);

                        Palabra lavadero = new Palabra("Lavadero", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lavadero);

                        Palabra lavamanos = new Palabra("Lavamanos", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lavamanos);

                        Palabra sanitario = new Palabra("Sanitario", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sanitario);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Meses":

                        Palabra enero = new Palabra("Enero", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(enero);

                        Palabra febrero = new Palabra("Febrero", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(febrero);

                        Palabra marzo = new Palabra("Marzo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(marzo);

                        Palabra abril = new Palabra("Abril", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(abril);

                        Palabra mayo = new Palabra("Mayo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mayo);

                        Palabra junio = new Palabra("Junio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(junio);

                        Palabra julio = new Palabra("Julio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(julio);

                        Palabra agosto = new Palabra("Agosto", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(agosto);

                        Palabra septiembre = new Palabra("Septiembre", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(septiembre);

                        Palabra octubre = new Palabra("Octubre", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(octubre);

                        Palabra noviembre = new Palabra("Noviembre", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(noviembre);

                        Palabra diciembre = new Palabra("Diciembre", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(diciembre);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Instrumentos musicales":

                        Palabra guitarra = new Palabra("Guitarra", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(guitarra);

                        Palabra acordeon = new Palabra("Acordeón", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(acordeon);

                        Palabra piano = new Palabra("Piano", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(piano);

                        Palabra violin = new Palabra("Violín", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(violin);

                        Palabra bateria = new Palabra("Batería", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bateria);

                        Palabra flauta = new Palabra("Flauta", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(flauta);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Contaduría":

                        Palabra balance = new Palabra("Balance", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(balance);

                        Palabra costo = new Palabra("Costo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(costo);

                        Palabra gasto = new Palabra("Gasto", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gasto);

                        Palabra iva = new Palabra("Iva", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(iva);

                        Palabra factura = new Palabra("Factura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(factura);

                        Palabra norma = new Palabra("Norma", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(norma);

                        Palabra impuesto = new Palabra("Impuesto", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(impuesto);

                        Palabra caja = new Palabra("Caja", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(caja);

                        Palabra flujo = new Palabra("Flujo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(flujo);

                        Palabra saldos = new Palabra("Saldos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(saldos);

                        Palabra informacion = new Palabra("Información", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(informacion);

                        Palabra inventario = new Palabra("Inventario", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(inventario);

                        Palabra proveedor = new Palabra("Proveedor", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(proveedor);

                        Palabra cartera = new Palabra("Cartera", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cartera);

                        Palabra castigo = new Palabra("Castigo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(castigo);

                        Palabra utilidad = new Palabra("Utilidad", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(utilidad);

                        Palabra perdida = new Palabra("Pérdida", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(perdida);

                        Palabra agenda = new Palabra("Agenda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(agenda);

                        Palabra contador = new Palabra("Contador", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(contador);

                        Palabra auxiliar = new Palabra("Auxiliar", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(auxiliar);

                        Palabra contable = new Palabra("Contable", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(contable);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Economía":

                        Palabra ahorro = new Palabra("Ahorro", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ahorro);

                        Palabra alquiler = new Palabra("Alquiler", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(alquiler);

                        Palabra balanza = new Palabra("Balanza", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(balanza);

                        Palabra beneficio = new Palabra("Beneficio", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(beneficio);

                        Palabra bolsa = new Palabra("Bolsa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bolsa);

                        Palabra cambio = new Palabra("Cambio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cambio);

                        Palabra capital = new Palabra("Capital", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(capital);

                        Palabra competencia = new Palabra("Competencia", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(competencia);

                        Palabra consumo = new Palabra("Consumo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(consumo);

                        Palabra descuento = new Palabra("Descuento", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(descuento);

                        Palabra estado = new Palabra("Estado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estado);

                        Palabra giro = new Palabra("Giro", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(giro);

                        Palabra interes = new Palabra("Interés", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(interes);

                        Palabra mercado = new Palabra("Mercado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mercado);

                        Palabra moneda = new Palabra("Moneda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(moneda);

                        Palabra oferta = new Palabra("Oferta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(oferta);

                        Palabra demanda = new Palabra("Demanda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(demanda);

                        Palabra poblacion = new Palabra("Población", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(poblacion);

                        Palabra pobreza = new Palabra("Pobreza", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pobreza);

                        Palabra salario = new Palabra("Salario", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(salario);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Sitios":

                        Palabra parque = new Palabra("Parque", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(parque);

                        Palabra piscina = new Palabra("Piscina", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(piscina);

                        Palabra escuela = new Palabra("Escuela", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(escuela);

                        Palabra colegio = new Palabra("Colegio", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(colegio);

                        Palabra ciudad = new Palabra("Ciudad", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ciudad);

                        Palabra pueblo = new Palabra("Pueblo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pueblo);

                        Palabra vereda = new Palabra("Vereda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(vereda);

                        Palabra finca = new Palabra("Finca", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(finca);

                        Palabra lago = new Palabra("Lago", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lago);

                        Palabra estadio = new Palabra("Estadio", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estadio);

                        Palabra iglesia = new Palabra("Iglesia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(iglesia);

                        Palabra banco = new Palabra("Banco", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(banco);

                        Palabra hospital = new Palabra("Hospital", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hospital);

                        Palabra oficina = new Palabra("Oficina", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(oficina);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Implementos aseo personal":

                        Palabra cepillo = new Palabra("Cepillo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cepillo);

                        Palabra jabon = new Palabra("Jabón", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(jabon);

                        Palabra shampoo = new Palabra("Shampoo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(shampoo);

                        Palabra toalla = new Palabra("Toalla", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(toalla);

                        Palabra peineta = new Palabra("Peineta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(peineta);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Diseño":

                        Palabra folleto = new Palabra("Folleto", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(folleto);

                        Palabra montaje = new Palabra("Montaje", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(montaje);

                        Palabra icono = new Palabra("Ícono", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(icono);

                        Palabra lienzo = new Palabra("Lienzo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lienzo);

                        Palabra mapa = new Palabra("Mapa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mapa);

                        Palabra marca = new Palabra("Marca", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(marca);

                        Palabra publicidad = new Palabra("Publicidad", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(publicidad);

                        Palabra tinta = new Palabra("Tinta", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tinta);

                        Palabra simbolo = new Palabra("Símbolo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(simbolo);

                        Palabra sombra = new Palabra("Sombra", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sombra);

                        Palabra textura = new Palabra("Textura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(textura);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Saludos":

                        Palabra hola = new Palabra("Hola", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hola);

                        Palabra adios = new Palabra("Adiós", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(adios);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Implementos aseo general":

                        Palabra escoba = new Palabra("Escoba", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(escoba);

                        Palabra trapeador = new Palabra("Trapeador", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(trapeador);

                        Palabra recogedor = new Palabra("Recogedor", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(recogedor);

                        Palabra balde = new Palabra("Balde", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(balde);

                        Palabra lavadora = new Palabra("Lavadora", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lavadora);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Comida chatarra":

                        Palabra hamburguesa = new Palabra("Hamburguesa", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(hamburguesa);

                        Palabra salchipapa = new Palabra("Salchipapa", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(salchipapa);

                        Palabra pizza = new Palabra("Pizza", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pizza);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Arte":

                        Palabra acrilico = new Palabra("Acrílico", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(acrilico);

                        Palabra acuarela = new Palabra("Acuarela", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(acuarela);

                        Palabra arte = new Palabra("Arte", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arte);

                        Palabra relieve = new Palabra("Relieve", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(relieve);

                        Palabra boceto = new Palabra("Boceto", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(boceto);

                        Palabra cera = new Palabra("Cera", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cera);

                        Palabra clasico = new Palabra("Clásico", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(clasico);

                        Palabra dibujo = new Palabra("Dibujo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(dibujo);

                        Palabra estatua = new Palabra("Estatua", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estatua);

                        Palabra imagen = new Palabra("Imagen", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(imagen);

                        Palabra lapiz = new Palabra("Lápiz", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lapiz);

                        Palabra marfil = new Palabra("Marfil", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(marfil);

                        Palabra mosaico = new Palabra("Mosaico", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mosaico);

                        Palabra mural = new Palabra("Mural", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mural);

                        Palabra plastilina = new Palabra("Plastilina", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(plastilina);

                        Palabra yeso = new Palabra("Yeso", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(yeso);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Policía":

                        Palabra arma = new Palabra("Arma", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arma);

                        Palabra uniforme = new Palabra("Uniforme", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(uniforme);

                        Palabra crimen = new Palabra("Crimen", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(crimen);

                        Palabra oficial = new Palabra("Oficial", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(oficial);

                        Palabra coronel = new Palabra("Coronel", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(coronel);

                        Palabra comandante = new Palabra("Comandante", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(comandante);

                        Palabra sargento = new Palabra("Sargento", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sargento);

                        Palabra captura = new Palabra("Captura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(captura);

                        Palabra riesgo = new Palabra("Riesgo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(riesgo);

                        Palabra estrategia = new Palabra("Estrategia", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estrategia);

                        Palabra anticipar = new Palabra("Anticipar", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(anticipar);

                        Palabra cargos = new Palabra("Cargos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cargos);

                        Palabra archivar = new Palabra("Archivar", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(archivar);

                        Palabra autoridad = new Palabra("Autoridad", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(autoridad);

                        Palabra cadena = new Palabra("Cadena", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cadena);

                        Palabra mando = new Palabra("Mando", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mando);

                        Palabra entidad = new Palabra("Entidad", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(entidad);

                        Palabra proceso = new Palabra("Proceso", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(proceso);

                        Palabra causa = new Palabra("Causa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(causa);

                        Palabra documental = new Palabra("Documental", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(documental);

                        Palabra comite = new Palabra("Comité", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(comite);

                        Palabra confidencial = new Palabra("Confidencial", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(confidencial);

                        Palabra consecuencia = new Palabra("Consecuencia", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(consecuencia);

                        Palabra control = new Palabra("Control", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(control);

                        Palabra cultura = new Palabra("Cultura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cultura);

                        Palabra equipo = new Palabra("Equipo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(equipo);

                        Palabra crisis = new Palabra("Crisis", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(crisis);

                        Palabra modelo = new Palabra("Modelo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(modelo);

                        Palabra nivel = new Palabra("Nivel", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nivel);

                        Palabra policia = new Palabra("Policía", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(policia);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Comunicación":

                        Palabra agendaC = new Palabra("Agenda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(agendaC);

                        Palabra canal = new Palabra("Canal", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(canal);

                        Palabra prensa = new Palabra("Prensa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(prensa);

                        Palabra codigo = new Palabra("Código", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(codigo);

                        Palabra identidad = new Palabra("Identidad", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(identidad);

                        Palabra lenguaje = new Palabra("Lenguaje", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lenguaje);

                        Palabra nombre = new Palabra("Nombre", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(nombre);

                        Palabra mensaje = new Palabra("Mensaje", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mensaje);

                        Palabra novedad = new Palabra("Novedad", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(novedad);

                        Palabra opinion = new Palabra("Opinión", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(opinion);

                        Palabra reportaje = new Palabra("Reportaje", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(reportaje);

                        Palabra seccion = new Palabra("Sección", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(seccion);

                        Palabra soporte = new Palabra("Soporte", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(soporte);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Administración":

                        Palabra medios = new Palabra("Medios", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(medios);

                        Palabra gerencia = new Palabra("Gerencia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gerencia);

                        Palabra empresa = new Palabra("Empresa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(empresa);

                        Palabra direccion = new Palabra("Dirección", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(direccion);

                        Palabra meta = new Palabra("Meta", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(meta);

                        Palabra programa = new Palabra("Programa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(programa);

                        Palabra datos = new Palabra("Datos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(datos);

                        Palabra planeacion = new Palabra("Planeación", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(planeacion);

                        Palabra vision = new Palabra("Visión", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(vision);

                        Palabra estrategiaA = new Palabra("Estrategia", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estrategiaA);

                        Palabra riesgos = new Palabra("Riesgos", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(riesgos);

                        Palabra cambioA = new Palabra("Cambio", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cambioA);

                        Palabra equipoA = new Palabra("Equipo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(equipoA);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Platos":

                        Palabra sancocho = new Palabra("Sancocho", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sancocho);

                        Palabra ajiaco = new Palabra("Ajiaco", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ajiaco);

                        Palabra frijoles = new Palabra("Frijoles", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(frijoles);

                        Palabra lentejas = new Palabra("Lentejas", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lentejas);

                        Palabra arroz = new Palabra("Arroz", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arroz);

                        Palabra chuleta = new Palabra("Chuleta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(chuleta);

                        Palabra pollo = new Palabra("Pollo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pollo);

                        Palabra higado = new Palabra("Hígado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(higado);

                        Palabra cerdo = new Palabra("Cerdo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cerdo);

                        Palabra costilla = new Palabra("Costilla", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(costilla);

                        Palabra pescado = new Palabra("Pescado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pescado);

                        Palabra patacon = new Palabra("Patacón", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(patacon);

                        Palabra pastel = new Palabra("Pastel", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pastel);

                        Palabra arepa = new Palabra("Arepa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arepa);

                        Palabra papa = new Palabra("Papa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(papa);

                        Palabra tamal = new Palabra("Tamal", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tamal);

                        Palabra sopa = new Palabra("Sopa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sopa);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Prendas de vestir":

                        Palabra zapatos = new Palabra("Zapatos", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(zapatos);

                        Palabra pantalon = new Palabra("Pantalón", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pantalon);

                        Palabra buzo = new Palabra("Buzo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(buzo);

                        Palabra camisa = new Palabra("Camisa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(camisa);

                        Palabra camiseta = new Palabra("Camiseta", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(camiseta);

                        Palabra corbata = new Palabra("Corbata", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(corbata);

                        Palabra sombrero = new Palabra("Sombrero", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sombrero);

                        Palabra bermuda = new Palabra("Bermuda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bermuda);

                        Palabra medias = new Palabra("Medias", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(medias);

                        Palabra falda = new Palabra("Falda", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(falda);

                        Palabra chaqueta = new Palabra("Chaqueta", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(chaqueta);

                        Palabra saco = new Palabra("Saco", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(saco);

                        Palabra calzon = new Palabra("Calzón", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(calzon);

                        Palabra boxer = new Palabra("Bóxer", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(boxer);

                        Palabra anillo = new Palabra("Anillo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(anillo);

                        Palabra collar = new Palabra("Collar", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(collar);

                        Palabra bufanda = new Palabra("Bufanda", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bufanda);

                        Palabra ruana = new Palabra("Ruana", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ruana);

                        Palabra chaleco = new Palabra("Chaleco", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(chaleco);

                        Palabra blusa = new Palabra("Blusa", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(blusa);

                        Palabra sandalia = new Palabra("Sandalia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(sandalia);

                        Palabra brasier = new Palabra("Brasier", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(brasier);

                        Palabra top = new Palabra("Top", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(top);

                        Palabra tanga = new Palabra("Tanga", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tanga);

                        Palabra arete = new Palabra("Arete", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(arete);

                        Palabra diadema = new Palabra("Diadema", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(diadema);

                        Palabra mono = new Palabra("Moño", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(mono);

                        Palabra pulsera = new Palabra("Pulsera", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(pulsera);

                        Palabra aros = new Palabra("Aros", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aros);

                        Palabra cadenaP = new Palabra("CadenaP", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cadenaP);

                        Palabra correa = new Palabra("Correa", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(correa);

                        Palabra gafas = new Palabra("Gafas", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gafas);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Derecho":

                        Palabra ley = new Palabra("Ley", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ley);

                        Palabra juez = new Palabra("Juez", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(juez);

                        Palabra abogado = new Palabra("Abogado", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(abogado);

                        Palabra jurado = new Palabra("Jurado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(jurado);

                        Palabra cita = new Palabra("Cita", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cita);

                        Palabra fiscalía = new Palabra("Fiscalía", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fiscalía);

                        Palabra tutela = new Palabra("Tutela", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tutela);

                        Palabra normaD = new Palabra("Norma", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(normaD);

                        Palabra constitucion = new Palabra("Constitución", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(constitucion);

                        Palabra derecho = new Palabra("Derecho", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(derecho);

                        Palabra cobro = new Palabra("Cobro", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cobro);

                        Palabra bienes = new Palabra("Bienes", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(bienes);

                        Palabra herencia = new Palabra("Herencia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(herencia);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Colores":

                        Palabra rojo = new Palabra("Rojo", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(rojo);

                        Palabra verde = new Palabra("Verde", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(verde);

                        Palabra azul = new Palabra("Azul", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(azul);

                        Palabra amarillo = new Palabra("Amarillo", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(amarillo);

                        Palabra negro = new Palabra("Negro", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(negro);

                        Palabra blanco = new Palabra("Blanco", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(blanco);

                        Palabra gris = new Palabra("Gris", 20, 10, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(gris);

                        Palabra cafe = new Palabra("Café", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cafe);

                        Palabra morado = new Palabra("Morado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(morado);

                        Palabra rosado = new Palabra("Rosado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(rosado);

                        Palabra salmon = new Palabra("Salmón", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(salmon);

                        Palabra durazno = new Palabra("Durazno", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(durazno);

                        Palabra fucsia = new Palabra("Fucsia", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fucsia);

                        Palabra plateado = new Palabra("Plateado", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(plateado);

                        Palabra dorado = new Palabra("Dorado", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(dorado);

                        Palabra violeta = new Palabra("Violeta", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(violeta);

                        Palabra purpura = new Palabra("Púrpura", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(purpura);

                        launchIntent(context, categoria.getId());

                        break;
                    case "Docencia":

                        Palabra utiles = new Palabra("Utiles", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(utiles);

                        Palabra taller = new Palabra("Taller", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(taller);

                        Palabra proyecto = new Palabra("Proyecto", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(proyecto);

                        Palabra tarea = new Palabra("Tarea", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tarea);

                        Palabra salon = new Palabra("Salón", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(salon);

                        Palabra educacion = new Palabra("Educación", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(educacion);

                        Palabra disciplinaD = new Palabra("Disciplina", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(disciplinaD);

                        Palabra actitud = new Palabra("Actitud", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(actitud);

                        Palabra aptitud = new Palabra("Aptitud", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(aptitud);

                        Palabra ensenanza = new Palabra("Enseñanza", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(ensenanza);

                        Palabra familia = new Palabra("Familia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(familia);

                        Palabra cuaderno = new Palabra("Cuaderno", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(cuaderno);

                        Palabra vacaciones = new Palabra("Vacaciones", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(vacaciones);

                        Palabra materia = new Palabra("Materia", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(materia);

                        Palabra recreo = new Palabra("Recreo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(recreo);

                        Palabra deporteD = new Palabra("Deporte", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(deporteD);

                        Palabra amistad = new Palabra("Amistad", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(amistad);

                        Palabra rector = new Palabra("Rector", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(rector);

                        Palabra profesor = new Palabra("Profesor", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(profesor);

                        Palabra maestro = new Palabra("Maestro", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(maestro);

                        Palabra actividad = new Palabra("Actividad", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(actividad);

                        Palabra notas = new Palabra("Notas", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(notas);

                        Palabra atencion = new Palabra("Atención", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(atencion);

                        Palabra tablero = new Palabra("Tablero", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(tablero);

                        Palabra uniformeD = new Palabra("Uniforme", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(uniformeD);

                        Palabra lapizD = new Palabra("Lápiz", 60, 20, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(lapizD);

                        Palabra borrador = new Palabra("Borrador", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(borrador);

                        Palabra docente = new Palabra("Docente", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(docente);

                        Palabra estudiante = new Palabra("Estudiante", 120, 40, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(estudiante);

                        Palabra etica = new Palabra("Ética", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(etica);

                        Palabra fisica = new Palabra("Física", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(fisica);

                        Palabra quimica = new Palabra("Química", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(quimica);

                        Palabra calculo = new Palabra("Cálculo", 90, 30, 0, tienda.getId(), categoria.getId());
                        dbP.insertPalabra(calculo);

                        launchIntent(context, categoria.getId());

                        break;
                }
            }

            launchIntent(context, categoria.getId());
        }

    };

    public void launchIntent(Context context, int idCategoria){
        Intent i = new Intent(context, PalabraNivel1.class);
        i.putExtra("idCategoria", "" + idCategoria);
        startActivity(i);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void crearCategorias(){
        Log.d("Nivel1Activity -> ", "agregando categorias");

        Categoria administracion = new Categoria();
        administracion.setNombre("Administración");
        administracion.setBloqueado(0);
        administracion.setNivel(nivel.getId());
        dbC.insertCategoria(administracion);

        Categoria arte = new Categoria();
        arte.setNombre("Arte");
        arte.setBloqueado(0);
        arte.setNivel(nivel.getId());
        dbC.insertCategoria(arte);

        Categoria colores = new Categoria();
        colores.setNombre("Colores");
        colores.setBloqueado(0);
        colores.setNivel(nivel.getId());
        dbC.insertCategoria(colores);

        Categoria comidaChatarra = new Categoria();
        comidaChatarra.setNombre("Comida chatarra");
        comidaChatarra.setBloqueado(0);
        comidaChatarra.setNivel(nivel.getId());
        dbC.insertCategoria(comidaChatarra);

        Categoria comunicacion = new Categoria();
        comunicacion.setNombre("Comunicación");
        comunicacion.setBloqueado(0);
        comunicacion.setNivel(nivel.getId());
        dbC.insertCategoria(comunicacion);

        Categoria contaduria = new Categoria();
        contaduria.setNombre("Contaduría");
        contaduria.setBloqueado(0);
        contaduria.setNivel(nivel.getId());
        dbC.insertCategoria(contaduria);

        Categoria deporte = new Categoria();
        deporte.setNombre("Deporte");
        deporte.setBloqueado(0);
        deporte.setNivel(nivel.getId());
        dbC.insertCategoria(deporte);

        Categoria derecho = new Categoria();
        derecho.setNombre("Derecho");
        derecho.setBloqueado(0);
        derecho.setNivel(nivel.getId());
        dbC.insertCategoria(derecho);

        Categoria dias = new Categoria();
        dias.setNombre("Días");
        dias.setBloqueado(0);
        dias.setNivel(nivel.getId());
        dbC.insertCategoria(dias);

        Categoria diseno = new Categoria();
        diseno.setNombre("Diseño");
        diseno.setBloqueado(0);
        diseno.setNivel(nivel.getId());
        dbC.insertCategoria(diseno);

        Categoria docencia = new Categoria();
        docencia.setNombre("Docencia");
        docencia.setBloqueado(0);
        docencia.setNivel(nivel.getId());
        dbC.insertCategoria(docencia);

        Categoria economia = new Categoria();
        economia.setNombre("Economía");
        economia.setBloqueado(0);
        economia.setNivel(nivel.getId());
        dbC.insertCategoria(economia);

        Categoria frutas = new Categoria();
        frutas.setNombre("Frutas");
        frutas.setBloqueado(0);
        frutas.setNivel(nivel.getId());
        dbC.insertCategoria(frutas);

        Categoria implementosAseoGeneral = new Categoria();
        implementosAseoGeneral.setNombre("Implementos aseo general");
        implementosAseoGeneral.setBloqueado(0);
        implementosAseoGeneral.setNivel(nivel.getId());
        dbC.insertCategoria(implementosAseoGeneral);

        Categoria implementosAseoPersonal = new Categoria();
        implementosAseoPersonal.setNombre("Implementos aseo personal");
        implementosAseoPersonal.setBloqueado(0);
        implementosAseoPersonal.setNivel(nivel.getId());
        dbC.insertCategoria(implementosAseoPersonal);

        Categoria ingenieria = new Categoria();
        ingenieria.setNombre("Ingeniería");
        ingenieria.setBloqueado(0);
        ingenieria.setNivel(nivel.getId());
        dbC.insertCategoria(ingenieria);

        Categoria medicina = new Categoria();
        medicina.setNombre("Medicina");
        medicina.setBloqueado(0);
        medicina.setNivel(nivel.getId());
        dbC.insertCategoria(medicina);

        Categoria mediosTransporte = new Categoria();
        mediosTransporte.setNombre("Medios de  transporte");
        mediosTransporte.setBloqueado(0);
        mediosTransporte.setNivel(nivel.getId());
        dbC.insertCategoria(mediosTransporte);

        Categoria meses = new Categoria();
        meses.setNombre("Meses");
        meses.setBloqueado(0);
        meses.setNivel(nivel.getId());
        dbC.insertCategoria(meses);

        Categoria numeros = new Categoria();
        numeros.setNombre("Números");
        numeros.setBloqueado(0);
        numeros.setNivel(nivel.getId());
        dbC.insertCategoria(numeros);

        Categoria partesCasa = new Categoria();
        partesCasa.setNombre("Partes de la casa");
        partesCasa.setBloqueado(0);
        partesCasa.setNivel(nivel.getId());
        dbC.insertCategoria(partesCasa);

        Categoria partesCuerpo = new Categoria();
        partesCuerpo.setNombre("Partes del cuerpo");
        partesCuerpo.setBloqueado(0);
        partesCuerpo.setNivel(nivel.getId());
        dbC.insertCategoria(partesCuerpo);

        Categoria platos = new Categoria();
        platos.setNombre("Platos");
        platos.setBloqueado(0);
        platos.setNivel(nivel.getId());
        dbC.insertCategoria(platos);

        Categoria policia = new Categoria();
        policia.setNombre("Policía");
        policia.setBloqueado(0);
        policia.setNivel(nivel.getId());
        dbC.insertCategoria(policia);

        Categoria prendasVestir = new Categoria();
        prendasVestir.setNombre("Prendas de vestir");
        prendasVestir.setBloqueado(0);
        prendasVestir.setNivel(nivel.getId());
        dbC.insertCategoria(prendasVestir);

        Categoria saludos = new Categoria();
        saludos.setNombre("Saludos");
        saludos.setBloqueado(0);
        saludos.setNivel(nivel.getId());
        dbC.insertCategoria(saludos);

        Categoria sitios = new Categoria();
        sitios.setNombre("Sitios");
        sitios.setBloqueado(0);
        sitios.setNivel(nivel.getId());
        dbC.insertCategoria(sitios);

        Categoria verduras = new Categoria();
        verduras.setNombre("Verduras");
        verduras.setBloqueado(0);
        verduras.setNivel(nivel.getId());
        dbC.insertCategoria(verduras);

        Categoria instrumentosMusicales = new Categoria();
        instrumentosMusicales.setNombre("Instrumentos musicales");
        instrumentosMusicales.setBloqueado(0);
        instrumentosMusicales.setNivel(nivel.getId());
        dbC.insertCategoria(instrumentosMusicales);
    }
}
