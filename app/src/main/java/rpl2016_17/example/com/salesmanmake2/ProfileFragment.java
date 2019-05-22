package rpl2016_17.example.com.salesmanmake2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    Button btnlogout;
    private static final String MY_PREFS_NAME = "login";

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        btnlogout = (Button)view.findViewById(R.id.btn_lo);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(),Kontrol.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}
