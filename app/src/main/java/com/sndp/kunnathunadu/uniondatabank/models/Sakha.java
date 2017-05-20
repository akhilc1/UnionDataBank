package com.sndp.kunnathunadu.uniondatabank.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by akhil on 4/3/17.
 */

public class Sakha implements Serializable {
    private String sakhaName;
    private Member president;
    private Member vicePresident;
    private Member secretary;
    private Member unionCommittee;
    private List<Member> committeeMembers;
    private List<Member> panchyathCommittee;
    private List<Kudumbayogam> kudumbayogamList;

    public String getSakhaName() {
        return sakhaName;
    }

    public void setSakhaName(String sakhaName) {
        this.sakhaName = sakhaName;
    }

    public List<Kudumbayogam> getKudumbayogamList() {
        return kudumbayogamList;
    }

    public void setKudumbayogamList(List<Kudumbayogam> kudumbayogamList) {
        this.kudumbayogamList = kudumbayogamList;
    }

    public List<Member> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<Member> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

    public List<Member> getPanchyathCommittee() {
        return panchyathCommittee;
    }

    public void setPanchyathCommittee(List<Member> panchyathCommittee) {
        this.panchyathCommittee = panchyathCommittee;
    }

    public Member getPresident() {
        return president;
    }

    public void setPresident(Member president) {
        this.president = president;
    }

    public Member getVicePresident() {
        return vicePresident;
    }

    public void setVicePresident(Member vicePresident) {
        this.vicePresident = vicePresident;
    }

    public Member getSecretary() {
        return secretary;
    }

    public void setSecretary(Member secretary) {
        this.secretary = secretary;
    }

    public Member getUnionCommittee() {
        return unionCommittee;
    }

    public void setUnionCommittee(Member unionCommittee) {
        this.unionCommittee = unionCommittee;
    }
}
