package com.cipolat.petpoint.UI.Home; /**
 * Created by sebastian on 04/04/18.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewHolderPet extends RecyclerView.ViewHolder {

    @BindView(R.id.petItm)
    TextView labelItm;
    @BindView(R.id.iconItm)
    ImageView imgIcon;

    private Context mCtx;

    ViewHolderPet(View itemView, Context contxt) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mCtx = contxt;
    }

    @OnClick(R.id.petItm)
    public void petClick() {
    }

    public void bindObject(Pet pet) {
        labelItm.setText(pet.getName());

        Resources res = this.mCtx.getResources();
        TypedArray iconArrray = res.obtainTypedArray(R.array.icons);
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(iconArrray.length());
        Drawable drawable = iconArrray.getDrawable(randomInt);

        imgIcon.setImageDrawable(drawable);
    }

}

