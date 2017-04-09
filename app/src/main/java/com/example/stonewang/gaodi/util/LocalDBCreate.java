package com.example.stonewang.gaodi.util;


import com.example.stonewang.gaodi.R;
import com.example.stonewang.gaodi.db.LandArmyDescribe;

/**
 * Created by stoneWang on 2017/4/9.
 */

public class LocalDBCreate {

    public static void CreatedLand(){
        LandArmyDescribe landArmyDescribe = new LandArmyDescribe();

        landArmyDescribe.setName("59式中型坦克");
        landArmyDescribe.setImageId("R.drawable.land_tank59");
        landArmyDescribe.setBaseParameter("59基本参数");
        landArmyDescribe.setHistoricalBackground("1959年生产出首批59式中型坦克，并完成了生产定型。该坦克的主要性能指标和结构与苏式T54A中型坦克相同。60年代初，新型装甲钢的研制与应用为大批量生产59式中型坦克创造了条件。70年代，根据部队的反映及坦克技术的发展，逐步开展对59式中型坦克的改进，其间进行了很多试验型样车的研制，完成了59-1、59-2和59-2A式中型坦克的发展工作。80年代，59式中型坦克批量生产已经停止，但作为T式坦克的改进和现代化工作仍在继续");
        landArmyDescribe.setDetailedInstroduction("59详细介绍");
        landArmyDescribe.setSumUp("总结");
        landArmyDescribe.save();

        landArmyDescribe.setName("69式中型坦克");
        landArmyDescribe.setImageId("R.drawable.land_tank69");
        landArmyDescribe.setBaseParameter("69基本参数");
        landArmyDescribe.setHistoricalBackground("69式是在59式坦克的基础上自行改进设计的中型坦克。1963年下达战技指标，1964年完成设计，1964年生产样车，1974年设计定型。69式坦克装有激光测距仪，炮长夜视瞄准镜，车长昼夜观察 指挥镜和火炮双向稳定器");
        landArmyDescribe.setDetailedInstroduction("69详细介绍");
        landArmyDescribe.setSumUp("总结");
        landArmyDescribe.save();
    }

}
