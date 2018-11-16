package com.mackentoch.beaconsandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.react.ReactActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BeaconsAndroidActivity extends ReactActivity {
    private static final String LOG_TAG = "BeaconsAndroidModule";
    private Context context;
    private RegionBootstrap regionBootstrap;
    private static final String NOTIFICATION_CHANNEL_ID = "SmartSpacePro";
    private static boolean channelCreated = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "BeaconsAndroidActivity onCreate");
        super.onCreate(savedInstanceState);
        
        this.context = getApplicationContext();

        this.regionBootstrap.addRegion(new Region("AprilBrother", Identifier.parse("B5B182C7-EAB1-4988-AA99-B5C1517008D9"), null, null));
    }

//    private Class getMainActivityClass() {
//        String packageName = this.context.getPackageName();
//        Intent launchIntent = this.context.getPackageManager().getLaunchIntentForPackage(packageName);
//        String className = launchIntent.getComponent().getClassName();
//        try {
//            return Class.forName(className);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private void checkOrCreateChannel(NotificationManager manager) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
//            return;
//        if (channelCreated)
//            return;
//        if (manager == null)
//            return;
//
//        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Smart_Space_Pro_Channel", android.app.NotificationManager.IMPORTANCE_HIGH);
//        channel.setDescription("Smart_Space_Pro_Channel_Description");
//        channel.enableLights(true);
//        channel.enableVibration(true);
//
//        manager.createNotificationChannel(channel);
//        channelCreated = true;
//    }
//
//    private void createNotification(String title, String message) {
//        Class intentClass = getMainActivityClass();
//        Intent notificationIntent = new Intent(this.context, intentClass);
//        Integer requestCode = new Random().nextInt(10000);
//        PendingIntent contentIntent = PendingIntent.getActivity(this.context, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this.context, NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(false)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setContentIntent(contentIntent);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notification.setCategory(NotificationCompat.CATEGORY_CALL);
//        }
//
//        NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
//        checkOrCreateChannel(notificationManager);
//
//        Notification info = notification.build();
//        info.defaults |= Notification.DEFAULT_LIGHTS;
//
//        notificationManager.notify(requestCode, info);
//    }

//    @Override
//    public void didEnterRegion(Region region) {
//        Log.i(LOG_TAG, ">>> didEnterRegion");
//
//        createNotification("[Beacon] DidEnterRegion", "You have entered the Region");
//
////        Intent intent = new Intent(this.context, intentClass);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////
////        // Important:  make sure to add android:launchMode="singleInstance" in the manifest
////        // to keep multiple copies of this activity from getting created if the user has
////        // already manually launched the app.
////        this.context.startActivity(intent);
//    }
//
//    @Override
//    public void didExitRegion(Region region) {
//        Log.i(LOG_TAG, ">>> didExitRegion");
//        createNotification("[Beacon] DidExitRegion", "You have exited the Region");
//    }
//
//    @Override
//    public void didDetermineStateForRegion(int i, Region region) {
//        Log.i(LOG_TAG, ">>> didDetermineStateForRegion");
//    }
}
