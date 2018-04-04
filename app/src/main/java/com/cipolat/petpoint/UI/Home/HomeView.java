package com.cipolat.petpoint.UI.Home;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import java.util.ArrayList;
/**
 * Created by sebastian on 23/07/17.
 */

public interface HomeView {
    void onGetPetsOk(ArrayList<Pet> list);
    void onError(ErrorType error);
}
