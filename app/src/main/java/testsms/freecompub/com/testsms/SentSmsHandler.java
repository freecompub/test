package testsms.freecompub.com.testsms;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

/**
 * Created by s826210 on 22/02/2016.
 */
public class SentSmsHandler extends ContentObserver {
    private Context context;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SentSmsHandler(Handler handler) {
        super(handler);
    }

        public SentSmsHandler(Handler handler, Context context){
            super(handler);
        this.context=context;
    }
    public void onChange(boolean selfChange){
        Uri uriSMS = Uri.parse("content://sms/out");
        Cursor cursor = context.getContentResolver().query(
                uriSMS, null, null, null, null);
        if (cursor.moveToNext()) {
            String protocol = cursor.getString(cursor.getColumnIndex("protocol"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            // Only processing outgoing sms event & only when it
            // is sent successfully (available in SENT box).
            if (protocol != null || type != Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT) {
                return;
            }
            int dateColumn = cursor.getColumnIndex("date");
            int bodyColumn = cursor.getColumnIndex("body");
            int addressColumn = cursor.getColumnIndex("address");
            String from = "0";
            String to = cursor.getString(addressColumn);
            Date now = new Date(cursor.getLong(dateColumn));
            String message = cursor.getString(bodyColumn);
            Log.e("test","to= "+to+" now= "+now+"message= "+message );

        }
    }
}