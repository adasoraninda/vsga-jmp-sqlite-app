package com.adasoranina.aplikasisqlite.model;

import java.io.Serializable;

public class Data implements Serializable {
    private int id;
    private String name;
    private String address;

    public Data(){}

    public Data(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getStringId() {
        return String.valueOf(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
