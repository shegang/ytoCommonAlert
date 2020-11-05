package com.yto.common.notice.api.requestparameter;

public class RequestParameter {
    /**
     * {
     *   "appCode": "bc7c151dbe8c45c8a3ce486d64d76bd7",
     *   "appSecret": "2690d6b105",
     *   "userCode": "01524106",
     *   "userName": "曾超"
     * }
     */
    private String appCode;//":
    private String appSecret;//
    private String userCode;//"
    private String userName;//"

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
