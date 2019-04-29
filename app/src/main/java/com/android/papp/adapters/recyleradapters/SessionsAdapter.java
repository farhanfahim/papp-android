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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 */
public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    View itemView = null;

    private Context activity;
    private List<SpinnerModel> arrData;
    private boolean isSessionRequest = false;

    public SessionsAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener, boolean isSessionRequest) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isSessionRequest = isSessionRequest;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_sessions, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);


        if (isSessionRequest) {
            holder.imgDone.setVisibility(View.VISIBLE);
        } else {
            holder.imgDone.setVisibility(View.GONE);
        }


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {


        if (isSessionRequest) {

            holder.imgCancel.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName() + "request"));

            holder.imgDone.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()+ "request"));

        } else {

            holder.contParentLayout.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));

            holder.imgCancel.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));

            holder.imgDone.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));
        }

    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        CircleImageView imgProfile;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.txtDesc)
        AnyTextView txtDesc;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;
        @BindView(R.id.imgDone)
        ImageView imgDone;
        @BindView(R.id.imgCancel)
        ImageView imgCancel;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
