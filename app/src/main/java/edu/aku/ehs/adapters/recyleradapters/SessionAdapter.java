package edu.aku.ehs.adapters.recyleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.widget.AnyTextView;

/**
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;



    private BaseActivity activity;
    private ArrayList<SessionModel> arrData;

    public SessionAdapter(BaseActivity activity, ArrayList<SessionModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_session, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SessionModel model = arrData.get(i);
        holder.txtSessionName.setText(model.getSessionName());
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SessionModel model) {
        holder.contListItem.setOnClickListener(v -> onItemClick.onItemClick(holder.getAdapterPosition(), model, v));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSessionName)
        AnyTextView txtSessionName;
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
        @BindView(R.id.txtStartDate)
        AnyTextView txtStartDate;
        @BindView(R.id.txtEndDate)
        AnyTextView txtEndDate;
        @BindView(R.id.contListItem)
        RoundKornerLinearLayout contListItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
