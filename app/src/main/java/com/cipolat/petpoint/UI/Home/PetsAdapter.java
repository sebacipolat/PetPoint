package com.cipolat.petpoint.UI.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;
import java.util.ArrayList;

/**
 * Created by sebastian on 02/06/16.
 */
public class PetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private Context mContext;
    private ArrayList<Pet> mListNews;
    private View.OnClickListener mListener;

    public PetsAdapter(Context context, ArrayList<Pet> list) {
        this.mContext = context;
        this.mListNews = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolderPet(v,this.mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderPet vhTopNews = (ViewHolderPet) holder;
        vhTopNews.bindObject(mListNews.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Pet getItemByPos(int pos) {
        return mListNews.get(pos);
    }

    @Override
    public int getItemCount() {
        return mListNews.size();
    }


    public void setOnClickListener(View.OnClickListener listen) {
        this.mListener = listen;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null)
            mListener.onClick(view);
    }


}







