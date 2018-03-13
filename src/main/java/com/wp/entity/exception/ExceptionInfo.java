package com.wp.entity.exception;

public class ExceptionInfo {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(String checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getWorkShopId() {
        return workShopId;
    }

    public void setWorkShopId(String workShopId) {
        this.workShopId = workShopId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWokerId() {
        return wokerId;
    }

    public void setWokerId(String wokerId) {
        this.wokerId = wokerId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    private String  checkPointId;
    private String workShopId;
    private String description;
    private String  level;
    private String wokerId;
    private String reportTime;
    private String status;
    private String isRead;
}
