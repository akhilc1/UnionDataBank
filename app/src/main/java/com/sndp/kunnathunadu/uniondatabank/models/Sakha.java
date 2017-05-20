package com.sndp.kunnathunadu.uniondatabank.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 4/3/17.
 */

public class Sakha implements Serializable {
    private String sakhaName;
    private List<Member> sakhaMembers = new ArrayList<>();

    public List<Member> getSakhaMembers() {
        return sakhaMembers;
    }

    public void setSakhaMembers(List<Member> sakhaMembers) {
        this.sakhaMembers = sakhaMembers;
    }

    public String getSakhaName() {
        return sakhaName;
    }

    public void setSakhaName(String sakhaName) {
        this.sakhaName = sakhaName;
    }


}
