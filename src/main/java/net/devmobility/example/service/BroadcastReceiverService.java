package net.devmobility.example.service;

import android.app.IntentService;
import android.content.Intent;

public class BroadcastReceiverService extends IntentService {

    public static final String ACTION = "net.devmobility.example.SendBroadcast";
    public static final String NAME = "name";
    public static final String MESSAGE = "message";

    public BroadcastReceiverService(){
        super("BroadcastReceiverService");
    }

    public BroadcastReceiverService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String name = intent.getStringExtra(NAME);
        Thread sendThread = new Thread() {
            @Override
            public void run() {
                try {
                    // Fake some work
                    Thread.sleep(10 * 1000);

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(ACTION);
                    sendIntent.putExtra(MESSAGE, "Hello, " + name + "!");
                    sendBroadcast(sendIntent);
                } catch (InterruptedException e) {
                }
            }
        };
        sendThread.start();
    }
}