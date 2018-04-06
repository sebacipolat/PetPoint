package com.cipolat.petpoint.UI.Detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sebastian on 04/04/18.
 */

public class DetailActivity extends AppCompatActivity implements LoaderCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.petNameLbl)
    TextView petNameLbl;
    @BindView(R.id.progress_bar_search)
    ProgressBar progressBar;
    private PetDetailsFragment petDetailFrg;

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
        petDetailFrg = (PetDetailsFragment) getFragmentManager().findFragmentById(R.id.detailpet_fragmnt);
        petDetailFrg.setListener(this);
        //get pet selected and setTitle
        if (getIntent().getSerializableExtra(PET_KEY) != null) {
            Pet petSelected = (Pet) getIntent().getSerializableExtra(PET_KEY);
            petNameLbl.setText(petSelected.getName());
            if (petDetailFrg != null)
                petDetailFrg.setPetID(petSelected.getId());
        }
    }

    @Override
    public void onLoading(boolean value) {
        progressBar.setVisibility(View.GONE);
    }
}
