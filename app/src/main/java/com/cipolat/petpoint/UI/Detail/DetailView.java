package com.cipolat.petpoint.UI.Detail;

import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;

public interface DetailView {
    void onGetDetailOk(Pet pet);
    void onError(ErrorType error);
}
