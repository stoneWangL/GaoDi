package com.example.stonewang.gaodi;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by stoneWang on 2017/3/3.
 */

public class JsonUtilsTwo {

    public void parseUserFromJson(String jsonData){
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        Log.d("TestGsonTwo","name--->"+user.getName());
        Log.d("TestGsonTwo", "age--->"+user.getAge());
    }


}
