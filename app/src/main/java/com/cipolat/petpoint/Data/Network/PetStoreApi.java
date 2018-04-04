package com.cipolat.petpoint.Data.Network;


import com.cipolat.petpoint.Data.Model.Pet;

import java.util.ArrayList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by sebastian on 16/06/16.
 */
public interface PetStoreApi {
    @Headers({"Accept: application/json"})
    @GET("findByStatus")
    Observable<ArrayList<Pet>> getAvailablePets(@Query("status") String status);
}