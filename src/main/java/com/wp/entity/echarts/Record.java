package com.wp.entity.echarts;

/**
 * Created by wp on 2017/12/30.
 */
public class Record {
    private String mission_id;	//id
    private String mission_condition;		//状态
    private String send_time;			//下达时间

    public String getMission_id() {
        return mission_id;
    }

    public void setMission_id(String mission_id) {
        this.mission_id = mission_id;
    }

    public String getMission_condition() {
        return mission_condition;
    }

    public void setMission_condition(String mission_condition) {
        this.mission_condition = mission_condition;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
