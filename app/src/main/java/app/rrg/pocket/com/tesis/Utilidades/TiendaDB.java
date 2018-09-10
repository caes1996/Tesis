package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Tienda;

public class TiendaDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public TiendaDB(Context context) {
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

    private ContentValues eventoMapperContentValues(Tienda tienda) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.USUARIO_TIENDA, tienda.getUsuario());
        return cv;
    }

    public long insertTienda(Tienda tienda) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_TIENDA, null, eventoMapperContentValues(tienda));
        this.closeDB();

        return rowID;
    }

    public void updateTienda(Tienda tienda) {
        this.openWriteableDB();
        String where = Utilidades.ID_TIENDA + "= ?";
        db.update(Utilidades.TABLA_TIENDA, eventoMapperContentValues(tienda), where, new String[]{String.valueOf(tienda.getId())});
        db.close();
    }

    public void deleteTienda(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_TIENDA + "= ?";
        db.delete(Utilidades.TABLA_TIENDA, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList<Tienda> loadTienda() {

        ArrayList list = new ArrayList<>();

        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_TIENDA, Utilidades.USUARIO_TIENDA};
        Cursor c = db.query(Utilidades.TABLA_TIENDA, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Tienda tienda = new Tienda();
                tienda.setId(c.getInt(0));
                tienda.setUsuario(c.getInt(1));
                list.add(tienda);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Tienda buscarTienda(int id) {
        Tienda tienda = new Tienda();
        this.openReadableDB();
        String where = Utilidades.ID_TIENDA + "= ?";
        Cursor c = db.query(Utilidades.TABLA_TIENDA, null, where, new String[]{String.valueOf(id)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            tienda.setId(c.getInt(0));
            tienda.setUsuario(c.getInt(1));
            c.close();
        }
        this.closeDB();
        return tienda;
    }
}
