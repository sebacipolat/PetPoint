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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeLayout.setOnRefreshListener(this);
        attachPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getList();
    }

    /**
     * get presenter stored
     */
    private void attachPresenter() {
        mPresenter = (HomePresenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new HomePresenter();
        }
        mPresenter.setView(this);
    }

    /**
     * Keep presenter state on configuration changes
     *
     * @return presenter
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    /**
     * Fill recyclerview with list pets
     *
     * @param lista pets array from api
     */
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

    /**
     * Retrieve pet list from api
     */
    private void getList() {
        showLoading(true);
        mPresenter.getPetsList();
    }

    /**
     * show or hide loading animation
     *
     * @param visible boolean value visibility
     */
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

    /**
     * show or hide swipelayout animation loading
     *
     * @param var boolean
     */
    private void showLoaderInidicator(boolean var) {
        swipeLayout.setRefreshing(var);
    }

    /**
     * Show the error placeholder
     */
    private void showErrorPlaceHolder() {
        Glide.with(this).clear(loader);
        loader.setVisibility(View.VISIBLE);
        swipeLayout.setVisibility(View.GONE);
        loader.setImageResource(R.drawable.error_cloud);
    }

    /**
     * Array pets coming from Api
     *
     * @param list Arraylist<pet>
     */
    @Override
    public void onGetPetsOk(ArrayList<Pet> list, boolean newData) {
        //show sort item menu
        showLoading(false);
        showLoaderInidicator(false);
        fillList(list);
        if (newData)
            showSimpleTextSnack(getString(R.string.home_get_list_ok));
    }

    /**
     * Array pet coming from finish sort
     *
     * @param list array pet
     */
    @Override
    public void onUpdateList(ArrayList<Pet> list) {
        showSimpleTextSnack(getString(R.string.sort_finish));
        showLoaderInidicator(false);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Array pet coming from pulltorefresh
     */
    @Override
    public void onRefresh() {
        showLoaderInidicator(true);
        mPresenter.refreshPetList();
    }

    /**
     * When request error happened show error placeholder
     * and show snackbar
     *
     * @param error error type
     */
    @Override
    public void onError(ErrorType error){
        showErrorPlaceHolder();
        if (error.isNetworkError())
            showErrorSnack(getString(R.string.error_network));
        else
            showErrorSnack(getString(R.string.error_data));

        showLoaderInidicator(false);
    }

    /**
     * show snackbar for error retry
     *
     * @param label text message
     */
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

    /**
     * show simple text snackbar
     *
     * @param text text message
     */
    private void showSimpleTextSnack(String text) {
        Snackbar.make(coordLay, text, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        menu.setHeaderTitle(getString(R.string.sort_title));

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        showLoaderInidicator(true);
        switch (item.getItemId()) {
            case R.id.ascndSort:
                mPresenter.sort(true);
                return true;
            case R.id.descndSort:
                mPresenter.sort(false);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort:
                if(mPresenter.isSorteable){
                    View view = findViewById(R.id.sort);
                    registerForContextMenu(view);
                    openContextMenu(view);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
