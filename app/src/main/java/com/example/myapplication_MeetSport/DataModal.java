package com.example.myapplication_MeetSport;

import java.io.Serializable;

public class DataModal implements Serializable {
    String sportName, sportBackImage;
    DataModal() {

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
