package com.juborajsarker.passwordmanager.model;

/**
 * Created by jubor on 2/12/2018.
 */

public class ModelPassword {

    private int ID;
    private String title;
    private String password;
    private String website;
    private char header;
    private String type;
    private String email;


    public ModelPassword() {


    }


    public ModelPassword(int ID, String title, String password, String website, char header, String type, String email) {

        this.ID = ID;
        this.title = title;
        this.password = password;
        this.website = website;
        this.header = header;
        this.type = type;
        this.email = email;


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

    public char getHeader() {
        return header;
    }

    public void setHeader(char header) {
        this.header = header;
    }

    public int getId() {
        return ID;
    }

    public void setId(int id) {
        this.ID = id;
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
