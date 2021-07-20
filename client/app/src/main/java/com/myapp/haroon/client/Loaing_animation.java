package com.myapp.haroon.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Loaing_animation extends AppCompatActivity {

    private Button result_ok;
    private TextView tvhaha;
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaing_animation);

        result_ok = (Button) findViewById(R.id.result_ok);
        result_ok.setOnClickListener(new ButtonClickListener());
        tvhaha = (TextView) findViewById(R.id.haha);
        start_sth();
        //

        //View v = this.getWindow().getDecorView();

        /*try {
            init_popwindow(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
    private void start_sth(){new start_sth().start();}
    class start_sth extends Thread{

            @Override
            public void run() {
                GlobalVariable gv = (GlobalVariable) getApplicationContext();   //global var
                try {
                    Thread.sleep(1000); //暫停，每一秒輸出一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gv.setget_result(1);
                Intent intent = new Intent();
                intent.setClass(Loaing_animation.this, Show_predict_result.class);
                //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Result_predict.this);
                startActivity(intent);
            }


    }
    private void init_animate(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.animation, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 10, 10);
        //TimeUnit.SECONDS.sleep(2);


    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.result_ok:
                    goto_page_home();
                    break;
                default:
                    break;
            }
        }
    }
    private void goto_page_home(){new goto_page_home().start();}
    class goto_page_home extends Thread {

        public goto_page_home() {
        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Loaing_animation.this, MainActivity.class);
            startActivity(intent);
            //finish();

            //}
        }
    }
}