package rpl2016_17.example.com.salesmanmake2;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    Button btnlogout;
    private static final String MY_PREFS_NAME = "login";
    TextView username,nomor,alamat,email;
    SharedPreferences preferences;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        nomor = view.findViewById(R.id.nomor);
        alamat = view.findViewById(R.id.alamat);

        preferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String name = preferences.getString("name","");
//        if (name != null){
//            username.setText(name);
//        }

        btnlogout = (Button)view.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preferences.edit().clear().apply();
                                Intent intent = new Intent(getActivity(), Kontrol.class);
                                getActivity().finish();
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        Log.e(TAG, "onCreateView: " +String.valueOf(preferences.getLong("id", 0)) );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidNetworking.get("http://sales-report.smkrus.com/api/profile/{id}")
                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        if (response != null) {
                            try {
                                boolean isSuccess = response.getBoolean("success");
                                if (isSuccess) {
                                    JSONObject payload = response.getJSONObject("payload");
                                    String email = payload.getString("email");
                                    String fullname = payload.getString("fullname");
                                    String address = payload.getString("address");
                                    String phone = String.valueOf(payload.getString("phone"));

                                    username.setText(fullname);
                                    nomor.setText(phone);
                                    alamat.setText(address);
                                    ProfileFragment.this.email.setText(email);
                                    Log.e(TAG, "onResponse: aaaa");
                                } else {
                                    JSONObject errorObj = response.getJSONObject("error");
                                    String message = errorObj.getString("message");
//                                  Toast.makeText(ProfileFragment.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        error.printStackTrace();
                        Log.e(TAG, "onError: " + error.getErrorCode());
//                        Toast.makeText(ProfileFragment.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
