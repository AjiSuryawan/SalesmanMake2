package rpl2016_17.example.com.salesmanmake2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DetailJobActivity extends AppCompatActivity {
    Button button;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button = findViewById(R.id.btn_input_job);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailJobActivity.this, FormRecord.class);
                startActivity(i);
            }
        });
    }
}
