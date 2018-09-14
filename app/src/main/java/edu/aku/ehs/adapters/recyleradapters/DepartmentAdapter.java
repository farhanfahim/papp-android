package edu.aku.ehs.adapters.recyleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.models.DepartmentModel;
import edu.aku.ehs.widget.AnyTextView;

/**
 */
public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;



    private BaseActivity activity;
    private ArrayList<DepartmentModel> arrData;

    public DepartmentAdapter(BaseActivity activity, ArrayList<DepartmentModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_department, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        DepartmentModel model = arrData.get(i);
        holder.txtDepartmentName.setText(model.getDeptName());

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final DepartmentModel model) {
        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDepartmentName)
        AnyTextView txtDepartmentName;
        @BindView(R.id.txtDepartmentID)
        AnyTextView txtDepartmentID;
        @BindView(R.id.contListItem)
        RoundKornerLinearLayout contListItem;
        @BindView(R.id.imgSelected)
        ImageView imgSelected;
        @BindView(R.id.contParent)
        RoundKornerRelativeLayout contParent;
        @BindView(R.id.imgHighlight)
        ImageView imgHighlight;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
