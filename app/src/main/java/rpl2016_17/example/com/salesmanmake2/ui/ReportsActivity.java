package rpl2016_17.example.com.salesmanmake2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rpl2016_17.example.com.salesmanmake2.Constants;
import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.adapter.ReportsAdapter;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class ReportsActivity extends AppCompatActivity {
    private RecyclerView rvJobs;
    private ReportsAdapter reportsAdapter;
    private List<Job> reportList = new ArrayList<>();
    private Toolbar toolbar;
    SwipeRefreshLayout swipeLayout;
    private ProgressDialog mProgress;
    private LinearLayout indata, inload, noreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
//
//        Report = getIntent().getExtras().getParcelable("extra_job");

        indata = findViewById(R.id.indata);
        inload = findViewById(R.id.inloading);
        noreport = findViewById(R.id.noreport);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvJobs = findViewById(R.id.rv_list2);
        swipeLayout = findViewById(R.id.swipe_container);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        fetchJobs();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                if (isConnected(ReportsActivity.this)) {
                    fetchJobs();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Reports is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
                        }
                    }, 3000);
                } else {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(ReportsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupRecyclerJobs();
    }

    //    String.valueOf(preferences.getLong("id", 0))
//    int idJobGan = 17;
    private void fetchJobs() {
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        AndroidNetworking.get(Constants.BASE_URL + "/api/report/all/{id}")
                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject payload = response.getJSONObject("payload");
                                JSONArray reports = payload.getJSONArray("reports");
                                if (payload != null) {
                                    reportList.clear();
                                    for (int i = 0; i < reports.length(); i++) {
                                        JSONObject report = reports.getJSONObject(i);
                                        Job item = new Job();
                                        item.setShop_name(report.getString("description"));
                                        item.setDescription(report.getString("description"));
                                        item.setLocation(report.getString("location"));
                                        item.setStatus(report.getString("status"));
                                        item.setCreated_at(report.getString("created_at"));
                                        item.setProof_image(report.getString("proof_image"));
                                        reportList.add(item);
                                        inload.setVisibility(View.GONE);
                                        indata.setVisibility(View.VISIBLE);
                                        Log.e("", "onResponse: " + reportList.size());
                                    }
                                    reportsAdapter.notifyDataSetChanged();
                                } else {
                                    inload.setVisibility(View.GONE);
                                    noreport.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReportsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("onError: " + anError.getErrorBody());
                        Toast.makeText(ReportsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupRecyclerJobs() {
        reportsAdapter = new ReportsAdapter(this, reportList);
        rvJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvJobs.setHasFixedSize(true);
        rvJobs.setAdapter(reportsAdapter);
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }
}
