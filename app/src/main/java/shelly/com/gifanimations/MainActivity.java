package shelly.com.gifanimations;

import android.app.AlarmManager;
import android.app.Notification;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private GifView gifView ;
    private static int i = 0 ;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;
    private Thread timerThread  = null ;
    EditText ed1,ed2,ed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main Activity", "started " + i++);
//        gifView = (GifView)findViewById(R.id.gifImageView);
//        gifView.setMovieResource(R.drawable.gun10);
//        gifView.setMovieTime(3000);



        sharedPreferences = this.getSharedPreferences("SERVICE", 0);
        if(!sharedPreferences.getBoolean("isServiceRunning" , false)){

            Log.d("inside Main", "Service Running = " + "False  ");

        }else Log.d("inside Main" , "service " + sharedPreferences.getBoolean("isServiceRunning" , false));

        Intent intent = getIntent();
        if(intent.getAction() != null && intent.getAction() == "shelly.com.gifanimations.wakeup"){

            Log.d("inside Main" , "came from receiver");
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
        }else {
            Intent serviceIntent = new Intent(this, MyService.class);
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
