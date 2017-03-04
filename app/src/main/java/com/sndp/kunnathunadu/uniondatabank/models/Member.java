package com.sndp.kunnathunadu.uniondatabank.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by akhil on 4/3/17.
 */

public class Member implements Serializable {
    private String name;
    private String houseName;
    private List<String> phoneNumbers;
    private String officialDesignation;

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getOfficialDesignation() {
        return officialDesignation;
    }

    public void setOfficialDesignation(String officialDesignation) {
        this.officialDesignation = officialDesignation;
    }
}
