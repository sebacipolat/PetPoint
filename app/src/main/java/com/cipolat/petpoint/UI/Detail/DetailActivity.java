package com.cipolat.petpoint.UI.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.petNameLbl)
    TextView petNameLbl;

    @BindView(R.id.constrLy)
    ConstraintLayout constrLy;

    private MapStoresFragment mapFrg;

    public static final String PET_KEY = "PET_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        PetDetailsFragment  petDetailFrg = (PetDetailsFragment) getFragmentManager().findFragmentById(R.id.detailpet_fragmnt);
        petDetailFrg.setRetainInstance(true);
        mapFrg = (MapStoresFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragmnt);

        //get pet selected and setTitle
        if (getIntent().getSerializableExtra(PET_KEY) != null) {
            Pet petSelected = (Pet) getIntent().getSerializableExtra(PET_KEY);
            petNameLbl.setText(petSelected.getName());
            if (petDetailFrg != null)
                petDetailFrg.setPetID(petSelected.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mapFrg != null)
            mapFrg.onActivityResult(requestCode, resultCode, data);
    }
}
