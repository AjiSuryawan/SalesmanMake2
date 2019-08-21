package rpl2016_17.example.com.salesmanmake2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import rpl2016_17.example.com.salesmanmake2.R;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
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
