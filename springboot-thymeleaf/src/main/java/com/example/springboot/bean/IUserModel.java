package com.example.springboot.bean;

public class IUserModel {
    private Long id;
    private String name;
    private int age;

    public IUserModel(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public IUserModel() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

}
