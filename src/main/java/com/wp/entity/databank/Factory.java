package com.wp.entity.databank;

/**
 * Created by wp on 2018/5/14.
 */
public class Factory {
    private String id;		//id
    private String factory;		//工厂名称
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
