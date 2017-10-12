package com.focus.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.focus.aidldemo.IMyAidlInterface;

public class RemoteService extends Service {
    private static final String TAG = "MainActivity";

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "call onBind method");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "call onUnbind method");
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "call onDestroy method");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    private final IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub(){

        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
