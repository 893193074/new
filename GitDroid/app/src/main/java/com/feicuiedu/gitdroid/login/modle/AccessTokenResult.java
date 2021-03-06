package com.feicuiedu.gitdroid.login.modle;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 授权登陆响应结果
 *
 * 作者：yuanchao on 2016/7/29 0029 12:08
 * 邮箱：yuanchao@feicuiedu.com
 */
public class AccessTokenResult implements Serializable {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("scope")
    private String scope;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    //    Accept: application/json
//    {
//        "access_token":"e72e16c7e42f292c6912e7710c838347ae178b4a", "scope":"repo,gist",
//            "token_type":"bearer"
//    }
}
