package com.example.event_management;

public class User {
    String uId;
    String uRegNo;
    String uName;
    String uMobile;

    public User() {
    }

    public User(String uId, String uRegNo, String uName, String uMobile) {
        this.uId = uId;
        this.uRegNo = uRegNo;
        this.uName = uName;
        this.uMobile = uMobile;
    }

    public String getuId() {
        return uId;
    }

    public String getuRegNo() {
        return uRegNo;
    }

    public String getuName() {
        return uName;
    }

    public String getuMobile() {
        return uMobile;
    }

}
