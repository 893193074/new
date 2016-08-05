package com.feicuiedu.gitdroid.userinfo.view;


import com.feicuiedu.gitdroid.login.modle.User;

import java.util.List;

/**
 * 加载更多的视图抽象
 * 作者：yuanchao on 2016/7/28 0028 11:34
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface UserListLoadMoreView {
    /** 显示上拉加载时的加载中视图*/
    void showLoadMoreLoading();

    /** 隐藏上拉加载时的加载中视图*/
    void hideLoadMore();

    /** 隐藏上拉加载时的错误视图*/
    void showLoadMoreErro(String erroMsg);

    void addMoreData(List<User> datas);
}