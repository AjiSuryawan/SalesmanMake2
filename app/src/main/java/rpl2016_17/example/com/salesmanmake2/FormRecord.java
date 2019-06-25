package rpl2016_17.example.com.salesmanmake2;

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
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.github.gcacace.signaturepad.views.SignaturePad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import rpl2016_17.example.com.salesmanmake2.data.Job;
import rpl2016_17.example.com.salesmanmake2.ui.JobsActivity;

public class FormRecord extends AppCompatActivity {
    private String cameraFilePath;

    Button GetImageFromGalleryButton, UploadImageOnServerButton, UploadSignature;

    ImageView ShowSelectedImage;

    EditText imageName;

    Bitmap FixBitmap;

    String ImageTag = "image_tag";

    String ImageName = "image_data";

    ProgressDialog progressDialog;

    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;

    String ConvertImage;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter;

    int RC;

    BufferedReader bufferedReader;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;

    Toolbar toolbar;

    private static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textViewLoc , textViewTime;
    LocationManager locationManager;
    String lattitude,longitude;

    private File selectedImageFile = null;
    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_record);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Input Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        GetImageFromGalleryButton = (Button) findViewById(R.id.buttonSelect);

        UploadImageOnServerButton = (Button) findViewById(R.id.buttonUpload);

        UploadSignature = (Button) findViewById(R.id.btn_Signature);

        ShowSelectedImage = (ImageView) findViewById(R.id.imageView);


        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        UploadSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureDialog();
            }
        });


        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameFromEditText = "makan";

                new AlertDialog.Builder(FormRecord.this)
                        .setTitle("Save data")
                        .setMessage("Apakah anda yakin ingin save data ?")
                        .setNegativeButton("Tidak", null)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                // kirim datanya
                                mProgress.show();
                                senData();
                                mProgress.dismiss();
                            }
                        }).create().show();


            }
        });


        if (ContextCompat.checkSelfPermission(FormRecord.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FormRecord.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5);
            }
        }


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        textViewLoc = (TextView)findViewById(R.id.tv_lokasi);
        button = (Button)findViewById(R.id.buttonSelect);
       textViewTime = (TextView)findViewById(R.id.tv_waktu);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy. 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        textViewTime.setText("Your current time : " + "\n" + currentDateandTime);


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

    private void showPictureDialog() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//
//        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            getLocation();
//        }

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            Log.e("", "putExtra: " );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "ioexception: " + e.getLocalizedMessage() );
        }
        Log.e("", "takePhotoFromCamera: " );
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ShowSelectedImage.setImageBitmap(FixBitmap);
                    ShowSelectedImage.setVisibility(View.VISIBLE);
                    UploadImageOnServerButton.setVisibility(View.VISIBLE);


                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();

                    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getLocation();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FormRecord.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
//            FixBitmap = (Bitmap) data.getExtras().get("data");
            Log.e("", "onActivityResult: " + cameraFilePath );
            selectedImageFile = new File(cameraFilePath);
            ShowSelectedImage.setImageURI(Uri.parse(cameraFilePath));
            try {
                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(cameraFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadImageOnServerButton.setVisibility(View.VISIBLE);
            ShowSelectedImage.setVisibility(View.VISIBLE);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();

            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getLocation();
            }
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            } else {

                Toast.makeText(FormRecord.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        Log.e("", "createImageFile: " + cameraFilePath);
        return image;
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(FormRecord.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (FormRecord.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FormRecord.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textViewLoc.setVisibility(View.VISIBLE);
                textViewTime.setVisibility(View.VISIBLE);

                textViewLoc.setText("Your current location is :"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

                textViewLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        maps();
                    }
                });

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                textViewLoc.setVisibility(View.VISIBLE);
                textViewTime.setVisibility(View.VISIBLE);

                textViewLoc.setText("Your current location is :"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

                textViewLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        maps();
                    }
                });


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                textViewLoc.setVisibility(View.VISIBLE);
                textViewTime.setVisibility(View.VISIBLE);
//                "geo:"+lattitude+","+longitude;
                textViewLoc.setText("Your current location is :"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);

                textViewLoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        maps();
                    }
                });

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void maps() {

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:"+lattitude+","+longitude));
//        startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:?q="+lattitude+","+longitude));
        startActivity(intent);
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

//    public void getCurrentTime(View view) {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
//        String strDate = "Current Time : " + mdformat.format(calendar.getTime());
//        display(strDate);
//    }
//
//    private void display(String num) {
//        textViewTime.setText(num);
//    }

    private void showSignatureDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_signature);
        final SignaturePad signaturePad = dialog.findViewById(R.id.signature_pad);
        Button btnApply = dialog.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = signaturePad.getTransparentSignatureBitmap(true);
                drawSignatureOnImage(signatureBitmap);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void drawSignatureOnImage(Bitmap signature) {
        int w = FixBitmap.getWidth();
        int h = FixBitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, FixBitmap.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(FixBitmap, 0f, 0f, null);
        canvas.drawBitmap(signature, 100f, 100f, null);

        ShowSelectedImage.setImageBitmap(result);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void senData (){
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        AndroidNetworking.upload(Constants.BASE_URL + "/api/report/send")
                .addMultipartFile("proof_image", selectedImageFile)
                .addMultipartParameter("job_id",String.valueOf(preferences.getLong("id", 0)))
                .addMultipartParameter("location", longitude + ";" + lattitude)
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

                            }else{//toast gagal
                                Toast.makeText(getApplicationContext(), "Laporan gagal dikirim", Toast.LENGTH_SHORT).show();
                                 }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(FormRecord.this, Constants.EROR, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
