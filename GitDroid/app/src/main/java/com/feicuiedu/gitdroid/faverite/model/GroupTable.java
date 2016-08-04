package com.feicuiedu.gitdroid.faverite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
@DatabaseTable(tableName = "faverite_group")
public class GroupTable {
    @DatabaseField(id = true)
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    @DatabaseField(columnName = "NAME")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private static List<GroupTable> DEFAULT_GROUPS;

    public static List<GroupTable> getDefaultGroups(Context context) {
        if (DEFAULT_GROUPS != null) return DEFAULT_GROUPS;

        try {
            InputStream inputStream = context.getAssets().open("repogroup.json");
            // 将流转换为字符串
            String content = IOUtils.toString(inputStream);
            // 将字符串转换为对象数组
            Gson gson = new Gson();
            DEFAULT_GROUPS = gson.fromJson(content, new TypeToken<List<GroupTable>>() {
            }.getType());
            return DEFAULT_GROUPS;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
