package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Nivel;

public class NivelDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public NivelDB(Context context) {
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

    private ContentValues eventoMapperContentValues(Nivel nivel) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_NIVEL, nivel.getNombre());
        cv.put(Utilidades.BLOQUEADO_NIVEL, nivel.getBloqueado());
        cv.put(Utilidades.VOCABULARIO_NIVEL, nivel.getVocabulario());
        return cv;
    }

    public long insertNivel(Nivel nivel) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_NIVEL, null, eventoMapperContentValues(nivel));
        this.closeDB();

        return rowID;
    }

    public void updateNivel(Nivel nivel) {
        this.openWriteableDB();
        String where = Utilidades.ID_NIVEL + "= ?";
        db.update(Utilidades.TABLA_NIVEL, eventoMapperContentValues(nivel), where, new String[]{String.valueOf(nivel.getId())});
        db.close();
    }

    public void deleteNivel(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_NIVEL + "= ?";
        db.delete(Utilidades.TABLA_NIVEL, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadNivel() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_NIVEL, Utilidades.NOMBRE_NIVEL, Utilidades.BLOQUEADO_NIVEL , Utilidades.VOCABULARIO_NIVEL};
        Cursor c = db.query(Utilidades.TABLA_NIVEL, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Nivel nivel = new Nivel();
                nivel.setId(c.getInt(0));
                nivel.setNombre(c.getString(1));
                nivel.setBloqueado(c.getInt(2));
                nivel.setVocabulario(c.getInt(3));
                list.add(nivel);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Nivel buscarNivel(String nombre) {
        Nivel nivel = new Nivel();
        this.openReadableDB();
        String where = Utilidades.NOMBRE_NIVEL + "= ?";
        Cursor c = db.query(Utilidades.TABLA_NIVEL, null, where, new String[]{String.valueOf(nombre)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            nivel.setId(c.getInt(0));
            nivel.setNombre(c.getString(1));
            nivel.setBloqueado(c.getInt(2));
            nivel.setVocabulario(c.getInt(3));
            c.close();
        }
        this.closeDB();
        return nivel;
    }

    public Nivel buscarNivelId(int id) {
        Nivel nivel = new Nivel();
        this.openReadableDB();
        String where = Utilidades.ID_NIVEL + "= ?";
        Cursor c = db.query(Utilidades.TABLA_NIVEL, null, where, new String[]{String.valueOf(id)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            nivel.setId(c.getInt(0));
            nivel.setNombre(c.getString(1));
            nivel.setBloqueado(c.getInt(2));
            nivel.setVocabulario(c.getInt(3));
            c.close();
        }
        this.closeDB();
        return nivel;
    }
}
