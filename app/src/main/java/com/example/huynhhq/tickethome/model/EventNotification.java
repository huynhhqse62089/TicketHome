package com.example.huynhhq.tickethome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HuynhHQ on 11/4/2017.
 */

public class EventNotification implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("realId")
    @Expose
    private int realId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startDate")
    @Expose
    private Long startDate;

    public EventNotification() {
    }

    public EventNotification(int id, String name, Long startDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }
}
