package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KidsCommunityAdapter extends RecyclerView.Adapter<KidsCommunityAdapter.ViewHolder> {
    private final OnItemClickListener onItemClick;

    private Context activity;
    private List<UserModel> arrData;
    private boolean isLEA;

    public KidsCommunityAdapter(Context activity, List<UserModel> arrData, OnItemClickListener onItemClickListener, boolean isLEA) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isLEA = isLEA;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_view_lea, parent, false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        UserModel model = arrData.get(i);
        holder.txtName.setText(model.getUserDetails().getFullName());


        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getUserDetails().getImage(), true);

        if (isLEA) {
            holder.imgNext.setVisibility(View.VISIBLE);
            holder.imgChat.setVisibility(View.GONE);
        } else {
            holder.imgChat.setVisibility(View.VISIBLE);
            holder.imgNext.setVisibility(View.GONE);

        }

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final UserModel model) {

        if (isLEA) {
            holder.contParentLayout.
                    setOnClickListener(view -> {
                        onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null);
                    });
        } else {
            holder.imgChat.
                    setOnClickListener(view -> {
                        onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null);
                    });
        }

    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.imgNext)
        ImageView imgNext;
        @BindView(R.id.imgChat)
        ImageView imgChat;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
