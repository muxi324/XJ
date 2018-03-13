package com.wp.entity.house;

/**
 * Created by wp on 2017/12/12.
 */
public class House {
    private String house_id;		//房源id
    private String house_address;   //地址
    private String house_owner;
    private String owner_phone;
    private String lock_code;
    private String lock_type;
    private String lock_model;
    private String door_type;
    private String longitude;
    private String latitude;
    private String set_condition;
    private String add_time;

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public String getHouse_owner() {
        return house_owner;
    }

    public void setHouse_owner(String house_owner) {
        this.house_owner = house_owner;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getLock_code() {
        return lock_code;
    }

    public void setLock_code(String lock_code) {
        this.lock_code = lock_code;
    }

    public String getLock_type() {
        return lock_type;
    }

    public void setLock_type(String lock_type) {
        this.lock_type = lock_type;
    }

    public String getLock_model() {
        return lock_model;
    }

    public void setLock_model(String lock_model) {
        this.lock_model = lock_model;
    }

    public String getDoor_type() {
        return door_type;
    }

    public void setDoor_type(String door_type) {
        this.door_type = door_type;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSet_condition() {
        return set_condition;
    }

    public void setSet_condition(String set_condition) {
        this.set_condition = set_condition;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
