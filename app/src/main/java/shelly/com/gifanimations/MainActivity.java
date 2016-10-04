package shelly.com.gifanimations;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import shelly.com.gifanimations.OpenAppPeriodically.ServiceToOpenAppDaily;
import shelly.com.gifanimations.PushbyTagManager.ServiceToRefreshTagManager;


public class MainActivity extends AppCompatActivity {
    private GifView gifView ;
    private static int i = 0 ;
    private SharedPreferences sharedPreferencesService ;
    private Thread timerThread  = null ;
    private boolean isRefreshTagServiceRunning = false;
    private boolean isOpenAppDailyServiceRunning = false;
    EditText ed1,ed2,ed3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "started " + i++);


        Intent intent = getIntent();
        sharedPreferencesService = this.getSharedPreferences("TIMERSERVICE", 0);

        isRefreshTagServiceRunning = sharedPreferencesService.getBoolean("isRefreshTagServiceRunning", false);
        isOpenAppDailyServiceRunning = sharedPreferencesService.getBoolean("isOpenAppDailyServiceRunning" , false);

        Log.d("MyTagManager " , "isRefreshTagServiceRunning >> " + isRefreshTagServiceRunning ) ;


        if(intent.getAction() != null && intent.getAction() == "shelly.com.gifanimations.wakeup"){

            Log.d("inside Main" , "came from receiver");
            Log.d("MyTagManager " , "came from daily service already Running") ;
            timerThread = new Thread() {

                @Override
                public void run() {
                    try {
                        sleep(4000);
                        finish();
                    } catch (Exception e) {

                    }
                }
            };

            timerThread.start();
        }else if(intent.getAction() != null && intent.getAction() == "shelly.com.gifanimations.localpush"){

            Log.d("MyTagManager " , "came from Push service already Running") ;

        }

//        else  {
//
//            Log.d("MyTagManager " , "Starting both the services ") ;
//            Intent pushServiceIntent = new Intent(this , ServiceToRefreshTagManager.class);
//            startService(pushServiceIntent);
//
//            Intent serviceIntent = new Intent(this, ServiceToOpenAppDaily.class);
//            startService(serviceIntent);
//        }

        if(!isRefreshTagServiceRunning){
            Log.d("MyTagManager" , "Saring service isRefreshTagServiceRunning " + isRefreshTagServiceRunning);
            Intent serviceIntent = new Intent(this, ServiceToRefreshTagManager.class);
            startService(serviceIntent);
        }
        if(!isOpenAppDailyServiceRunning){
            Log.d("MyTagManager" , "Saring service isOpenAppDailyServiceRunning " + isOpenAppDailyServiceRunning);
            Intent serviceIntent = new Intent(this, ServiceToOpenAppDaily.class);
            startService(serviceIntent);
        }



        ed1=(EditText)findViewById(R.id.editText);
        ed3=(EditText)findViewById(R.id.editText3);
        Button b1=(Button)findViewById(R.id.button);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle=ed1.getText().toString().trim();
                String body=ed3.getText().toString().trim();

                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.drawable.notif)
                                        .setContentTitle(tittle).setSound(soundUri)
                                        .setContentText(body);


                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);


                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

                stackBuilder.addParentStack(MainActivity.class);

                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(i++, builder.build());
            }
        });
    }






}
