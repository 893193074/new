package com.feicuiedu.gitdroid.faverite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicuiedu.gitdroid.faverite.model.GroupTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Administrator on 2016/8/3.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "faverite.db";

    private static final int VERSION = 1;

    private static DbHelper Instance;
    private final Context context;
    public static synchronized DbHelper getInstance(Context context){
        if (Instance == null) {
            Instance = new DbHelper(context.getApplicationContext());
        }

        return Instance;
    }

    private DbHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, GroupTable.class);


            List<GroupTable> datas=GroupTable.getDefaultGroups(context);
            new GroupDao(this).createOrUpdate(datas);
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,GroupTable.class,true);
            onCreate(database);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
