package com.example.shippingdriverapplication.models.responses;

import com.example.shippingdriverapplication.models.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OrderResponse {

    @SerializedName("data")
    @Expose
    private Order order;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;

    public OrderResponse() {
    }

    public OrderResponse(Order order, String message, int code) {
        this.order = order;
        this.message = message;
        this.code = code;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
