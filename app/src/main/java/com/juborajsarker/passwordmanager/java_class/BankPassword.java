package com.juborajsarker.passwordmanager.java_class;

/**
 * Created by jubor on 2/12/2018.
 */

public class BankPassword {

    String title;
    String password;
    String description;
    String website;
    char header;


    public BankPassword() {


    }


    public BankPassword(String title, String password, String description, String website, char header) {
        this.title = title;
        this.password = password;
        this.description = description;
        this.website = website;
        this.header = header;
    }




    public BankPassword(String title, String password, String website, char header) {
        this.title = title;
        this.password = password;
        this.website = website;
        this.header = header;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
