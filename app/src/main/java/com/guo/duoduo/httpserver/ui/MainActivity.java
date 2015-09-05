package com.guo.duoduo.httpserver.ui;


import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.guo.duoduo.httpserver.R;
import com.guo.duoduo.httpserver.service.WebService;
import com.guo.duoduo.httpserver.utils.Constant;
import com.guo.duoduo.httpserver.utils.Network;


public class MainActivity extends AppCompatActivity
{

    private TextView hint;
    private MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MainHandler(this);

        initView();

        startService(new Intent(getApplicationContext(), WebService.class));
    }

    private void initView()
    {
        hint = (TextView) findViewById(R.id.hint);

        String ip = Network.getLocalIp(getApplicationContext());
        if (TextUtils.isEmpty(ip))
        {
            Message msg = new Message();
            msg.what = Constant.MSG.GET_NETWORK_ERROR;
            handler.sendMessage(msg);
        }
        else
        {
            hint.setText("在PC的浏览器中输入IP：" + ip + Constant.Config.Web_Root + " " + "回车即可");
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopService(new Intent(getApplicationContext(), WebService.class));
    }

    private static class MainHandler extends Handler
    {
        private WeakReference<MainActivity> weakReference;

        public MainHandler(MainActivity activity)
        {
            weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            final MainActivity activity = weakReference.get();
            if (activity == null)
                return;

            switch (msg.what)
            {
                case Constant.MSG.GET_NETWORK_ERROR :
                    activity.hint.setText("手机网络地址获取失败，即将退出程序");
                    activity.handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            activity.finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    }, 2 * 1000);
                    break;
            }
        }
    }
}
