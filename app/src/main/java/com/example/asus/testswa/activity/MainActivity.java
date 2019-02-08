package com.example.asus.testswa.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.testswa.adapter.PeopleAdapter;
import com.example.asus.testswa.model.Example;
import com.example.asus.testswa.model.People;
import com.example.asus.testswa.R;
import com.example.asus.testswa.retrofit.MyAPI;
import com.example.asus.testswa.retrofit.RetrofitClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycle)
    RecyclerView recyclerView;

    private List<People> people = new ArrayList<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //recycle
        initRecycle();

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        MenuItem menuItem = menu.findItem(R.id.search);

        MenuItem saved = menu.findItem(R.id.saved_people);

        assert searchManager != null;

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.search_people));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() >= 1){
                    getPeopleData(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getPeopleData(newText);
                return false;
            }
        });
        menuItem.getIcon().setVisible(false,false);

        saved.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, SavedPeopleActivity.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    private void initRecycle() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getPeopleData(final String keyword) {

        Retrofit retrofit = RetrofitClient.getInstance();
        MyAPI myAPI = retrofit.create(MyAPI.class);

        disposable.add(myAPI.getPeople(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Example>() {
                    @Override
                    public void onNext(Example example) {
                        people = example.getResults();
                        displayData(people,keyword);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, R.string.error_network,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                }));
    }


    private void displayData(List<People> people,String keyword) {
        PeopleAdapter metricAdapter = new PeopleAdapter(this,people);
        recyclerView.setAdapter(metricAdapter);
        metricAdapter.notifyDataSetChanged();
        if (keyword.length() == 0){
            people.clear();
            metricAdapter.notifyDataSetChanged();
        }
    }
}
