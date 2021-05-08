package com.example.caleo_app.models;

public class User {

    private String name;
    private int age;
    private int weight;
    private String gender;
    private int height;
    private int calCount;
    private int calGoal;

//    public User(String name, int age, int weight, int height, int calGoal) {
//        this.name = name;
//        this.age = age;
//        this.weight = weight;
//        this.height = height;
//        this.calGoal = calGoal;
//    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCalCount() {
        return calCount;
    }

    public void setCalCount(int calCount) {
        this.calCount = calCount;
    }

    public int getCalGoal() {
        return calGoal;
    }

    public void setCalGoal(int calGoal) {
        this.calGoal = calGoal;
    }
}
