package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    public static String textView;
    public static TextView wea;
    private Button app_info, look_profile, fill_in_symptom, goto_predict, history;
    //private Button btn_Notify;
    //long[] vibrate = {0,100,200,300};
    //音樂Uri參數
    //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GlobalVariable gv = (GlobalVariable) getApplicationContext();
        //gv.setip("140.113.66.153");
        //gv.setport(8787);
        //wea = findViewById(R.id.weather);
        //Thread t1 = new otherThread();
        //t1.start();

        //app_info = (Button) findViewById(R.id.app_info);
        look_profile = (Button) findViewById(R.id.look_profile);
        fill_in_symptom = (Button) findViewById(R.id.fill_in_symptom);
        goto_predict = (Button) findViewById(R.id.goto_predict);
        history = (Button) findViewById(R.id.history);

        //app_info.setOnClickListener(new ButtonClickListener());
        look_profile.setOnClickListener(new ButtonClickListener());
        fill_in_symptom.setOnClickListener(new ButtonClickListener());
        goto_predict.setOnClickListener(new ButtonClickListener());
        history.setOnClickListener(new ButtonClickListener());

        //btn_Notify = (Button)findViewById(R.id.btn_Notify);
        //btn_Notify.setOnClickListener(new ButtonClickListener());

    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //case R.id.app_info:
                    //app_info();
                    //break;
                case R.id.look_profile:
                    look_profile();
                    break;
                case R.id.fill_in_symptom:
                    fill_in_symptom();
                    break;
                case R.id.goto_predict:
                    goto_predict();
                    break;
                case R.id.history:
                    history();
                    break;
                //case R.id.btn_Notify:
                    //notice_sth();
                    //break;
                default:
                    break;
            }
        }
    }

    private void scanPorts() {
        //new ScanPorts(1300,1400).start();
    }
    //private void app_info(){new app_info().start();}
    private void look_profile(){new look_profile().start();}
    private void fill_in_symptom(){new fill_in_symptom().start();}
    private void goto_predict(){new goto_predict().start();}
    private void history(){new history().start();}
    //private void notice_sth(){new notice_sth().start();}

    /*class app_info extends Thread{

        public app_info(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, App_info.class);
            startActivity(intent);
            //finish();
        }
    }*/

    class look_profile extends Thread{

        public look_profile(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Edit_user_profile.class);
            startActivity(intent);
            //finish();
        }
    }
    class fill_in_symptom extends Thread{

        public fill_in_symptom(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Choose_which_day.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_predict extends Thread{

        public goto_predict(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Result_predict.class);
            startActivity(intent);
            //finish();
        }
    }
    class history extends Thread{

        public history(){
        }
        @Override
        public void run() {
            //GlobalVariable gv = (GlobalVariable) getApplicationContext();   //global var
            //gv.setWeather(textView);

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, today_weather_and_psi.class);
            startActivity(intent);
            //finish();
        }
    }

    public static Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle gotMsg = msg.getData();
            textView = (gotMsg.getString("output"));
            //wea.setText(textView);
        }
    };


}