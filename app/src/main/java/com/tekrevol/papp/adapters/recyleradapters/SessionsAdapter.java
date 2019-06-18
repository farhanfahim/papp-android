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
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.receiving_model.SessionRecievingModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    View itemView = null;

    private Context activity;
    private List<SessionRecievingModel> arrData;
    private boolean isAcceptedUpcomingSession;

    public SessionsAdapter(Context activity, List<SessionRecievingModel> arrData, OnItemClickListener onItemClickListener, boolean isAcceptedUpcomingSession) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isAcceptedUpcomingSession = isAcceptedUpcomingSession;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_sessions, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SessionRecievingModel model = arrData.get(i);


        if (isAcceptedUpcomingSession) {
            holder.imgDone.setVisibility(View.GONE);
//            holder.imgCancel.setVisibility(View.GONE);
        } else {
            holder.imgDone.setVisibility(View.VISIBLE);
//            holder.imgCancel.setVisibility(View.VISIBLE);
        }

        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getUser().getUserDetails().getImage(), true);


//        String date = DateManager.convertToUserTimeZone(model.getCreatedAt());
        holder.txtDate.setText(DateManager.getDate(model.getScheduleDate(), AppConstants.DISPLAY_DATE_ONLY_FORMAT));
        holder.txtTime.setText(DateManager.getDate(model.getScheduleDate(), AppConstants.DISPLAY_TIME_ONLY_FORMAT));
        holder.txtDesc.setText("with dependent of " + model.getUser().getUserDetails().getFullName());


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SessionRecievingModel model) {


        if (isAcceptedUpcomingSession) {
            holder.contParentLayout.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));

            holder.imgCancel.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));

            holder.imgDone.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName()));

        } else {

            holder.contParentLayout.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName() + "request"));

            holder.imgCancel.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName() + "request"));

            holder.imgDone.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, SessionsAdapter.class.getSimpleName() + "request"));


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
