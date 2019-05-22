package rpl2016_17.example.com.salesmanmake2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class essay extends AppCompatActivity {
    SearchView searchView;

    List<Item> productList;
    Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();
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
        productList.add(
                new Item(

                        "Agenda 1"

                ));

        productList.add(
                new Item(
                        "Agenda 2"
                ));

        productList.add(
                new Item(
                        "Agenda 3"
                ));

        productList.add(
                new Item(
                        "Agenda 4"
                ));

        productList.add(
                new Item(
                        "Agenda 5"
                ));

        productList.add(
                new Item(
                        "Agenda 6"
                ));

        productList.add(
                new Item(
                        "Agenda 7"
                ));

        productList.add(
                new Item(
                        "Agenda 8"
                ));

        productList.add(
                new Item(
                        "Agenda 9"
                ));

        productList.add(
                new Item(
                        "Agenda 10"
                ));

        //creating recyclerview adapter
        adapter = new Adapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.searchfile,menu);
        final MenuItem myActionMenuItem=menu.findItem(R.id.search);
        searchView=(SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Item> filtermodelist=filter(productList,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }
    private List<Item> filter(List<Item> pl, String query){

        query=query.toLowerCase();
        final List<Item> filteredModeList=new ArrayList<>();
        for (Item model:pl){
            final String text=model.getAgenda().toLowerCase();
            if (text.startsWith(query)){
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }
    private void changeSearchViewTextColor (View view){
        if (view !=null){
            if (view instanceof ViewGroup){
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i <viewGroup.getChildCount();i++){
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }

            }
        }
    }
    }