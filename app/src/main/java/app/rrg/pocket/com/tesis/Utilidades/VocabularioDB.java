package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Vocabulario;

public class VocabularioDB {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public VocabularioDB(Context context) {
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

    private ContentValues eventoMapperContentValues(Vocabulario vocabulario) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.USUARIO_VOCABULARIO, vocabulario.getUsuario());
        return cv;
    }

    public long insertVocabulario(Vocabulario vocabulario) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_VOCABULARIO, null, eventoMapperContentValues(vocabulario));
        this.closeDB();

        return rowID;
    }

    public void updateVocabulario(Vocabulario vocabulario) {
        this.openWriteableDB();
        String where = Utilidades.ID_VOCABULARIO + "= ?";
        db.update(Utilidades.TABLA_VOCABULARIO, eventoMapperContentValues(vocabulario), where, new String[]{String.valueOf(vocabulario.getId())});
        db.close();
    }

    public void deleteEvento(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_VOCABULARIO + "= ?";
        db.delete(Utilidades.TABLA_VOCABULARIO, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadVocabulario() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_VOCABULARIO, Utilidades.USUARIO_VOCABULARIO};
        Cursor c = db.query(Utilidades.TABLA_VOCABULARIO, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Vocabulario vocabulario = new Vocabulario();
                vocabulario.setId(c.getInt(0));
                vocabulario.setUsuario(c.getInt(1));
                list.add(vocabulario);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Vocabulario buscarVocabulario(int idUsuario) {
        Vocabulario vocabulario = new Vocabulario();
        this.openReadableDB();
        String where = Utilidades.USUARIO_VOCABULARIO + "= ?";
        Cursor c = db.query(Utilidades.TABLA_VOCABULARIO, null, where, new String[]{String.valueOf(idUsuario)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            vocabulario.setId(c.getInt(0));
            vocabulario.setUsuario(c.getInt(1));
            c.close();
        }
        this.closeDB();
        return vocabulario;
    }
}
