package com.example.demo;

public class DummyPayload {

    private String dummyName;
    private int dummyAge;

    public DummyPayload(String dummyName, int dummyAge) {
        this.dummyName = dummyName;
        this.dummyAge = dummyAge;
    }

    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String dummyName) {
        this.dummyName = dummyName;
    }

    public int getDummyAge() {
        return dummyAge;
    }

    public void setDummyAge(int dummyAge) {
        this.dummyAge = dummyAge;
    }
}
