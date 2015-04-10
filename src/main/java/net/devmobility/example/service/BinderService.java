package net.devmobility.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class BinderService extends Service {

    private static final String TAG = BinderService.class.getSimpleName();

    public static final int SHOW_RESULT = 100;

    private final IBinder binder = new LocalBinder();

    private Handler handler;

    private Thread updateThread;
    private boolean updating;

    public BinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        stopUpdating();
        super.onDestroy();
    }

    public void startUpdating() {

        Log.d(TAG, "Updating...");
        if (updating) {
            return;
        }
        updating = true;
        updateThread = new Thread() {
            @Override
            public void run() {
                int result = 0;
                try {
                    while (!isInterrupted() && updating) {
                        result += 5;
                        Log.i(TAG, "Result = " + result);
                        notifyHandler(result);
                        Thread.sleep(10*1000);
                    }
                } catch (InterruptedException e) {
                    updating = false;
                }
            }
        };
        updateThread.start();
    }

    public void stopUpdating() {
        if (updateThread != null) {
            updateThread.interrupt();
            updateThread = null;
        }
        updating = false;
        Log.d(TAG, "Stopped.");
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void notifyHandler(int result) {
        if (handler == null) {
            return;
        }
        Message msg = handler.obtainMessage(SHOW_RESULT);
        msg.obj = result;
        handler.sendMessage(msg);
    }

    public class LocalBinder extends Binder {
        BinderService getService() {
            // Return this instance of BinderService so clients can call public methods
            return BinderService.this;
        }
    }
}
