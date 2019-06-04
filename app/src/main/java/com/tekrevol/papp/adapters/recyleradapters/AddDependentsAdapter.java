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
import com.tekrevol.papp.models.sending_model.DependantSendingModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.models.sending_model.DependantSendingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class AddDependentsAdapter extends RecyclerView.Adapter<AddDependentsAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<DependantSendingModel> arrData;

    public AddDependentsAdapter(Context activity, List<DependantSendingModel> arrData, OnItemClickListener onItemClickListener) {
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
        DependantSendingModel model = arrData.get(i);

        holder.txtName.setText(model.getFirstName() + " " + model.getLastName());
        holder.txtGender.setText(AppConstants.getGenderString(model.getGender()));

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final DependantSendingModel model) {
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
        @BindView(R.id.txtRemove)
        AnyTextView txtRemove;
        @BindView(R.id.txtGender)
        AnyTextView txtGender;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
