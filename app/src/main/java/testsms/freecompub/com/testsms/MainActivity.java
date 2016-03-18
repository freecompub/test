package testsms.freecompub.com.testsms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import testsms.freecompub.com.testsms.mail.GMailSender;

public class MainActivity extends AppCompatActivity {
    Intent serviceIntent;
    private static TestService mServiceReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
super.onResume();
        serviceIntent = new Intent(MainActivity.this, TestService.class);
        startService(serviceIntent);
        // Registro el broadcast del Service para obtener los datos
        mServiceReceiver = new TestService();
        startService(serviceIntent);



        try {
            GMailSender sender = new GMailSender("username@gmail.com", "password");
            sender.sendMail("This is Subject",
                    "This is Body",
                    "user@gmail.com",
                    "user@yahoo.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }



    }
}
