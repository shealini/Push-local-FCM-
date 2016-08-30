package shelly.com.gifanimations;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thegirlwithspellingmistake on 25/08/16.
 */
public class MyService extends IntentService {

   private Date futureDate;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor ;


    public MyService() {
        super("");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        futureDate = new Date(new Date().getTime() + 86400000);
        Log.d("inside service", futureDate.getHours() + "is" + futureDate.getMinutes());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPreferences = this.getSharedPreferences("SERVICE" , 0);
        editor = sharedPreferences.edit();

        editor.putBoolean("isServiceRunning" , true);

        Log.d("inside service", "Service Running = " + "True ");
        editor.commit();

        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intentReceiver = new Intent(this, MyAppReciever.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intentReceiver, 0);
//        intent.putExtra("isnotif" , false);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 02);
        calendar.set(Calendar.SECOND , 0);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 seconds .
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000*60*1, alarmIntent);


        //NOTIFICATION
//
//        AlarmManager alarmMgrNotif = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//        Intent intentReceiverNotif = new Intent(this, MyAppReciever.class);
//        intent.putExtra("isnotif" , true);
//
//        PendingIntent alarmIntentnotif = PendingIntent.getBroadcast(this, 0, intentReceiverNotif, 0);
//
//// Set the alarm to start at 8:30 a.m.
//        Calendar calendarnotif = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 6);
//        calendar.set(Calendar.MINUTE, 30);
//        calendar.set(Calendar.SECOND , 0);
//
//// setRepeating() lets you specify a precise custom interval--in this case,
//// 1 seconds .
//        alarmMgrNotif.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*1,alarmIntentnotif);


    }
}
