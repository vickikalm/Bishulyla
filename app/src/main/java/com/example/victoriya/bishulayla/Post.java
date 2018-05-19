package com.example.victoriya.bishulayla;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Victoriya on 3/10/2018.
 */
@IgnoreExtraProperties
public class Post {
    public String key;
    public String uid;
    public String name;
    public String phone;
    public String meatmilk;
    public String title;
    public String bought;
    public List<Prod> shoppingList;

    public Post(String key, String uid, String name, String phone,String meatmilk, String title, String bought, List<Prod> lst) {
        this.key = key;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.meatmilk = meatmilk;
        this.title = title;
        this.bought = bought;
        this.shoppingList=lst;
    }

    public Post() {
    }
    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result=new HashMap<>();
        result.put("name", name);
        result.put("phone", phone);
        result.put("shoppingList", shoppingList);

        return result;


    }
}
