package com.example.shippinguserapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class TypeOrder {

    @SerializedName("id_type")
    @Expose
    private int idType;
    @SerializedName("name_type")
    @Expose
    private String nameType;
    @SerializedName("describe")
    @Expose
    private String describe;

    public TypeOrder() {
    }

    public TypeOrder(int idType, String nameType, String describe) {
        this.idType = idType;
        this.nameType = nameType;
        this.describe = describe;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

