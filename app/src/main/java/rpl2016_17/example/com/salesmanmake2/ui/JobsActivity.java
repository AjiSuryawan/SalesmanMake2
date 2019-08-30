package rpl2016_17.example.com.salesmanmake2.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import rpl2016_17.example.com.salesmanmake2.adapter.ListAdapter;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class JobsActivity extends AppCompatActivity {
    private RecyclerView rvJobs;
    private ListAdapter jobsAdapter;
    private List<Job> jobList = new ArrayList<>();
    private Toolbar toolbar;
    SwipeRefreshLayout swipeLayout,swipenojob;
    private ProgressDialog mProgress;
    private LinearLayout indata, inload, nojob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        indata = findViewById(R.id.indata);
        inload = findViewById(R.id.inloading);
        nojob = findViewById(R.id.nojob);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jobs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvJobs = findViewById(R.id.rv_list);
        swipeLayout = findViewById(R.id.swipe_container);
        swipenojob = findViewById(R.id.swipe_no_job);

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
                if (isConnected(JobsActivity.this)) {
                    fetchJobs();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Job is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
                        }
                    }, 3000);
                } else {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(JobsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipenojob.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                if (isConnected(JobsActivity.this)) {
                    fetchJobs();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Job is Up to date!", Toast.LENGTH_SHORT).show();// Delay in millis
                        }
                    }, 3000);
                } else {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(JobsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setupRecyclerJobs();
    }

    private void setupRecyclerJobs() {
        jobsAdapter = new ListAdapter(this, jobList);
        rvJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvJobs.setHasFixedSize(true);
        rvJobs.setAdapter(jobsAdapter);
    }

    private void fetchJobs() {
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        AndroidNetworking.get(Constants.BASE_URL + "/api/job/active/all/{id}")
                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("Log " + response.getBoolean("success"));
                            if (response.getBoolean("success")) {
                                JSONObject payload = response.getJSONObject("payload");
                                JSONArray jobs = payload.getJSONArray("jobs");
                                jobList.clear();
                                for (int i = 0; i < jobs.length(); i++) {
                                    JSONObject job = jobs.getJSONObject(i);
                                    Job item = new Job();
                                    item.setShop_name(job.getString("shop_name"));
                                    item.setDescription(job.getString("job_description"));
                                    item.setId(job.getInt("job_id"));
                                    item.setShop_address(job.getString("shop_address"));
                                    item.setShop_phone(job.getString("shop_phone"));
                                    jobList.add(item);
                                    inload.setVisibility(View.GONE);
                                    indata.setVisibility(View.VISIBLE);
                                    Log.e("", "onResponse: " + jobList.size());
                                }
                                jobsAdapter.notifyDataSetChanged();
                            } else {
                                inload.setVisibility(View.GONE);
                                nojob.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(JobsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("woy eror : " + anError);
                        Toast.makeText(JobsActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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