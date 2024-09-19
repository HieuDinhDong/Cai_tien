package com.example.shippingdriverapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Driver {

    @SerializedName("id_driver")
    @Expose
    private int idDriver;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("info_driver")
    @Expose
    private InfoDriver infoDriver;

    public Driver() {
    }

    public Driver(int idDriver, String fullName, String email, String phone, String password, InfoDriver infoDriver) {
        this.idDriver = idDriver;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.infoDriver = infoDriver;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InfoDriver getInfoDriver() {
        return infoDriver;
    }

    public void setInfoDriver(InfoDriver infoDriver) {
        this.infoDriver = infoDriver;
    }
}
