package com.example.madooding.healthpy.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by madooding on 11/14/2016 AD.
 */

public class UserData implements Serializable{
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
    private Date birthDate;

    public UserData(String name, String lastName, String email, String sex, int birthDay, int birthMonth, int birthYear, int weight, int height, List<String> uneatable, List<String> congenitalDisease, String fb_id, String profileImgURI){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.weight = weight;
        this.height = height;
        this.uneatable = uneatable;
        this.congenitalDisease = congenitalDisease;
        this.fb_id = fb_id;
        this.profileImgURI = profileImgURI;
    }


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

    public boolean isVegetarian() {return isVegetarian; }

    public void setVegetarian(boolean vegetarian){ isVegetarian = vegetarian; }

    public Date getBirthDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return simpleDateFormat.parse(Integer.toString(birthDay + 1) + "/" + Integer.toString(birthMonth) + "/" + Integer.toString(birthYear));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public List<String> getUneatable(){
        return uneatable;
    }

    public void setUneatable(List<String> uneatableList){
        this.uneatable = uneatableList;
    }

    public List<String> getCongenitalDisease(){
        return  congenitalDisease;
    }

    public void setCongenitalDisease(List<String> congenitalDiseaseList){
        this.congenitalDisease = congenitalDiseaseList;
    }

}
