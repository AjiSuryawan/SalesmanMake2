package rpl2016_17.example.com.salesmanmake2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.ReportsActivity;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private SharedPreferences preferences;
    private TextView tvUsername;
    private CircleImageView ivProfile;
    private LinearLayout cardJobs, cardReports;
    private ImageView ivLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /** init view */
        tvUsername = findViewById(R.id.tv_username);
        ivProfile = findViewById(R.id.iv_profile);
        cardJobs = findViewById(R.id.card_jobs);
        cardReports = findViewById(R.id.card_reports);
        ivLogout = findViewById(R.id.iv_logout);

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        fetchProfile();

        /** click listener */
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(i);
                finish();
            }
        });

        cardJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, JobsActivity.class);
                startActivity(intent);

            }
        });

        cardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchProfile(){
        AndroidNetworking.get("http://sales-report.smkrus.com/api/profile/{id}")
                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        if (response != null) {
                            try {
                                boolean isSuccess = response.getBoolean("success");
                                if (isSuccess) {
                                    JSONObject payload = response.getJSONObject("payload");
//                                    String email = payload.getString("email");
                                    String fullname = payload.getString("fullname");
//                                    String address = payload.getString("address");
//                                    String phone = String.valueOf(payload.getString("phone"));
                                    tvUsername.setText(fullname);
                                } else {
                                    JSONObject errorObj = response.getJSONObject("error");
                                    String message = errorObj.getString("message");
                                  Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DashboardActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        error.printStackTrace();
                        Log.e(TAG, "onError: " + error.getErrorCode());
                        Toast.makeText(DashboardActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
