package com.example.madooding.healthpy.model;

import java.util.List;

/**
 * Created by madooding on 11/14/2016 AD.
 */

public class UserData {
    private String name;
    private String lastName;
    private String email;
    private String fb_id;
    private String profileImgURI;
    private String sex;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private int weight;
    private int height;
    private boolean isRegistered = false;
    private boolean isVegetarian = false;
    private List<String> uneatable, congenitalDisease;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getProfileImgURI() {
        return profileImgURI;
    }

    public void setProfileImgURI(String profileImgURI) {
        this.profileImgURI = profileImgURI;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
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

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
