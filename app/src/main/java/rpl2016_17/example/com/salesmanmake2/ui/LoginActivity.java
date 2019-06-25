package rpl2016_17.example.com.salesmanmake2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import rpl2016_17.example.com.salesmanmake2.Constants;
import rpl2016_17.example.com.salesmanmake2.R;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    EditText email, pw;
    ImageView info;

    Button btnlogin;
    String text_email, text_pw;
    private static final String MY_PREFS_NAME = "login";
    private ProgressDialog mProgress;

//    public String emailku = email.getText().toString();
//    public String pwku = pw.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.et_email);
        pw = findViewById(R.id.et_pw);
        btnlogin = findViewById(R.id.btn_login);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        btnlogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                mProgress.show();
                text_email = email.getText().toString();
                text_pw = pw.getText().toString();
                if (text_email.length()==0) {
                    email.setError("Enter email");
                    mProgress.dismiss();
                } else if (text_pw.length()==0) {
                    pw.setError("Enter password");
                    mProgress.dismiss();
                }else {
                    AndroidNetworking.post(Constants.BASE_URL + "/api/login")
                            .addBodyParameter("email", text_email)
                            .addBodyParameter("password", text_pw)
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
                                                editor.putString("name", email.getText().toString())
                                                        .putLong("id", id);
                                                editor.apply();
                                                mProgress.dismiss();
                                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                mProgress.dismiss();
                                                JSONObject errorObj = response.getJSONObject("error");
                                                String message = errorObj.getString("message");
                                                Toast.makeText(LoginActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            mProgress.dismiss();
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    Log.e(TAG, "onError: " + error.getLocalizedMessage());
                                    Toast.makeText(LoginActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                                }
                            });


                }

            }
        });


    }

    private void connection() {

    }
}
