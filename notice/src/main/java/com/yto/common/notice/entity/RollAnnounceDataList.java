package com.yto.common.notice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RollAnnounceDataList implements Serializable {
   /* {"announceList":[
    {
            "announceId":152,
            "announceName":"操作时间",
            "belongSectionCode":"EUYlzWQp",
            "belongSectionName":"西安研发中心",
            "belongColumnCode":"",
            "belongColumnName":null,
            "content":"<p>分公司分公司</p>",
            "isTop":1,
            "announceLabel":"普通",
            "announceType":2,
            "announceStatus":1,
            "delFlag":0,
            "approverStaffCode":"02011740",
            "approverStaffName":"胡秦霞",
            "approveDate":"2020-10-26 16:07:41",
            "remark":null,
            "createTime":"2020-10-26 16:07:41",
            "updateStaffCode":"02011740",
            "updateStaffName":"胡秦霞",
            "updateTime":"2020-10-26 16:07:41",
            "staffCode":"02011740",
            "staffName":"胡秦霞",
            "ifPopUp":1,
            "checkChoice":1,
            "startTime":"2020-10-26 00:00:00",
            "endTime":"2020-10-27 23:59:59",
            "viewCount":0,
            "downloadCount":0,
            "announceTargetAppList":null,
            "sectionOrg":null,
            "announceAppendixList":null,
            "contentKey1":"",
            "contentKey2":"",
            "contentKey3":"",
            "contentKey4":"",
            "contentKey5":"",
            "commentFlag":0,
            "appCode":"bc7c151dbe8c45c8a3ce486d64d76bd7"
    }],
        "announceToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHAtc2VjcmV0IjoiMjY5MGQ2YjEwNSIsImlzcyI6ImFubm91bmNlbWVudC1hdXRoIiwiYXBwLWNvZGUiOiJiYzdjMTUxZGJlOGM0NWM4YTNjZTQ4NmQ2NGQ3NmJkNyIsInVzZXItY29kZSI6IjAxNTI0MTA2IiwiZXhwIjoxNjAzODA5ODM4LCJpYXQiOjE2MDM3ODEwMzgsImp0aSI6IjIwMjAxMDI3MTQ0MzU4X0YzOTYxNjZBQTlBNTQ2NTNBMzRCQ0ZCRDI4QzdGNTdEIn0.amZYWmX5cFGU6D8xSmPHW3ZOPQ7Pm5wosqszU_oS4Ak"
}*/

    private String announceToken;
    private List<AnnounceData> announceList = new ArrayList<>();

   public String getAnnounceToken() {
      return announceToken;
   }

   public void setAnnounceToken(String announceToken) {
      this.announceToken = announceToken;
   }

   public List<AnnounceData> getAnnounceList() {
      return announceList;
   }

   public void setAnnounceList(List<AnnounceData> announceList) {
      this.announceList = announceList;
   }


}
