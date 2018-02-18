package com.juborajsarker.passwordmanager.model;

/**
 * Created by jubor on 2/16/2018.
 */

public class CardModel {

    int ID;
    String bankName;
    String nameOnCard;
    String cardNumber;
    String pin;
    String ccv;
    String validityMonth;
    String validityYear;
    char header;
    String type;

    public CardModel() {

    }

    public CardModel(int ID, String bankName, String nameOnCard, String cardNumber, String pin,
                     String ccv, String validityMonth, String validityYear, char header, String type) {

        this.ID = ID;
        this.bankName = bankName;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.ccv = ccv;
        this.validityMonth = validityMonth;
        this.validityYear = validityYear;
        this.header = header;
        this.type = type;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getValidityMonth() {
        return validityMonth;
    }

    public void setValidityMonth(String validityMonth) {
        this.validityMonth = validityMonth;
    }

    public String getValidityYear() {
        return validityYear;
    }

    public void setValidityYear(String validityYear) {
        this.validityYear = validityYear;
    }

    public char getHeader() {
        return header;
    }

    public void setHeader(char header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
