package shelly.com.gifanimations.OpenAppPeriodically;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.common.api.ResultCallback;

import java.util.concurrent.TimeUnit;

import shelly.com.gifanimations.ContainerHolderSingleton;
import shelly.com.gifanimations.MainActivity;
import shelly.com.gifanimations.R;

/**
 * Created by Thegirlwithspellingmistake on 23/08/16.
 */
public class RecieverToOpenAppDaily extends BroadcastReceiver {
    private static int i  ;
    private static final String CONTAINER_ID = "GTM-K8XVZX";
    private  String title = "";
    private String content = "";

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("inside receiver", "");

        Log.d("MyTagManager ", "running Daily app open RECEIVER ") ;

        //TODO : to send notification or open app periodically #uncomment below code

        Intent intentActivity = new Intent(context , MainActivity.class);
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intentActivity.setAction("shelly.com.gifanimations.wakeup");
        context.startActivity(intentActivity);

    }

}