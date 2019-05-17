package com.android.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyTextView;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class GiftsAndRewardsAdapter extends RecyclerView.Adapter<GiftsAndRewardsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<SpinnerModel> arrData;

    public GiftsAndRewardsAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
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
        SpinnerModel model = arrData.get(i);

        switch (i) {
            case 0:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.img_client_2, holder.imgProfile);
                break;

            case 1:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.img_client_6, holder.imgProfile);
                break;

            case 2:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.img_client_7, holder.imgProfile);
                break;

            case 3:
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.img_client_10, holder.imgProfile);
                break;
            case 4:
                Glide.with(activity)
                        .load(R.raw.gif_client_4)
                        .into(holder.imgProfile);
                break;

            case 5:
                Glide.with(activity)
                        .load(R.raw.gif_client_5)
                        .into(holder.imgProfile);
                break;

            case 6:
                Glide.with(activity)
                        .load(R.raw.gif_client_11)
                        .into(holder.imgProfile);
                break;

            case 7:
                Glide.with(activity)
                        .load(R.raw.gif_client_12)
                        .into(holder.imgProfile);
                break;

        }

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        holder.contRedeemButton.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        // FIXME: 2019-05-17 Remove hardcoded count
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtPrice)
        AnyTextView txtPrice;
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
