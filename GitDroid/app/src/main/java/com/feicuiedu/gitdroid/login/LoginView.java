package com.feicuiedu.gitdroid.login;

/**
 * 作者：yuanchao on 2016/7/29 0029 17:02
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface LoginView {

    // 显示进度
    void showProgress();

    void showMessage(String msg);

    // 重置WebView
    void resetWeb();

    // 导航切换至Main页面
    void navigateToMain();
}
