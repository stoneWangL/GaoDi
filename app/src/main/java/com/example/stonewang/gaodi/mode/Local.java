package com.example.stonewang.gaodi.mode;

/**
 * Created by stoneWang on 2017/4/8.
 */

public class Local {
    private String name;
    private int imageId;
    private String introduce;

    public Local (String name, int imageId, String introduce){
        this.name = name;
        this.imageId = imageId;
        this.introduce = introduce;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
