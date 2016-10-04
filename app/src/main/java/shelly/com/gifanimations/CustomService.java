package shelly.com.gifanimations;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Thegirlwithspellingmistake on 25/08/16.
 *
 * Firebase push
 */
public class CustomService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
