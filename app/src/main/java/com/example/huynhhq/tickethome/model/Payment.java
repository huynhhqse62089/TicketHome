package com.example.huynhhq.tickethome.model;

/**
 * Created by HuynhHQ on 10/28/2017.
 */

public class Payment {

    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;
    private String address;
    private String nameTicket;
    private String countTicket;
    private Event event;
    private String payMethod;
    private String payMethodId;

    public Payment() {
    }

    public Payment(String username, String email, String phoneNumber, String address, String nameTicket, String countTicket, Event event, String payMethod) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.nameTicket = nameTicket;
        this.countTicket = countTicket;
        this.event = event;
        this.payMethod = payMethod;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNameTicket() {
        return nameTicket;
    }

    public void setNameTicket(String nameTicket) {
        this.nameTicket = nameTicket;
    }

    public String getCountTicket() {
        return countTicket;
    }

    public void setCountTicket(String countTicket) {
        this.countTicket = countTicket;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(String payMethodId) {
        this.payMethodId = payMethodId;
    }

}
