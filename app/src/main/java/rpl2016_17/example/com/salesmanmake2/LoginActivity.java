package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    EditText email, pw;
    ImageView info;

    Button btnlogin;
    String text_email, text_pw;
    private static final String MY_PREFS_NAME = "login";

    public String emailku = email.getText().toString();
    public String pwku = pw.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        pw = findViewById(R.id.et_pw);
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
                text_pw = pw.getText().toString();
                if (text_email.isEmpty() && text_pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email dan Nomor Handphone tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (text_email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (text_pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nomor Handphone tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    AndroidNetworking.post("http://sales-report.smkrus.com/api/login")
                            .addBodyParameter("email", emailku)
                            .addBodyParameter("password", pwku)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null) {
                                        try {
                                            boolean isSuccess = response.getBoolean("success");
                                            if (isSuccess) {
                                                JSONObject payload = response.getJSONObject("payload");
                                                Long id = payload.getLong("id");
                                                //code sharpref..
                                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                                editor.putString("name", email.getText().toString());
                                                editor.apply();
                                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                JSONObject errorObj = response.getJSONObject("error");
                                                String message = errorObj.getString("message");
                                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    Log.e(TAG, "onError: " + error.getLocalizedMessage());
                                    Toast.makeText(LoginActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }

            }
        });


    }
}
