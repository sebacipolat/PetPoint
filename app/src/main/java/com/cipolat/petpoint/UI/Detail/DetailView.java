package com.cipolat.petpoint.UI.Detail;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
/**
 * Created by sebastian on 23/07/17.
 */

public interface DetailView {
    void onGetDetailOk(Pet pet);
    void onError(ErrorType error);
}
