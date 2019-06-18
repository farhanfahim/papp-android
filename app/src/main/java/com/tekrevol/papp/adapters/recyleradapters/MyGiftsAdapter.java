package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.receiving_model.GiftsHistoryModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyGiftsAdapter extends RecyclerView.Adapter<MyGiftsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<GiftsHistoryModel> arrData;

    public MyGiftsAdapter(Context activity, List<GiftsHistoryModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_my_gifts, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        GiftsHistoryModel model = arrData.get(i);


        if (model.getGifts() == null) {
            holder.txtItemName.setText("");

            holder.txtPoints.setText("for " + model.getPoints() + " points");
            holder.txtDate.setText(DateManager.convertToUserTimeZone(model.getCreatedAt()));

            return;
        }


        Glide.with(activity)
                .load(ImageLoaderHelper.getImageURLFromPath(model.getGifts().getImage()))
                .error(R.drawable.app_icon_placeholder)
                .into(holder.imgProfile);

        holder.txtItemName.setText(model.getGifts().getItemName());
        holder.txtPoints.setText("for " + model.getPoints() + " points");
        holder.txtDate.setText(DateManager.convertToUserTimeZone(model.getCreatedAt()));



//        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final GiftsHistoryModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtToken)
        AnyTextView txtToken;
        @BindView(R.id.txtItemName)
        AnyTextView txtItemName;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
