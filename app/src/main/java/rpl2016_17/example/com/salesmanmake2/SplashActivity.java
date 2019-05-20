package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
                Intent i = new Intent(SplashActivity.this.getApplicationContext(), Kontrol.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
