package com.example.shippinguserapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OrderOfUser {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("order")
    @Expose
    private Order order;

    public OrderOfUser() {
    }

    public OrderOfUser(int id, Driver driver, Order order) {
        this.id = id;
        this.driver = driver;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
