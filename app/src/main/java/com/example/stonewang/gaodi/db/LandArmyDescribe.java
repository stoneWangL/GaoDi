package com.example.stonewang.gaodi.db;

import org.litepal.crud.DataSupport;

/**
 * Created by stoneWang on 2017/4/9.
 */

public class LandArmyDescribe extends DataSupport{
    String name;//武器名
    String imageId;//图片
    String baseParameter;//基本参数
    String historicalBackground;//研发历史背景
    String detailedInstroduction;//详细介绍
    String sumUp; //总结

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getBaseParameter() {
        return baseParameter;
    }

    public void setBaseParameter(String baseParameter) {
        this.baseParameter = baseParameter;
    }

    public String getHistoricalBackground() {
        return historicalBackground;
    }

    public void setHistoricalBackground(String historicalBackground) {
        this.historicalBackground = historicalBackground;
    }

    public String getDetailedInstroduction() {
        return detailedInstroduction;
    }

    public void setDetailedInstroduction(String detailedInstroduction) {
        this.detailedInstroduction = detailedInstroduction;
    }

    public String getSumUp() {
        return sumUp;
    }

    public void setSumUp(String sumUp) {
        this.sumUp = sumUp;
    }
}
