package com.cipolat.petpoint.UI.Home;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.Data.Network.PetStoreApiInteractor;
import com.cipolat.petpoint.UI.Base.Presenter;

import java.util.ArrayList;
import java.util.Collections;

public class HomePresenter implements Presenter<HomeView> {
    private HomeView mHomeView;
    private PetStoreApiInteractor mInteractor;
    private ArrayList<Pet> petList;
    public boolean isSorteable=false;

    public HomePresenter() {
        mInteractor = new PetStoreApiInteractor();
    }

    @Override
    public void setView(HomeView view) {
        if (view == null) throw new IllegalArgumentException("You can't set a null view");
        mHomeView = view;
    }

    /**
     * Get petlist from API
     */
    public void getPetsList() {
        if (petList == null) {
            mInteractor.findPetsAvailable(new PetStoreApiInteractor.PetCallback() {
                @Override
                public void onSuccess(ArrayList<Pet> response){
                    isSorteable=true;
                    petList = response;
                    mHomeView.onGetPetsOk(response, true);
                }

                @Override
                public void onError(ErrorType e){
                    isSorteable=false;
                    mHomeView.onError(e);
                }
            });
        } else {
            mHomeView.onGetPetsOk(petList, false);
        }
    }

    /**
     * Pull to refresh
     * reload pets list
     */
    public void refreshPetList(){
        petList=null;
        getPetsList();
    }

    /**
     * detach view to presenter
     */
    @Override
    public void detachView() {
        mHomeView = null;
    }

    /**
     * Sort array pets ascending or descending ordered by pet ID
     *
     * @param sort TRUE ascending / FALSE descending
     */
    public void sort(boolean sort){
        if (petList != null) {
            if (sort)
                Collections.sort(petList, Pet.PetComparatorAscending);
            else
                Collections.sort(petList, Pet.PetComparatorDescending);
            mHomeView.onUpdateList(petList);
        }
    }
}
