package com.example.huynhhq.tickethome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by HuynhHQ on 10/15/2017.
 */

public class Status implements Serializable{

    @SerializedName("status")
    @Expose
    private boolean status;

    public Status() {
    }

    public Status(boolean status) {

        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
