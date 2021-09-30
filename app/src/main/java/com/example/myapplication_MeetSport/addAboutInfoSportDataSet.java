package com.example.myapplication_MeetSport;

import java.io.Serializable;

public class addAboutInfoSportDataSet implements Serializable {

    String sportTitle;
    String sportContent;
    String howManyMan;
    String whoPay;
    String sportStartTime;
    String sportEndTime;
    String Map;
    String userEmail;
    String fuzzyID;

    public String getFuzzyID() {
        return fuzzyID;
    }

    public void setFuzzyID(String fuzzyID) {
        this.fuzzyID = fuzzyID;
    }


    addAboutInfoSportDataSet() {

    }


    public String getSportTitle() {
        return sportTitle;
    }

    public void setSportTitle(String sportTitle) {
        this.sportTitle = sportTitle;
    }

    public String getSportContent() {
        return sportContent;
    }

    public void setSportContent(String sportContent) {
        this.sportContent = sportContent;
    }

    public String getHowManyMan() {
        return howManyMan;
    }

    public void setHowManyMan(String howManyMan) {
        this.howManyMan = howManyMan;
    }

    public String getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(String whoPay) {
        this.whoPay = whoPay;
    }

    public String getSportStartTime() {
        return sportStartTime;
    }

    public void setSportStartTime(String sportStartTime) {
        this.sportStartTime = sportStartTime;
    }

    public String getSportEndTime() {
        return sportEndTime;
    }

    public void setSportEndTime(String sportEndTime) {
        this.sportEndTime = sportEndTime;
    }

    public String getMap() {
        return Map;
    }

    public void setMap(String map) {
        Map = map;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
