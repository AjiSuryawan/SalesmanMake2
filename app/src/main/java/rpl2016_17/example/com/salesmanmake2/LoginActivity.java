package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText email, hp;
    ImageView info;
    Button btnlogin;
    String text_email,text_hp;
    private static final String MY_PREFS_NAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        hp = findViewById(R.id.et_hp);
        btnlogin = findViewById(R.id.btn_login);
        info = findViewById(R.id.info_btn);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

            private void openDialog() {
                dialog dial = new dialog();
                dial.show(getSupportFragmentManager(),"dial");
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                text_email = email.getText().toString();
                text_hp = hp.getText().toString();
                if(text_email.isEmpty() && text_hp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email dan Nomor Handphone tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else if(text_email.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if(text_hp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nomor Handphone tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("name", email.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
