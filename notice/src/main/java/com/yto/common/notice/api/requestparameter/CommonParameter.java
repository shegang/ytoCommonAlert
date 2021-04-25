package com.yto.common.notice.api.requestparameter;

import java.io.Serializable;

public class CommonParameter implements Serializable {

    private String dataDigest;//
    private String logisticsInterface;//
    private String appCode;//
    private String currentDate;//

    public String getDataDigest() {
        return dataDigest;
    }

    public void setDataDigest(String dataDigest) {
        this.dataDigest = dataDigest;
    }

    public String getLogisticsInterface() {
        return logisticsInterface;
    }

    public void setLogisticsInterface(String logisticsInterface) {
        this.logisticsInterface = logisticsInterface;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
