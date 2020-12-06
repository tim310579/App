package com.myapp.haroon.client;
import android.app.Application;
import android.widget.TextView;

public class GlobalVariable extends Application{
    private String weather;
    private String date;
    private String pre_date;
    private String ID_number;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String city;
    private String temperature, humidity, psi;

    private String day_rec, night_rec;
    private String night_symptom, day_symptom, nose_symptom1, nose_symptom2, nose_symptom3, nose_symptom4;
    private String eye_symptom, skin_symptom, grade;
    private String asthma, a_rhinitis, a_conjunctivitis, a_dermatitis;

    private String ip;
    private int port;
    private String predict_result;
    private int get_result;

    private String pre_day_rec, pre_night_rec;
    private String pre_night_symptom, pre_day_symptom, pre_nose_symptom1, pre_nose_symptom2, pre_nose_symptom3, pre_nose_symptom4;
    private String pre_eye_symptom, pre_skin_symptom, pre_grade;
    private String pre_asthma, pre_a_rhinitis, pre_a_conjunctivitis, pre_a_dermatitis;

    //設定變數值
    public void setWeather(String weather) { this.weather = weather; }
    public void setdate(String date){
        this.date = date;
    }
    public void setID_number(String ID_number){
        this.ID_number = ID_number;
    }
    public void setage(String age){
        this.age = age;
    }
    public void setgender(String gender){
        this.gender = gender;
    }
    public void setheight(String height){
        this.height = height;
    }
    public void setweight(String weight){
        this.weight = weight;
    }
    public void setcity(String city){
        this.city = city;
    }
    public void settemperature(String temperature){ this.temperature = temperature; }
    public void sethumidity(String humidity){ this.humidity = humidity; }
    public void setpsi(String psi){ this.psi = psi; }

    public void setday_rec(String day_rec){
        this.day_rec = day_rec;
    }
    public void setnight_rec(String night_rec){
        this.night_rec = night_rec;
    }

    public void setnight_symptom(String night_symptom){ this.night_symptom = night_symptom; }
    public void setday_symptom(String day_symptom){ this.day_symptom = day_symptom; }
    public void setnose_symptom1(String nose_symptom1){ this.nose_symptom1 = nose_symptom1; }
    public void setnose_symptom2(String nose_symptom2){ this.nose_symptom2 = nose_symptom2; }
    public void setnose_symptom3(String nose_symptom3){ this.nose_symptom3 = nose_symptom3; }
    public void setnose_symptom4(String nose_symptom4){ this.nose_symptom4 = nose_symptom4; }

    public void seteye_symptom(String eye_symptom){ this.eye_symptom = eye_symptom; }
    public void setskin_symptom(String skin_symptom){ this.skin_symptom = skin_symptom; }
    public void setgrade(String grade){ this.grade = grade; }

    public void setasthma(String asthma){ this.asthma = asthma; }
    public void seta_rhinitis(String a_rhinitis){ this.a_rhinitis = a_rhinitis; }
    public void seta_conjunctivitis(String a_conjunctivitis){ this.a_conjunctivitis = a_conjunctivitis; }
    public void seta_dermatitis(String a_dermatitis){ this.a_dermatitis = a_dermatitis; }

    public void setip(String ip){ this.ip = ip;}
    public void setport(int port){ this.port = port;}

    public void setpredict_result(String predict_result){ this.predict_result = predict_result; }
    public void setget_result(int get_result){ this.get_result = get_result; }
    //yesterday
    public void set_pre_date(String pre_date) { this.pre_date = pre_date;}
    public void set_pre_day_rec(String pre_day_rec){
        this.pre_day_rec = pre_day_rec;
    }
    public void set_pre_night_rec(String pre_night_rec){
        this.pre_night_rec = pre_night_rec;
    }

    public void set_pre_night_symptom(String pre_night_symptom){ this.pre_night_symptom = pre_night_symptom; }
    public void set_pre_day_symptom(String pre_day_symptom){ this.pre_day_symptom = pre_day_symptom; }
    public void set_pre_nose_symptom1(String pre_nose_symptom1){ this.pre_nose_symptom1 = pre_nose_symptom1; }
    public void set_pre_nose_symptom2(String pre_nose_symptom2){ this.pre_nose_symptom2 = pre_nose_symptom2; }
    public void set_pre_nose_symptom3(String pre_nose_symptom3){ this.pre_nose_symptom3 = pre_nose_symptom3; }
    public void set_pre_nose_symptom4(String pre_nose_symptom4){ this.pre_nose_symptom4 = pre_nose_symptom4; }

    public void set_pre_eye_symptom(String pre_eye_symptom){ this.pre_eye_symptom = pre_eye_symptom; }
    public void set_pre_skin_symptom(String pre_skin_symptom){ this.pre_skin_symptom = pre_skin_symptom; }
    public void set_pre_grade(String pre_grade){ this.pre_grade = pre_grade; }

    public void set_pre_asthma(String pre_asthma){ this.pre_asthma = pre_asthma; }
    public void set_pre_a_rhinitis(String pre_a_rhinitis){ this.pre_a_rhinitis = pre_a_rhinitis; }
    public void set_pre_a_conjunctivitis(String pre_a_conjunctivitis){ this.pre_a_conjunctivitis = pre_a_conjunctivitis; }
    public void set_pre_a_dermatitis(String pre_a_dermatitis){ this.pre_a_dermatitis = pre_a_dermatitis; }


    //取得 變數值
    public String getWeather() { return weather; }
    public String getdate() {
        return date;
    }
    public String getID_number() {
        return ID_number;
    }
    public String getage(){
        return age;
    }
    public String getgender(){
        return gender;
    }
    public String getheight(){
        return height;
    }
    public String getweight(){ return weight; }
    public String getcity(){ return city; }
    public String gettemperature(){ return temperature; }
    public String gethumidity(){ return humidity; }
    public String getpsi(){ return psi; }

    public String getday_rec(){
        return day_rec;
    }
    public String getnight_rec(){return night_rec; }

    public String getnight_symptom(){ return night_symptom; }
    public String getday_symptom(){ return day_symptom; }
    public String getnose_symptom1(){ return nose_symptom1; }
    public String getnose_symptom2(){ return nose_symptom2; }
    public String getnose_symptom3(){ return nose_symptom3; }
    public String getnose_symptom4(){ return nose_symptom4; }

    public String geteye_symptom(){ return eye_symptom; }
    public String getskin_symptom(){ return skin_symptom; }
    public String getgrade(){ return grade; }

    public String getasthma(){ return asthma; }
    public String geta_rhinitis(){ return a_rhinitis; }
    public String geta_conjunctivitis(){ return a_conjunctivitis; }
    public String geta_dermatitis(){ return a_dermatitis; }

    public String getip(){ return ip; }
    public int getport(){ return port; }

    public String getpredict_result(){ return predict_result; }
    public int getget_result(){ return get_result; }
    //yesterday
    public String get_pre_date() { return pre_date; }
    public String get_pre_day_rec(){
        return pre_day_rec;
    }
    public String get_pre_night_rec(){return pre_night_rec; }

    public String get_pre_night_symptom(){ return pre_night_symptom; }
    public String get_pre_day_symptom(){ return pre_day_symptom; }
    public String get_pre_nose_symptom1(){ return pre_nose_symptom1; }
    public String get_pre_nose_symptom2(){ return pre_nose_symptom2; }
    public String get_pre_nose_symptom3(){ return pre_nose_symptom3; }
    public String get_pre_nose_symptom4(){ return pre_nose_symptom4; }

    public String get_pre_eye_symptom(){ return pre_eye_symptom; }
    public String get_pre_skin_symptom(){ return pre_skin_symptom; }
    public String get_pre_grade(){ return pre_grade; }

    public String get_pre_asthma(){ return pre_asthma; }
    public String get_pre_a_rhinitis(){ return pre_a_rhinitis; }
    public String get_pre_a_conjunctivitis(){ return pre_a_conjunctivitis; }
    public String get_pre_a_dermatitis(){ return pre_a_dermatitis; }

}
