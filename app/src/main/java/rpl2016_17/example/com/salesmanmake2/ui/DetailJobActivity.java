package rpl2016_17.example.com.salesmanmake2.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class DetailJobActivity extends AppCompatActivity {
    private Button btnMakeReport, btnMaps;
    private Toolbar toolbar;
    private String name, address, phone, desc;
    private static final String TAG = DetailJobActivity.class.getSimpleName();
    TextView tvNama, tvAlamat, tvNomor, tvDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rincian Pekerjaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvNama = findViewById(R.id.tv_shop_name);
        tvAlamat = findViewById(R.id.tv_shop_address);
        tvNomor = findViewById(R.id.tv_shop_phone);
        tvDeskripsi = findViewById(R.id.tv_detail_job);

        final Job job = getIntent().getExtras().getParcelable("extra_job");

        btnMakeReport = findViewById(R.id.btn_make_report);
        btnMakeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailJobActivity.this, SendReportActivity.class);
                i.putExtra("extra_job", job);
                startActivity(i);
            }
        });

        btnMaps = findViewById(R.id.btn_see_maps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maps();
            }
        });

        if (job != null) {
            name = job.getShop_name();
            address = job.getShop_address();
            phone = job.getShop_phone();
            desc = job.getDescription();

            Log.e(TAG, "onCreate: " + name);
            Log.e(TAG, "onCreate: " + phone);
            Log.e(TAG, "onCreate: " + desc);
            Log.e(TAG, "onCreate: " + address);

            tvNama.setText(name);
            tvAlamat.setText(address);
            tvNomor.setText(String.valueOf(phone));
            tvDeskripsi.setText(desc);
        }
    }

    private void maps() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:?q=" + address));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
