package com.myapp.haroon.client;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;
public class Result_predict extends AppCompatActivity {
    /**
     * 主 变量
     */
    private String red_or_green;
    private Handler handler;
    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;

    // Socket变量
    private Socket socket;

    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;

    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;

    // 输入流读取器对象
    InputStreamReader isr ;
    BufferedReader br ;

    // 接收服务器发送过来的消息
    String response;
    String red_msg, green_msg;

    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    /**
     * 按钮 变量
     */

    // 连接 断开连接 发送数据到服务器 的按钮变量
    private Button btnConnect, btnDisconnect, btnSend, result_ok;

    // 显示接收服务器消息 按钮
    private TextView Receive, receive_message;

    // 输入需要发送的消息 输入框
    private EditText mEdit;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_predict);
        mContext = Result_predict.this;
        /**
         * 初始化操作
         */

        // 初始化所有按钮
        //receive_message = (TextView) findViewById(R.id.tv_receive_message);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new ButtonClickListener());
        result_ok = (Button) findViewById(R.id.result_ok);
        result_ok.setOnClickListener(new ButtonClickListener());
        View v = this.getCurrentFocus();
        GlobalVariable gv = (GlobalVariable) getApplicationContext();   //global var
        //if(gv.getget_result()==1){
        //    init_popwindow(v);
        //}
        /*
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        receive_message.setText(response);
                        break;
                }
                //Receive.append(response+"adasd\n");
                //super.handleMessage(msg);
            }
        };*/
    }
    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnSend:

                    send_symp();

                    //init_animate(v);

                    //init_popwindow(v);


                    break;
                case R.id.result_ok:
                    goto_page_home();
                    break;
                default:
                    break;
            }
        }
    }

    private void send_symp(){new send_symp().start();}
    private void goto_page_home(){new goto_page_home().start();}



    class send_symp extends Thread {

        @Override
        public void run() {
            GlobalVariable gv = (GlobalVariable) getApplicationContext();   //global var

            //SocketAddress socketAddress = new InetSocketAddress("192.168.1.138", 1311);

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
                //socket = new Socket("192.168.1.138", 1311);
                // 步骤1：从Socket 获得输出流对象OutputStream
                // 该对象作用：发送数据



                //socket.connect(socketAddress, 50);
                outputStream = socket.getOutputStream();

                outputStream.write(("predict "+gv.getID_number()).getBytes("utf-8"));
                try {
                    Thread.sleep(100); //暫停，每一秒輸出一次
                }catch (InterruptedException e) {
                    return;
                }


                //TimeUnit.SECONDS.sleep(1);
                outputStream.write(("exit").getBytes("utf-8"));
                outputStream.flush();



                is = socket.getInputStream();

                // 步骤2：创建输入流读取器对象 并传入输入流对象
                // 该对象作用：获取服务器返回的数据
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                response = br.readLine();

                String tmp1 = "1";
                String tmp2 = "0";
                //int flag1 = 9, flag2 = 0;
                //int flag1 = tmp1.compareTo(response);
                //int flag2 = tmp2.compareTo(response);
                //int tmp = Integer.parseInt(response);
                //byte[] b = response.getBytes(StandardCharsets.UTF_8);
                //String tmp = new String(b, StandardCharsets.US_ASCII);
                red_msg = "紅燈區(應注意)";
                green_msg = "綠燈區(請保持)";
                String[] responses = response.split(" ");
                String the_result = responses[0];
                String notice = responses[1];
                if(tmp1.compareTo(the_result) == 0) {
                    //receive_message.setText(red_msg);
                    //receive_message.setTextColor(Color.parseColor("#f20505"));
                    red_or_green = red_msg;
                    gv.setpredict_result(red_msg);
                    gv.setmore_notice(notice);
                }
                else if(tmp2.compareTo(the_result) == 0) {
                    //receive_message.setText(green_msg);
                    //receive_message.setTextColor(Color.parseColor("#00cc00"));
                    red_or_green = green_msg;
                    gv.setpredict_result(green_msg);
                    gv.setmore_notice(notice);
                }
                else {

                    //receive_message.setText(response);
                    gv.setpredict_result(the_result);
                    gv.setmore_notice(null);
                }
                // 步骤4:通知主线程,将接收的消息显示到界面
                //Message msg = Message.obtain();
                //msg.what = 0;
                //handler.sendMessage(msg);

                outputStream.close();
                //br.close();
                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }


            Intent intent = new Intent();
            intent.setClass(Result_predict.this, Loaing_animation.class);
            //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Result_predict.this);
            startActivity(intent);
            //finish();
        }
    }

    class goto_page_home extends Thread {

        public goto_page_home() {
        }

        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(Result_predict.this, MainActivity.class);
            startActivity(intent);
            //finish();

            //}
        }
    }



}