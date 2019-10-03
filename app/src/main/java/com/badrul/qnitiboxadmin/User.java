package com.badrul.qnitiboxadmin;

public class User {

    //private int userid;
    private int id;
    private String adminID;
    private String adminName;
    private String adminPhone;
    private String adminLocation;

    public User(int id, String adminID, String adminName, String adminPhone, String adminLocation) {

        this.id = id;
        this.adminID = adminID;
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminLocation = adminLocation;

    }

    public int getNewId() {
        return id;
    }

    public String getAdminID() {
        return adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public String getAdminLocation() {
        return adminLocation;
    }

}
