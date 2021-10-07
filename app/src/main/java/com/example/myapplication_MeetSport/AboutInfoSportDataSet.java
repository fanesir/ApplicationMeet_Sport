package com.example.myapplication_MeetSport;

import java.io.Serializable;

public class AboutInfoSportDataSet implements Serializable {



    String sportTitle, sportContent, howManyMan, whoPay, sportStartTime, sportEndTime, Map, userEmail, fuzzyID, Mapid, UserHostName;

    AboutInfoSportDataSet() {

    }

    public String getMapid() {
        return Mapid;
    }

    public void setMapid(String mapid) {
        this.Mapid = mapid;
    }


    public String getFuzzyID() {
        return fuzzyID;
    }

    public void setFuzzyID(String fuzzyID) {
        this.fuzzyID = fuzzyID;
    }


    public String getUserHostName() {
        return UserHostName;
    }

    public void setUserHostName(String userHostName) {
        UserHostName = userHostName;
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

