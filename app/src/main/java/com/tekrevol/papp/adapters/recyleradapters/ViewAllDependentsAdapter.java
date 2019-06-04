package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
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
public class ViewAllDependentsAdapter extends RecyclerView.Adapter<ViewAllDependentsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;

    private Context activity;
    private List<UserModel> arrData;

    public ViewAllDependentsAdapter(Context activity, List<UserModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_add_dependents, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        UserModel model = arrData.get(i);

        holder.txtEmail.setVisibility(View.VISIBLE);

        holder.txtName.setText(model.getUserDetails().getFullName());
        holder.txtGender.setText(AppConstants.getGenderString(model.getUserDetails().getGender()));
        holder.txtEmail.setText(model.getEmail());
        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgDependentProfile, model.getUserDetails().getImage(), true);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final UserModel model) {
        holder.txtRemove.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgDependentProfile)
        CircleImageView imgDependentProfile;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtGender)
        AnyTextView txtGender;
        @BindView(R.id.txtEmail)
        AnyTextView txtEmail;
        @BindView(R.id.txtRemove)
        AnyTextView txtRemove;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
