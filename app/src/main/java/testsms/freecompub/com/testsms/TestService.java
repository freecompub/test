package testsms.freecompub.com.testsms;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class TestService extends Service {
    ContentResolver content;
    ContentResolver contentResolver;
    public static final String mAction = "SMSTracker";

    public TestService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        contentResolver = this.getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms/out"), true, new SentSmsHandler(new Handler()));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
