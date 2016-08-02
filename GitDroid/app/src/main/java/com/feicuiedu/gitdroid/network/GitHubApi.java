package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.hotrepo.repolist.modle.RepoResult;
import com.feicuiedu.gitdroid.login.modle.AccessTokenResult;
import com.feicuiedu.gitdroid.login.modle.User;
import com.feicuiedu.gitdroid.repoinfo.RepoContentResult;
import com.feicuiedu.gitdroid.userinfo.model.model.UsersResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit能将标准的reset接口，用Java接口来描述(通过注解),
 *
 * 通过Retrofit的create方法，去创建Call模型
 *
 * 作者：yuanchao on 2016/7/28 0028 16:36
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface GitHubApi {

    // GitHub开发者，申请时填写的(重定向返回时的一个标记)
    String CALL_BACK = "http://www.3213123213@163.com";

    // GitHub开发者，申请就行
    String CLIENT_ID = "d12f77e7cd94e44b2621";
    String CLIENT_SECRET = "b2fb0de0feba975a82913d0346c58987b1f91e64";

    // 授权时申请的可访问域
    String AUTH_SCOPE = "user,public_repo,repo";
    // 授权登陆页面(用WebView来加载)
    String AUTH_RUL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    /**
     * 获取访问令牌API
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    /**
     * 获取用户信息
     */
    @GET("user")
    Call<User> getUserInfo();

    /**
     * 获取仓库
     * @Param query 查询参数(language:java)
     * @Param pageId 查询页数据(从1开始)
     */
    @GET("/search/repositories")
    Call<RepoResult> searchRepos(
            @Query("q") String query,
            @Query("page") int pageId);

    /***
     * 获取readme
     * @param owner 仓库拥有者
     * @param repo 仓库名称
     * @return 仓库的readme页面内容,将是markdown格式且做了base64处理
     */
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(
            @Path("owner") String owner,
            @Path("repo") String repo);


    /***
     * 获取一个markdonw内容对应的HTML页面
     *
     * @param body 请求体,内容来自getReadme后的RepoContentResult
     */
    @Headers({
            "Content-Type:text/plain"
    })
    @POST("/markdown/raw")
    Call<ResponseBody> markDown(@Body RequestBody body);

    /**
     * 获取用户列表
     * @Param query 查询参数(language:java)
     * @Param pageId 查询页数据(从1开始)
     */
    @GET("/search/users")
    Call<UsersResult> searchUsers(
            @Query("q") String query,
            @Query("page") int pageId);


    /**
     * 获取用户
     * @Param login 请求路径,用户名
     */
    @GET("/users/{login}")
    Call<User> getUser(@Path("login")String login);
}