package com.example.shippinguserapplication.models.responses;

import com.example.shippinguserapplication.models.Driver;
import com.example.shippinguserapplication.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DriverResponse {

    @SerializedName("data")
    @Expose
    private Driver driver;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;

    public DriverResponse() {
    }

    public DriverResponse(Driver driver, String message, int code) {
        this.driver = driver;
        this.message = message;
        this.code = code;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
