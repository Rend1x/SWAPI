package com.example.asus.testswa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.asus.testswa.R;
import com.example.asus.testswa.adapter.PeopleAdapter;
import com.example.asus.testswa.database.PeopleRepository;
import com.example.asus.testswa.model.People;
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

    private PeopleRepository mRepository;
    private List<People> mPeopleList = new ArrayList<>();
    private PeopleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_people);

        ButterKnife.bind(this);

        initRecycle();
        initDB();
        loadData();
    }

    private void initRecycle() {
        mAdapter = new PeopleAdapter(this, mPeopleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initDB() {
        PeopleDatabase database = PeopleDatabase.getInstance(this);
        mRepository = PeopleRepository.getInstance(PeopleData.getInstance(database.peopleDAO()));
    }

    private void loadData() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Disposable disposable = mRepository.getAllPeople()
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
                        Toast.makeText(SavedPeopleActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }


    private void onGetAllPeople(List<People> people) {
        mPeopleList.clear();
        mPeopleList.addAll(people);
        mAdapter.notifyDataSetChanged();
    }

}
