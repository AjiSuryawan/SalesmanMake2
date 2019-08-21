package rpl2016_17.example.com.salesmanmake2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class DetailReportsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView tvNama, tvLokasi, tvTanggal, tvDeskripsi, tvImage, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reports);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rincian Pekerjaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNama = findViewById(R.id.tv_shop_name2);
        tvLokasi = findViewById(R.id.tv_location);
        tvTanggal = findViewById(R.id.tv_created_at);
        tvDeskripsi = findViewById(R.id.tv_detail_job);
        tvImage = findViewById(R.id.tv_proof_image);
        tvStatus = findViewById(R.id.tv_status);

        Job job = getIntent().getExtras().getParcelable("extra_job");

        if (job != null) {
            String name = job.getShop_name();
            String image = job.getProof_image();
            String status = job.getStatus();
            String address = job.getLocation();
            String date = job.getCreated_at();
            String desc = job.getDescription();

            tvNama.setText(name);
            tvImage.setText(image);
            tvStatus.setText(status);
            tvLokasi.setText(address);
            tvTanggal.setText(date);
            tvDeskripsi.setText(desc);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
