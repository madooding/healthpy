package com.example.madooding.healthpy.model;

/**
 * Created by madooding on 11/8/2016 AD.
 */

public class NutritionType {
    private String name;
    private float value;
    private int color;

    public NutritionType(String name, float value, int color){
        this.setName(name);
        this.setValue(value);
        this.setColor(color);
    }


    public String getThaiName(){
        switch (this.name){
            case "protein": return "โปรตีน";
            case "carbohydrate": return  "คาร์โบไฮเดรต";
            case "fat": return "ไขมันทั้งหมด";
            case "cholesterol": return "โคเลสเตอรอล";
            case "sodium": return "โซเดียม" ;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
