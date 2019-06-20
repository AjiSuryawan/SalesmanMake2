package rpl2016_17.example.com.salesmanmake2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View view;
    rpl2016_17.example.com.salesmanmake2.Adapter adapter;
    RecyclerView recyclerView;
    private List<Item> itemList = new ArrayList<>();
    SearchView searchView;
    private final static String BASE_URL = "http://sales-report.smkrus.com";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        setuprecyclerview();
        jsonrequest();
        return view;
    }

    private void jsonrequest() {
        SharedPreferences preferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        AndroidNetworking.get(BASE_URL + "/api/job/active/all/{id}")
                .addPathParameter("id", String.valueOf(preferences.getLong("id", 0)))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject payload = response.getJSONObject("payload");
                                JSONArray jobs = payload.getJSONArray("jobs");
                                itemList.clear();
                                for (int i = 0; i < jobs.length(); i++) {
                                    JSONObject job = jobs.getJSONObject(i);
                                    Item item = new Item();
                                    item.setShop_name(job.getString("shop_name"));
                                    item.setDescription(job.getString("job_description"));
                                    item.setId(job.getInt("job_id"));
                                    item.setShop_address(job.getString("shop_address"));
                                    item.setShop_phone(job.getString("shop_phone"));
                                    itemList.add(item);
                                    Log.e("", "onResponse: " + itemList.size());
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("", "onError: " + anError.getErrorBody());
                    }
                });

    }

    private void setuprecyclerview() {
        adapter = new Adapter(getActivity(), itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        @Override
//        public void onFolderClick(File file) {
//            ExplorerFragment explorerFragment = new ExplorerFragment();
//            Bundle args = new Bundle();
//            args.putString(ExplorerFragment.DATA_LOKASI, file.getAbsolutePath());
//            explorerFragment.setArguments(args);
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.container, explorerFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }


        //creating recyclerview adapter


    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.searchfile, menu);
//        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
//        searchView = (SearchView) myActionMenuItem.getActionView();
//        changeSearchViewTextColor(searchView);
//        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (!searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                final List<Item> filtermodelist = filter(itemList, newText);
//                adapter.setfilter(filtermodelist);
//                return true;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//
//    private List<Item> filter(List<Item> pl, String query) {
//
//        query = query.toLowerCase();
//        final List<Item> filteredModeList = new ArrayList<>();
//        for (Item model : pl) {
//            final String text = model.getShop_name().toLowerCase();
//            if (text.startsWith(query)) {
//                filteredModeList.add(model);
//            }
//        }
//        return filteredModeList;
//    }
//
//    private void changeSearchViewTextColor(View view) {
//        if (view != null) {
//            if (view instanceof ViewGroup) {
//                ViewGroup viewGroup = (ViewGroup) view;
//                for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                    changeSearchViewTextColor(viewGroup.getChildAt(i));
//                }
//
//            }
//        }
//    }
}
