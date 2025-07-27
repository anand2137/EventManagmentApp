package com.example.event_management;

public class Event {
    String eId;
    String eName;
    String eDate;
    long eSecond;
    String eDuration;
    String club;
    String eVenue;
    String eDisc;

    public Event() {
    }

    public Event(String eId, String eName, String eDate, long eSecond, String eDuration, String club, String eVenue, String eDisc) {
        this.eId = eId;
        this.eName = eName;
        this.eDate = eDate;
        this.eSecond = eSecond;
        this.eDuration = eDuration;
        this.club = club;
        this.eVenue = eVenue;
        this.eDisc = eDisc;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public long geteSecond() {
        return eSecond;
    }

    public void seteSecond(long eSecond) {
        this.eSecond = eSecond;
    }

    public String geteDuration() {
        return eDuration;
    }

    public void seteDuration(String eDuration) {
        this.eDuration = eDuration;
    }

    public String geteVenue() {
        return eVenue;
    }

    public void seteVenue(String eVenue) {
        this.eVenue = eVenue;
    }

    public String geteDisc() {
        return eDisc;
    }

    public void seteDisc(String eDisc) {
        this.eDisc = eDisc;
    }

    public String getClub() {
        return club;
    }
}
