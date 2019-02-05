package com.example.asus.testswa.retrofit;

import com.example.asus.testswa.model.Example;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAPI {
    @GET("people/")
    Observable<Example> getPeople(
            @Query("search") String keyword
    );
}
