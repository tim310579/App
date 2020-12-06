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

public class Symptom_page1 extends AppCompatActivity {
    private int mYear, mMonth, mDay;
    private String day_rec, night_rec;
    String night_symptom, day_symptom, nose_symptom1, nose_symptom2, nose_symptom3, nose_symptom4;
    private TextView miss_notice;

    private TextView tv_day_rec, tv_night_rec;
    private EditText mEdit_day_rec, mEdit_night_rec;

    private TextView tv_fever, tv_night_symptom;
    private TextView tv_today_date;

    private Button btn_home, btn_page0, btn_page2;
    final String[] night_symptoms = {"請選擇", "0(healthy)", "1(seldom cough)", "2(sometimes cough)", "3(severe)"};
    final String[] day_symptoms = {"請選擇", "0(healthy)", "1(seldom cough)", "2(sometimes cough)", "3(severe)", "4(need first aid)"};
    final String[] nose_symptom1s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    final String[] nose_symptom2s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    final String[] nose_symptom3s = {"請選擇", "0(normal)", "1(seldom)", "2(frequent)"};
    //final String[] nose_symptom4s = {"請選擇(running nose)", "0(normal)", "1(seldom)", "2(frequent)"};


    RadioGroup mRG, radiogroup_night_symptom, radiogroup_day_symptom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_page1);

        final TextView dateText = (TextView)findViewById(R.id.dateText);
        tv_today_date = (TextView) findViewById(R.id.tv_today_date);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String today_date = dateFormat.format(date);
        tv_today_date.setText(" "+today_date);


        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        gv.setdate(today_date);

        night_symptom = gv.getnight_symptom();
        day_symptom = gv.getday_symptom();
        nose_symptom1 = gv.getnose_symptom1();
        nose_symptom2 = gv.getnose_symptom2();
        nose_symptom3 = gv.getnose_symptom3();
        String tmp = "請";
        if(gv.getnight_symptom()!=null && tmp.compareTo(gv.getnight_symptom())!=0){
            night_symptoms[0] = night_symptoms[0].replace("請選擇", night_symptoms[Integer.parseInt(night_symptom)+1]);
        }
        if(gv.getday_symptom()!=null && tmp.compareTo(gv.getday_symptom())!=0){
            day_symptoms[0] = day_symptoms[0].replace("請選擇", day_symptoms[Integer.parseInt(day_symptom)+1]);
        }
        if(gv.getnose_symptom1()!=null && tmp.compareTo(gv.getnose_symptom1())!=0){ //has chosen and not chose "請"
            nose_symptom1s[0] = nose_symptom1s[0].replace("請選擇", nose_symptom1s[Integer.parseInt(nose_symptom1)+1]);
        }
        if(gv.getnose_symptom2()!=null && tmp.compareTo(gv.getnose_symptom2())!=0){
            nose_symptom2s[0] = nose_symptom2s[0].replace("請選擇", nose_symptom2s[Integer.parseInt(nose_symptom2)+1]);
        }
        if(gv.getnose_symptom3()!=null && tmp.compareTo(gv.getnose_symptom3())!=0){
            nose_symptom3s[0] = nose_symptom3s[0].replace("請選擇", nose_symptom3s[Integer.parseInt(nose_symptom3)+1]);
        }

        /*final Button dateButton = (Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String format;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Symptom_page1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        dateText.setText(format);
                        GlobalVariable gv = (GlobalVariable)getApplicationContext();
                        gv.setdate(format);
                        dateButton.setText(format);
                    }

                }, mYear,mMonth, mDay).show();


            }


        });*/
        //night symptom
        Spinner spinner_night_symptom = (Spinner)findViewById(R.id.spinner_night_symptom);
        ArrayAdapter<String> night_symptom_List = new ArrayAdapter<>(Symptom_page1.this,
                android.R.layout.simple_spinner_dropdown_item,
                night_symptoms);
        spinner_night_symptom.setAdapter(night_symptom_List);
        spinner_night_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                night_symptom = night_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //day symptom
        Spinner spinner_day_symptom = (Spinner)findViewById(R.id.spinner_day_symptom);
        //final String[] genders = {"請選擇性別", "male", "female"};
        ArrayAdapter<String> day_symptom_List = new ArrayAdapter<>(Symptom_page1.this,
                android.R.layout.simple_spinner_dropdown_item,
                day_symptoms);
        spinner_day_symptom.setAdapter(day_symptom_List);
        spinner_day_symptom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day_symptom = day_symptoms[position].substring(0, 1);
                //Toast.makeText(.this, "您選擇了:" + lunch[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //nose_symp1
        Spinner spinner_nose_symptom1 = (Spinner)findViewById(R.id.spinner_nose_symptom1);
        ArrayAdapter<String> nose_symptom1_List = new ArrayAdapter<>(Symptom_page1.this,
                android.R.layout.simple_spinner_dropdown_item,
                nose_symptom1s);
        spinner_nose_symptom1.setAdapter(nose_symptom1_List);
        spinner_nose_symptom1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nose_symptom1 = nose_symptom1s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //2
        Spinner spinner_nose_symptom2 = (Spinner)findViewById(R.id.spinner_nose_symptom2);
        ArrayAdapter<String> nose_symptom2_List = new ArrayAdapter<>(Symptom_page1.this,
                android.R.layout.simple_spinner_dropdown_item,
                nose_symptom2s);
        spinner_nose_symptom2.setAdapter(nose_symptom2_List);
        spinner_nose_symptom2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nose_symptom2 = nose_symptom2s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //3
        Spinner spinner_nose_symptom3 = (Spinner)findViewById(R.id.spinner_nose_symptom3);
        ArrayAdapter<String> nose_symptom3_List = new ArrayAdapter<>(Symptom_page1.this,
                android.R.layout.simple_spinner_dropdown_item,
                nose_symptom3s);
        spinner_nose_symptom3.setAdapter(nose_symptom3_List);
        spinner_nose_symptom3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nose_symptom3 = nose_symptom3s[position].substring(0, 1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        tv_day_rec = (TextView) findViewById(R.id.tv_day_rec);
        mEdit_day_rec = (EditText) findViewById(R.id.mEdit_day_rec);
        tv_night_rec = (TextView) findViewById(R.id.tv_night_rec);
        mEdit_night_rec = (EditText) findViewById(R.id.mEdit_night_rec);

        btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new ButtonClickListener());
        btn_page0 = (Button) findViewById(R.id.btn_page0);
        btn_page0.setOnClickListener(new ButtonClickListener());
        btn_page2 = (Button) findViewById(R.id.btn_page2);
        btn_page2.setOnClickListener(new ButtonClickListener());

        miss_notice = (TextView) findViewById(R.id.tv_miss_notice);

        if(gv.getday_rec()!=null) mEdit_day_rec.setText(" "+gv.getday_rec());
        if(gv.getnight_rec()!=null) mEdit_night_rec.setText(" "+gv.getnight_rec());
    }
    /*private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }*/

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
            intent.setClass(Symptom_page1.this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_page0 extends Thread{

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Symptom_page1.this, Choose_which_day.class);
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
            if (TextUtils.isEmpty(mEdit_day_rec.getText())){ day_rec = gv.getday_rec(); }
            else day_rec=mEdit_day_rec.getText().toString().trim();
            if (TextUtils.isEmpty(mEdit_night_rec.getText())){ night_rec = gv.getnight_rec(); }
            else night_rec=mEdit_night_rec.getText().toString().trim();
            //night_rec=mEdit_night_rec.getText().toString();
            gv.setday_rec(day_rec);
            gv.setnight_rec(night_rec);

            //if(gv.getday_rec()==null){System.out.println("hahahahahahahaha");}
            //System.out.println("HAHA"+gv.getday_rec()+"|");
            gv.setnight_symptom(night_symptom);
            gv.setday_symptom(day_symptom);
            gv.setnose_symptom1(nose_symptom1);
            gv.setnose_symptom2(nose_symptom2);
            gv.setnose_symptom3(nose_symptom3);
            //gv.setnose_symptom4(nose_symptom4);

            System.out.println(gv.getdate());
            String tmp = "請";
            /*if(tmp.compareTo(gv.getnight_symptom())==0){gv.setnight_symptom("-1");}//no choose
            if(tmp.compareTo(gv.getday_symptom())==0){gv.setnight_symptom("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom1())==0){gv.setnose_symptom1("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom2())==0){gv.setnose_symptom2("-1");}//no choose
            if(tmp.compareTo(gv.getnose_symptom3())==0){gv.setnose_symptom3("-1");}//no choose*/
            //System.out.println(tmp.compareTo(gv.getnight_symptom()));
            //tmp="-1";
            if(gv.getday_rec()==null || gv.getnight_rec()==null || tmp.compareTo(gv.getnight_symptom())==0 || tmp.compareTo(gv.getday_symptom())==0
                    || tmp.compareTo(gv.getnose_symptom1())==0 || tmp.compareTo(gv.getnose_symptom2())==0 || tmp.compareTo(gv.getnose_symptom3())==0){
                System.out.println("data missing");
                //String tmp = "Data missing";
                miss_notice.setText("Data missing!!");
            }
            else {
                //miss_notice.setText("");
                Intent intent = new Intent();
                intent.setClass(Symptom_page1.this, Symptom_page2.class);
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