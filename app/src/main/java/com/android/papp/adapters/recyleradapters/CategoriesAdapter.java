package com.android.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.papp.R;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyTextView;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<SpinnerModel> arrData;

    public CategoriesAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);


        if (model.isSelected()) {
            holder.contParentLayout.setBackgroundColor(activity.getResources().getColor(R.color.base_amber));
            holder.txtCategory.setTextColor(activity.getResources().getColor(R.color.c_white));
        } else {
            holder.contParentLayout.setBackgroundColor(activity.getResources().getColor(R.color.c_white));
            holder.txtCategory.setTextColor(activity.getResources().getColor(R.color.txtBlack));
        }

        holder.txtCategory.setText(model.getText());
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, CategoriesAdapter.class.getSimpleName()));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCategory)
        AnyTextView txtCategory;
        @BindView(R.id.contParentLayout)
        RoundKornerLinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
