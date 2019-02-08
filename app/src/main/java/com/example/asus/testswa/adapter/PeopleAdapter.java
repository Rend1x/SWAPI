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

import com.example.asus.testswa.R;
import com.example.asus.testswa.database.PeopleRepository;
import com.example.asus.testswa.model.People;
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

    private Context mContext;
    private List<People> mPeopleList;
    private PeopleRepository mRepository;


    public PeopleAdapter(Context context, List<People> people) {
        this.mContext = context;
        this.mPeopleList = people;
    }


    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.people_layout, viewGroup, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, final int pos) {

        holder.mName.setText(mPeopleList.get(pos).getName());
        holder.mBirth.setText(mPeopleList.get(pos).getBirth_year());
        holder.mGender.setText(mPeopleList.get(pos).getGender());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PeopleDatabase mDatabase = PeopleDatabase.getInstance(mContext);
                mRepository = PeopleRepository.getInstance(PeopleData.getInstance(mDatabase.peopleDAO()));
                Dialog mDialog = new Dialog(mContext);
                mDialog.setContentView(R.layout.dialog_people);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        People people = new People(mPeopleList.get(pos).getName(),
                                mPeopleList.get(pos).getHeight(),
                                mPeopleList.get(pos).getMass(),
                                mPeopleList.get(pos).getHair_color(),
                                mPeopleList.get(pos).getSkin_color(),
                                mPeopleList.get(pos).getEye_color(),
                                mPeopleList.get(pos).getBirth_year(),
                                mPeopleList.get(pos).getGender());
                        mRepository.insertPeople(people);
                        e.onComplete();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                            @Override
                            public void accept(Object o) throws Exception {
                                Toast.makeText(mContext, R.string.saved_people_2, Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(mContext, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                CompositeDisposable compositeDisposable = new CompositeDisposable();
                compositeDisposable.add(disposable);

                TextView mNameDialog = mDialog.findViewById(R.id.dialog_name);
                TextView mBirthDialog = mDialog.findViewById(R.id.dialog_birth);
                TextView mGenderDialog = mDialog.findViewById(R.id.dialog_gender);
                TextView mHeightDialog = mDialog.findViewById(R.id.dialog_height);
                TextView mMassDialog = mDialog.findViewById(R.id.dialog_mass);
                TextView mHairDialog = mDialog.findViewById(R.id.dialog_hair);
                TextView mEyeDialog = mDialog.findViewById(R.id.dialog_eye);

                mNameDialog.setText(mPeopleList.get(pos).getName());
                mBirthDialog.setText(mPeopleList.get(pos).getBirth_year());
                mGenderDialog.setText(mPeopleList.get(pos).getGender());
                mHeightDialog.setText(mPeopleList.get(pos).getHeight());
                mMassDialog.setText(mPeopleList.get(pos).getMass());
                mHairDialog.setText(mPeopleList.get(pos).getHair_color());
                mEyeDialog.setText(mPeopleList.get(pos).getEye_color());

                mDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }
}
