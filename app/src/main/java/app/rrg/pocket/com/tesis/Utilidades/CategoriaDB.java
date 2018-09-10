package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Categoria;

public class CategoriaDB {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public CategoriaDB(Context context) {
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

    private ContentValues eventoMapperContentValues(Categoria categoria) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_CATEGORIA, categoria.getNombre());
        cv.put(Utilidades.BLOQUEADO_CATEGORIA, categoria.getBloqueado());
        cv.put(Utilidades.NIVEL_CATEGORIA, categoria.getNivel());
        return cv;
    }

    public long insertCategoria(Categoria categoria) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_CATEGORIA, null, eventoMapperContentValues(categoria));
        this.closeDB();

        return rowID;
    }

    public void updateCategoria(Categoria categoria) {
        this.openWriteableDB();
        String where = Utilidades.ID_CATEGORIA + "= ?";
        db.update(Utilidades.TABLA_CATEGORIA, eventoMapperContentValues(categoria), where, new String[]{String.valueOf(categoria.getId())});
        db.close();
    }

    public void deleteCategoria(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_CATEGORIA + "= ?";
        db.delete(Utilidades.TABLA_CATEGORIA, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList loadCategoria(int id) {
        ArrayList<Categoria> list = new ArrayList<>();

        this.openReadableDB();
        String where = Utilidades.NIVEL_CATEGORIA + "= ?";
        String[] campos = new String[]{Utilidades.ID_CATEGORIA, Utilidades.NOMBRE_CATEGORIA, Utilidades.BLOQUEADO_CATEGORIA , Utilidades.NIVEL_CATEGORIA};
        Cursor c = db.query(Utilidades.TABLA_CATEGORIA, campos, where, new String[]{String.valueOf(id)}, null, null, null);

        try {
            while (c.moveToNext()) {
                Categoria categoria = new Categoria();
                categoria.setId(c.getInt(0));
                categoria.setNombre(c.getString(1));
                categoria.setBloqueado(c.getInt(2));
                categoria.setNivel(c.getInt(3));
                list.add(categoria);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Categoria buscarCategoria(int id) {

        Categoria categoria = new Categoria();
        this.openReadableDB();
        String where = Utilidades.ID_CATEGORIA + "= ?";
        Cursor c = db.query(Utilidades.TABLA_CATEGORIA, null, where, new String[]{String.valueOf(id)}, null, null, null);
        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            categoria.setId(c.getInt(0));
            categoria.setNombre(c.getString(1));
            categoria.setBloqueado(c.getInt(2));
            categoria.setNivel(c.getInt(3));
            c.close();
        }
        this.closeDB();
        return categoria;
    }
}
