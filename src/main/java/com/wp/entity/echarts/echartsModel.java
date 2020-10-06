package com.wp.entity.echarts;

public class echartsModel {
    private String day;
    private String value;

    public echartsModel(String day, String value) {
        this.day = day;
        this.value = value;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
