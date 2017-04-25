package com.example.stonewang.gaodi.mode;

/**
 * Created by stoneWang on 2017/4/25.
 */

public class Airforce {
    private String name;
    private int imageId;

    public Airforce(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
