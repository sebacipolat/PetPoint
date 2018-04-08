package com.cipolat.petpoint.UI.Home;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import java.util.ArrayList;

public interface HomeView {
    void onGetPetsOk(ArrayList<Pet> list,boolean newData);
    void onUpdateList(ArrayList<Pet> list);
    void onError(ErrorType error);
}
