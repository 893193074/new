package com.feicuiedu.gitdroid.userinfo.model;

import com.feicuiedu.gitdroid.login.modle.User;
import com.feicuiedu.gitdroid.network.GitHubClient;
import com.feicuiedu.gitdroid.userinfo.model.model.UsersResult;
import com.feicuiedu.gitdroid.userinfo.model.view.UserListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/2.
 */
public class UserInfoPresenter {
    private UserListView userListView;
    private Call<UsersResult> usersResultCall;
    private int nextPage = 0;

    public UserInfoPresenter (UserListView userListView){
        this.userListView=userListView;
    }
    // 下拉刷新处理
    public void refresh() {
        userListView.hideLoadMore();
        userListView.showContentView();
        nextPage = 1; // 永远刷新最新数据
        if(usersResultCall != null)usersResultCall.cancel();
        usersResultCall = GitHubClient.getInstance().searchUsers("followers:"+">1000",nextPage);
        usersResultCall.enqueue(ptrCallback);
    }

    // 加载更多处理
    public void loadMore() {
        userListView.showLoadMoreLoading();
        if(usersResultCall != null)usersResultCall.cancel();
        usersResultCall = GitHubClient.getInstance().searchUsers("followers:"+">1000",nextPage);
        usersResultCall.enqueue(loadmoreCallback);
    }

    private Callback<UsersResult> loadmoreCallback = new Callback<UsersResult>() {
        @Override public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.hideLoadMore();
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                userListView.showLoadMoreErro("结果为空!");
                return;
            }
            // 取出搜索到的所有用户
            List<User> userList = usersResult.getRepoList();
            userListView.addMoreData(userList);
            nextPage++;
        }

        @Override public void onFailure(Call<UsersResult> call, Throwable t) {
            // 视图停止刷新
            userListView.hideLoadMore();
            userListView.showMessage("loadmoreCallback onFailure" + t.getMessage());
        }
    };

    private Callback<UsersResult> ptrCallback = new Callback<UsersResult>() {
        @Override public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.stopRefresh();
            UsersResult usersResult = response.body();
            if (usersResult == null) {
                userListView.showErrorView("结果为空!");
                return;
            }
            // 取出搜索到的所有用户
            List<User> userList = usersResult.getRepoList();
            userListView.refreshData(userList);
            // 下拉刷新成功(1), 下一面则更新为2
            nextPage = 2;
        }

        @Override public void onFailure(Call<UsersResult> call, Throwable t) {
            userListView.stopRefresh();
            userListView.showMessage("ptrCallback onFailure" + t.getMessage());
        }
    };

}
