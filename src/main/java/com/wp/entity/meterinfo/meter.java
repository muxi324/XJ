package com.wp.entity.meterinfo;


public class meter {
    private int meter_id;
    private String meter_name;
    private String state;


    public int getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(int meter_id) {
        this.meter_id = meter_id;
    }

    public String getMeter_name() {
        return meter_name;
    }

    public void setMeter_name(String meter_name) {
        this.meter_name = meter_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
