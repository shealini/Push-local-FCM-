package shelly.com.gifanimations.OpenAppPeriodically;

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
public class ServiceToOpenAppDaily extends IntentService {

   private Date futureDate;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor ;


    public ServiceToOpenAppDaily() {
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

        Log.d("MyTagManager " , "running Daily app open SERVICE ") ;

        sharedPreferences = this.getSharedPreferences("TIMERSERVICE", 0);
        editor = sharedPreferences.edit();

        editor.putBoolean("isOpenAppDailyServiceRunning", true);

        editor.commit();

        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent intentReceiver = new Intent(this, RecieverToOpenAppDaily.class);

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
                1000*60*60*1, alarmIntent);


    }
}
