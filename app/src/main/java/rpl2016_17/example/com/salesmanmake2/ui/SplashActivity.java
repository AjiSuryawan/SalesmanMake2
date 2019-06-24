package rpl2016_17.example.com.salesmanmake2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import rpl2016_17.example.com.salesmanmake2.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {

            /*
             * showing splash screen with a timer. This will be useful when you
             * want to show case your app logo/company
             */
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                String restoredText = prefs.getString("name", "");
                if (restoredText == "") {
                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplication(), DashboardActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
