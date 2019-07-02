package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.managers.retrofit.entities.ErrorModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<ErrorModel> arrData;

    public ErrorAdapter(Context activity, List<ErrorModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_error, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        ErrorModel model = arrData.get(i);

        holder.txtLabel.setText(model.getLabel());
        holder.txtMessage.setText(model.getMessage());

    }


    @Override
    public int getItemCount() {
        if (arrData == null) {
            return 0;
        }
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtLabel)
        AnyTextView txtLabel;
        @BindView(R.id.txtMessage)
        AnyTextView txtMessage;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
