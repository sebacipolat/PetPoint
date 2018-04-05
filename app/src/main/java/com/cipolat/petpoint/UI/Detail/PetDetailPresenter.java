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

    public PetDetailPresenter() {
        mInteractor = new PetStoreApiInteractor();
    }

    @Override
    public void setView(DetailView view) {
        if (view == null) throw new IllegalArgumentException("You can't set a null view");
        mDetailView = view;
    }

    public void getPetDetails(long data) {
        mInteractor.getPetDetail(data, new PetStoreApiInteractor.PetDetailCallback() {
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

    @Override
    public void detachView() {
        mDetailView = null;
    }
}
