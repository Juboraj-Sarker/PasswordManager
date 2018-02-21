package com.juborajsarker.passwordmanager.model;

/**
 * Created by jubor on 2/20/2018.
 */

public class UserInfo {

    String email;
    String uid;

    String manufacture;
    String brandName;
    String modelName;
    String serialNumber;

    String osVersion;
    int apiLevel;
    String versionName;




    public UserInfo() {

    }


    public UserInfo(String email, String uid, String manufacture, String brandName, String modelName,
                    String serialNumber, String osVersion, int apiLevel, String versionName) {
        this.email = email;
        this.uid = uid;
        this.manufacture = manufacture;
        this.brandName = brandName;
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.osVersion = osVersion;
        this.apiLevel = apiLevel;
        this.versionName = versionName;


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public int getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(int apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }



}
