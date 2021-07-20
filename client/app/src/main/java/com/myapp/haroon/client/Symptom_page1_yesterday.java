package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Symptom_page1_yesterday extends AppCompatActivity {
    private int mYear, mMonth, mDay;
    private String pre_day_rec, pre_night_rec;
    String pre_night_symptom, pre_day_symptom, pre_nose_symptom1, pre_nose_symptom2, pre_nose_symptom3, pre_nose_symptom4;
    private TextView miss_notice;

    private TextView tv_pre_day_rec, tv_pre_night_rec;
    private EditText mEdit_pre_day_rec, mEdit_pre_night_rec;

    private TextView tv_pre_fever, tv_pre_night_symptom;
    private TextView tv_pre_date;

    private Button btn_home, btn_page0, btn_page2;
    /*final String[] pre_night_symptoms = {"請選擇", "0(healthy)", "1(seldom cough)", "2(sometimes cough)", "3(severe)"};
    final String[] pre_day_symptoms = {"請選擇", "0(healthy)", "1(seldom cough)", "2(sometimes cough)", "3(severe)", "4(need first aid)"};
    final String[] pre_nose_symptom1s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    final String[] pre_nose_symptom2s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    final String[] pre_nose_symptom3s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    //final String[] nose_symptom4s = {"請選擇(running nose)", "0(normal)", "1(seldom)", "2(frequent)"};
    */

    final String[] pre_night_symptoms = {"請選擇", "正常", "輕微", "中度", "嚴重"};
    final String[] pre_day_symptoms = {"請選擇", "正常", "輕微", "中度", "嚴重", "需急診治療"};
    final String[] pre_nose_symptom1s = {"請選擇", "正常", "輕微", "中度以上"};
    final String[] pre_nose_symptom2s = {"請選擇", "正常", "輕微", "中度以上"};
    final String[] pre_nose_symptom3s = {"請選擇", "正常", "輕微", "中度以上"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page1_yesterday);

        final TextView dateText = (TextView)findViewById(R.id.dateText);
        tv_pre_date = (TextView) findViewById(R.id.tv_pre_date);


        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        //取得昨天的日期
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String pre_date = format.format(cal.getTime());
        tv_pre_date.setText(" "+pre_date);


        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        gv.set_pre_date(pre_date);

        pre_night_symptom = gv.get_pre_night_symptom();
        pre_day_symptom = gv.get_pre_day_symptom();
        pre_nose_symptom1 = gv.get_pre_nose_symptom1();
        pre_nose_symptom2 = gv.get_pre_nose_symptom2();
        pre_nose_symptom3 = gv.get_pre_nose_symptom3();
        String tmp = "請";
        if(gv.get_pre_night_symptom()!=null && tmp.compareTo(gv.get_pre_night_symptom())!=0){
            pre_night_symptoms[0] = pre_night_symptoms[0].replace("請選擇", pre_night_symptoms[Integer.parseInt(pre_night_symptom)+1]);
        }
        if(gv.get_pre_day_symptom()!=null && tmp.compareTo(gv.get_pre_day_symptom())!=0){
            pre_day_symptoms[0] = pre_day_symptoms[0].replace("請選擇", pre_day_symptoms[Integer.parseInt(pre_day_symptom)+1]);
        }
        if(gv.get_pre_nose_symptom1()!=null && tmp.compareTo(gv.get_pre_nose_symptom1())!=0){ //has chosen and not chose "請"
            pre_nose_symptom1s[0] = pre_nose_symptom1s[0].replace("請選擇", pre_nose_symptom1s[Integer.parseInt(pre_nose_symptom1)+1]);
        }
        if(gv.get_pre_nose_symptom2()!=null && tmp.compareTo(gv.get_pre_nose_symptom2())!=0){
            pre_nose_symptom2s[0] = pre_nose_symptom2s[0].replace("請選擇", pre_nose_symptom2s[Integer.parseInt(pre_nose_symptom2)+1]);
        }
        if(gv.get_pre_nose_symptom3()!=null && tmp.compareTo(gv.get_pre_nose_symptom3())!=0){
            pre_nose_symptom3s[0] = pre_nose_symptom3s[0].replace("請選擇", pre_nose_symptom3s[Integer.parseInt(pre_nose_symptom3)+1]);
        }

        //night symptom
        Spinner spinner_night_symptom = (Spinner)findViewById(R.id.spinner_night_symptom);
        ArrayAdapter<String> night_symptom_List = new ArrayAdapter<>(Symptom_page1_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_night_symptoms);
        spinner_night_symptom.setAdapter(night_symptom_List);
        spinner_night_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_night_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        pre_night_symptom = pre_night_symptoms[position].substring(0, 1);
                    }
                }
                else { pre_night_symptom = Integer.toString(position-1);}

                //pre_night_symptom = pre_night_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //day symptom
        Spinner spinner_day_symptom = (Spinner)findViewById(R.id.spinner_day_symptom);
        //final String[] genders = {"請選擇性別", "male", "female"};
        ArrayAdapter<String> day_symptom_List = new ArrayAdapter<>(Symptom_page1_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_day_symptoms);
        spinner_day_symptom.setAdapter(day_symptom_List);
        spinner_day_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_day_symptoms[position].substring(0, 1))==0) {   //1st & no choose
                        pre_day_symptom = pre_day_symptoms[position].substring(0, 1);
                    }
                }
                else { pre_day_symptom = Integer.toString(position-1);}
                //pre_day_symptom = pre_day_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //nose_symp1
        Spinner spinner_nose_symptom1 = (Spinner)findViewById(R.id.spinner_nose_symptom1);
        ArrayAdapter<String> nose_symptom1_List = new ArrayAdapter<>(Symptom_page1_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_nose_symptom1s);
        spinner_nose_symptom1.setAdapter(nose_symptom1_List);
        spinner_nose_symptom1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_nose_symptom1s[position].substring(0, 1))==0) {   //1st & no choose
                        pre_nose_symptom1 = pre_nose_symptom1s[position].substring(0, 1);
                    }
                }
                else { pre_nose_symptom1 = Integer.toString(position-1);}
                //pre_nose_symptom1 = pre_nose_symptom1s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //2
        Spinner spinner_nose_symptom2 = (Spinner)findViewById(R.id.spinner_nose_symptom2);
        ArrayAdapter<String> nose_symptom2_List = new ArrayAdapter<>(Symptom_page1_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_nose_symptom2s);
        spinner_nose_symptom2.setAdapter(nose_symptom2_List);
        spinner_nose_symptom2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_nose_symptom2s[position].substring(0, 1))==0) {   //1st & no choose
                        pre_nose_symptom2 = pre_nose_symptom2s[position].substring(0, 1);
                    }
                }
                else { pre_nose_symptom2 = Integer.toString(position-1);}
                //pre_nose_symptom2 = pre_nose_symptom2s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //3
        Spinner spinner_nose_symptom3 = (Spinner)findViewById(R.id.spinner_nose_symptom3);
        ArrayAdapter<String> nose_symptom3_List = new ArrayAdapter<>(Symptom_page1_yesterday.this,
                android.R.layout.simple_spinner_dropdown_item,
                pre_nose_symptom3s);
        spinner_nose_symptom3.setAdapter(nose_symptom3_List);
        spinner_nose_symptom3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tmp = "請";
                if (position == 0) {
                    if(tmp.compareTo(pre_nose_symptom3s[position].substring(0, 1))==0) {   //1st & no choose
                        pre_nose_symptom3 = pre_nose_symptom3s[position].substring(0, 1);
                    }
                }
                else { pre_nose_symptom3 = Integer.toString(position-1);}
                //pre_nose_symptom3 = pre_nose_symptom3s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        tv_pre_day_rec = (TextView) findViewById(R.id.tv_day_rec);
        mEdit_pre_day_rec = (EditText) findViewById(R.id.mEdit_day_rec);
        tv_pre_night_rec = (TextView) findViewById(R.id.tv_night_rec);
        mEdit_pre_night_rec = (EditText) findViewById(R.id.mEdit_night_rec);

        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new ButtonClickListener());
        btn_page0 = (Button) findViewById(R.id.btn_page0);
        btn_page0.setOnClickListener(new ButtonClickListener());
        btn_page2 = (Button) findViewById(R.id.btn_page2);
        btn_page2.setOnClickListener(new ButtonClickListener());

        miss_notice = (TextView) findViewById(R.id.tv_miss_notice);

        if(gv.get_pre_day_rec()!=null) mEdit_pre_day_rec.setText(" "+gv.get_pre_day_rec());
        if(gv.get_pre_night_rec()!=null) mEdit_pre_night_rec.setText(" "+gv.get_pre_night_rec());
    }


    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_home:
                    goto_home();
                    break;
                case R.id.btn_page0:
                    goto_page0();
                    break;
                case R.id.btn_page2:
                    goto_page2();
                    break;
                default:
                    break;
            }
        }
    }
    private void goto_home(){new goto_home().start();}
    private void goto_page0(){new goto_page0().start();}
    private void goto_page2(){new goto_page2().start();}
    class goto_home extends Thread{

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page1_yesterday.this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_page0 extends Thread{

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page1_yesterday.this, Choose_which_day.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_page2 extends Thread{

        public goto_page2(){
        }

        @Override
        public void run(){

            GlobalVariable gv = (GlobalVariable)getApplicationContext();
            if (TextUtils.isEmpty(mEdit_pre_day_rec.getText())){ pre_day_rec = gv.get_pre_day_rec(); }
            else pre_day_rec = mEdit_pre_day_rec.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_pre_night_rec.getText())){ pre_night_rec = gv.get_pre_night_rec(); }
            else pre_night_rec = mEdit_pre_night_rec.getText().toString().trim();
            //night_rec=mEdit_night_rec.getText().toString();
            gv.set_pre_day_rec(pre_day_rec);
            gv.set_pre_night_rec(pre_night_rec);

            //if(gv.getday_rec()==null){System.out.println("hahahahahahahaha");}
            //System.out.println("HAHA"+gv.getday_rec()+"|");
            gv.set_pre_night_symptom(pre_night_symptom);
            gv.set_pre_day_symptom(pre_day_symptom);
            gv.set_pre_nose_symptom1(pre_nose_symptom1);
            gv.set_pre_nose_symptom2(pre_nose_symptom2);
            gv.set_pre_nose_symptom3(pre_nose_symptom3);
            //gv.setnose_symptom4(nose_symptom4);


            String tmp = "請";
            /*if(tmp.compareTo(gv.getnight_symptom())==0){gv.setnight_symptom("-1");}//no choose
            if(tmp.compareTo(gv.getday_symptom())==0){gv.setnight_symptom("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom1())==0){gv.setnose_symptom1("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom2())==0){gv.setnose_symptom2("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom3())==0){gv.setnose_symptom3("-1");}//no choose*/
            //System.out.println(tmp.compareTo(gv.getnight_symptom()));
            //tmp="-1";
            if(gv.get_pre_day_rec()==null || gv.get_pre_night_rec()==null || tmp.compareTo(gv.get_pre_night_symptom())==0 || tmp.compareTo(gv.get_pre_day_symptom())==0
                    || tmp.compareTo(gv.get_pre_nose_symptom1())==0 || tmp.compareTo(gv.get_pre_nose_symptom2())==0 || tmp.compareTo(gv.get_pre_nose_symptom3())==0){
                System.out.println("data missing");
                //String tmp = "Data missing";
                miss_notice.setText("Data missing!!");
            }
            else {
                //miss_notice.setText("");
                Intent intent = new Intent();
                intent.setClass(Symptom_page1_yesterday.this, Symptom_page2_yesterday.class);
                startActivity(intent);
            }
            //finish();
            //outputStream.write((gv.getID_number()+", "+gv.getgender()+", "+gv.getage()+", "+gv.getheight()+", "+gv.getweight()+", "+day_rec+", "+night_rec+", "+night_symptom+", "+day_symptom+"\n").getBytes("utf-8"));
            //outputStream.flush();

            //socket.close();
            //}
        }
    }
}