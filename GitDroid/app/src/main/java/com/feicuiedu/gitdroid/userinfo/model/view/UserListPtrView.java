package com.feicuiedu.gitdroid.userinfo.model.view;



import com.feicuiedu.gitdroid.login.modle.User;

import java.util.List;

/**
 * 作者：yuanchao on 2016/7/28 0028 10:14
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface UserListPtrView {
    /** 显示下拉刷新时的内容视图*/
    void showContentView();
    /** 显示下拉刷新时的错误视图*/
    void showErrorView(String errorMsg);
    /** 显示下拉刷新时的空视图*/
    void showEmptyView();
    void showMessage(String msg);
    void stopRefresh();
    void refreshData(List<User> data);
}
