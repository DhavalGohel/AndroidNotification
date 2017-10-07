package com.dg.androidnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;
    private int mNotificationID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Simple Notification
    public void sendNotification(View view) {
        showNotification();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotificationBadge(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String id = "my_channel_01";

            CharSequence name = id;

            String description = id;

            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setShowBadge(true);
            mChannel.setDescription("Channegl 1 Decriptipon");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            getManager().createNotificationChannel(mChannel);

        }else {
            showNotification();
        }
    }

    public void sendNotificationReply(View view){
        String replyLabel = "Replay";
        RemoteInput remoteInput = new RemoteInput.Builder("My Name Is Dhaval")
                .setLabel(replyLabel)
                .build();

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher
                , replyLabel, getReplyPendingIntent())
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        setBuilder("Replay Notification","Replay notification data text","replay_notification");
        mBuilder.addAction(replyAction); // reply action from step b above
        getManager().notify(mNotificationID, mBuilder.build());
        mNotificationID++;
    }

    private PendingIntent getReplyPendingIntent(){
        Intent resultIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resultIntent = new Intent(this, ReceiverActivity.class);
            resultIntent.putExtra("Id" , mNotificationID);
            resultIntent.putExtra("CHANNEL_ID" , "replay_notificaion");
            resultIntent.putExtra("replay","replay");
            return PendingIntent.getBroadcast(getApplicationContext(), 100, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            resultIntent = new Intent(this, ReceiverActivity.class);
            resultIntent.putExtra("Id" , mNotificationID);
            resultIntent.putExtra("CHANNEL_ID" , "replay_notificaion");
            resultIntent.putExtra("replay","replay");
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 100, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }


    private void showNotification(){
        setBuilder("Simple Notification","Simple notification data text","simple_notification");

        Intent resultIntent = new Intent(this, ReceiverActivity.class);
        resultIntent.putExtra("Id" , mNotificationID);
        resultIntent.putExtra("CHANNEL_ID" , "simple_notificaiton");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        getManager().notify(mNotificationID, mBuilder.build());
        mNotificationID++;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNotificationID = 1;
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    private void setBuilder(String title, String data,String ChannelId) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300 };
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(data)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setColor(Color.RED)
                .setChannel(ChannelId)
                .setSound(alarmSound)
                .setVibrate(vibrate)
                .setLights(0xff00ff00, 300, 100);
    }

    public static CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence("My Name Is Dhaval");
        }
        return null;
    }

    public void showVideoActivity(View view){
        startActivity(new Intent(this,VideoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
