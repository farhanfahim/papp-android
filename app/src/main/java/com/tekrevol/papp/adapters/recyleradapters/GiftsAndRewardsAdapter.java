package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.models.receiving_model.GiftsModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class GiftsAndRewardsAdapter extends RecyclerView.Adapter<GiftsAndRewardsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;

    private SharedPreferenceManager sharedPreferenceManager;

    private Context activity;
    private List<GiftsModel> arrData;

    public GiftsAndRewardsAdapter(Context activity, List<GiftsModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.sharedPreferenceManager = SharedPreferenceManager.getInstance(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_gifts_and_rewards, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        GiftsModel model = arrData.get(i);


        Glide.with(activity)
                .load(ImageLoaderHelper.getImageURLFromPath(model.getImage()))
                .error(R.drawable.app_icon_placeholder)
                .into(holder.imgProfile);

        holder.txtItemName.setText(model.getItemName());
        holder.txtPoints.setText("for " + model.getPoints() + " points");


        if (model.getPoints() > sharedPreferenceManager.getCurrentUser().getUserDetails().getTotalPoints()) {
            holder.contRedeemButton.setAlpha(0.2f);
            holder.contRedeemButton.setOnClickListener(null);
        } else {
            holder.contRedeemButton.setAlpha(1f);
            setListener(holder, model);
        }


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final GiftsModel model) {
        holder.contRedeemButton.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtItemName)
        AnyTextView txtItemName;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;
        @BindView(R.id.contRedeemButton)
        LinearLayout contRedeemButton;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
