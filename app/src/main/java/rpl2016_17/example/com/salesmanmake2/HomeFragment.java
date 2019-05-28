package rpl2016_17.example.com.salesmanmake2;


import android.content.Intent;
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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View view;
    rpl2016_17.example.com.salesmanmake2.Adapter adapter;
    RecyclerView recyclerView;
    List<Item> itemList;
    SearchView searchView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        Adapter adapter = (Adapter) new rpl2016_17.example.com.salesmanmake2.Adapter(getContext(), itemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter((RecyclerView.Adapter) adapter);
        return view;
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


        //adding some items to our list
        itemList.add(
                new Item(


                        "Agenda 1"
                ));

        itemList.add(
                new Item(

                        "Agenda 2"
                ));

        itemList.add(
                new Item(

                        "Agenda 3"
                ));

        itemList.add(
                new Item(

                        "Agenda 4"
                ));

        itemList.add(
                new Item(

                        "Agenda 5"
                ));

        itemList.add(
                new Item(

                        "Agenda 6"
                ));

        itemList.add(
                new Item(

                        "Agenda 7"
                ));

        itemList.add(
                new Item(

                        "Agenda 8"
                ));

        itemList.add(
                new Item(

                        "Agenda 9"
                ));

        itemList.add(
                new Item(

                        "Agenda 10"
                ));


        //creating recyclerview adapter
        adapter = new rpl2016_17.example.com.salesmanmake2.Adapter(getActivity(), itemList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Item> filtermodelist = filter(itemList, newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }



    private List<Item> filter(List<Item> pl, String query) {

        query = query.toLowerCase();
        final List<Item> filteredModeList = new ArrayList<>();
        for (Item model : pl) {
            final String text = model.getAgenda().toLowerCase();
            if (text.startsWith(query)) {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }

            }
        }
    }
}
