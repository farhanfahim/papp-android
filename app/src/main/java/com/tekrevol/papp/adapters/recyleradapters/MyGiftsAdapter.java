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
import com.tekrevol.papp.models.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.models.SpinnerModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class MyGiftsAdapter extends RecyclerView.Adapter<MyGiftsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;



    private Context activity;
    private List<SpinnerModel> arrData;

    public MyGiftsAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
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
        SpinnerModel model = arrData.get(i);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
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
        @BindView(R.id.txtPrice)
        AnyTextView txtPrice;
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
