package com.sndp.kunnathunadu.uniondatabank.greenrobot.events;

/**
 * Created by akhil on 26/2/17.
 */

public class ShowSakhaDetailsEvents {
    private String sakhaName;

    public ShowSakhaDetailsEvents(String sakhaName) {
        this.sakhaName = sakhaName;
    }

    public String getSakhaName() {
        return sakhaName;
    }
}
