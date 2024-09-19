package com.example.shippingdriverapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Order {

    @SerializedName("id_order")
    @Expose
    private int idOrder;
    @SerializedName("address_start")
    @Expose
    private String addressStart;
    @SerializedName("address_end")
    @Expose
    private String addressEnd;
    @SerializedName("phone_receiver")
    @Expose
    private String phoneReceiver;
    @SerializedName("name_receiver")
    @Expose
    private String nameReceiver;
    @SerializedName("price_ship")
    @Expose
    private double priceShip;
    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("commodities")
    @Expose
    private ArrayList<Commodities> listCommodities;
    @SerializedName("type_order")
    @Expose
    private TypeOrder typeOrder;

    public Order() {
    }

    public Order(int idOrder, String addressStart, String addressEnd, String phoneReceiver, String nameReceiver, double priceShip, String note, int status, User user, ArrayList<Commodities> listCommodities, TypeOrder typeOrder) {
        this.idOrder = idOrder;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.phoneReceiver = phoneReceiver;
        this.nameReceiver = nameReceiver;
        this.priceShip = priceShip;
        this.note = note;
        this.status = status;
        this.user = user;
        this.listCommodities = listCommodities;
        this.typeOrder = typeOrder;
    }

    public Order(int idOrder, String addressStart, String addressEnd, String phoneReceiver, String nameReceiver, double priceShip, String note, String time, int status, User user, ArrayList<Commodities> listCommodities, TypeOrder typeOrder) {
        this.idOrder = idOrder;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.phoneReceiver = phoneReceiver;
        this.nameReceiver = nameReceiver;
        this.priceShip = priceShip;
        this.note = note;
        this.time = time;
        this.status = status;
        this.user = user;
        this.listCommodities = listCommodities;
        this.typeOrder = typeOrder;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Commodities> getListCommodities() {
        return listCommodities;
    }

    public void setListCommodities(ArrayList<Commodities> listCommodities) {
        this.listCommodities = listCommodities;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public double getPriceShip() {
        return priceShip;
    }

    public void setPriceShip(double priceShip) {
        this.priceShip = priceShip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeOrder getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(TypeOrder typeOrder) {
        this.typeOrder = typeOrder;
    }
}
