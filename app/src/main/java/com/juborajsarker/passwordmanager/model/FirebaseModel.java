package com.juborajsarker.passwordmanager.model;

/**
 * Created by jubor on 2/20/2018.
 */

public class FirebaseModel {

    private int ID;
    private String title;
    private String password;
    private  String website;
    private String header;
    private String type;
    private String email;


    public FirebaseModel() {


    }

    public FirebaseModel(int ID, String title, String password, String website, String header, String type, String email) {
        this.ID = ID;
        this.title = title;
        this.password = password;
        this.website = website;
        this.header = header;
        this.type = type;
        this.email = email;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
