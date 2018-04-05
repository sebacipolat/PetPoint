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

public class ViewHolderPet extends RecyclerView.ViewHolder {

    @BindView(R.id.petItm)
    TextView labelItm;
    @BindView(R.id.iconItm)
    ImageView imgIcon;

    private TypedArray iconArrray;

    ViewHolderPet(View itemView, Context contxt) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        Resources res = contxt.getResources();
        iconArrray = res.obtainTypedArray(R.array.icons);
    }

    public void bindObject(Pet pet) {
        if (pet != null) {
            if (pet.getName() != null)
                labelItm.setText(pet.getName());

            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(iconArrray.length());
            Drawable drawable = iconArrray.getDrawable(randomInt);
            imgIcon.setImageDrawable(drawable);
        }
    }

}

