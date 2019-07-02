package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.models.general.TupleModel;
import com.tekrevol.papp.widget.AnyTextView;

/**
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    Typeface bold;

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<TupleModel> arrData;

    public SampleAdapter(Context activity, List<TupleModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        bold = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue Medium.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_lab, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TupleModel model = arrData.get(i);

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TupleModel model) {
//        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtLabName)
        AnyTextView txtLabName;
        @BindView(R.id.txtRange)
        AnyTextView txtRange;
        @BindView(R.id.txtResult)
        AnyTextView txtResult;
        @BindView(R.id.txtUnit)
        AnyTextView txtUnit;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtState)
        AnyTextView txtState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
