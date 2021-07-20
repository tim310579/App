package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class Symptom_page2_yesterday extends AppCompatActivity {
    private Socket socket;

    OutputStream outputStream;

    String pre_nose_symptom4, pre_eye_symptom, pre_skin_symptom;
    String pre_asthma, pre_a_rhinitis, pre_a_conjunctivitis, pre_a_dermatitis;
    private TextView miss_notice;
    private TextView tv_nose_symptom1, tv_nose_symptom2, tv_nose_symptom3, tv_nose_symptom4;

    private Button btn_page1, btn_home, btn_home_real;

    /*final String[] pre_nose_symptom4s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};

    final String[] pre_eye_symptoms = {"請選擇", "0(Normal)", "1(Not Comfortable)", "2(Photophobia)"};
    final String[] pre_skin_symptoms = {"請選擇", "0(Normal)", "1(Itchy)", "2(Mild Swollen)", "3(Swollen over two parts)"};
    final String[] pre_asthmas = {"請選擇", "0(No need to take drugs)", "1(Use controller or take controller pills )", "2(Use controller and take controller pills )",
            "3(Take reliever 4 time a day until stridor is cured)", "4(Need emergency aid)"};
    final String[] pre_a_rhinitiss = {"請選擇", "0(Normal)", "1(Mild)", "2(Apply nose pharmaceutical preparation)", "3(Take anti-allergy pills and apply nose pharmaceutical preparation)"};
    final String[] pre_a_conjunctivitiss = {"請選擇", "0(Normal)", "1(Allergy)", "2(Chronicle allergy and irritation)"};
    final String[] pre_a_dermatitiss = {"請選擇", "0(Normal)", "1(Mild)", "2(Allergy)", "3(chronicle allergy)"};
    */
    final String[] pre_nose_symptom4s = {"請選擇", "正常", "輕微", "中度以上"};
    final String[] pre_eye_symptoms = {"請選擇", "正常", "些微不舒服", "畏光"};
    final String[] pre_skin_symptoms = {"請選擇", "正常", "發癢", "輕度腫脹", "兩部分以上腫脹"};
    final String[] pre_asthmas = {"請選擇", "不需服藥 ", "吸入保養藥或早晚口服保養藥(擇一)", "吸入保養藥再加上早晚口服保養藥",
            "立即服用口服天四次急救藥，直至刻嗽喘鳴消失 ", "需急診治療"};
    final String[] pre_a_rhinitiss = {"請選擇", "正常：不需服藥", "輕微：做運動，喝溫熱開水", "中等：需噴鼻藥保養", "嚴重：需口服抗過敏治療，再加噴鼻藥保養"};
    final String[] pre_a_conjunctivitiss = {"請選擇", "正常", "過敏發作", "長期過敏發作"};
    final String[] pre_a_dermatitiss = {"請選擇", "正常", "輕微敏感 ", "過敏症狀發作", "長期過敏"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page2_yesterday);

        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        pre_nose_symptom4 = gv.get_pre_nose_symptom4();
        pre_eye_symptom = gv.get_pre_eye_symptom();
        pre_skin_symptom = gv.get_pre_skin_symptom();
        pre_asthma = gv.get_pre_asthma();
        pre_a_rhinitis = gv.get_pre_a_rhinitis();
        pre_a_conjunctivitis = gv.get_pre_a_conjunctivitis();
        pre_a_dermatitis = gv.get_pre_a_dermatitis();
        String tmp = "請";
        if(gv.get_pre_nose_symptom4()!=null && tmp.compareTo(gv.get_pre_nose_symptom4())!=0){
            pre_nose_symptom4s[0] = pre_nose_symptom4s[0].replace("請選擇", pre_nose_symptom4s[Integer.parseInt(pre_nose_symptom4)+1]);
        }
        if(gv.get_pre_eye_symptom()!=null && tmp.compareTo(gv.get_pre_eye_symptom())!=0){
            pre_eye_symptoms[0] = pre_eye_symptoms[0].replace("請選擇", pre_eye_symptoms[Integer.parseInt(pre_eye_symptom)+1]);
        }
        if(gv.get_pre_skin_symptom()!=null && tmp.compareTo(gv.get_pre_skin_symptom())!=0){
            pre_skin_symptoms[0] = pre_skin_symptoms[0].replace("請選擇", pre_skin_symptoms[Integer.parseInt(pre_skin_symptom)+1]);
        }
        if(gv.get_pre_asthma()!=null && tmp.compareTo(gv.get_pre_asthma())!=0){
            pre_asthmas[0] = pre_asthmas[0].replace("請選擇", pre_asthmas[Integer.parseInt(pre_asthma)+1]);
        }
        if(gv.get_pre_a_rhinitis()!=null && tmp.compareTo(gv.get_pre_a_rhinitis())!=0){
            pre_a_rhinitiss[0] = pre_a_rhinitiss[0].replace("請選擇", pre_a_rhinitiss[Integer.parseInt(pre_a_rhinitis)+1]);
        }
        if(gv.get_pre_a_conjunctivitis()!=null && tmp.compareTo(gv.get_pre_a_conjunctivitis())!=0){
            pre_a_conjunctivitiss[0] = pre_a_conjunctivitiss[0].replace("請選擇", pre_a_conjunctivitiss[Integer.parseInt(pre_a_conjunctivitis)+1]);
        }
        if(gv.get_pre_a_dermatitis()!=null && tmp.compareTo(gv.get_pre_a_dermatitis())!=0){
            pre_a_dermatitiss[0] = pre_a_dermatitiss[0].replace("請選擇", pre_a_dermatitiss[Integer.parseInt(pre_a_dermatitis)+1]);
        }


        //4
        Spinner spinner_nose_symptom4 = (Spinner)findViewById(R.id.spinner_nose_symptom4);
        ArrayAdapter<String> nose_symptom4_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_nose_symptom4s);
        spinner_nose_symptom4.setAdapter(nose_symptom4_List);
        spinner_nose_symptom4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_nose_symptom4s[position].substring(0, 1))==0) {   //1st & no choose
                        pre_nose_symptom4 = pre_nose_symptom4s[position].substring(0, 1);
                    }
                }
                else { pre_nose_symptom4 = Integer.toString(position-1);}
                //pre_nose_symptom4 = pre_nose_symptom4s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //eye
        Spinner spinner_eye_symptom = (Spinner)findViewById(R.id.spinner_eye_symptom);
        ArrayAdapter<String> eye_symptom_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_eye_symptoms);
        spinner_eye_symptom.setAdapter(eye_symptom_List);
        spinner_eye_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_eye_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        pre_eye_symptom = pre_eye_symptoms[position].substring(0, 1);
                    }
                }
                else { pre_eye_symptom = Integer.toString(position-1);}
                //pre_eye_symptom = pre_eye_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //skin
        Spinner spinner_skin_symptom = (Spinner)findViewById(R.id.spinner_skin_symptom);
        ArrayAdapter<String> skin_symptom_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_skin_symptoms);
        spinner_skin_symptom.setAdapter(skin_symptom_List);
        spinner_skin_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_skin_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        pre_skin_symptom = pre_skin_symptoms[position].substring(0, 1);
                    }
                }
                else { pre_skin_symptom = Integer.toString(position-1);}
                //pre_skin_symptom = pre_skin_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //asthma
        Spinner spinner_asthma = (Spinner)findViewById(R.id.spinner_asthma);
        ArrayAdapter<String> asthma_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_asthmas);
        spinner_asthma.setAdapter(asthma_List);
        spinner_asthma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_asthmas[position].substring(0, 1))==0) {   //1st & no choose
                        pre_asthma = pre_asthmas[position].substring(0, 1);
                    }
                }
                else { pre_asthma = Integer.toString(position-1);}
                //pre_asthma = pre_asthmas[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //a_rhinitis
        Spinner spinner_a_rhinitis = (Spinner)findViewById(R.id.spinner_a_rhinitis);
        ArrayAdapter<String> a_rhinitis_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_a_rhinitiss);
        spinner_a_rhinitis.setAdapter(a_rhinitis_List);
        spinner_a_rhinitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_a_rhinitiss[position].substring(0, 1))==0) {   //1st & no choose
                        pre_a_rhinitis = pre_a_rhinitiss[position].substring(0, 1);
                    }
                }
                else { pre_a_rhinitis = Integer.toString(position-1);}
                //pre_a_rhinitis = pre_a_rhinitiss[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //a_con...
        Spinner spinner_a_conjunctivitis = (Spinner)findViewById(R.id.spinner_a_conjunctivitis);
        ArrayAdapter<String> a_conjunctivitis_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_a_conjunctivitiss);
        spinner_a_conjunctivitis.setAdapter(a_conjunctivitis_List);
        spinner_a_conjunctivitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_a_conjunctivitiss[position].substring(0, 1))==0) {   //1st & no choose
                        pre_a_conjunctivitis = pre_a_conjunctivitiss[position].substring(0, 1);
                    }
                }
                else { pre_a_conjunctivitis = Integer.toString(position-1);}
                //pre_a_conjunctivitis = pre_a_conjunctivitiss[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //a_der...
        Spinner spinner_a_dermatitis = (Spinner)findViewById(R.id.spinner_a_dermatitis);
        ArrayAdapter<String> a_dermatitis_List = new ArrayAdapter<>(Symptom_page2_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_a_dermatitiss);
        spinner_a_dermatitis.setAdapter(a_dermatitis_List);
        spinner_a_dermatitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_a_dermatitiss[position].substring(0, 1))==0) {   //1st & no choose
                        pre_a_dermatitis = pre_a_dermatitiss[position].substring(0, 1);
                    }
                }
                else { pre_a_dermatitis = Integer.toString(position-1);}
                //pre_a_dermatitis = pre_a_dermatitiss[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btn_page1 = (Button) findViewById(R.id.btn_page1);
        btn_page1.setOnClickListener(new ButtonClickListener());

        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new ButtonClickListener());

        btn_home_real = (Button) findViewById(R.id.btn_home_real);
        btn_home_real.setOnClickListener(new ButtonClickListener());
        //btn_page3 = (Button) findViewById(R.id.btn_page3);
        //btn_page3.setOnClickListener(new ButtonClickListener());

        miss_notice = (TextView) findViewById(R.id.tv_miss_notice);
    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_page1:
                    goto_page1();
                    break;
                case R.id.btn_home:
                    goto_home();
                    break;
                case  R.id.btn_home_real:
                    goto_home_real();
                    break;
                default:
                    break;
            }
        }
    }
    private void goto_page1(){new goto_page1().start();}
    //private void goto_page3(){new goto_page3().start();}
    private void goto_home(){new goto_home().start();}
    private void goto_home_real(){new goto_home_real().start();}
    class goto_page1 extends Thread {

        public goto_page1() {
        }

        @Override
        public void run() {

            Intent intent = new Intent();
            intent.setClass(Symptom_page2_yesterday.this, Symptom_page1_yesterday.class);
            startActivity(intent);
            //finish();
            //}
        }
    }
    class goto_home_real extends Thread{

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page2_yesterday.this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_home extends Thread{

        public goto_home(){
        }

        @Override
        public void run(){
            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            gv.set_pre_nose_symptom4(pre_nose_symptom4);
            gv.set_pre_eye_symptom(pre_eye_symptom);
            gv.set_pre_skin_symptom(pre_skin_symptom);
            gv.set_pre_asthma(pre_asthma);
            gv.set_pre_a_rhinitis(pre_a_rhinitis);
            gv.set_pre_a_conjunctivitis(pre_a_conjunctivitis);
            gv.set_pre_a_dermatitis(pre_a_dermatitis);
            String tmp = "請";
            if( tmp.compareTo(gv.get_pre_nose_symptom4())==0 || tmp.compareTo(gv.get_pre_eye_symptom())==0 || tmp.compareTo(gv.get_pre_skin_symptom())==0 || tmp.compareTo(gv.get_pre_asthma())==0
                    || tmp.compareTo(gv.get_pre_a_rhinitis())==0 || tmp.compareTo(gv.get_pre_a_conjunctivitis())==0 || tmp.compareTo(gv.get_pre_a_dermatitis())==0){

                System.out.println("data missing");
                miss_notice.setText("Data missing!!");
            }
            else {
                try {
                  //  socket = new Socket(gv.getip(), gv.getport());
                    //if(gv.getip()!=null && gv.getport()!=null) {    //have set ip and port
                        socket = new Socket(gv.getip(), Integer.parseInt(gv.getport()));
                   // }
                    //else{   //not set ip, port
                     //   socket = new Socket("140.113.86.106", 10070);
                        //socket = new Socket("140.113.66.153", 8787);
                   // }
                    //socket = new Socket("140.113.86.106", 10077);
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
                    outputStream.write(("input " + gv.getID_number() + " " + gv.get_pre_date() + " "// + gv.get_pre_gender() + ", " + gv.get_pre_age() + ", " + gv.get_pre_height() + ", " + gv.get_pre_weight() + ", "
                            + gv.get_pre_night_rec() + " " + gv.get_pre_day_rec() + " " //+ gv.get_pre_night_symptom() + ", " + gv.get_pre_day_symptom() + ", "
                            + gv.get_pre_nose_symptom1() + " " + gv.get_pre_nose_symptom2() + " " + gv.get_pre_nose_symptom3() + " " + gv.get_pre_nose_symptom4() + " "
                            + gv.get_pre_skin_symptom() + " " + gv.get_pre_eye_symptom() + " " + gv.get_pre_day_symptom() + " "
                            + gv.get_pre_asthma() + " " + gv.get_pre_a_rhinitis() + " "
                            + gv.get_pre_a_dermatitis() + " " + gv.get_pre_a_conjunctivitis()).getBytes("utf-8"));
                    TimeUnit.SECONDS.sleep(1);
                    outputStream.write(("exit").getBytes("utf-8"));

                    outputStream.flush();

                    socket.close();


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(Symptom_page2_yesterday.this, Choose_which_day.class);
                startActivity(intent);
                //finish();
                //}
            }
        }
    }
}