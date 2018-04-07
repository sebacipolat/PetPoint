package com.cipolat.petpoint.UI.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.cipolat.petpoint.Data.Model.ErrorType;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;
import com.cipolat.petpoint.UI.Detail.DetailActivity;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeView, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private HomePresenter mPresenter;
    private PetsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeLayout.setOnRefreshListener(this);

        mPresenter = new HomePresenter();
        mPresenter.setView(this);
        getList();

    }

    private void fillList(ArrayList<Pet> lista) {
        mAdapter = new PetsAdapter(this, lista);
        listRecylr.setLayoutManager(new GridLayoutManager(this, 2));
        listRecylr.setHasFixedSize(true);
        listRecylr.setAdapter(mAdapter);
        listRecylr.setVisibility(View.VISIBLE);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pet itmPet = mAdapter.getItemByPos(listRecylr.getChildAdapterPosition(view));
                if (itmPet != null) {
                    Intent inten = new Intent(MainActivity.this, DetailActivity.class);
                    inten.putExtra(DetailActivity.PET_KEY, itmPet);
                    startActivity(inten);
                }
            }
        });
    }


    @Override
    public void onGetPetsOk(ArrayList<Pet> list) {
        showLoading(false);
        showLoaderInidicator(false);
        fillList(list);
        Snackbar.make(coordLay, getString(R.string.home_get_list_ok), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onError(ErrorType error){
        showErrorPlaceHolder();
        if (error.isNetworkError())
            showErrorSnack(getString(R.string.error_network));
        else
            showErrorSnack(getString(R.string.error_data));

        showLoaderInidicator(false);
    }

    private void getList(){
        showLoading(true);
        mPresenter.getPetsList();
    }

    private void showLoading(boolean visible) {
        if (visible) {
            Glide.with(this).load(R.drawable.loaderdog).into(loader);
            loader.setVisibility(View.VISIBLE);
            swipeLayout.setVisibility(View.GONE);
        } else {
            loader.setVisibility(View.GONE);
            swipeLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showLoaderInidicator(boolean var) {
        swipeLayout.setRefreshing(var);
    }

    private void showErrorPlaceHolder() {
        Glide.with(this).clear(loader);
        loader.setVisibility(View.VISIBLE);
        swipeLayout.setVisibility(View.GONE);
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

    @Override
    public void onRefresh() {
        showLoaderInidicator(true);
        mPresenter.getPetsList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
