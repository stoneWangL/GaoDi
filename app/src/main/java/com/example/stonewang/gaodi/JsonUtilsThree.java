package com.example.stonewang.gaodi;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by stoneWang on 2017/3/4.
 */

public class JsonUtilsThree {

    public void parseUserFromJson(String jsonData){
        Type listType = new TypeToken<LinkedList<User>>(){}.getType();
        Gson gson = new Gson();
        LinkedList<User> users = gson.fromJson(jsonData, listType);
        for (Iterator iterator = users.iterator(); iterator.hasNext(); ){
            User user = (User) iterator.next();
            Log.d("TestGsonThree","name--->"+user.getName());
            Log.d("TestGsonThree", "age--->"+user.getAge());
        }
    }
}
