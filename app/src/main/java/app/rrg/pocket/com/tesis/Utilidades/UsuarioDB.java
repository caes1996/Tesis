package app.rrg.pocket.com.tesis.Utilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.rrg.pocket.com.tesis.DBHelper;
import app.rrg.pocket.com.tesis.Entities.Usuario;


public class UsuarioDB {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UsuarioDB(Context context) {
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

    private ContentValues usuarioMapperContentValues(Usuario usuario) {
        ContentValues cv = new ContentValues();
        cv.put(Utilidades.NOMBRE_USUARIO, usuario.getNombre());
        cv.put(Utilidades.EDAD_USUARIO, usuario.getEdad());
        cv.put(Utilidades.PUNTAJE_USUARIO, usuario.getPuntaje());
        cv.put(Utilidades.TAMANO_USUARIO, usuario.getTamano());
        return cv;
    }

    public long insertUsuario(Usuario usuario) {
        this.openWriteableDB();
        long rowID = db.insert(Utilidades.TABLA_USUARIO, null, usuarioMapperContentValues(usuario));
        this.closeDB();

        return rowID;
    }

    public void updateUsuario(Usuario usuario) {
        this.openWriteableDB();
        String where = Utilidades.ID_USUARIO + "= ?";
        db.update(Utilidades.TABLA_USUARIO, usuarioMapperContentValues(usuario), where, new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    public void deleteUsuario(int id) {
        this.openWriteableDB();
        String where = Utilidades.ID_USUARIO + "= ?";
        db.delete(Utilidades.TABLA_USUARIO, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    public ArrayList<Usuario> loadUsuarios(){

        ArrayList list = new ArrayList<>();
        this.openReadableDB();
        String[] campos = new String[]{Utilidades.ID_USUARIO, Utilidades.NOMBRE_USUARIO, Utilidades.EDAD_USUARIO, Utilidades.PUNTAJE_USUARIO, Utilidades.TAMANO_USUARIO};
        Cursor c = db.query(Utilidades.TABLA_USUARIO, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(c.getInt(0));
                usuario.setNombre(c.getString(1));
                usuario.setEdad(c.getString(2));
                usuario.setPuntaje(c.getInt(3));
                usuario.setTamano(c.getString(4));
                list.add(usuario);
            }
        } finally {
            c.close();
        }
        this.closeDB();

        return list;
    }

    public Usuario buscarUsuarios(int id) {
        Usuario usuario = new Usuario();
        this.openReadableDB();
        String where = Utilidades.ID_USUARIO + "= ?";
        Cursor c = db.query(Utilidades.TABLA_USUARIO, null, where,  new String[]{String.valueOf(id)}, null, null, null);

        if( c != null || c.getCount() >=0) {
            c.moveToFirst();
            usuario.setId(c.getInt(0));
            usuario.setNombre(c.getString(1));
            usuario.setEdad(c.getString(2));
            usuario.setPuntaje(c.getInt(3));
            usuario.setTamano(c.getString(4));
            c.close();
        }

        this.closeDB();
        return usuario;
    }

}