package com.example.huynhhq.tickethome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public class Event implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("organiezerId")
    @Expose
    private int organiezerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("createdTime")
    @Expose
    private String createdTime;
    @SerializedName("enable")
    @Expose
    private boolean enable;
    @SerializedName("numberSlot")
    @Expose
    private int numberSlot;
    @SerializedName("cityId")
    @Expose
    private int cityId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("type")
    @Expose
    private List<Type> type;
    @SerializedName("check")
    @Expose
    private boolean check;
    @SerializedName("notify")
    @Expose
    private boolean notify ;

    public Event(int id, int organiezerId, String name, String description, String startDate, String endDate, String address, String createdTime, boolean enable, int numberSlot, int cityId) {
        this.id = id;
        this.organiezerId = organiezerId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.createdTime = createdTime;
        this.enable = enable;
        this.numberSlot = numberSlot;
        this.cityId = cityId;
    }

    public Event() {
    }

    public Event(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganiezerId() {
        return organiezerId;
    }

    public void setOrganiezerId(int organiezerId) {
        this.organiezerId = organiezerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getNumberSlot() {
        return numberSlot;
    }

    public void setNumberSlot(int numberSlot) {
        this.numberSlot = numberSlot;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
