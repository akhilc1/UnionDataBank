package com.sndp.kunnathunadu.uniondatabank.models;

import java.io.Serializable;

/**
 * Created by akhil on 4/3/17.
 */

public class Kudumbayogam implements Serializable {
    private String kudumbayogamName;
    private String convener;
    private String convenerPhone;
    private String jointConvener;
    private String jointConvenerPhone;

    public String getKudumbayogamName() {
        return kudumbayogamName;
    }

    public void setKudumbayogamName(String kudumbayogamName) {
        this.kudumbayogamName = kudumbayogamName;
    }

    public String getConvener() {
        return convener;
    }

    public void setConvener(String convener) {
        this.convener = convener;
    }

    public String getConvenerPhone() {
        return convenerPhone;
    }

    public void setConvenerPhone(String convenerPhone) {
        this.convenerPhone = convenerPhone;
    }

    public String getJointConvener() {
        return jointConvener;
    }

    public void setJointConvener(String jointConvener) {
        this.jointConvener = jointConvener;
    }

    public String getJointConvenerPhone() {
        return jointConvenerPhone;
    }

    public void setJointConvenerPhone(String jointConvenerPhone) {
        this.jointConvenerPhone = jointConvenerPhone;
    }
}
