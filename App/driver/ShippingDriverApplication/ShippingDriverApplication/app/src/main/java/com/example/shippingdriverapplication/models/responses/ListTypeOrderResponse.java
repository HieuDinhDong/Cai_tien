package com.example.shippingdriverapplication.models.responses;

import com.example.shippingdriverapplication.models.TypeOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ListTypeOrderResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<TypeOrder> listTypeOrders;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;

    public ListTypeOrderResponse() {
    }

    public ListTypeOrderResponse(ArrayList<TypeOrder> listTypeOrders, String message, int code) {
        this.listTypeOrders = listTypeOrders;
        this.message = message;
        this.code = code;
    }

    public ArrayList<TypeOrder> getListTypeOrders() {
        return listTypeOrders;
    }

    public void setListTypeOrders(ArrayList<TypeOrder> listTypeOrders) {
        this.listTypeOrders = listTypeOrders;
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
