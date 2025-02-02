package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.helperclasses.DateHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tekrevol.papp.constatnts.AppConstants.INPUT_DATE_FORMAT;

/**
 *
 */
public class OnGoingTaskAdapter extends RecyclerView.Adapter<OnGoingTaskAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    private final int status;


    private View itemView = null;


    private Context activity;
    private List<TaskReceivingModel> arrData;

    public OnGoingTaskAdapter(Context activity, List<TaskReceivingModel> arrData, int status, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_task_ongoing, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TaskReceivingModel model = arrData.get(i);

        holder.txtTitle.setText(model.getTitle());
        holder.txtDesc.setText("Reward: " + model.getRewardPoints() + " points");
        if (model.getTaskUsers() != null) {
            holder.txtDuration.setText("Started: " + DateHelper.getElapsedTimeNew(DateManager.convertToUserTimeZone(model.getTaskUsers().getStartDate()), INPUT_DATE_FORMAT));
        } else {
            holder.txtDuration.setText("Started: -");
        }

        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgTask, model.getIcon(), false);


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TaskReceivingModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, status));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgTask)
        CircleImageView imgTask;
        @BindView(R.id.txtDesc)
        AnyTextView txtDesc;
        @BindView(R.id.txtTitle)
        AnyTextView txtTitle;
        @BindView(R.id.txtDuration)
        AnyTextView txtDuration;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
