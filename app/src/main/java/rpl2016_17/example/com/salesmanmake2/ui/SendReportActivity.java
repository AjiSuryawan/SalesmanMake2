package rpl2016_17.example.com.salesmanmake2.ui;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import rpl2016_17.example.com.salesmanmake2.Constants;
import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class SendReportActivity extends AppCompatActivity implements IPickResult {

    private Toolbar toolbar;
    private Button btnPickImage, btnSignature, btnSendReport;
    private ImageView ivSelectedImage, ivPickImage;
    private File selectedImageFile = null;
    private Bitmap signatureBitmap;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude, et_desc;
    Bitmap selectedImage;
    EditText desc;
    private ProgressDialog mProgress;
    List<Address> addresses;
    Job job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_record);

        job = getIntent().getExtras().getParcelable("extra_job");
        toolbar = findViewById(R.id.toolbar);
//        btnPickImage = findViewById(R.id.btn_pick_image);
        btnSignature = findViewById(R.id.btn_Signature);
        ivSelectedImage = findViewById(R.id.iv_selectedImage);
        ivPickImage = findViewById(R.id.iv_pick_image);
        btnSendReport = findViewById(R.id.btn_kirim_laporan);
        desc = findViewById(R.id.et_Deskirpsi);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Uploading...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        ivPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(getSupportFragmentManager());
            }
        });


//        btnPickImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PickImageDialog.build(new PickSetup()).show(getSupportFragmentManager());
//            }
//        });

        btnSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureDialog();
            }
        });


        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_desc = desc.getText().toString();
                if (et_desc.length() == 0) {
                    desc.setError("Insert Deskripsi");
                } else if (selectedImage == null) {
                    Toast.makeText(getApplicationContext(), "Please Insert Photo", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(SendReportActivity.this)
                            .setTitle("Kirim Laporan")
                            .setMessage("Apakah anda yakin ingin kirim laporan ?")
                            .setNegativeButton("Tidak", null)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    mProgress.show();
                                    senData();
//                                    mProgress.dismiss();
//                                    Intent i = new Intent(getApplicationContext(), JobsActivity.class);
//                                    startActivity(i);
//                                    finish();
                                }
                            }).create().show();
                }
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
            selectedImage = pickResult.getBitmap();
            ivSelectedImage.setImageBitmap(selectedImage);
            btnSignature.setEnabled(true);
            ivSelectedImage.setVisibility(View.VISIBLE);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();

            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getLocation();
            }

            selectedImageFile = new File(pickResult.getPath());
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, pickResult.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Konfirmasi Pembatalan");
        builder.setMessage("Apakah anda yakin membatalkan input data?");
        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showSignatureDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_signature);
        final SignaturePad signaturePad = dialog.findViewById(R.id.signature_pad);
        Button btnApply = dialog.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signatureBitmap = signaturePad.getTransparentSignatureBitmap(true);
                drawSignatureOnImage(signatureBitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void drawSignatureOnImage(Bitmap signature) {
        int w = selectedImage.getWidth();
        int h = selectedImage.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, selectedImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(selectedImage, 0f, 0f, null);
        canvas.drawBitmap(signature, 30f, 100f, null);
        ivSelectedImage.setImageBitmap(result);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(SendReportActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (SendReportActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SendReportActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                try {
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Geocoder geocoder = new Geocoder(SendReportActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Toast.makeText(getApplicationContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil lokasi", Toast.LENGTH_SHORT).show();
                }
                Log.i("", "Lattitude nya : " + lattitude + "Longitude nya : " + longitude);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                try {
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Geocoder geocoder = new Geocoder(SendReportActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Toast.makeText(getApplicationContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil lokasi", Toast.LENGTH_SHORT).show();
                }

                Log.i("", "Lattitude nya : " + lattitude + "Longitude nya : " + longitude);
//                textViewLoc.setVisibility(View.VISIBLE);
//                textViewTime.setVisibility(View.VISIBLE);

//                textViewLoc.setText("Your current location is :"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);

//                textViewLoc.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        maps();
//                    }
//                });


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                try {
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);
                    Geocoder geocoder = new Geocoder(SendReportActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Toast.makeText(getApplicationContext(), addresses.get(0).getAddressLine(4), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil lokasi", Toast.LENGTH_SHORT).show();
                }
                Log.i("", "Lattitude nya : " + lattitude + "Longitude nya : " + longitude);

            } else {
                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void senData() {
//        String.valueOf(preferences.getLong("id", 0))
//        String job_id = "12";
        final SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        et_desc = desc.getText().toString();
//        Log.d("job", String.valueOf(preferences.getLong("id", 0)));
//        Log.d("location", String.valueOf(addresses));
//        Log.d("description", et_desc);
        AndroidNetworking.upload(Constants.BASE_URL + "/api/report/send")
                .addMultipartFile("proof_image", selectedImageFile)
                .addMultipartParameter("job_id", String.valueOf(job.getId()))
                .addMultipartParameter("location", lattitude + "," + longitude)
                .addMultipartParameter("description", et_desc)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if (response.getBoolean("success")) {
                                //tost
                                Toast.makeText(getApplicationContext(), "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show();
                            } else {//toast gagal
                                Toast.makeText(getApplicationContext(), "Laporan gagal dikirim", Toast.LENGTH_SHORT).show();
                            }
                            mProgress.dismiss();
                            Intent i = new Intent(getApplicationContext(), JobsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Catch nya : ", e.getLocalizedMessage());
                            Toast.makeText(SendReportActivity.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("On Erornya", error.getErrorBody());
                    }
                });
    }

    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}


