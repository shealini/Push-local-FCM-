package shelly.com.gifanimations.PushbyTagManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

import shelly.com.gifanimations.ContainerHolderSingleton;
import shelly.com.gifanimations.MainActivity;
import shelly.com.gifanimations.R;

/**
 * Created by Thegirlwithspellingmistake on 23/08/16.
 */
public class RecieverToSendNotification extends BroadcastReceiver {
    private static int i  ;
    private static final String CONTAINER_ID = "GTM-K8XVZX";
    private  String title = "";
    private String content = "";
    private static String previousId = "0" ;


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MyTagManager ", "running Tag Manager  RECEIVER & old id is  " + previousId) ;

        refreshTagManager(context);

    }

    private void refreshTagManager(final Context context){
        TagManager tagManager = TagManager.getInstance(context);
        tagManager.setVerboseLoggingEnabled(true);

        com.google.android.gms.common.api.PendingResult<ContainerHolder> pending = tagManager.loadContainerPreferNonDefault(
                CONTAINER_ID,
                R.raw.gtm_analytics
        );


        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                final Container container = containerHolder.getContainer();

                containerHolder.refresh();

               String id = container.getString("id");

                if(previousId.equals(id)){
                    Log.d("MyTagManager ", "Id still same ") ;
                }
                else {

                    Log.d("MyTagManager ", "id are diffrent new id=  " +id)  ;

                    previousId = id;

                    title = container.getString("title");
                    content = container.getString("content");
                    sendNotification(context , title , content);
                }

                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("IL", "failure loading container");

                    return;
                }

                ContainerHolderSingleton.setContainerHolder(containerHolder);
                ContainerLoadedCallback.registerCallbacksForContainer(container);
                containerHolder.setContainerAvailableListener(new ContainerLoadedCallback());
            }
        }, 2, TimeUnit.SECONDS);

    }

    private void sendNotification(Context context, String title , String content){
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notif)
                .setContentTitle("TITLE " + " " + i )
                .setSound(soundUri)
                .setAutoCancel(true)
                .setContentText("I AM CONTENT ");


        Intent resultIntent = new Intent(context, MainActivity.class);

        //TODO : app closes with in five secs Fix this or open some other acivity
        // or set bool to diffreniae auo open and local push
        resultIntent.setAction("shelly.com.gifanimations.wakeup");


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(i++, builder.build());
    }

    private static class ContainerLoadedCallback implements ContainerHolder.ContainerAvailableListener {
        @Override
        public void onContainerAvailable(ContainerHolder containerHolder, String containerVersion) {
            // We load each container when it becomes available.
            Container container = containerHolder.getContainer();
            registerCallbacksForContainer(container);
        }

        public static void registerCallbacksForContainer(Container container) {
            // Register two custom function call macros to the container.
        }
    }
}