package app.rrg.pocket.com.tesis.Utilidades;

public class Utilidades {
    //General
    public static final String DB_NAME = "pocketDB.db";
    public static final int DB_VERSION = 1;

    // Constantes tabla usuarios
    public static final String TABLA_USUARIO = "tabla_usuarios";
    public static final String ID_USUARIO = "id";
    public static final String NOMBRE_USUARIO = "nombre";
    public static final String EDAD_USUARIO = "edad";
    public static final String PUNTAJE_USUARIO = "puntaje";
    public static final String TAMANO_USUARIO = "tamano";

    public static final String CREAR_USUARIO =
            "CREATE TABLE "+TABLA_USUARIO+" ("+
                    ID_USUARIO+" INTEGER PRIMARY KEY AUTOINCREMENT,"    +
                    NOMBRE_USUARIO      +" TEXT,"   +
                    EDAD_USUARIO      +" TEXT,"   +
                    PUNTAJE_USUARIO      +" TEXT,"   +
                    TAMANO_USUARIO  +" TEXT);"  ;

    // Constantes tabla tienda
    public static final String TABLA_TIENDA = "tabla_tienda";
    public static final String ID_TIENDA = "id";
    public static final String USUARIO_TIENDA = "usuario";

    public static final String CREAR_TIENDA =
            "CREATE TABLE "+TABLA_TIENDA+"("+
                    ID_TIENDA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    USUARIO_TIENDA + " INTEGER,FOREIGN KEY("+USUARIO_TIENDA+") REFERENCES tabla_usuarios("+ID_USUARIO+"));";

    // Constantes tabla vocabulario
    public static final String TABLA_VOCABULARIO = "tabla_vocabulario";
    public static final String ID_VOCABULARIO = "id";
    public static final String USUARIO_VOCABULARIO = "usuario";

    public static final String CREAR_VOCABULARIO =
            "CREATE TABLE "+TABLA_VOCABULARIO+"("+
                    ID_VOCABULARIO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    USUARIO_VOCABULARIO+" INTEGER,FOREIGN KEY("+USUARIO_VOCABULARIO+") REFERENCES tabla_usuarios("+ID_USUARIO+"));";

    // Constantes tabla nivel
    public static final String TABLA_NIVEL = "tabla_nivel";
    public static final String ID_NIVEL = "id";
    public static final String NOMBRE_NIVEL = "nombre";
    public static final String BLOQUEADO_NIVEL = "bloqueado";
    public static final String VOCABULARIO_NIVEL = "vocabulario";

    public static final String CREAR_NIVEL =
            "CREATE TABLE "+TABLA_NIVEL+"("+
                    ID_NIVEL+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOMBRE_NIVEL      +" TEXT,"   +
                    BLOQUEADO_NIVEL      +" TEXT,"   +
                    VOCABULARIO_NIVEL+" INTEGER,FOREIGN KEY("+VOCABULARIO_NIVEL+") REFERENCES tabla_vocabulario("+ID_VOCABULARIO+"));";

    // Constantes tabla categoria
    public static final String TABLA_CATEGORIA = "tabla_categoria";
    public static final String ID_CATEGORIA = "id";
    public static final String NOMBRE_CATEGORIA = "nombre";
    public static final String BLOQUEADO_CATEGORIA = "bloqueado";
    public static final String NIVEL_CATEGORIA = "nivel";

    public static final String CREAR_CATEGORIA =
            "CREATE TABLE "+TABLA_CATEGORIA+"("+
                    ID_CATEGORIA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOMBRE_CATEGORIA      +" TEXT,"   +
                    BLOQUEADO_CATEGORIA      +" TEXT,"   +
                    NIVEL_CATEGORIA+" INTEGER,FOREIGN KEY("+NIVEL_CATEGORIA+") REFERENCES tabla_nivel("+ID_NIVEL+"));";

    // Constantes tabla palabra
    public static final String TABLA_PALABRA = "tabla_palabra";
    public static final String ID_PALABRA = "id";
    public static final String NOMBRE_PALABRA = "nombre";
    public static final String COSTO_PALABRA = "costo";
    public static final String PUNTAJE_PALABRA = "puntaje";
    public static final String BLOQUEADO_PALABRA = "bloqueado";
    public static final String TIENDA_PALABRA = "tienda";
    public static final String CATEGORIA_PALABRA = "categoria";

    public static final String CREAR_PALABRA =
            "CREATE TABLE "+TABLA_PALABRA+"("+
                    ID_PALABRA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOMBRE_PALABRA      +" TEXT,"   +
                    COSTO_PALABRA       +" TEXT,"   +
                    PUNTAJE_PALABRA     +" TEXT,"   +
                    BLOQUEADO_PALABRA   +" TEXT,"   +
                    TIENDA_PALABRA      +" INTEGER," +
                    CATEGORIA_PALABRA   +" INTEGER,FOREIGN KEY(" + CATEGORIA_PALABRA + ") REFERENCES tabla_categoria(" + ID_CATEGORIA + "));";

    // Constantes tabla palabra
    public static final String TABLA_RETO = "tabla_reto";
    public static final String ID_RETO = "id";
    public static final String VARIABLE_RETO = "variable";
    public static final String NIVEL_RETO = "nivel";
    public static final String CATEGORIA_RETO = "categoria";
    public static final String PALABRAS_RETO = "palabras";
    public static final String NPALABRAS_RETO = "npalabras";
    public static final String PUNTOS_RETO = "puntos";
    public static final String USUARIO_RETO = "usuario";

    public static final String CREAR_RETO =
            "CREATE TABLE "+TABLA_RETO+"("+
                    ID_RETO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    VARIABLE_RETO       +" TEXT,"   +
                    NIVEL_RETO          +" TEXT,"   +
                    CATEGORIA_RETO      +" TEXT,"   +
                    PALABRAS_RETO       +" TEXT,"   +
                    NPALABRAS_RETO      +" INTEGER," +
                    PUNTOS_RETO         +" INTEGER," +
                    USUARIO_RETO        +" INTEGER,FOREIGN KEY(" + USUARIO_RETO + ") REFERENCES tabla_usuarios(" + ID_USUARIO + "));";
}
