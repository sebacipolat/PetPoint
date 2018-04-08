package com.cipolat.petpoint.UI.Home;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.Data.Network.PetStoreApiInteractor;
import com.cipolat.petpoint.UI.Base.Presenter;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by sebastian on 23/07/17.
 */
public class HomePresenter implements Presenter<HomeView> {
    private HomeView mHomeView;
    private PetStoreApiInteractor mInteractor;
    private ArrayList<Pet> petList;

    public HomePresenter() {
        mInteractor = new PetStoreApiInteractor();
    }

    @Override
    public void setView(HomeView view) {
        if (view == null) throw new IllegalArgumentException("You can't set a null view");
        mHomeView = view;
    }

    public void getPetsList() {
        mInteractor.findPetsAvailable(new PetStoreApiInteractor.PetCallback() {
            @Override
            public void onSuccess(ArrayList<Pet> response) {
                petList = response;
                mHomeView.onGetPetsOk(response);
            }

            @Override
            public void onError(ErrorType e) {
                mHomeView.onError(e);
            }
        });
    }

    @Override
    public void detachView() {
        mHomeView = null;
    }

    public void sort(boolean sort) {
        if (sort)
            Collections.sort(petList, Pet.PetComparatorAscending);
        else
            Collections.sort(petList, Pet.PetComparatorDescending);
        mHomeView.onUpdateList(petList);
    }
}
