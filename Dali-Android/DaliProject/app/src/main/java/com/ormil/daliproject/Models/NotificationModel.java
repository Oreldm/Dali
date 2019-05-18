package com.ormil.daliproject.Models;

import java.util.Date;

public class NotificationModel {
    private String imageSrc;
    private String notificationInfo;
    private Date notificationTimestamp;

    public NotificationModel( String imageSrc, String notificationInfo, Date notificationTimestamp) {
        this.imageSrc = imageSrc;
        this.notificationInfo = notificationInfo;
        this.notificationTimestamp = notificationTimestamp;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(String notificationInfo) {
        this.notificationInfo = notificationInfo;
    }

    public Date getNotificationTimestamp() {
        return notificationTimestamp;
    }

    public void setNotificationTimestamp(Date notificationTimestamp) {
        this.notificationTimestamp = notificationTimestamp;
    }
}
