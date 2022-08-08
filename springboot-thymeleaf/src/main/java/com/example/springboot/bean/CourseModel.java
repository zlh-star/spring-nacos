package com.example.springboot.bean;

public class CourseModel {
    private int id;
    private String name;
    private String address;

    public CourseModel(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public CourseModel() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
