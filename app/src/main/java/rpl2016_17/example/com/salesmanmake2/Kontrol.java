package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Kontrol extends AppCompatActivity {

    private static final String MY_PREFS_NAME = "login" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("name", "");
        if (restoredText == "") {
            Intent intent = new Intent(getApplication(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplication(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
