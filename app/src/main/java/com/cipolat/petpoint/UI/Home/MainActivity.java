package com.cipolat.petpoint.UI.Home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.listRecylr)
    RecyclerView listRecylr;
    @BindView(R.id.loader)
    ImageView loader;
    @BindView(R.id.coordLayout)
    CoordinatorLayout coordLay;

    private HomePresenter mPresenter;
    private PetsAdapter mAdapter;
    private LinearLayoutManager mLinearManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new HomePresenter(this);
        mPresenter.setView(this);
        getList();
    }

    private void fillList(final ArrayList<Pet> lista) {
        mAdapter = new PetsAdapter(this, lista);
        mLinearManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listRecylr.setLayoutManager(mLinearManager);
        listRecylr.setHasFixedSize(true);
        listRecylr.setAdapter(mAdapter);
       /* mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article itmArt = mAdapter.getItemByPos(listNotas.getChildAdapterPosition(view));
                if (itmArt != null) {
                    Intent inten = new Intent(HomeActivity.this, ArticleViewerActivity.class);
                    inten.putExtra(ArticleViewerActivity.ARTICLE_ID, String.valueOf(itmArt.getData().getId()));
                    inten.putExtra(ArticleViewerActivity.ARTICLE_SLUG, String.valueOf(itmArt.getArticleSlug()));
                    inten.putExtra(ArticleViewerActivity.ARTICLE_SECTION_NAME, String.valueOf(itmArt.getArticleSectionName()));
                    startActivity(inten);
                }
            }
        });
*/
        listRecylr.setVisibility(View.VISIBLE);
    }


    @Override
    public void onGetPetsOk(ArrayList<Pet> list) {
        showLoading(false);
        fillList(list);
        Snackbar.make(coordLay, getString(R.string.home_get_list_ok), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onError(ErrorType error) {
        showErrorPlaceHolder();
        if (error.isNetworkError())
            showErrorSnack(getString(R.string.error_network));
        else
            showErrorSnack(getString(R.string.error_data));
    }

    private void getList() {
        showLoading(true);
        mPresenter.getPetsList();
    }

    private void showLoading(boolean visible) {
        if (visible) {
            Glide.with(this).load(R.drawable.loaderdog).into(loader);
            loader.setVisibility(View.VISIBLE);
            listRecylr.setVisibility(View.GONE);
        } else {
            loader.setVisibility(View.GONE);
            listRecylr.setVisibility(View.VISIBLE);
        }
    }

    private void showErrorPlaceHolder() {
        Glide.with(this).clear(loader);
        loader.setVisibility(View.VISIBLE);
        listRecylr.setVisibility(View.GONE);
        loader.setImageResource(R.drawable.error_cloud);
    }

    private void showErrorSnack(String label) {
        Snackbar.make(coordLay, label, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.yellow))
                .setAction(getString(R.string.snack_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getList();
                    }
                })
                .show();
    }

}
