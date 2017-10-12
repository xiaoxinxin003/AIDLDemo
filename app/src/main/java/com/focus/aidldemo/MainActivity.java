package com.focus.aidldemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.focus.aidldemo.service.RemoteService;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    //    @BindView(R.id.btn_unbind) Button mUnbindBtn;
    @OnClick(R.id.btn_unbind)
    void unbind() {
        onUnbindClick();
    }

    @OnClick(R.id.btn_onbind)
    void bind() {
        onBindClick();
    }

    private IMyAidlInterface remoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        ButterKnife.inject(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            remoteService = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                Log.i(TAG, "Client pid= " + Process.myPid());
                Log.i(TAG, "RemoteService pid= " + remoteService.getPid());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "Service has unexpectedly disconnected");
            remoteService = null;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.e(TAG, "Service onBindingDied" + name);
        }
    };

    public static boolean isServiceRun(Context mContext, String className) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }


    private void onBindClick() {
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE); // 绑定服务
    }

    private void onUnbindClick() {
        if (isServiceRun(getApplicationContext(), "com.focus.aidldemo.service.RemoteService")) {
            unbindService(connection);
        } else {
            Toast.makeText(this, "service is not running", Toast.LENGTH_SHORT).show();
        }
    }
}
