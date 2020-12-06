package com.myapp.haroon.client;

import android.os.Bundle;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class otherThread extends Thread {
    public void run(){
        //System.out.print("another thread!");
        try{
            String url = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001?Authorization=CWB-F9B4D7D6-170E-4071-B87B-E8E988812246&locationName=%E6%96%B0%E7%AB%B9%E5%B8%82";
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();
            int cp;
            while((cp = rd.read())!= -1){
                sb.append((char) cp);
            }
            String weatherStr = sb.toString();
            System.out.println(sb.toString());
            Bundle carrier = new Bundle();
            carrier.putString("output", weatherStr);
            Message msg = new Message();
            msg.setData(carrier);
            MainActivity.mHandler.sendMessage(msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
