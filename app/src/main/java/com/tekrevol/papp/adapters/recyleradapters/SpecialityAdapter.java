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
public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    private final boolean isShowCancelButton;


    private Context activity;
    private List<SpinnerModel> arrData;

    public SpecialityAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener, boolean isShowCancelButton) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isShowCancelButton = isShowCancelButton;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_add_speciality, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);

        if (isShowCancelButton) {
            holder.imgCancel.setVisibility(View.VISIBLE);
        } else {
            holder.imgCancel.setVisibility(View.GONE);
        }


        holder.txtSpeciality.setText(model.getText());
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        holder.imgCancel.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSpeciality)
        AnyTextView txtSpeciality;
        @BindView(R.id.imgCancel)
        ImageView imgCancel;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
