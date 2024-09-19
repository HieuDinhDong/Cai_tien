package com.example.shippingdriverapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Commodities {
    @SerializedName("id_com")
    @Expose
    private int idCom;
    @SerializedName("name_com")
    @Expose
    private String nameCom;
    @SerializedName("describe_com")
    @Expose
    private String describeCom;
    @SerializedName("weight")
    @Expose
    private double weight;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("order")
    @Expose
    private int idOrder;

    public Commodities() {
    }

    public Commodities(int idCom, String nameCom, String describeCom, double weight, double price) {
        this.idCom = idCom;
        this.nameCom = nameCom;
        this.describeCom = describeCom;
        this.weight = weight;
        this.price = price;
    }

    public Commodities(int idCom, String nameCom, String describeCom, double weight, double price, int idOrder) {
        this.idCom = idCom;
        this.nameCom = nameCom;
        this.describeCom = describeCom;
        this.weight = weight;
        this.price = price;
        this.idOrder = idOrder;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public String getNameCom() {
        return nameCom;
    }

    public void setNameCom(String nameCom) {
        this.nameCom = nameCom;
    }

    public String getDescribeCom() {
        return describeCom;
    }

    public void setDescribeCom(String describeCom) {
        this.describeCom = describeCom;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
