package com.example.stonewang.gaodi.db;

import com.example.stonewang.gaodi.gson.GaoDiNews;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by stoneWang on 2017/3/5.
 */

public class NewsList extends DataSupport {
    private List<GaoDiNews> NewsList;

    public List<GaoDiNews> getNewsList() {
        return NewsList;
    }

    public void setNewsList(List<GaoDiNews> newsList) {
        NewsList = newsList;
    }
}
