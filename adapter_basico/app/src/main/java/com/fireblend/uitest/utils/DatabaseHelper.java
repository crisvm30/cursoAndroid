package com.fireblend.uitest.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fireblend.uitest.entities.Contact;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Estudiantes on 09/06/2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private Dao<Contact,Integer> mContactDao = null;

    public DatabaseHelper(Context context){
        super(context,"ormlite.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource,Contact.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,Contact.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Dao<Contact,Integer> getContactDao() throws SQLException{
        if(mContactDao == null){
            mContactDao = getDao(Contact.class);
        }
        return mContactDao;
    }

    @Override
    public void close(){
        mContactDao = null;
        super.close();
    }
}
