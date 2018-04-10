package com.unidt.gson;

import com.google.gson.Gson;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/9.
 */
public class PosData {
    public String topic = "";
    public List<String> data =  new ArrayList<String>();

    public static void main(String[] args) {
        Gson gson = new Gson();
        PosData pd = new PosData();
        pd.topic = "andatong";
        pd.data.add("9090,2018-03-12 10:09:21 120 27");
        pd.data.add("9090,2018-03-12 10:09:28 110 20");
        pd.data.add("9090,2018-03-12 10:10:11 100 10");
        String json = gson.toJson(pd);

        PosData pd2 = gson.fromJson(json, PosData.class);
        System.out.println(json);
        System.out.println(pd2.data);
    }
}
