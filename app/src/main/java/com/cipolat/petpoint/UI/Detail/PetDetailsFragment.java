package com.cipolat.petpoint.UI.Detail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;



public class PetDetailsFragment extends Fragment implements DetailView {
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.petName)
    TextView petName;
    @BindView(R.id.petStatus)
    TextView petStatus;
    @BindView(R.id.cardPlaceItm)
    CardView cardPlaceItm;
    @BindView(R.id.vsError)
    ViewStub vsError;
    @BindView(R.id.loader)
    ProgressBar loader;

    private Unbinder unbinder;
    private View viewInflated;

    private PetDetailPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pet_detail_fragmentl, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new PetDetailPresenter();
        mPresenter.setView(this);
        return view;
    }

    /**
     * Set petid this will be used to retrieve the pet
     * data
     * @param id long pet ID
     */
    public void setPetID(long id) {
        mPresenter.getPetDetails(id);
    }

    /**
     * fill ui with pet details
     * try retrieve image and set status label
     * @param data Pet object
     */
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

    /**
     * Pet data coming from api
     * @param pet
     */
    @Override
    public void onGetDetailOk(Pet pet) {
        showLoading(false);
        fillPetData(pet);
    }

    /**
     * Modify visibility of loading spinner
     * @param visible
     */
    public void showLoading(boolean visible) {
        if (visible) {
            loader.setVisibility(View.VISIBLE);
            if (viewInflated != null)
                viewInflated.setVisibility(View.GONE);
        } else
            loader.setVisibility(View.GONE);
    }

    /**
     * Error coming from api request
     * show retry option and display info
     * @param error type error
     */
    @Override
    public void onError(ErrorType error) {
        showLoading(false);
        profileImage.setVisibility(View.GONE);
        if (viewInflated == null)
            viewInflated = vsError.inflate();
        ErrorView stubView = new ErrorView(viewInflated);

        if (error.isNetworkError()) {
            stubView.setText(getString(R.string.error_network));
        } else {
            stubView.setText(getString(R.string.error_data));
        }
    }

    public class ErrorView {
        @BindView(R.id.errorTitle)
        TextView errorTitle;
        @BindView(R.id.retryBtn)
        Button retryBtn;

        public ErrorView(View view) {
            ButterKnife.bind(this, view);
        }

        public void setText(String lbl) {
            errorTitle.setText(lbl);
        }

        @OnClick(R.id.retryBtn)
        public void onRetry() {
            showLoading(true);
            if (mPresenter != null)
                mPresenter.retry();
        }
    }

}
