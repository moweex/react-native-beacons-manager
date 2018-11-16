package com.mackentoch.beaconsandroid;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Random;

public class BeaconsAndroid {
    private static final String LOG_TAG = "BeaconsAndroidModule";
    private static Context context;
    public static RegionBootstrap regionBootstrap;
    private static BeaconManager mBeaconManager;
    private static BootstrapNotifier bootstrapNotifier;
    private static boolean haveDetectedBeaconsSinceBoot = false;
    private static final String NOTIFICATION_CHANNEL_ID = "SmartSpacePro";
    private static boolean channelCreated = false;

    public static void init(Context context, BootstrapNotifier bootstrapNotifier) {
        BeaconsAndroid.context = context.getApplicationContext();
        BeaconsAndroid.bootstrapNotifier = bootstrapNotifier;
//        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(context);

        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb.  To find the proper
        // layout expression for other beacon types, do a web search for "setBeaconLayout"
        // including the quotes.
        //
//        beaconManager.getBeaconParsers().clear();
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        // Uncomment the code below to use a foreground service to scan for beacons. This unlocks
        // the ability to continually scan for long periods of time in the background on Andorid 8+
        // in exchange for showing an icon at the top of the screen and a always-on notification to
        // communicate to users that your app is using resources in the background.
        //

//        Notification.Builder builder = new Notification.Builder(BeaconsAndroid.context);
//        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
//        builder.setContentTitle("Scanning for Beacons");
//        Class intentClass = getMainActivityClass();
//        Intent intent = new Intent(BeaconsAndroid.context, intentClass);
//        PendingIntent pendingIntent = PendingIntent.getActivity(BeaconsAndroid.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            builder.setVisibility(Notification.VISIBILITY_SECRET);
////        }
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
//                    "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("My Notification Channel Description");
//            NotificationManager notificationManager = (NotificationManager) BeaconsAndroid.context.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(channel);
//            builder.setChannelId(channel.getId());
//        }
//
//        Notification noti = builder.build();
////        noti.defaults = 0;
//
//        beaconManager.enableForegroundServiceScanning(builder.build(), 456);
//        // For the above foreground scanning service to be useful, you need to disable
//        // JobScheduler-based scans (used on Android 8+) and set a fast background scan
//        // cycle that would otherwise be disallowed by the operating system.
//        //
//        beaconManager.setEnableScheduledScanJobs(false);
//        beaconManager.setBackgroundBetweenScanPeriod(0);
//        beaconManager.setBackgroundScanPeriod(1100);
//
//        BeaconsAndroid.regionBootstrap = new RegionBootstrap(BeaconsAndroid.bootstrapNotifier, new ArrayList<Region>());
    }

    public static void addRegion(Region region) {
        BeaconsAndroid.regionBootstrap = new RegionBootstrap(BeaconsAndroid.bootstrapNotifier, region);
    }

    public static void removeRegion(Region region) {
        BeaconsAndroid.regionBootstrap = new RegionBootstrap(BeaconsAndroid.bootstrapNotifier, new ArrayList<Region>());
    }

    public static void didEnterRegion(Region region) {
        Log.i(LOG_TAG, "|>>> didEnterRegion");
        createNotification("[Beacon] DidEnterRegion", "You have entered the Region");
        // The very first time since boot that we detect an beacon, we launch the
        // MainActivity
        Class intentClass = getMainActivityClass();
        Intent intent = new Intent(BeaconsAndroid.context, intentClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Important:  make sure to add android:launchMode="singleInstance" in the manifest
        // to keep multiple copies of this activity from getting created if the user has
        // already manually launched the app.
        BeaconsAndroid.context.startActivity(intent);
    }

    public static void didExitRegion(Region region) {
        Log.i(LOG_TAG, "|>>> didExitRegion");
        createNotification("[Beacon] DidExitRegion", "You have exited the Region");
    }

    public static void didDetermineStateForRegion(int i, Region region) {
        Log.i(LOG_TAG, "|>>> didDetermineStateForRegion");

//        BeaconsAndroid.mBeaconManager.addRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                //CODE FOR BEACON DETECTION
//            }
//        });
//
//        try {
//            BeaconsAndroid.mBeaconManager.startRangingBeaconsInRegion(region);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    public static void setManager(BeaconManager mBeaconManager) {
        BeaconsAndroid.mBeaconManager = mBeaconManager;

        Notification.Builder builder = new Notification.Builder(BeaconsAndroid.context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle("Scanning for Beacons");
        Class intentClass = getMainActivityClass();
        Intent intent = new Intent(BeaconsAndroid.context, intentClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(BeaconsAndroid.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setVisibility(Notification.VISIBILITY_SECRET);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
                    "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My Notification Channel Description");
            NotificationManager notificationManager = (NotificationManager) BeaconsAndroid.context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }

//        Notification noti = builder.build();
//        noti.defaults = 0;

        BeaconsAndroid.mBeaconManager.enableForegroundServiceScanning(builder.build(), 457);
        // For the above foreground scanning service to be useful, you need to disable
        // JobScheduler-based scans (used on Android 8+) and set a fast background scan
        // cycle that would otherwise be disallowed by the operating system.
        //
        BeaconsAndroid.mBeaconManager.setEnableScheduledScanJobs(false);
        BeaconsAndroid.mBeaconManager.setBackgroundBetweenScanPeriod(0);
        BeaconsAndroid.mBeaconManager.setBackgroundScanPeriod(1100);

        BeaconsAndroid.regionBootstrap = new RegionBootstrap(BeaconsAndroid.bootstrapNotifier, new ArrayList<Region>());
    }

    public static void removeManager() {
        BeaconsAndroid.mBeaconManager = null;
    }

    private static Region createRegion(String regionId, String beaconUuid) {
        Identifier id1 = (beaconUuid == null) ? null : Identifier.parse(beaconUuid);
        return new Region(regionId, id1, null, null);
    }

    private static Region createRegion(String regionId, String beaconUuid, String minor, String major) {
        Identifier id1 = (beaconUuid == null) ? null : Identifier.parse(beaconUuid);
        return new Region(
                regionId,
                id1,
                major.length() > 0 ? Identifier.parse(major) : null,
                minor.length() > 0 ? Identifier.parse(minor) : null
        );
    }

    private static Class getMainActivityClass() {
        String packageName = BeaconsAndroid.context.getPackageName();
        Intent launchIntent = BeaconsAndroid.context.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void checkOrCreateChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        if (channelCreated)
            return;
        if (manager == null)
            return;

        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Smart_Space_Pro_Channel", android.app.NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Smart_Space_Pro_Channel_Description");
        channel.enableLights(true);
        channel.enableVibration(true);

        manager.createNotificationChannel(channel);
        channelCreated = true;
    }

    private static void createNotification(String title, String message) {
        Class intentClass = getMainActivityClass();
        Intent notificationIntent = new Intent(BeaconsAndroid.context, intentClass);
        Integer requestCode = new Random().nextInt(10000);
        PendingIntent contentIntent = PendingIntent.getActivity(BeaconsAndroid.context, requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(BeaconsAndroid.context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(contentIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setCategory(NotificationCompat.CATEGORY_CALL);
        }

        NotificationManager notificationManager = (NotificationManager) BeaconsAndroid.context.getSystemService(Context.NOTIFICATION_SERVICE);
        checkOrCreateChannel(notificationManager);

        Notification info = notification.build();
        info.defaults |= Notification.DEFAULT_LIGHTS;

        notificationManager.notify(requestCode, info);
    }
}
