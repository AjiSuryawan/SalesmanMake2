package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText email, pasword;
    Button btnlogin;
    private static final String MY_PREFS_NAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        pasword = findViewById(R.id.et_hp);
        btnlogin = findViewById(R.id.btn_lg);

        btnlogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("name", email.getText().toString());
                editor.apply();
                Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
