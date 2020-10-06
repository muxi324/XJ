package com.wp.entity.yunxing;

public class YunxingInfo {
    private String id;                  //id
    private String Date;
    private String zhiBanTime;          //值班时间
    private String paiFangZhiShu;       //排放指数
    private String riPaiFangDunShu;  //日排放吨数
    private String YunXingQK;    //运行情况
    private String YuLvLiang;    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getZhiBanTime() {
        return zhiBanTime;
    }

    public void setZhiBanTime(String zhiBanTime) {
        this.zhiBanTime = zhiBanTime;
    }

    public String getPaiFangZhiShu() {
        return paiFangZhiShu;
    }

    public void setPaiFangZhiShu(String paiFangZhiShu) {
        this.paiFangZhiShu = paiFangZhiShu;
    }

    public String getRiPaiFangDunShu() {
        return riPaiFangDunShu;
    }

    public void setRiPaiFangDunShu(String riPaiFangDunShu) {
        this.riPaiFangDunShu = riPaiFangDunShu;
    }

    public String getYunXingQK() {
        return YunXingQK;
    }

    public void setYunXingQK(String yunXingQK) {
        YunXingQK = yunXingQK;
    }

    public String getYuLvLiang() {
        return YuLvLiang;
    }

    public void setYuLvLiang(String yuLvLiang) {
        YuLvLiang = yuLvLiang;
    }

    @Override
    public String toString() {
        return "YunxingInfo{" +
                "id='" + id + '\'' +
                ", Date='" + Date + '\'' +
                ", zhiBanTime='" + zhiBanTime + '\'' +
                ", paiFangZhiShu='" + paiFangZhiShu + '\'' +
                ", riPaiFangDunShu='" + riPaiFangDunShu + '\'' +
                ", YunXingQK='" + YunXingQK + '\'' +
                ", YuLvLiang='" + YuLvLiang + '\'' +
                '}';
    }
}
