package com.example.asus.testswa.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.testswa.database.PeopleRepository;
import com.example.asus.testswa.model.People;
import com.example.asus.testswa.R;
import com.example.asus.testswa.room.PeopleData;
import com.example.asus.testswa.room.PeopleDatabase;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleViewHolder> {

    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<People> peopleList;
    private PeopleRepository repository;


    public PeopleAdapter(Context context, List<People> people) {
        this.context = context;
        this.peopleList = people;
    }


    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.people_layout,viewGroup,false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, final int pos) {

        holder.name.setText(peopleList.get(pos).getName());
        holder.birth.setText(peopleList.get(pos).getBirth_year());
        holder.gender.setText(peopleList.get(pos).getGender());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PeopleDatabase database = PeopleDatabase.getInstance(context);
                repository = PeopleRepository.getInstance(PeopleData.getInstance(database.peopleDAO()));
                Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.dialog_people);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        People people = new People(peopleList.get(pos).getName(),
                                peopleList.get(pos).getHeight(),
                                peopleList.get(pos).getMass(),
                                peopleList.get(pos).getHair_color(),
                                peopleList.get(pos).getSkin_color(),
                                peopleList.get(pos).getEye_color(),
                                peopleList.get(pos).getBirth_year(),
                                peopleList.get(pos).getGender());
                        repository.insertPeople(people);
                        e.onComplete();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Toast.makeText(context, R.string.saved_people_2, Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(context, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                compositeDisposable.add(disposable);

                TextView name_dialog = mDialog.findViewById(R.id.dialog_name);
                TextView birth_dialog = mDialog.findViewById(R.id.dialog_birth);
                TextView gender_dialog = mDialog.findViewById(R.id.dialog_gender);
                TextView height_dialog = mDialog.findViewById(R.id.dialog_height);
                TextView mass_dialog = mDialog.findViewById(R.id.dialog_mass);
                TextView hair_dialog = mDialog.findViewById(R.id.dialog_hair);
                TextView eye_dialog = mDialog.findViewById(R.id.dialog_eye);

                name_dialog.setText(peopleList.get(pos).getName());
                birth_dialog.setText(peopleList.get(pos).getBirth_year());
                gender_dialog.setText(peopleList.get(pos).getGender());
                height_dialog.setText(peopleList.get(pos).getHeight());
                mass_dialog.setText(peopleList.get(pos).getMass());
                hair_dialog.setText(peopleList.get(pos).getHair_color());
                eye_dialog.setText(peopleList.get(pos).getEye_color());

                mDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }
}
