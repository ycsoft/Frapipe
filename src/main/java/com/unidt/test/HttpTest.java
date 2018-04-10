package com.unidt.test;

import com.google.gson.Gson;
import com.unidt.gson.PosData;
import com.unidt.tools.kafka.FraPipe;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by admin on 2018/3/9.
 */
public class HttpTest {

    public PosData loadData(String filename) {
        PosData pd = new PosData();
        pd.topic = "andatong";
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader breader = new BufferedReader(reader);
            String line = null;
            while( (line = breader.readLine()) != null) {
                System.out.println(line);
                pd.data.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pd;
    }
    public void testPost(String body) {

        try{
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://115.159.47.253:8070/put");
            post.setRequestBody(body);
            client.executeMethod(post);

        }catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        HttpTest ht = new HttpTest();
        PosData pd = ht.loadData("PositionReport20180225000141396.csv");

        Gson gson = new Gson();
        String str = gson.toJson(pd);
        ht.testPost(str);
    }
}
