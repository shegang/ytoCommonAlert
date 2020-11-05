package com.yto.common.notice.entity;

import java.io.Serializable;

public class PopAnnounceData implements Serializable {
    /**
     *  "announce": {
     *       "announceId": 152,
     *       "announceName": "操作时间",
     *       "belongSectionCode": "EUYlzWQp",
     *       "belongSectionName": "西安研发中心",
     *       "belongColumnCode": "",
     *       "belongColumnName": null,
     *       "content": "<p>分公司分公司</p>",
     *       "isTop": 1,
     *       "announceLabel": "普通",
     *       "announceType": 2,
     *       "announceStatus": 1,
     *       "delFlag": 0,
     *       "approverStaffCode": "02011740",
     *       "approverStaffName": "胡秦霞",
     *       "approveDate": "2020-10-26 16:07:41",
     *       "remark": null,
     *       "createTime": "2020-10-26 16:07:41",
     *       "updateStaffCode": "02011740",
     *       "updateStaffName": "胡秦霞",
     *       "updateTime": "2020-10-26 16:07:41",
     *       "staffCode": "02011740",
     *       "staffName": "胡秦霞",
     *       "ifPopUp": 1,
     *       "checkChoice": 1,
     *       "startTime": "2020-10-26 00:00:00",
     *       "endTime": "2020-10-27 23:59:59",
     *       "viewCount": 0,
     *       "downloadCount": 0,
     *       "announceTargetAppList": null,
     *       "sectionOrg": null,
     *       "announceAppendixList": null,
     *       "contentKey1": "",
     *       "contentKey2": "",
     *       "contentKey3": "",
     *       "contentKey4": "",
     *       "contentKey5": "",
     *       "commentFlag": 0,
     *       "appCode": "bc7c151dbe8c45c8a3ce486d64d76bd7"
     *     },
     *     "announceToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHAtc2VjcmV0IjoiMjY5MGQ2YjEwNSIsImlzcyI6ImFubm91bmNlbWVudC1hdXRoIiwiYXBwLWNvZGUiOiJiYzdjMTUxZGJlOGM0NWM4YTNjZTQ4NmQ2NGQ3NmJkNyIsInVzZXItY29kZSI6IjAxNTI0MTA2IiwiZXhwIjoxNjAzODA5NDQ2LCJpYXQiOjE2MDM3ODA2NDYsImp0aSI6IjIwMjAxMDI3MTQzNzI2XzlFMjIxQTU5MjM5NjQ5QzI5OTcxQzQ2MkJDNDM2MTdBIn0.Re9wm2tsknWP2TrqZns7_a6JMgv1eYeVpnTJYCumGVk"
     */
    private String announceToken;
    private AnnounceData announce;

    public String getAnnounceToken() {
        return announceToken;
    }

    public void setAnnounceToken(String announceToken) {
        this.announceToken = announceToken;
    }

    public AnnounceData getAnnounce() {
        return announce;
    }

    public void setAnnounce(AnnounceData announce) {
        this.announce = announce;
    }


}
