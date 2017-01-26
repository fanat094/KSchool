package com.ks.fanat94.kschool.adapters;

import java.io.Serializable;

public class ScheduleItem implements Serializable{

     String name_les;
     String time_les;

    public ScheduleItem() {
    }

    public ScheduleItem(String name_les, String time_les) {
        this.name_les = name_les;
        this.time_les = time_les;
    }

    public String getName_Les() {
        return name_les;
    }

    public String getTime_Les() {
        return time_les;
    }


    public void setName_Les(String name_les) {
        this.name_les = name_les;
    }

    public void setLTime_Les(String time_les) {
        this.time_les = time_les;
    }

}


