package rpl2016_17.example.com.salesmanmake2.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
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
    private ReportsAdapter jobsAdapter;
    private List<Job> jobList = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvJobs = findViewById(R.id.rv_list2);
        setupRecyclerJobs();
        fetchJobs();
    }

    private void fetchJobs() {
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        AndroidNetworking.get(Constants.BASE_URL + "/api/report/all/{id}")
//                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
//                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject payload = response.getJSONObject("payload");
                                final JSONArray jobs = payload.getJSONArray("jobs");
                                jobList.clear();

                                for (int i = 0; i < jobs.length(); i++) {
                                    JSONObject job = jobs.getJSONObject(i);
                                    Job item = new Job();
                                    item.setDescription(job.getString("description"));
                                    item.setLocation(job.getString("location"));
                                    item.setStatus(job.getString("status"));
                                    item.setCreated_at(job.getString("created_at"));
                                    item.setProof_image(job.getString("proof_image"));
                                    jobList.add(item);
                                    Log.e("", "onResponse: " + jobList.size());
                                }
                                jobsAdapter.notifyDataSetChanged();

                                AndroidNetworking.get(Constants.BASE_URL + "/api/job/active/{id}")
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONObject payload = response.getJSONObject("payload");
                                                    JSONArray object = payload.getJSONArray("jobs");
                                                    jobList.clear();

                                                    for (int i = 0; i < jobs.length(); i++) {
                                                        JSONObject job = jobs.getJSONObject(i);
                                                        Job item = new Job();
                                                        item.setShop_name(job.getString("shop_name"));
                                                        jobList.add(item);
                                                        Log.e("", "onResponse: " + jobList.size());
                                                    }
                                                    jobsAdapter.notifyDataSetChanged();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                            }
                                        });


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ReportsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("", "onError: " + anError.getErrorBody());
                        Toast.makeText(ReportsActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void setupRecyclerJobs() {
        jobsAdapter = new ReportsAdapter(this, jobList);
        rvJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvJobs.setHasFixedSize(true);
        rvJobs.setAdapter(jobsAdapter);
    }

}