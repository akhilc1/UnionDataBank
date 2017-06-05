package com.sndp.kunnathunadu.uniondatabank.greenrobot.events;

import com.sndp.kunnathunadu.uniondatabank.models.Member;

/**
 * Created by akhil on 27/5/17.
 */

public class ShowEditMemberLayoutEvent {
    private int position;
    private Member member;
    private String sakhaName;

    public ShowEditMemberLayoutEvent(int position, Member member, String sakhaName) {
        this.position = position;
        this.member = member;
        this.sakhaName = sakhaName;
    }

    public String getSakhaName() {
        return sakhaName;
    }

    public void setSakhaName(String sakhaName) {
        this.sakhaName = sakhaName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
