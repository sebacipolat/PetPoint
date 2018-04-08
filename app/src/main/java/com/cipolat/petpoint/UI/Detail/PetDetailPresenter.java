package com.cipolat.petpoint.UI.Detail;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.Data.Network.PetStoreApiInteractor;
import com.cipolat.petpoint.UI.Base.Presenter;

/**
 * Created by sebastian on 23/07/17.
 */

public class PetDetailPresenter implements Presenter<DetailView> {
    private DetailView mDetailView;
    private PetStoreApiInteractor mInteractor;
    private long petID;

    public PetDetailPresenter() {
        mInteractor = new PetStoreApiInteractor();
    }

    @Override
    public void setView(DetailView view) {
        if (view == null) throw new IllegalArgumentException("You can't set a null view");
        mDetailView = view;
    }

    /**
     * Get pet details by pet id
     * @param pet_id
     */
    public void getPetDetails(long pet_id) {
        petID=pet_id;
        mInteractor.getPetDetail(pet_id, new PetStoreApiInteractor.PetDetailCallback() {
            @Override
            public void onSuccess(Pet response) {
                mDetailView.onGetDetailOk(response);
            }

            @Override
            public void onError(ErrorType e) {
                mDetailView.onError(e);
            }
        });
    }

    /**
     * Retry last pet detail api call
     */
    public void retry(){
        if(petID>0)
            getPetDetails(petID);
    }

    /**
     * detach view
     */
    @Override
    public void detachView() {
        mDetailView = null;
    }
}
