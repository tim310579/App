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

public class Show_predict_result extends AppCompatActivity {

    private Button result_ok, btn_back_predict;
    private TextView tvhaha;
    private TextView the_predict_result, the_more_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_predict_result);

        result_ok = (Button) findViewById(R.id.result_ok);
        result_ok.setOnClickListener(new ButtonClickListener());

        btn_back_predict = (Button) findViewById(R.id.btn_back_predict);
        btn_back_predict.setOnClickListener(new ButtonClickListener());

        the_predict_result = (TextView) findViewById(R.id.id_predict_result);
        the_more_notice = (TextView) findViewById(R.id.id_more_notice);
        show_the_result();

    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_back_predict:
                    goto_predict_page();
                    break;
                case R.id.result_ok:
                    goto_page_home();
                    break;
                default:
                    break;
            }
        }
    }
    private void show_the_result(){new show_the_result().start();}
    private void goto_predict_page(){new goto_predict_page().start();}
    private void goto_page_home(){new goto_page_home().start();}

    class show_the_result extends Thread{
        @Override
        public void run(){
            GlobalVariable gv = (GlobalVariable) getApplicationContext();
            if (gv.getpredict_result()==null){
                the_predict_result.setText("抱歉，未能連接到伺服器");
            }
            else {
                the_predict_result.setText(gv.getpredict_result());
                the_more_notice.setText(gv.getmore_notice());
                if (gv.getpredict_result().compareTo("紅燈區(應注意)") == 0) {
                    the_predict_result.setTextColor(Color.parseColor("#f20505"));
                } else if (gv.getpredict_result().compareTo("綠燈區(請保持)") == 0) {
                    the_predict_result.setTextColor(Color.parseColor("#00cc00"));
                }
                gv.setpredict_result(null);
                gv.setmore_notice(null);
            }

        }
    }
    class goto_predict_page extends Thread {

        public goto_predict_page() {
        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Show_predict_result.this, Result_predict.class);
            startActivity(intent);
            //finish();

            //}
        }
    }
    class goto_page_home extends Thread {

        public goto_page_home() {
        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Show_predict_result.this, MainActivity.class);
            startActivity(intent);
            //finish();

            //}
        }
    }
}