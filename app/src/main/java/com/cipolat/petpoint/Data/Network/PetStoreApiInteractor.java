package com.cipolat.petpoint.Data.Network;

import android.util.Log;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class PetStoreApiInteractor {
    private PetStoreApi apiService;

    public PetStoreApiInteractor() {
        apiService = ApiClient.getClient().create(PetStoreApi.class);
    }

    /**
     * hit api to retrieve available pets
     *
     * @param callback listener
     */
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

    /**
     * Get pet details from api by pet id
     *
     * @param pet_id   pet id
     * @param callback callback
     */
    public void getPetDetail(long pet_id, final PetDetailCallback callback) {
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

    /**
     * Request en paralelo
     * se ejecutan a la vez
     * emite valor a medida q terminan
     */
    public void requestParalelo() {
        //Llamadas en paralelo
        //Concatena request y los ejecutas a medida que se terminan
        Observable<Pet> pet100 = apiService.getPetDetail(100).subscribeOn(Schedulers.io());
        Observable<Pet> pet500 = apiService.getPetDetail(500).subscribeOn(Schedulers.io());

        Observable<Pet> merged = Observable.merge(pet100, pet500);
        merged.subscribe(new DisposableObserver<Pet>() {
            @Override
            public void onNext(Pet pet) {
                Log.e("PET=>", "Name:" + pet.getName() + " ID:" + pet.getId());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Request en cadena
     * ejecuta 1ero uno y luego otro segun data del 1ero
     */
    public void requestChain() {
        apiService.getPetDetail(100).flatMap(new Function<Pet, ObservableSource<Pet>>() {
            @Override
            public ObservableSource<Pet> apply(Pet pet) throws Exception {
                Log.e("PET=>", "Name:" + pet.getName() + " ID:" + pet.getId());
                return apiService.getPetDetail(pet.getId() + 400).observeOn(AndroidSchedulers.mainThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribeWith(new DisposableObserver<Pet>() {
            @Override
            public void onNext(Pet pet) {
                Log.e("PET Finish=>", "Name:" + pet.getName() + " ID:" + pet.getId());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        /**
         *Response:
         * PET 100
         * PET 500
         */
    }
    /**
     * Request en cadena
     * ejecuta 1ero uno y luego otro segun data del 1ero
     * modifica el valor que se obtiene
     */
    public void requestChainModifyValue() {
        apiService.getPetDetail(100).flatMap(new Function<Pet, ObservableSource<Pet>>() {
            @Override
            public ObservableSource<Pet> apply(Pet pet) throws Exception {
                Log.e("PET=>", "Name:" + pet.getName() + " ID:" + pet.getId());
                return apiService.getPetDetail(pet.getId() + 400).observeOn(AndroidSchedulers.mainThread());
            }
        }).map(new Function<Pet, Pet>() {

            @Override
            public Pet apply(Pet pet) throws Exception {
                Log.e("PET MAP=>", "Name:" + pet.getName() + " ID:" + pet.getId());
                pet.setId(pet.getId() + 100000);
                return pet;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribeWith(new DisposableObserver<Pet>() {
            @Override
            public void onNext(Pet pet) {
                Log.e("PET Finish=>", "Name:" + pet.getName() + " ID:" + pet.getId());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        /**
         *Response:
         * Lista PET 100
         */
    }
    /**
     * Request agrupados
     * ejecuta ambos request y agrupa sus valores
     */
    public void requestZip() {
        Observable<Pet> pet100 = apiService.getPetDetail(100).subscribeOn(Schedulers.io());
        Observable<Pet> pet500 = apiService.getPetDetail(500).subscribeOn(Schedulers.io());
        Observable<ArrayList<Pet>> combinados=Observable.zip(pet100, pet500, new BiFunction<Pet, Pet, ArrayList<Pet>>() {
            @Override
            public ArrayList<Pet> apply(Pet pet, Pet pet2) throws Exception {
                ArrayList<Pet> lista=new ArrayList<>();
                lista.add(pet);
                lista.add(pet2);
                return lista;
            }
        });
        combinados.subscribe(new DisposableObserver<ArrayList<Pet>>() {
            @Override
            public void onNext(ArrayList<Pet> pets) {
                Log.e("PET LIST=>", pets.size()+"");

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        /**
         *Response:
         * PET 100
         * PET 100000
         * PET 100000
         */
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
