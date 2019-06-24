package rpl2016_17.example.com.salesmanmake2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rpl2016_17.example.com.salesmanmake2.FormRecord;
import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class DetailJobActivity extends AppCompatActivity {
    private Button btnMakeReport;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rincian Pekerjaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvNama = findViewById(R.id.tv_shop_name);
        TextView tvAlamat = findViewById(R.id.tv_shop_address);
        TextView tvNomor = findViewById(R.id.tv_shop_phone);
        TextView tvDeskripsi = findViewById(R.id.tv_detail_job);


        btnMakeReport = findViewById(R.id.btn_make_report);
        btnMakeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailJobActivity.this, FormRecord.class);
                startActivity(i);
            }
        });

        Job job = getIntent().getExtras().getParcelable("extra_job");

        if(job != null){
            String name = job.getShop_name();
            String address = job.getShop_address();
            String phone = job.getShop_phone();
            String desc = job.getDescription();

            tvNama.setText(name);
            tvAlamat.setText(address);
            tvNomor.setText(String.valueOf(phone));
            tvDeskripsi.setText(desc);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
