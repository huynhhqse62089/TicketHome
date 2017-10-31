package com.example.huynhhq.tickethome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HuynhHQ on 10/29/2017.
 */

public class Ticket implements Serializable {

    @SerializedName("eventId")
    @Expose
    private int eventId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("available")
    @Expose
    private boolean available;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("shipAddress")
    @Expose
    private String shipAddress;
    @SerializedName("isPaid")
    @Expose
    private boolean isPaid;

    public Ticket() {
    }

    public Ticket(int eventId, String username, String description, String position, boolean available, String price, String paymentMethod, String shipAddress, boolean isPaid) {
        this.eventId = eventId;
        this.username = username;
        this.description = description;
        this.position = position;
        this.available = available;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.shipAddress = shipAddress;
        this.isPaid = isPaid;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
