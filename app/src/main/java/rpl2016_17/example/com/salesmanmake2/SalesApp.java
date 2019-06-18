package rpl2016_17.example.com.salesmanmake2;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class SalesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
