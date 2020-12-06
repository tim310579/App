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

public class App_info extends AppCompatActivity {

    private Button app_info_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        app_info_ok = (Button) findViewById(R.id.app_info_ok);
        app_info_ok.setOnClickListener(new ButtonClickListener());

    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.app_info_ok:
                    app_info_ok();
                    break;
                default:
                    break;
            }
        }
    }
    private void app_info_ok(){new app_info_ok().start();}
    class app_info_ok extends Thread{

        public app_info_ok(){
        }

        @Override
        public void run(){

            Intent intent = new Intent();
            intent.setClass(App_info.this, MainActivity.class);
            startActivity(intent);
            //finish();
            //}
        }
    }
}