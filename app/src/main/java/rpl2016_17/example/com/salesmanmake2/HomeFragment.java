package rpl2016_17.example.com.salesmanmake2;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View view;
    rpl2016_17.example.com.salesmanmake2.Adapter adapter;
    RecyclerView recyclerView;
   private List<Item> itemList;
    SearchView searchView;
    private final String JSON_URL = "https://jsonblob.com/c6c60e09-82e9-11e9-b2e9-47087bbfeddd";
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
        itemList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        jsonrequest();

        recyclerView.setHasFixedSize(true);

        return view;
    }

    private void jsonrequest(){
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0;i < response.length();i++){


                    try {
                        jsonObject = response.getJSONObject(i);
                        Item item = new Item();
                        item.setShop_name(jsonObject.getString("shop_name"));
                        item.setDescription(jsonObject.getString("job_description"));
                        item.setId(jsonObject.getInt("job_id"));
                        item.setShop_address(jsonObject.getString("shop_address"));
                        item.setShop_phone(jsonObject.getInt("shop_phone"));
                        itemList.add(item);



                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(itemList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void setuprecyclerview(List<Item> itemList){
        Adapter adapter =  new Adapter(getActivity(),itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<>();
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
        adapter = new rpl2016_17.example.com.salesmanmake2.Adapter(getActivity(), itemList);





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
