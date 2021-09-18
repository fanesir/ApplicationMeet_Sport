package com.example.myapplication_MeetSport;

import java.io.Serializable;

public class DataModal implements Serializable {
    String sportName;
    String sportBackImage;
    String sportEngName;

    DataModal() {

    }

    public String getSportEngName() {
        return sportEngName;
    }

    public void setSportEngName(String sportEngName) {
        this.sportEngName = sportEngName;
    }

    public String getSportBackImage() {
        return sportBackImage;
    }

    public void setSportBackImage(String sportBackImage) {
        this.sportBackImage = sportBackImage;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }


}
