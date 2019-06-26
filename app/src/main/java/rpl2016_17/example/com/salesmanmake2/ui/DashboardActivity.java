package rpl2016_17.example.com.salesmanmake2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import rpl2016_17.example.com.salesmanmake2.Constants;
import rpl2016_17.example.com.salesmanmake2.R;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private SharedPreferences preferences;
    private TextView tvUsername;
    private CircleImageView ivProfile;
    private LinearLayout cardJobs, cardReports;
    private ImageView ivLogout;
    private static final int TIME_LIMIT = 1500;
    private static long backPressed;
    private ProgressDialog mProgress;

    SwipeRefreshLayout swipeLayout;


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
        swipeLayout = findViewById(R.id.swipe_container);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        mProgress.show();
        fetchProfile();
        mProgress.dismiss();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                fetchProfile();
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeLayout.setRefreshing(false);
                    }
                }, 3000);
                Toast.makeText(getApplicationContext(), "Profile is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
            }
        });

        /** click listener */
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(DashboardActivity.this)
                        .setMessage("Apakah anda yakin ingin keluar ?")
                        .setNegativeButton("Tidak", null)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).create().show();
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

                                  Toast.makeText(DashboardActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DashboardActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        error.printStackTrace();
                        Log.e(TAG, "onError: " + error.getErrorCode());
                        Toast.makeText(DashboardActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (TIME_LIMIT + backPressed > System.currentTimeMillis()){
            super.onBackPressed();;
        }else {
            Toast.makeText(getApplicationContext(),"Tekan lagi untuk keluar",Toast.LENGTH_SHORT).show();
        }
        backPressed =System.currentTimeMillis();
    }
}
