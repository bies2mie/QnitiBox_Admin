package com.badrul.qnitiboxadmin;

public class Order {

    //private int userid;
    private int orderID;
    private String cardID;
    private String nameID;
    private String phoneID;
    private String emailID;
    private String matrixID;
    private String orderType;
    private String orderDay;
    private String orderDate;
    private String orderTime;
    private String orderQTT;
    private String orderUserType;
    // private int usercredit;
    private String puLocation;
    private String puTime;
    private String orderStatus;
    private String completeDate;
    private String completeTime;
    private String totalPrice;



    public Order(int orderID, String cardID, String nameID, String phoneID, String emailID, String matrixID, String orderType, String orderDay, String orderDate, String orderTime, String orderQTT, String orderUserType, String puLocation, String puTime, String orderStatus, String completeDate, String completeTime,String totalPrice) {

        this.orderID = orderID;
        this.nameID = nameID;
        this.cardID = cardID;
        this.phoneID = phoneID;
        this.emailID = emailID;
        this.matrixID = matrixID;
        this.orderType = orderType;
        this.orderDay = orderDay;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderQTT = orderQTT;
        this.orderUserType = orderUserType;
        this.puLocation = puLocation;
        this.puTime = puTime;
        this.orderStatus = orderStatus;
        this.completeDate = completeDate;
        this.completeTime = completeTime;
        this.totalPrice = totalPrice;


    }

    public int getOrderID() {
        return orderID;
    }

    public String getCardID() {
        return cardID;
    }

    public String getNameID() {
        return nameID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getMatrixID() {
        return matrixID;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderQTT() {
        return orderQTT;
    }

    public String getOrderUserType() {
        return orderUserType;
    }

    public String getPuLocation() {
        return puLocation;
    }

    public String getPuTime() {
        return puTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}