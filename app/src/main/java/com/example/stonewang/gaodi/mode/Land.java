package com.example.stonewang.gaodi.mode;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class Land {
    private String name;
    private int imageId;

    public Land(String name, int imageId){
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
