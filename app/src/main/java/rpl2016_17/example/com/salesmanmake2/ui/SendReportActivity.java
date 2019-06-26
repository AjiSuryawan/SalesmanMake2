package rpl2016_17.example.com.salesmanmake2.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

import rpl2016_17.example.com.salesmanmake2.R;

public class SendReportActivity extends AppCompatActivity implements IPickResult {

    private Toolbar toolbar;
    private Button btnPickImage;
    private Button btnSignature;
    private ImageView ivSelectedImage;
    private Button btnSendReport;
    private File selectedImageFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_record);

        toolbar = findViewById(R.id.toolbar);
        btnPickImage = findViewById(R.id.btn_pick_image);
        btnSignature = findViewById(R.id.btn_Signature);
        ivSelectedImage =findViewById(R.id.iv_selectedImage);
        btnSendReport = findViewById(R.id.btn_kirim_laporan);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(getSupportFragmentManager());
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onPickResult(PickResult pickResult) {
        if (pickResult.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            ivSelectedImage.setImageBitmap(pickResult.getBitmap());
            btnSignature.setEnabled(true);

            //Image path
            //r.getPath();
            selectedImageFile = new File(pickResult.getPath());
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}


