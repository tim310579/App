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

public class Symptom_page2 extends AppCompatActivity {
    private Socket socket;

    OutputStream outputStream;


    String nose_symptom4, eye_symptom, skin_symptom, grade;
    String asthma, a_rhinitis, a_conjunctivitis, a_dermatitis;
    private TextView miss_notice;
    private TextView tv_nose_symptom1, tv_nose_symptom2, tv_nose_symptom3, tv_nose_symptom4;

    private Button btn_page1, btn_home, btn_home_real;

    //final String[] nose_symptom4s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    //final String[] eye_symptoms = {"請選擇", "0(Normal)", "1(Not Comfortable)", "2(Photophobia)"};
    //final String[] skin_symptoms = {"請選擇", "0(Normal)", "1(Itchy)", "2(Mild Swollen)", "3(Swollen over two parts)"};
    //final String[] asthmas = {"請選擇", "0(No need to take drugs)", "1(Use controller or take controller pills )", "2(Use controller and take controller pills )",
    //        "3(Take reliever 4 time a day until stridor is cured)", "4(Need emergency aid)"};
    //final String[] a_rhinitiss = {"請選擇", "0(Normal)", "1(Mild)", "2(Apply nose pharmaceutical preparation)", "3(Take anti-allergy pills and apply nose pharmaceutical preparation)"};
    //final String[] a_conjunctivitiss = {"請選擇", "0(Normal)", "1(Allergy)", "2(Chronicle allergy and irritation)"};
    //final String[] a_dermatitiss = {"請選擇", "0(Normal)", "1(Mild)", "2(Allergy)", "3(chronicle allergy)"};

    final String[] nose_symptom4s = {"請選擇", "正常", "輕微", "中度以上"};
    final String[] eye_symptoms = {"請選擇", "正常", "些微不舒服", "畏光"};
    final String[] skin_symptoms = {"請選擇", "正常", "發癢", "輕度腫脹", "兩部分以上腫脹"};
    final String[] asthmas = {"請選擇", "不需服藥 ", "吸入保養藥或早晚口服保養藥(擇一)", "吸入保養藥再加上早晚口服保養藥",
            "立即服用口服天四次急救藥，直至刻嗽喘鳴消失 ", "需急診治療"};
    final String[] a_rhinitiss = {"請選擇", "正常：不需服藥", "輕微：做運動，喝溫熱開水", "中等：需噴鼻藥保養", "嚴重：需口服抗過敏治療，再加噴鼻藥保養"};
    final String[] a_conjunctivitiss = {"請選擇", "正常", "過敏發作", "長期過敏發作"};
    final String[] a_dermatitiss = {"請選擇", "正常", "輕微敏感 ", "過敏症狀發作", "長期過敏"};

    RadioGroup mRG, radiogroup_night_symptom, radiogroup_day_symptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page2);

        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        nose_symptom4 = gv.getnose_symptom4();
        eye_symptom = gv.geteye_symptom();
        skin_symptom = gv.getskin_symptom();
        asthma = gv.getasthma();
        a_rhinitis = gv.geta_rhinitis();
        a_conjunctivitis = gv.geta_conjunctivitis();
        a_dermatitis = gv.geta_dermatitis();
        String tmp = "請";
        if(gv.getnose_symptom4()!=null && tmp.compareTo(gv.getnose_symptom4())!=0){
            nose_symptom4s[0] = nose_symptom4s[0].replace("請選擇", nose_symptom4s[Integer.parseInt(nose_symptom4)+1]);
        }
        if(gv.geteye_symptom()!=null && tmp.compareTo(gv.geteye_symptom())!=0){
            eye_symptoms[0] = eye_symptoms[0].replace("請選擇", eye_symptoms[Integer.parseInt(eye_symptom)+1]);
        }
        if(gv.getskin_symptom()!=null && tmp.compareTo(gv.getskin_symptom())!=0){
            skin_symptoms[0] = skin_symptoms[0].replace("請選擇", skin_symptoms[Integer.parseInt(skin_symptom)+1]);
        }
        if(gv.getasthma()!=null && tmp.compareTo(gv.getasthma())!=0){
            asthmas[0] = asthmas[0].replace("請選擇", asthmas[Integer.parseInt(asthma)+1]);
        }
        if(gv.geta_rhinitis()!=null && tmp.compareTo(gv.geta_rhinitis())!=0){
            a_rhinitiss[0] = a_rhinitiss[0].replace("請選擇", a_rhinitiss[Integer.parseInt(a_rhinitis)+1]);
        }
        if(gv.geta_conjunctivitis()!=null && tmp.compareTo(gv.geta_conjunctivitis())!=0){
            a_conjunctivitiss[0] = a_conjunctivitiss[0].replace("請選擇", a_conjunctivitiss[Integer.parseInt(a_conjunctivitis)+1]);
        }
        if(gv.geta_dermatitis()!=null && tmp.compareTo(gv.geta_dermatitis())!=0){
            a_dermatitiss[0] = a_dermatitiss[0].replace("請選擇", a_dermatitiss[Integer.parseInt(a_dermatitis)+1]);
        }


        //4
        Spinner spinner_nose_symptom4 = (Spinner)findViewById(R.id.spinner_nose_symptom4);
        ArrayAdapter<String> nose_symptom4_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                nose_symptom4s);
        spinner_nose_symptom4.setAdapter(nose_symptom4_List);
        spinner_nose_symptom4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(nose_symptom4s[position].substring(0, 1))==0) {   //1st & no choose
                        nose_symptom4 = nose_symptom4s[position].substring(0, 1);
                    }
                }
                else { nose_symptom4 = Integer.toString(position-1);}
                //nose_symptom4 = nose_symptom4s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //eye
        Spinner spinner_eye_symptom = (Spinner)findViewById(R.id.spinner_eye_symptom);
        ArrayAdapter<String> eye_symptom_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                eye_symptoms);
        spinner_eye_symptom.setAdapter(eye_symptom_List);
        spinner_eye_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(eye_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        eye_symptom = eye_symptoms[position].substring(0, 1);
                    }
                }
                else { eye_symptom = Integer.toString(position-1);}
                //eye_symptom = eye_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    //skin
        Spinner spinner_skin_symptom = (Spinner)findViewById(R.id.spinner_skin_symptom);
        ArrayAdapter<String> skin_symptom_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                skin_symptoms);
        spinner_skin_symptom.setAdapter(skin_symptom_List);
        spinner_skin_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(skin_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        skin_symptom = skin_symptoms[position].substring(0, 1);
                    }
                }
                else { skin_symptom = Integer.toString(position-1);}
                //skin_symptom = skin_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    //asthma
        Spinner spinner_asthma = (Spinner)findViewById(R.id.spinner_asthma);
        ArrayAdapter<String> asthma_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                asthmas);
        spinner_asthma.setAdapter(asthma_List);
        spinner_asthma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(asthmas[position].substring(0, 1))==0) {   //1st & no choose
                        asthma = asthmas[position].substring(0, 1);
                    }
                }
                else { asthma = Integer.toString(position-1);}
                //asthma = asthmas[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //a_rhinitis
        Spinner spinner_a_rhinitis = (Spinner)findViewById(R.id.spinner_a_rhinitis);
        ArrayAdapter<String> a_rhinitis_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                a_rhinitiss);
        spinner_a_rhinitis.setAdapter(a_rhinitis_List);
        spinner_a_rhinitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(a_rhinitiss[position].substring(0, 1))==0) {   //1st & no choose
                        a_rhinitis = a_rhinitiss[position].substring(0, 1);
                    }
                }
                else { a_rhinitis = Integer.toString(position-1);}
                //a_rhinitis = a_rhinitiss[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    //a_con...
        Spinner spinner_a_conjunctivitis = (Spinner)findViewById(R.id.spinner_a_conjunctivitis);
        ArrayAdapter<String> a_conjunctivitis_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                a_conjunctivitiss);
        spinner_a_conjunctivitis.setAdapter(a_conjunctivitis_List);
        spinner_a_conjunctivitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(a_conjunctivitiss[position].substring(0, 1))==0) {   //1st & no choose
                        a_conjunctivitis = a_conjunctivitiss[position].substring(0, 1);
                    }
                }
                else { a_conjunctivitis = Integer.toString(position-1);}
                //a_conjunctivitis = a_conjunctivitiss[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //a_der...
        Spinner spinner_a_dermatitis = (Spinner)findViewById(R.id.spinner_a_dermatitis);
        ArrayAdapter<String> a_dermatitis_List = new ArrayAdapter<>(Symptom_page2.this,
                android.R.layout.simple_spinner_dropdown_item,
                a_dermatitiss);
        spinner_a_dermatitis.setAdapter(a_dermatitis_List);
        spinner_a_dermatitis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(a_dermatitiss[position].substring(0, 1))==0) {   //1st & no choose
                        a_dermatitis = a_dermatitiss[position].substring(0, 1);
                    }
                }
                else { a_dermatitis = Integer.toString(position-1);}
                //a_dermatitis = a_dermatitiss[position].substring(0, 1);
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
            intent.setClass(Symptom_page2.this, Symptom_page1.class);
            startActivity(intent);
            //finish();
            //}
        }
    }
    class goto_home_real extends Thread{

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page2.this, MainActivity.class);
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
            gv.setnose_symptom4(nose_symptom4);
            gv.seteye_symptom(eye_symptom);
            gv.setskin_symptom(skin_symptom);
            gv.setasthma(asthma);
            gv.seta_rhinitis(a_rhinitis);
            gv.seta_conjunctivitis(a_conjunctivitis);
            gv.seta_dermatitis(a_dermatitis);
            String tmp = "請";
            if( tmp.compareTo(gv.getnose_symptom4())==0 || tmp.compareTo(gv.geteye_symptom())==0 || tmp.compareTo(gv.getskin_symptom())==0 || tmp.compareTo(gv.getasthma())==0 || tmp.compareTo(gv.geta_rhinitis())==0
                    || tmp.compareTo(gv.geta_conjunctivitis())==0 || tmp.compareTo(gv.geta_dermatitis())==0){

                System.out.println("data missing");
                miss_notice.setText("Data missing!!");
            }
            else {
                try {
                    //socket = new Socket(gv.getip(), gv.getport());
                    //if(gv.getip()!=null && gv.getport()!=null) {    //have set ip and port
                        socket = new Socket(gv.getip(), Integer.parseInt(gv.getport()));
                    //}
                    //else{   //not set ip, port
                        //socket = new Socket("140.113.86.106", 10070);
                        //socket = new Socket("140.113.66.153", 8787);
                    //}
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
                intent.setClass(Symptom_page2.this, Choose_which_day.class);
                startActivity(intent);
                //finish();
                //}
            }
        }
    }
}