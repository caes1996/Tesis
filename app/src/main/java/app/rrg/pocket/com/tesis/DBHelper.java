package app.rrg.pocket.com.tesis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.rrg.pocket.com.tesis.Utilidades.Utilidades;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Utilidades.DB_NAME, null, Utilidades.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_USUARIO);
        db.execSQL(Utilidades.CREAR_TIENDA);
        db.execSQL(Utilidades.CREAR_VOCABULARIO);
        db.execSQL(Utilidades.CREAR_NIVEL);
        db.execSQL(Utilidades.CREAR_CATEGORIA);
        db.execSQL(Utilidades.CREAR_PALABRA);
        db.execSQL(Utilidades.CREAR_RETO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabla_usuarios");
        db.execSQL("DROP TABLE IF EXISTS tabla_tienda");
        db.execSQL("DROP TABLE IF EXISTS tabla_vocabulario");
        db.execSQL("DROP TABLE IF EXISTS tabla_nivel");
        db.execSQL("DROP TABLE IF EXISTS tabla_categoria");
        db.execSQL("DROP TABLE IF EXISTS tabla_plabra");
        db.execSQL("DROP TABLE IF EXISTS tabla_reto");
        onCreate(db);
    }
}
