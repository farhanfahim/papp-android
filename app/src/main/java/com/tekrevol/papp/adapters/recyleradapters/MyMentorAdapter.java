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
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyMentorAdapter extends RecyclerView.Adapter<MyMentorAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<UserModel> arrData;

    public MyMentorAdapter(Context activity, List<UserModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_lea, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        UserModel model = arrData.get(i);
        holder.txtName.setText(model.getUserDetails().getFullName());
        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getUserDetails().getImage(), true);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final UserModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, MyMentorAdapter.class.getSimpleName()));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
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
