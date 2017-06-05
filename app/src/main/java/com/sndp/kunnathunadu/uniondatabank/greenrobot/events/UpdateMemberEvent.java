package com.sndp.kunnathunadu.uniondatabank.greenrobot.events;

import com.sndp.kunnathunadu.uniondatabank.models.Sakha;

/**
 * Created by akhil on 27/5/17.
 */

public class UpdateMemberEvent {
    int position;
    Sakha sakha;

    public UpdateMemberEvent(Sakha sakha, int position) {
        this.sakha = sakha;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Sakha getSakha() {
        return sakha;
    }

    public void setSakha(Sakha sakha) {
        this.sakha = sakha;
    }
}
