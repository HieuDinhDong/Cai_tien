package com.example.shippingdriverapplication.models.responses;

import com.example.shippingdriverapplication.models.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListOrderResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Order> listOrders;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;

    public ListOrderResponse() {
    }

    public ListOrderResponse(ArrayList<Order> listOrders, String message, int code) {
        this.listOrders = listOrders;
        this.message = message;
        this.code = code;
    }

    public ArrayList<Order> getListOrders() {
        return listOrders;
    }

    public void setListOrders(ArrayList<Order> listOrders) {
        this.listOrders = listOrders;
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
