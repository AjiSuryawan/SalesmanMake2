package rpl2016_17.example.com.salesmanmake2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class intromanager extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE);
        int firsttime = sharedPreferences.getInt("first_time", 0);
        if (firsttime == 0) {
            Intent i = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(getApplicationContext(),Kontrol.class);
            startActivity(i);
            finish();
        }
    }


}