package com.example.asus.testswa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.asus.testswa.adapter.PeopleAdapter;
import com.example.asus.testswa.database.PeopleRepository;
import com.example.asus.testswa.model.People;
import com.example.asus.testswa.R;
import com.example.asus.testswa.room.PeopleData;
import com.example.asus.testswa.room.PeopleDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SavedPeopleActivity extends AppCompatActivity {

    @BindView(R.id.recycle_saved)
    RecyclerView recyclerView;

    private CompositeDisposable compositeDisposable;
    private PeopleRepository repository;
    private List<People> peopleList;
    private PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_people);

        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();
        peopleList = new ArrayList<>();

        initRecycle();
        initDB();

    }

    private void initRecycle() {
        adapter = new PeopleAdapter(this,peopleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initDB() {
        PeopleDatabase database = PeopleDatabase.getInstance(this);
        repository = PeopleRepository.getInstance(PeopleData.getInstance(database.peopleDAO()));
        loadData();
    }

    private void loadData() {
        Disposable disposable = repository.getAllPeople()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<People>>() {
                    @Override
                    public void accept(List<People> people) throws Exception {
                        onGetAllPeople(people);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(SavedPeopleActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }


    private void onGetAllPeople(List<People> people) {
        peopleList.clear();
        peopleList.addAll(people);
        adapter.notifyDataSetChanged();
    }

}
