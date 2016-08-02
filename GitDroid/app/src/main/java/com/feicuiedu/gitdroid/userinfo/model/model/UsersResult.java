package com.feicuiedu.gitdroid.userinfo.model.model;


import com.feicuiedu.gitdroid.login.modle.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：yuanchao on 2016/8/2 0002 11:05
 * 邮箱：yuanchao@feicuiedu.com
 */
public class UsersResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<User> userList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<User> getRepoList() {
        return userList;
    }
    
//    "total_count": 603,
//            "incomplete_results": false,
//            "items": [
}
