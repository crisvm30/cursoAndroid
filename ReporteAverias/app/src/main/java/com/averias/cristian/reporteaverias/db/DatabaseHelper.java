package com.averias.cristian.reporteaverias.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.averias.cristian.reporteaverias.entities.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static int DATABASE_VERSION = 1;
    private Dao <Usuario,Integer> mUsuarioDao = null;
    private final String TAG = "DatabaseHelper";

    public static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    public DatabaseHelper(Context context){

        super(context,"ormlite.db",null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,Usuario.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,Usuario.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Usuario,Integer> getUsuarioDao() throws SQLException{
        if(mUsuarioDao == null){
            mUsuarioDao = getDao(Usuario.class);
        }
        return mUsuarioDao;
    }

    @Override
    public void close(){
        mUsuarioDao = null;
        super.close();
    }


    public int agregarUsuario(String nombre,String usuario,String contrasena,String correo,String telefono,String cedula){
        int resultado = 0;
        try {
            getUsuarioDao();

            List<Usuario> lista = mUsuarioDao.queryForAll();
            resultado = mUsuarioDao.create(new Usuario(nombre,usuario,contrasena,correo,telefono,cedula));

        } catch (SQLException e) {
            Log.e(TAG, "Error en la creación de un usuario!");
            e.printStackTrace();
        }
        return resultado;
    }

    public int consultarUsuario(String usuario,String contrasena){
        int resultado = 0;
        try {
            getUsuarioDao();

            Map<String,Object> queryMap = new HashMap<String,Object>();

            queryMap.put("nombre_usuario",usuario);
            queryMap.put("contrasena_usuario",contrasena);

            List<Usuario> listaUsuario = mUsuarioDao.queryForFieldValues(queryMap);

            if(!listaUsuario.isEmpty()){
                resultado = 1;
            }

        } catch (SQLException e) {
            Log.e(TAG, "Error en la búsqueda de un usuario!");
            e.printStackTrace();
        }
        return resultado;
    }

}
