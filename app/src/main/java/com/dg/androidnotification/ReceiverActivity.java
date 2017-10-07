package com.dg.androidnotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by dhaval gohel on 3/10/17.
 */

public class ReceiverActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // do whatever you want with the message. Send to the server or add to the db.
        // for this tutorial, we'll just show it in a toast;
        CharSequence message = MainActivity.getReplyMessage(intent);
        String messageId = intent.getStringExtra("replay");
        int noID = intent.getIntExtra("Id",1);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(noID);

        Toast.makeText(context, "Message ID: " + messageId + "\nMessage: " + message,
                Toast.LENGTH_SHORT).show();
    }
}
