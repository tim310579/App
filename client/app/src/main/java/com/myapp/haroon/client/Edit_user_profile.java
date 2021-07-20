package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Edit_user_profile extends AppCompatActivity {

    private Socket socket;

    OutputStream outputStream;

    private String ID_number;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String city;
    private String ip, port;


    private Button edit_OK, male, female, Back_home, btn_setip;

    private EditText title, mEdit_gender;
    private TextView tv_ID_number;
    private EditText mEdit_ID_number;
    private TextView tv_age;
    private EditText mEdit_age;
    private TextView tv_height;
    private EditText mEdit_height;
    private TextView tv_weight;
    private EditText mEdit_weight;
    private EditText mEdit_city;

    private EditText mEdit_ip, mEdit_port;

    private TextView show_msg;

    final String[] genders = {"請選擇性別", "male", "female"};
    final String[] citys = {"請選擇縣市", "基隆市", "臺北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣"
            , "臺中市", "南投縣", "彰化縣", "雲林縣", "嘉義市", "嘉義縣", "臺南市", "高雄市", "屏東縣", "宜蘭縣",
            "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        gender = gv.getgender();
        city = gv.getcity();
        if(gv.getgender()!=null) genders[0] = genders[0].replace("請選擇性別", gender);
        if(gv.getcity()!=null) citys[0] = citys[0].replace("請選擇縣市", city);

        Spinner spinner_gender = (Spinner)findViewById(R.id.spinner_gender);
        //final String[] genders = {"請選擇性別", "male", "female"};
        ArrayAdapter<String> genderList = new ArrayAdapter<>(Edit_user_profile.this,
                android.R.layout.simple_spinner_dropdown_item,
                genders);
        spinner_gender.setAdapter(genderList);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender= genders[position];
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        edit_OK = (Button) findViewById(R.id.edit_OK);
        //male = (Button) findViewById(R.id.male);
        //female = (Button) findViewById(R.id.female);
        Back_home = (Button) findViewById(R.id.Back_home);
        //btn_setip = (Button) findViewById(R.id.setip);

        tv_ID_number = (TextView) findViewById(R.id.tv_ID_number);
        mEdit_ID_number = (EditText) findViewById(R.id.mEdit_ID_number);

        tv_age = (TextView) findViewById(R.id.tv_age);
        mEdit_age = (EditText) findViewById(R.id.mEdit_age);
        tv_height = (TextView) findViewById(R.id.tv_height);
        mEdit_height = (EditText) findViewById(R.id.mEdit_height);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        mEdit_weight = (EditText) findViewById(R.id.mEdit_weight);

        //mEdit_ip = (EditText) findViewById(R.id.mEdit_ip);
        //mEdit_port = (EditText) findViewById(R.id.mEdit_port);

        if(gv.getID_number()!=null) mEdit_ID_number.setText(" "+gv.getID_number());
        if(gv.getage()!=null) mEdit_age.setText(" "+gv.getage());
        if(gv.getheight()!=null) mEdit_height.setText(" "+gv.getheight());
        if(gv.getweight()!=null) mEdit_weight.setText(" "+gv.getweight());
        //if(gv.getip()!=null) mEdit_ip.setText(" "+gv.getip());
        //if(gv.getport()!=null) mEdit_port.setText(" "+gv.getport());

        edit_OK.setOnClickListener(new ButtonClickListener());
        //male.setOnClickListener(new ButtonClickListener());
        //female.setOnClickListener(new ButtonClickListener());
        Back_home.setOnClickListener(new ButtonClickListener());
        //btn_setip.setOnClickListener(new ButtonClickListener());

        //gender = gv.getgender();

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //final String[] citys = {"請選擇縣市", "基隆市", "臺北市", "新北市", "桃園市", "臺中市", "南投縣", "彰化縣",
        //        "雲林縣", "嘉義市", "嘉義縣", "臺南市", "高雄市", "屏東縣", "宜蘭縣",
         //       "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣", "新竹市", "新竹縣", "苗栗縣"};
        ArrayAdapter<String> cityList = new ArrayAdapter<>(Edit_user_profile.this,
                android.R.layout.simple_spinner_dropdown_item,
                citys);
        spinner.setAdapter(cityList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = citys[position];
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_OK:
                    edit_OK();
                    break;

                case R.id.Back_home:
                    Back_home();
                    break;
                default:
                    break;
            }
        }
    }

    private void scanPorts() {
        //new ScanPorts(1300,1400).start();
    }
    private void edit_OK(){new edit_OK().start();}
    private void Back_home(){new Back_home().start();}

    class Back_home extends Thread{
        public Back_home(){

        }
        @Override
        public void run(){
            Intent intent = new Intent();
            intent.setClass(Edit_user_profile.this  , MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }

    class edit_OK extends Thread {

        public edit_OK() {

        }

        @Override
        public void run() {
            GlobalVariable gv = (GlobalVariable) getApplicationContext();
            if (TextUtils.isEmpty(mEdit_ID_number.getText())){ ID_number = gv.getID_number(); }// ID is null
            else ID_number = mEdit_ID_number.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_age.getText())){ age = gv.getage(); }// ID is null
            else age = mEdit_age.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_height.getText())){ height = gv.getheight(); }// ID is null
            else height = mEdit_height.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_weight.getText())){ weight = gv.getweight(); }// ID is null
            else weight = mEdit_weight.getText().toString().trim();

            /*if (TextUtils.isEmpty(mEdit_ip.getText())){ ip = "140.113.86.106"; }// ID is null
            else ip = mEdit_ip.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_port.getText())){ port = "10070"; }// ID is null
            else port = mEdit_port.getText().toString().trim();*/

            gv.setID_number(ID_number);
            gv.setage(age);
            gv.setgender(gender);
            gv.setheight(height);
            gv.setweight(weight);
            gv.setcity(city);
            //gv.setip(ip);
            //int tmp = Integer.parseInt(port);
            //gv.setport(port);
            //System.out.println(ip);
            //System.out.println(port);
            //float ref = Integer.valueOf()

            if(gv.getID_number()==null || gv.getage()==null || gv.getgender()=="請選擇性別"
                    || gv.getheight()==null || gv.getweight()==null || gv.getcity()=="請選擇縣市"){
                System.out.println("data missing");
            }
            else {
                try {
                    //if(gv.getip()!=null && gv.getport()!=null) {    //have set ip and port
                    socket = new Socket(gv.getip(), Integer.parseInt(gv.getport()));
                    //}
                    //else{   //not set ip, port
                    //socket = new Socket("140.113.86.106", 10070);
                        //socket = new Socket("140.113.66.153", 8787);
                    //}
                    //socket = new Socket("140.113.86.106", 10077);
                    //socket = new Socket (InetAddress.getByName("http://11f9ed8bf747.ngrok.io").getHostAddress(), 8787);
                    //socket.connect("https://d9e1864693bf.ngrok.io", 8787);
                    //socket = new Socket(InetAddress.getByName("https://d9e1864693bf.ngrok.io").getHostAddress(), 8787);

                    //socket = new Socket("192.168.1.138", 1312);
                    //socket.connect(socketAddress, 50);
                    outputStream = socket.getOutputStream();
                    String send_gender = gv.getgender() == "male" ? "m" : "f";
                    outputStream.write(("register " + gv.getID_number() + " " + send_gender + " " + gv.getage() + " " +
                            gv.getheight() + " " + gv.getweight() + " " + gv.getcity()).getBytes("utf-8"));
                    TimeUnit.SECONDS.sleep(1);
                    outputStream.write(("exit").getBytes("utf-8"));

                    outputStream.flush();
                    socket.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(Edit_user_profile.this, MainActivity.class);
                startActivity(intent);
                //finish();
                //socket.close();
                //}
            }
        }
    }
}