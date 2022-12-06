package com.example.planner;

public class Category {
    private String name;
    private String info;

    public Category(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
