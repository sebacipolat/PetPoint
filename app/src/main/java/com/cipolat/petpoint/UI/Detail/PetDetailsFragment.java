package com.cipolat.petpoint.UI.Detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sebastian on 04/04/18.
 */

public class PetDetailsFragment extends Fragment implements DetailView {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @BindView(R.id.petName)
    TextView petName;

    @BindView(R.id.petStatus)
    TextView petStatus;

    @BindView(R.id.cardPlaceItm)
    CardView cardPlaceItm;

    Unbinder unbinder;

    private PetDetailPresenter mPresenter;
    private LoaderCallback mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pet_detail_fragmentl, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new PetDetailPresenter();
        mPresenter.setView(this);
        return view;
    }

    public void setPetID(long id) {
        mPresenter.getPetDetails(id);
    }

    public void setListener(LoaderCallback cllbk) {
        this.mCallback = cllbk;
    }

    private void fillPetData(Pet data) {
        if (data.getPhotoUrls() != null && data.getPhotoUrls().size() > 0) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.dog)
                    .error(R.drawable.dog);
            Glide.with(this).load(data.getPhotoUrls().get(0)).apply(options).into(profileImage);
        } else
            profileImage.setImageResource(R.drawable.dog);

        if (data.getName() != null)
            petName.setText(data.getName().toUpperCase());

        if (data.getStatus() != null) {
            petStatus.setVisibility(View.VISIBLE);
            petStatus.setText(data.getStatus().toUpperCase());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onGetDetailOk(Pet pet) {
        if (mCallback != null)
            mCallback.onLoading(false);
        fillPetData(pet);
    }

    @Override
    public void onError(ErrorType error) {
      /*  if (error.isNetworkError())
            showErrorSnack(getString(R.string.error_network));
        else
            showErrorSnack(getString(R.string.error_data));
*/
    }
}
