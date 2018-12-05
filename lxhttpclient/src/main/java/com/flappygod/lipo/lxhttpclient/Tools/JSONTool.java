/*
 * Copyright 2013 The JA-SIG Collaborative. All rights reserved.
 * distributed with this file and available online at
 * http://www.etong.com/
 */
package com.flappygod.lipo.lxhttpclient.Tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author:李俊霖
 * @version:2015-5-6下午2:26:43
 * @since 1.0
 */
public class JSONTool {



    /***************************
     * @param hash hashMap
     * @return
     */
    public static JSONObject LinkedMapToJson(LinkedTreeMap hash) throws JSONException {
        JSONObject jb = new JSONObject();
        Iterator<String> keys = hash.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (hash.get(key) instanceof HashMap) {
                jb.put(key, HashMapToJson((HashMap) hash.get(key)));
            } else if (hash.get(key) instanceof LinkedTreeMap) {
                jb.put(key, LinkedMapToJson((LinkedTreeMap) hash.get(key)));
            } else if (hash.get(key) instanceof ArrayList) {
                JSONArray array = ArrayListToJson((ArrayList) hash.get(key));
                jb.put(key, array);
            } else {
                jb.put(key, hash.get(key));
            }
        }
        return jb;
    }


    /*************
     * hashMap转换为JSon对象
     * @param hash
     * @return
     */
    public static JSONObject HashMapToJson(HashMap hash) throws JSONException{
        JSONObject jb = new JSONObject();
        Iterator<String> keys = hash.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (hash.get(key) instanceof HashMap) {
                jb.put(key, HashMapToJson((HashMap) hash.get(key)));
            } else if (hash.get(key) instanceof LinkedTreeMap) {
                jb.put(key, LinkedMapToJson((LinkedTreeMap) hash.get(key)));
            } else if (hash.get(key) instanceof ArrayList) {
                JSONArray array = ArrayListToJson((ArrayList) hash.get(key));
                jb.put(key, array);
            } else {
                jb.put(key, hash.get(key));
            }
        }
        return jb;
    }


    /********
     * 列表转换为jsonArray
     * @param list
     * @return
     */
    private static JSONArray ArrayListToJson(ArrayList list) throws JSONException {
        List datalist = (ArrayList) list;
        JSONArray array = new JSONArray();
        for (int s = 0; s < datalist.size(); s++) {
            if (datalist.get(s) instanceof HashMap) {
                array.put( HashMapToJson((HashMap) datalist.get(s)));
            }else  if (datalist.get(s) instanceof LinkedTreeMap) {
                array.put(LinkedMapToJson((LinkedTreeMap) datalist.get(s)));
            } else if (datalist.get(s) instanceof ArrayList) {
                array.put(ArrayListToJson((ArrayList) datalist.get(s)));
            } else {
                array.put(datalist.get(s));
            }
        }
        return array;
    }



}
