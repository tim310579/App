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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class Symptom_page3 extends AppCompatActivity {

    private Socket socket;

    OutputStream outputStream;
    private TextView miss_notice;
    String asthma, a_rhinitis, a_conjunctivitis, a_dermatitis;

    private Button btn_page2, btn_page4, btn_home;
    RadioGroup mRG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page3);

        mRG = (RadioGroup)findViewById(R.id.radiogroup_asthma);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                asthma=text.substring(0, 1);
            }
        });

        mRG = (RadioGroup)findViewById(R.id.radiogroup_a_rhinitis);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                a_rhinitis=text.substring(0, 1);
            }
        });


        btn_page2 = (Button) findViewById(R.id.btn_page2);
        btn_page2.setOnClickListener(new ButtonClickListener());

        btn_page4 = (Button) findViewById(R.id.btn_page4);
        btn_page4.setOnClickListener(new ButtonClickListener());
        //btn_home = (Button) findViewById(R.id.btn_home);
        //btn_home.setOnClickListener(new ButtonClickListener());

        miss_notice = (TextView) findViewById(R.id.tv_miss_notice);
    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_page2:
                    goto_page2();
                    break;
                case R.id.btn_page4:
                    goto_page4();
                    break;
                //case R.id.btn_home:
                //    goto_home();
                 //   break;
                default:
                    break;
            }
        }
    }
    private void goto_page2(){new goto_page2().start();}
    //private void goto_home(){new goto_home().start();}
    private void goto_page4(){new goto_page4().start();}
    class goto_page2 extends Thread {

        public goto_page2() {
        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page3.this, Symptom_page2.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_page4 extends Thread {

        public goto_page4() {
        }

        @Override
        public void run() {
            //try {
                GlobalVariable gv = (GlobalVariable) getApplicationContext();
                gv.setasthma(asthma);
                gv.seta_rhinitis(a_rhinitis);
                //gv.seta_conjunctivitis(a_conjunctivitis);
                //gv.seta_dermatitis(a_dermatitis);

                //Intent intent = new Intent();
                //intent.setClass(Symptom_page3.this, MainActivity.class);
                //startActivity(intent);
            if( gv.getasthma()==null || gv.geta_rhinitis()==null ){
                System.out.println("data missing");
                miss_notice.setText("Data missing!!");
            }
            else {
                /*
                try {
                    socket = new Socket("140.113.66.153", 8787);
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
                }*/
                Intent intent = new Intent();
                intent.setClass(Symptom_page3.this, Symptom_page4.class);
                startActivity(intent);
            }
            //}
        }
    }
}