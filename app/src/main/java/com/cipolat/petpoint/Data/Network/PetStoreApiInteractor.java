package com.cipolat.petpoint.Data.Network;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sebastian on 23/07/17.
 */

public class PetStoreApiInteractor {
    private PetStoreApi apiService;

    public PetStoreApiInteractor() {
        apiService = ApiClient.getClient().create(PetStoreApi.class);
    }

    public void findPetsAvailable(final PetCallback callback) {
        apiService.getAvailablePets(Pet.AVAILABLE)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ArrayList<Pet>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<Pet> newsResponse) {
                        if (newsResponse != null)
                            callback.onSuccess(newsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(new ErrorType(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getPetDetail(long pet_id,final PetDetailCallback callback) {
        apiService.getPetDetail(pet_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Pet>() {
                    @Override
                    public void onNext(@NonNull Pet petResponse) {
                        if (petResponse != null)
                            callback.onSuccess(petResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onError(new ErrorType(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface PetCallback {
        void onSuccess(ArrayList<Pet> response);
        void onError(ErrorType e);
    }
    public interface PetDetailCallback {
        void onSuccess(Pet response);
        void onError(ErrorType e);
    }
}
