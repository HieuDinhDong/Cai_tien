package com.example.shippingdriverapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;


@Generated("jsonschema2pojo")
public class InfoDriver {

    @SerializedName("id_info")
    @Expose
    private int idInfo;
    @SerializedName("front_cccd")
    @Expose
    private String frontCccd;
    @SerializedName("behind_cccd")
    @Expose
    private String behindCccd;
    @SerializedName("front_license")
    @Expose
    private String frontLicense;
    @SerializedName("behind_license")
    @Expose
    private String behindLicense;
    @SerializedName("front_regis")
    @Expose
    private String frontRegis;
    @SerializedName("behind_regis")
    @Expose
    private String behindRegis;

    public InfoDriver() {
    }

    public InfoDriver(int idInfo, String frontCccd, String behindCccd, String frontLicense, String behindLicense, String frontRegis, String behindRegis) {
        this.idInfo = idInfo;
        this.frontCccd = frontCccd;
        this.behindCccd = behindCccd;
        this.frontLicense = frontLicense;
        this.behindLicense = behindLicense;
        this.frontRegis = frontRegis;
        this.behindRegis = behindRegis;
    }

    public int getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(int idInfo) {
        this.idInfo = idInfo;
    }

    public String getFrontCccd() {
        return frontCccd;
    }

    public void setFrontCccd(String frontCccd) {
        this.frontCccd = frontCccd;
    }

    public String getBehindCccd() {
        return behindCccd;
    }

    public void setBehindCccd(String behindCccd) {
        this.behindCccd = behindCccd;
    }

    public String getFrontLicense() {
        return frontLicense;
    }

    public void setFrontLicense(String frontLicense) {
        this.frontLicense = frontLicense;
    }

    public String getBehindLicense() {
        return behindLicense;
    }

    public void setBehindLicense(String behindLicense) {
        this.behindLicense = behindLicense;
    }

    public String getFrontRegis() {
        return frontRegis;
    }

    public void setFrontRegis(String frontRegis) {
        this.frontRegis = frontRegis;
    }

    public String getBehindRegis() {
        return behindRegis;
    }

    public void setBehindRegis(String behindRegis) {
        this.behindRegis = behindRegis;
    }
}
