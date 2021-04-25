package com.yto.common.notice.api.requestparameter;

import java.io.Serializable;

public class LogisticsInterfaceParameter implements Serializable {
    private String userCode;//员工编号
    private String token;
    private String source;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
