package testsms.freecompub.com.testsms;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(TestService.mAction);
//        registerReceiver(mServiceReceiver, intentFilter);

    }
}
