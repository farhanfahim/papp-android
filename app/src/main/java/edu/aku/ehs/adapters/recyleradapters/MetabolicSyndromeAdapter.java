package edu.aku.ehs.adapters.recyleradapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.models.MetabolicSyndromeRules;
import edu.aku.ehs.widget.AnyTextView;

/**
 */
public class MetabolicSyndromeAdapter extends RecyclerView.Adapter<MetabolicSyndromeAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<MetabolicSyndromeRules> arrData;

    public MetabolicSyndromeAdapter(Context activity, List<MetabolicSyndromeRules> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
     }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_metabolic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        MetabolicSyndromeRules model = arrData.get(i);

        holder.chkIsTrue.setChecked(model.getIsTrue());
        holder.txtRule.setText(model.getRule());

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final MetabolicSyndromeRules model) {
//        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chkIsTrue)
        CheckBox chkIsTrue;
        @BindView(R.id.txtRule)
        AnyTextView txtRule;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
