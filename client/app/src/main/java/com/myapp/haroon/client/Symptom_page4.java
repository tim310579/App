package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class Symptom_page4 extends AppCompatActivity {
    private String pre_day_rec, pre_night_rec;
    private Socket socket;
    private TextView miss_notice;
    OutputStream outputStream;
    //String pre_night_symptom, pre_day_symptom, pre_nose_symptom1, pre_nose_symptom2, pre_nose_symptom3, pre_nose_symptom4;
    String a_conjunctivitis, a_dermatitis;

    private TextView tv_pre_day_rec, tv_pre_night_rec;
    private EditText mEdit_pre_day_rec, mEdit_pre_night_rec;

    private Button btn_page3, btn_page5, btn_home;


    RadioGroup mRG, radiogroup_pre_night_symptom, radiogroup_pre_day_symptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page4);

        mRG = (RadioGroup) findViewById(R.id.radiogroup_a_conjunctivitis);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                a_conjunctivitis = text.substring(0, 1);
            }
        });
        mRG = (RadioGroup) findViewById(R.id.radiogroup_a_dermatitis);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                a_dermatitis = text.substring(0, 1);
            }
        });



        btn_page3 = (Button) findViewById(R.id.btn_page3);
        btn_page3.setOnClickListener(new ButtonClickListener());

        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new ButtonClickListener());
        //btn_page5 = (Button) findViewById(R.id.btn_page5);
        //btn_page5.setOnClickListener(new ButtonClickListener());
        miss_notice = (TextView) findViewById(R.id.tv_miss_notice);
    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_page3:
                    goto_page3();
                    break;
                case R.id.btn_home:
                    goto_home();
                    break;
                default:
                    break;
            }
        }
    }
    private void goto_page3(){new goto_page3().start();}
    private void goto_home(){new goto_home().start();}
    class goto_page3 extends Thread {

        public goto_page3() {

        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page4.this, Symptom_page3.class);
            startActivity(intent);
            //finish();
        }

    }
    class goto_home extends Thread{

        public goto_home(){

        }

        @Override
        public void run(){

            //try {
                GlobalVariable gv = (GlobalVariable) getApplicationContext();
                gv.seta_conjunctivitis(a_conjunctivitis);
                gv.seta_dermatitis(a_dermatitis);

            if( gv.geta_conjunctivitis()==null || gv.geta_dermatitis()==null){
                System.out.println("data missing");
                miss_notice.setText("Data missing!!");
            }
            else {

                try {
                    socket = new Socket("140.113.66.153", 8787);
                    //socket = new Socket(InetAddress.getByName("https://d9e1864693bf.ngrok.io").getHostAddress(), 8787);
                    //socket = new Socket (InetAddress.getByName("http://11f9ed8bf747.ngrok.io").getHostAddress(), 8787);

                    //Socket socket = new Socket("192.168.1.138", 1311);
                    //socket.connect(socketAddress, 50);
                    outputStream = socket.getOutputStream();
                    //OutputStream outputStream;

                    //Socket socket = new Socket();
                    //SocketAddress socketAddress = new InetSocketAddress("192.168.1.138", 1311);
                    //socket.connect(socketAddress, 50);
                    //outputStream = socket.getOutputStream();
                    outputStream.write(("input " + gv.getID_number() + " " + gv.getdate() + " "// + gv.getgender() + ", " + gv.getage() + ", " + gv.getheight() + ", " + gv.getweight() + ", "
                            + gv.getnight_rec() + " " + gv.getday_rec() + " " //+ gv.getnight_symptom() + ", " + gv.getday_symptom() + ", "
                            + gv.getnose_symptom1() + " " + gv.getnose_symptom2() + " " + gv.getnose_symptom3() + " " + gv.getnose_symptom4() + " "
                            + gv.getskin_symptom() + " " + gv.geteye_symptom() + " " + gv.getday_symptom() + " "
                            + gv.getasthma() + " " + gv.geta_rhinitis() + " "
                            + gv.geta_dermatitis() + " " + gv.geta_conjunctivitis()).getBytes("utf-8"));
                    TimeUnit.SECONDS.sleep(1);
                    outputStream.write(("exit").getBytes("utf-8"));

                    outputStream.flush();

                    socket.close();


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(Symptom_page4.this, MainActivity.class);
                startActivity(intent);
                //finish();

                //}
                //catch (IOException e){
                //   e.printStackTrace();
                //}
            }
        }
    }
}