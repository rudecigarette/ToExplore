package com.example.materialtest.models;

public class Word {
    private String Eng;
    private String Chi;
    public Word(String eng, String chi) {
        Eng = eng;
        Chi = chi;

    }

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }

    public String getChi() {
        return Chi;
    }

    public void setChi(String chi) {
        Chi = chi;
    }

}
