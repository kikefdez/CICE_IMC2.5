package com.quique.cice_imc2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quique on 03/02/2017.
 */

public class BBDD extends SQLiteOpenHelper {
    private static final String sqlCrearUsuarios ="CREATE TABLE Usuario (nrid INTEGER PRIMARY KEY AUTOINCREMENT, Login TEXT, Password TEXT, Nombre TEXT)";
    public BBDD(Context contexto,String nombre, SQLiteDatabase.CursorFactory cursorFactory, int version){ super(contexto, nombre, cursorFactory, version); }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(sqlCrearUsuarios); }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public String actualizarDatos(Usuario datosUsuario){
        String respuesta="Todo OK";
        String consultaSQL ="";
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        try
        {
            consultaSQL += (datosUsuario.getNRID() == "0") ? generarInsert(datosUsuario) : generarUpdate(datosUsuario);
            sqlDB.execSQL(consultaSQL);
            Log.e("actualizarDatos", "Datos del usuario actualizados correctamente");

        } catch (Throwable ex) { Log.e("actualizarDatos", ex.getMessage() + " - " + consultaSQL);
            respuesta = "Error en la actualizacion de datos."; }
        sqlDB.close();

        return respuesta;
    }
    private String generarInsert(Usuario datosUsuario) {
        String campo =""; String valor="";

        campo += "Login, "; valor += "'" + datosUsuario.getLogin() + "', ";
        campo += "Password, "; valor += "'" + datosUsuario.getPassword() + "', ";
        campo += "Nombre"; valor += "'" + datosUsuario.getNombre() + "'";

        return "INSERT INTO Usuario (" + campo + ") VALUES (" + valor + ");";
    }
    private String generarUpdate(Usuario datosUsuario){
        String consultaSQL="UPDATE Usuario SET ";
        consultaSQL += "Login = '" + datosUsuario.getLogin() + "', ";
        consultaSQL += "Password = '" + datosUsuario.getPassword() + "', ";
        consultaSQL += "Nombre = '" + datosUsuario.getNombre() + "' ";
        consultaSQL += "WHERE nrid = '" + datosUsuario.getNRID() + "';";
        return  consultaSQL;
    }

    public List<Usuario> recuperarDatos(String search) {
        String consultaSQL="";
        List<Usuario> listado = new ArrayList<Usuario>();

        SQLiteDatabase sqlDB = this.getReadableDatabase();
        try
        {
            consultaSQL += "SELECT nrid, Login, Password, Nombre FROM Usuario";
            if(search != "")
                consultaSQL += " WHERE " + search;
            Cursor resultados = sqlDB.rawQuery(consultaSQL, null);
            if(null != resultados && resultados.getCount() > 0)
            {
                resultados.moveToFirst();
                do {
                    listado.add(new Usuario(resultados.getString(0), resultados.getString(1), resultados.getString(2), resultados.getString(3)));
                } while (resultados.moveToNext());
            }
            Log.e("recuperarDatos", "Datos del usuario recuperados correctamente");

        } catch (Throwable ex) { Log.e("recuperarDatos", ex.getMessage() + " - " + consultaSQL); }
        sqlDB.close();

        return listado;
    }
}
