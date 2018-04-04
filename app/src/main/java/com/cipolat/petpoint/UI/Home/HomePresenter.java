package com.cipolat.petpoint.UI.Home;

import android.content.Context;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.Data.Network.PetStoreApiInteractor;
import com.cipolat.petpoint.UI.Base.Presenter;
import java.util.ArrayList;
/**
 * Created by sebastian on 23/07/17.
 */

public class HomePresenter implements Presenter<HomeView> {
    private Context mCtx;
    private HomeView mHomeView;
    private PetStoreApiInteractor mInteractor;

    public HomePresenter(Context mCtx) {
        this.mCtx = mCtx;
        this.mInteractor = new PetStoreApiInteractor();
    }

    @Override
    public void setView(HomeView view) {
        if (view == null) throw new IllegalArgumentException("You can't set a null view");
        this.mHomeView = view;
    }

    public void getPetsList() {
        mInteractor.findPetsAvailable(new PetStoreApiInteractor.PetCallback() {
            @Override
            public void onSuccess(ArrayList<Pet> response) {
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
}
