package com.yto.common.notice;

public class NoticeConfig {
    private static boolean isRelease = true;

    public static boolean isIsRelease() {
        return isRelease;
    }

    public static void setIsRelease(boolean isRelease) {
        NoticeConfig.isRelease = isRelease;
    }
}
