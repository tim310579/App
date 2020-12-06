package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class today_weather_and_psi extends AppCompatActivity {
    private Button Back_home;
    private Handler handler;

    private Handler mMainHandler;

    // Socket变量
    private Socket socket;
    // 输入流对象
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;

    OutputStream outputStream;

    private Button get_weather;
    //private String temperature, humidity, psi;
    public TextView tv_temp, tv_hum, tv_psi;
    public TextView tv_notice_fill_data_please;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_weather_and_psi);
        Back_home = (Button) findViewById(R.id.Back_home);
        Back_home.setOnClickListener(new ButtonClickListener());

        get_weather = (Button) findViewById(R.id.get_weather);
        get_weather.setOnClickListener(new ButtonClickListener());

        tv_temp = (TextView) findViewById(R.id.tv_temperature);
        tv_hum = (TextView) findViewById(R.id.tv_humidity);
        tv_psi = (TextView) findViewById(R.id.tv_PSI);
        tv_notice_fill_data_please = (TextView) findViewById(R.id.tv_notice_fill_data_please);

    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.get_weather:
                    get_the_wether();
                    break;
                case R.id.Back_home:
                    Back_home();
                    break;

                default:
                    break;
            }
        }
    }
    private void get_the_wether(){new get_the_wether().start();}
    private void Back_home(){new Back_home().start();}
    class Back_home extends Thread{
        public Back_home(){

        }
        @Override
        public void run(){
            Intent intent = new Intent();
            intent.setClass(today_weather_and_psi.this  , MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    class get_the_wether extends Thread {

        public get_the_wether() {
        }

        @Override
        public void run() {
            GlobalVariable gv = (GlobalVariable) getApplicationContext();   //global var
            try {
                //socket = new Socket(gv.getip(), gv.getport());
                socket = new Socket("140.113.66.153", 8787);
                //socket = new Socket("192.168.1.138", 1311);

                outputStream = socket.getOutputStream();

                outputStream.write(("get_weather_air "+gv.getID_number()).getBytes("utf-8"));
                TimeUnit.SECONDS.sleep(1);
                outputStream.write(("exit").getBytes("utf-8"));
                outputStream.flush();

                is = socket.getInputStream();

                // 步骤2：创建输入流读取器对象 并传入输入流对象
                // 该对象作用：获取服务器返回的数据
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                response = br.readLine();
                String miss_user_data = "No user data!";
                if(miss_user_data.compareTo(response) == 0){
                    tv_notice_fill_data_please.setText("請設定個人資料!!");
                    tv_notice_fill_data_please.setTextColor(Color.parseColor("#ff0000"));
                }
                else {
                    String[] tmp = response.split(" ");
                    String temp = tmp[0];
                    String humid = tmp[1];
                    String psi = tmp[2];

                    System.out.println(temp);
                    System.out.println(humid);

                    tv_temp.setText(" " + temp);
                    tv_hum.setText(" " + humid);
                    tv_psi.setText(" " + psi);

                    if ("良好".compareTo(psi) == 0) tv_psi.setTextColor(Color.parseColor("#0080FF"));
                    else if ("普通".compareTo(psi) == 0)
                        tv_psi.setTextColor(Color.parseColor("#00DB00"));
                    else if ("不良".compareTo(psi) == 0)
                        tv_psi.setTextColor(Color.parseColor("#FFD306"));
                    else if ("非常不良".compareTo(psi) == 0)
                        tv_psi.setTextColor(Color.parseColor("#EA0000"));
                    else tv_psi.setTextColor(Color.parseColor("#6F00D2"));
                }

                outputStream.close();
                br.close();
                socket.close();



            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            //finish();
            //}
        }
    }
}