package com.example.naveenkumar.notifieradmin;

/**
 * Created by VSRK on 12/21/2015.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NotificationService extends NotificationListenerService {

    Context context;
    DatabaseHandler db = new DatabaseHandler(this);


    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {


        String pack = sbn.getPackageName();



        //sbn.getNotification().color;
        Bundle extras = sbn.getNotification().extras;
        //int extr = sbn.getNotification().color;


        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(pack, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        String app = (String) packageManager.getApplicationLabel(applicationInfo);
        //  Log.i("Package",pack);
        // Log.i("Ticker",ticker);
        // Log.i("Title",title);
        // Log.i("Text",text);

//        Intent msgrcv = new Intent("Msg");
        //      msgrcv.putExtra("package", pack);
        //    msgrcv.putExtra("ticker", ticker);
        //  msgrcv.putExtra("title", title);
        //msgrcv.putExtra("text", text);
        Log.d("PACK", pack);
        // notify.setText(text);
        // notify.setTitle(title);
        //notify.setPackage(pack);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = sdf.format(new Date());
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String dat = time.substring(6, 8);
        String hour = time.substring(9, 11);
        String minit = time.substring(11, 13);
        String second = time.substring(13, 15);


        if (!app.equals("Instagram") && !app.equals("Chrome")
                && !app.equals("Pages Manager")
                && !app.equals("Clock")
                && !app.equals("Google Play Music")
                && !app.equals("Google Play Store")
                && !app.equals("Volsbb Onetouch")
                && !app.equals("Instagram")
                && !app.equals("Assist")
                && !app.equals("Download Manager")
             //   && !app.equals("WhatsApp")
                && !app.equals("ES File Explorer")
                && !app.equals("Downloads")
                && !app.equals("Play Music")
                && !app.equals("Messaging")
                && !app.equals("System UI")
                && !app.equals("Gmail")
                && !app.equals("Google+")
                && !app.equals("Phone")
                && !app.equals("Android System")
                && !app.equals("Android system")
                && !app.equals("Android System WebView")
                && !app.equals("BrowserMessage")
                && !app.equals("Dialler")
                && !app.equals("Phone")
                && !app.equals("Maps")
                && !app.equals("Facebook")
                && !app.equals("Facebook Messenger")
                && !app.equals("Messenger")
                && !app.equals("Bluetooth Share")
                ) {

            String type = "app";

            db.addNotify(new Notify(pack, title, text, app, hour, minit, second, dat, month, year, type));
        }/*else if (app.equals("Twitter") ||
                app.equals("Messenger")||
                app.equals("Pages Manager")||
                app.equals("Phone") ||
                app.equals("Facebook Messenger") ||
                app.equals("Facebook") ||
                app.equals("Messaging") ||
                app.equals("Gmail") ||
                app.equals("Google+") ||
                app.equals("Chrome") ||
                app.equals("BrowserMessage") ||
                app.equals("Maps") ||
                app.equals("WhatsApp")) {

            String type = "phone";

            db.addNotify(new Notify(pack, title, text, app, hour, minit, second, dat, month, year, type));

        }*/
        else
        {
            Log.v("TAG",app);                //LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        }
    }



    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg","Notification Removed");

    }
}