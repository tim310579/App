package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose_which_day extends AppCompatActivity {

    private Button btn_yesterday, btn_today, btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_which_day);

        btn_home = (Button) findViewById(R.id.Back_home);
        btn_home.setOnClickListener(new ButtonClickListener());

        btn_yesterday = (Button) findViewById(R.id.id_yesterday);
        btn_today = (Button) findViewById(R.id.id_today);

        btn_yesterday.setOnClickListener(new ButtonClickListener());
        btn_today.setOnClickListener(new ButtonClickListener());

    }
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Back_home:
                    goto_home();
                    break;
                case R.id.id_yesterday:
                    goto_yesterday();
                    break;
                case R.id.id_today:
                    goto_today();
                    break;
                default:
                    break;
            }
        }
    }
    private void goto_home(){new goto_home().start();}
    private void goto_yesterday(){new goto_yesterday().start();}
    private void goto_today(){new goto_today().start();}

    class goto_home extends Thread{

        public goto_home(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Choose_which_day.this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_yesterday extends Thread{

        public goto_yesterday(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Choose_which_day.this, Symptom_page1_yesterday.class);
            startActivity(intent);
            //finish();
        }
    }
    class goto_today extends Thread{

        public goto_today(){
        }
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Choose_which_day.this, Symptom_page1.class);
            startActivity(intent);
            //finish();
        }
    }
}