package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Palabra;

public class PalabraDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public PalabraDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db!=null){
            db.close();
        }
    }

    //CRUD

    private ContentValues eventoMapperContentValues(Palabra palabra) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_PALABRA, palabra.getNombre());
        cv.put(Utilidades.COSTO_PALABRA, palabra.getCosto());
        cv.put(Utilidades.PUNTAJE_PALABRA, palabra.getPuntaje());
        cv.put(Utilidades.BLOQUEADO_PALABRA, palabra.getBloqueado());
        cv.put(Utilidades.TIENDA_PALABRA, palabra.getTienda());
        cv.put(Utilidades.CATEGORIA_PALABRA, palabra.getCategoria());
        return cv;
    }

    public long insertPalabra(Palabra palabra) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_PALABRA, null, eventoMapperContentValues(palabra));
        this.closeDB();

        return rowID;
    }

    public void updatePalabra(Palabra palabra) {
        this.openWriteableDB();
        String where = Utilidades.ID_PALABRA + "= ?";
        db.update(Utilidades.TABLA_PALABRA, eventoMapperContentValues(palabra), where, new String[]{String.valueOf(palabra.getId())});
        db.close();
    }

    public void deletePalabra(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_PALABRA + "= ?";
        db.delete(Utilidades.TABLA_PALABRA, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadPalabra(int id) {
        ArrayList<Palabra> list = new ArrayList<>();

        this.openReadableDB();
        String where = Utilidades.CATEGORIA_PALABRA + "= ?";
        String[] campos = new String[]{Utilidades.ID_PALABRA, Utilidades.NOMBRE_PALABRA, Utilidades.COSTO_PALABRA, Utilidades.PUNTAJE_PALABRA, Utilidades.BLOQUEADO_PALABRA, Utilidades.TIENDA_PALABRA, Utilidades.CATEGORIA_PALABRA};
        Cursor c = db.query(Utilidades.TABLA_PALABRA, campos, where, new String[]{String.valueOf(id)}, null, null, null);

        try {
            while (c.moveToNext()) {
                Palabra palabra = new Palabra();
                palabra.setId(c.getInt(0));
                palabra.setNombre(c.getString(1));
                palabra.setCosto(c.getInt(2));
                palabra.setPuntaje(c.getInt(3));
                palabra.setBloqueado(c.getInt(4));
                palabra.setTienda(c.getInt(5));
                palabra.setCategoria(c.getInt(6));
                list.add(palabra);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public ArrayList loadTotalPalabras(int id) {
        ArrayList<Palabra> list = new ArrayList<>();

        this.openReadableDB();
        String where = Utilidades.CATEGORIA_PALABRA + "= ?";
        String[] campos = new String[]{Utilidades.ID_PALABRA, Utilidades.NOMBRE_PALABRA, Utilidades.COSTO_PALABRA, Utilidades.PUNTAJE_PALABRA, Utilidades.BLOQUEADO_PALABRA, Utilidades.TIENDA_PALABRA, Utilidades.CATEGORIA_PALABRA};
        Cursor c = db.query(Utilidades.TABLA_PALABRA, campos, where, new String[]{String.valueOf(id)}, null, null, null);

        try {
            while (c.moveToNext()) {
                Palabra palabra = new Palabra();
                palabra.setId(c.getInt(0));
                palabra.setNombre(c.getString(1));
                palabra.setCosto(c.getInt(2));
                palabra.setPuntaje(c.getInt(3));
                palabra.setBloqueado(c.getInt(4));
                palabra.setTienda(c.getInt(5));
                palabra.setCategoria(c.getInt(6));
                list.add(palabra);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Palabra buscarPalabra(int id) {
        Palabra palabra = new Palabra();
        this.openReadableDB();
        String where = Utilidades.ID_PALABRA + "= ?";
        Cursor c = db.query(Utilidades.TABLA_PALABRA, null, where, new String[]{String.valueOf(id)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            palabra.setId(c.getInt(0));
            palabra.setNombre(c.getString(1));
            palabra.setCosto(c.getInt(2));
            palabra.setPuntaje(c.getInt(3));
            palabra.setBloqueado(c.getInt(4));
            palabra.setTienda(c.getInt(5));
            palabra.setCategoria(c.getInt(6));
            c.close();
        }
        this.closeDB();
        return palabra;
    }

}
