package com.feicuiedu.gitdroid.faverite.dao;


import com.feicuiedu.gitdroid.faverite.model.GroupTable;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class GroupDao {

    private Dao<GroupTable,Long> dao;
    public GroupDao(OrmLiteSqliteOpenHelper openHelper){
        try {
            openHelper.getDao(GroupTable.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /** 添加和更新 仓库类别表*/
    public void createOrUpdate(GroupTable table) {
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加和更新 仓库类别表*/
    public void createOrUpdate(List<GroupTable> tables) {
        for (GroupTable table : tables) {
            createOrUpdate(table);
        }
    }

    /**
     * 查询指定ID 仓库类别
     */
    public GroupTable queryForId(long id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询所有 仓库类别
     */
    public List<GroupTable> queryForAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
