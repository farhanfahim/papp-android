package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.helperclasses.StringHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.models.receiving_model.SessionUsers;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class SessionHistoryAdapter extends RecyclerView.Adapter<SessionHistoryAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<SessionRecievingModel> arrData;
    private boolean isMentor;

    public SessionHistoryAdapter(Context activity, List<SessionRecievingModel> arrData, OnItemClickListener onItemClickListener, boolean isMentor) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isMentor = isMentor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_session_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SessionRecievingModel model = arrData.get(i);

        if (isMentor) {
            holder.txtSessionByTitle.setText("Session Conducted with:");
            holder.txtSessionByDesc.setText(model.getUser().getUserDetails().getFullName());
            ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getUser().getUserDetails().getImage(), true);
        } else {
            holder.txtSessionByTitle.setText("Session Conducted by:");
            holder.txtSessionByDesc.setText(model.getMentor().getUserDetails().getFullName());
            ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getMentor().getUserDetails().getImage(), true);
        }

        if (StringHelper.isNullOrEmpty(model.getAddress())) {
            holder.txtLocation.setText("Online Session");
        } else {
            holder.txtLocation.setText(model.getAddress());
        }

        holder.txtSessionOn.setText(DateManager.convertToUserTimeZone(model.getStartDate()));


        for (SessionUsers sessionUser : model.getSessionUsers()) {
            for (UserModel dependant : model.getUser().getDependants()) {
                if (dependant.getId() == sessionUser.getDependantId()) {
                    holder.txtDependentName.setText(dependant.getUserDetails().getFullName());
                    break;
                }
            }

        }



        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SessionRecievingModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSessionByTitle)
        AnyTextView txtSessionByTitle;
        @BindView(R.id.txtSessionByDesc)
        AnyTextView txtSessionByDesc;
        @BindView(R.id.txtSessionOn)
        AnyTextView txtSessionOn;
        @BindView(R.id.txtLocation)
        AnyTextView txtLocation;
        @BindView(R.id.txtDependentName)
        AnyTextView txtDependentName;
        @BindView(R.id.imgProfile)
        ImageView imgProfile;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
