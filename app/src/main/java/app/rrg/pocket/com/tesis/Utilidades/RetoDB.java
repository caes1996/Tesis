package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Palabra;
import app.rrg.pocket.com.tesis.Entities.Reto;

public class RetoDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public RetoDB(Context context) {
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

    private ContentValues eventoMapperContentValues(Reto reto) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.VARIABLE_RETO, reto.getVariable());
        cv.put(Utilidades.NIVEL_RETO, reto.getNivel());
        cv.put(Utilidades.CATEGORIA_RETO, reto.getCategoria());
        cv.put(Utilidades.PALABRAS_RETO, reto.getPalabra());
        cv.put(Utilidades.NPALABRAS_RETO, reto.getNpalabra());
        cv.put(Utilidades.PUNTOS_RETO, reto.getPuntos());
        cv.put(Utilidades.USUARIO_RETO, reto.getUsuario());
        return cv;
    }

    public long insertReto(Reto reto) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_RETO, null, eventoMapperContentValues(reto));
        this.closeDB();

        return rowID;
    }

    public void updateReto(Reto reto) {
        this.openWriteableDB();
        String where = Utilidades.ID_RETO + "= ?";
        db.update(Utilidades.TABLA_RETO, eventoMapperContentValues(reto), where, new String[]{String.valueOf(reto.getId())});
        db.close();
    }

    public void deleteReto(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_RETO + "= ?";
        db.delete(Utilidades.TABLA_RETO, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadReto() {
        ArrayList<Reto> list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_RETO, Utilidades.VARIABLE_RETO, Utilidades.NIVEL_RETO, Utilidades.CATEGORIA_RETO, Utilidades.PALABRAS_RETO, Utilidades.NPALABRAS_RETO, Utilidades.PUNTOS_RETO, Utilidades.USUARIO_RETO};
        Cursor c = db.query(Utilidades.TABLA_RETO, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Reto reto = new Reto();
                reto.setId(c.getInt(0));
                reto.setVariable(c.getInt(1));
                reto.setNivel(c.getString(2));
                reto.setCategoria(c.getString(3));
                reto.setPalabra(c.getInt(4));
                reto.setNpalabra(c.getInt(5));
                reto.setPuntos(c.getInt(6));
                reto.setUsuario(c.getInt(7));
                list.add(reto);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Reto buscarReto(int id) {
        Reto reto = new Reto();
        this.openReadableDB();
        String where = Utilidades.ID_RETO + "= ?";
        Cursor c = db.query(Utilidades.TABLA_RETO, null, where, new String[]{String.valueOf(id)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            reto.setId(c.getInt(0));
            reto.setVariable(c.getInt(1));
            reto.setNivel(c.getString(2));
            reto.setCategoria(c.getString(3));
            reto.setPalabra(c.getInt(4));
            reto.setNpalabra(c.getInt(5));
            reto.setPuntos(c.getInt(6));
            reto.setUsuario(c.getInt(7));
            c.close();
        }
        this.closeDB();
        return reto;
    }

}
