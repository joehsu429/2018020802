package com.example.student.a2018020802;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class MyAlarmService extends Service {
    NotificationChannel channelLove;
    String idLove = "LOVE";
    NotificationManager nm;
    int NOTIFICATION_ID = 879;

    public MyAlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Myservice", "This is onCreate");
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26)//少了這一個在26以上無法執行
        {
            regChannel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", "Service start");
        Log.d("My", "This is onStartCommand");
        new Thread() {
            @Override
            public void run() {
                super.run();
//                int i;
//                for (i = 0; i < 5; i++) {
//                    try {
//                        Thread.sleep(1000);
//                        Log.d("My", "This is onStartCommand" );
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                sentNotification();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressWarnings("deprecation")
    private void sentNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(MyAlarmService.this, idLove);
        } else {
            builder = new Notification.Builder(MyAlarmService.this);
        }

        // Intent it = new Intent(MainActivity.this, InfoActivity.class);
        // it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
        //         Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 123, it,
        //         PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("測試");
        builder.setContentText("時間到");
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setAutoCancel(true);
        // builder.setContentIntent(pi);

        Notification notify = builder.build();
        nm.notify(NOTIFICATION_ID, notify);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void regChannel() {
        channelLove = new NotificationChannel(idLove, "Channel Love", NotificationManager.IMPORTANCE_HIGH);
        channelLove.setDescription("最重要的人");
        channelLove.enableLights(true);
        channelLove.enableVibration(true);

        nm.createNotificationChannel(channelLove);
    }
}