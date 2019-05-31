package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class DependentsAdapter extends RecyclerView.Adapter<DependentsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    View itemView = null;

    private Context activity;
    private List<UserModel> arrData;

    public DependentsAdapter(Context activity, List<UserModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_dependents, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        UserModel model = arrData.get(i);


        if (model.isSelected()) {
            holder.txtName.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            holder.contSelected.setVisibility(View.VISIBLE);

        } else {
            holder.contSelected.setVisibility(View.GONE);
            holder.txtName.setTextColor(activity.getResources().getColor(R.color.txtDarkGrey));
        }


        holder.txtName.setText(model.getUserDetails().getFullName());
        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgDependentProfile, model.getUserDetails().getImage(), true);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final UserModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, DependentsAdapter.class.getSimpleName()));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDependentProfile)
        CircleImageView imgDependentProfile;
        @BindView(R.id.contSelected)
        RelativeLayout contSelected;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
