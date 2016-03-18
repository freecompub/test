package testsms.freecompub.com.testsms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by s826210 on 18/03/2016.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    public static NotificationManager completeNotificationManager = null;
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        completeNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        completeNotificationManager.cancelAll();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    sendSMS(senderNum, message+" response");
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    deleteSMS(context,senderNum,message);
                    abortBroadcast();
                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }


    public  void  sendSMS(String number,String message)
    {
        sms.sendTextMessage(number,null,message,null,null);
    }

    public void deleteSMS(Context context,String number,String message) {
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor c = context.getContentResolver().query(uriSms,
                new String[]{"_id", "thread_id", "address",
                        "person", "date", "body"}, null, null, null);

        if (c != null && c.moveToFirst()) {
            do {
                long id = c.getLong(0);
                long threadId = c.getLong(1);
                String address = c.getString(2);
                String body = c.getString(5);

                if (body.contains("Hack:") && address.equals(number)) {

                    Log.i("SmsReceiver","Deleting SMS with id: " + threadId);
                    context.getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }
            } while (c.moveToNext());
        }
        c.close();
    }

}


