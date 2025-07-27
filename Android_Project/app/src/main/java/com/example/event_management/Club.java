package com.example.event_management;

public class Club {

    String cId;
    String cName;
    String cRegNo;
    String cEmail;
    String cFacAd;

    public Club() {
    }

    public Club(String cId, String cName, String cRegNo, String cEmail, String cFacAd) {
        this.cId = cId;
        this.cName = cName;
        this.cRegNo = cRegNo;
        this.cEmail = cEmail;
        this.cFacAd = cFacAd;
    }

    public String getcId() {
        return cId;
    }

    public String getcName() {
        return cName;
    }

    public String getcFacAd() {
        return cFacAd;
    }

    public String getcRegNo() {
        return cRegNo;
    }

    public String getcEmail() {
        return cEmail;
    }
}
