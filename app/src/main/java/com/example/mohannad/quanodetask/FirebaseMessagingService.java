package com.example.mohannad.quanodetask;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingService";

    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String title = remoteMessage.getNotification().getTitle();
//        String message = remoteMessage.getNotification().getBody();
//        Log.d(TAG, " onMessageRecieved: Message Recieved \n" +
//                "Title " + title + "\n" +
//                "Message " + message);
//
//        sendNotification(title,message);

        //Check if message contains a data payload
        if(remoteMessage.getData().size()> 0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonmessage = data.getString("extra_information");
                Log.d(TAG, " onMessageRecieved \n" +
                "Extra information " + jsonmessage);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }

        //Check if message contains a notification payload
        if(remoteMessage.getNotification() != null){
            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click action

            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Message: " + message);
            Log.d(TAG, "Click Action: " + click_action);

            sendNotification(title, message, click_action);
        }
    }

    @Override
    public void onDeletedMessages() {

    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void sendNotification(String title, String messageBody, String click_action) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
//                PendingIntent.FLAG_ONE_SHOT);

        Intent intent;

        if (click_action.equals("MainActivity")) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        else if (click_action.equals("AnotherActivity")) {
            intent = new Intent(this, AnotherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else if (click_action.equals("SecondActivity")) {
            intent = new Intent(this, SecondActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        else if (click_action.equals("ThirdActivity")) {
            intent = new Intent(this, ThirdActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
               PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }
}


