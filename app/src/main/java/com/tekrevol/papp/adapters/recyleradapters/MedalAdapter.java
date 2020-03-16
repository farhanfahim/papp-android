package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 */
public class MedalAdapter extends RecyclerView.Adapter<MedalAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    private View itemView = null;



    private Context activity;
    private List<TaskReceivingModel> arrData;

    public MedalAdapter(Context activity, List<TaskReceivingModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_medal, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TaskReceivingModel model = arrData.get(i);
        ImageLoaderHelper.loadImageWithouAnimationByPath( holder.imgMedal, model.getIcon(), false);
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TaskReceivingModel model) {
        holder.contMedalLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, MedalAdapter.class.getSimpleName()));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgMedal)
        CircleImageView imgMedal;
        @BindView(R.id.contMedalLayout)
        LinearLayout contMedalLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
