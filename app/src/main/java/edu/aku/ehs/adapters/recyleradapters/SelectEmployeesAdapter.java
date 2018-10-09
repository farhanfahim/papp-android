package edu.aku.ehs.adapters.recyleradapters;

import android.graphics.drawable.TransitionDrawable;
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
import edu.aku.ehs.helperclasses.Helper;
import edu.aku.ehs.helperclasses.StringHelper;
import edu.aku.ehs.helperclasses.ui.helper.AnimationHelper;
import edu.aku.ehs.models.peoplesoft.employee.EMPLOYEE;
import edu.aku.ehs.widget.AnyTextView;

import static android.view.View.VISIBLE;

/**
 */
public class SelectEmployeesAdapter extends RecyclerView.Adapter<SelectEmployeesAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private BaseActivity activity;
    private ArrayList<EMPLOYEE> arrData;

    public SelectEmployeesAdapter(BaseActivity activity, ArrayList<EMPLOYEE> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        EMPLOYEE model = arrData.get(i);
        holder.txtEmployeeName.setText(model.getNAME());
        holder.txtDeptName.setText(model.getDESCR());
        holder.txtDesignationAndGrade.setText(model.getTITLE() + " | " + model.getGRADE());
        holder.txtEmployeeID.setText(model.getEMPLID());
        holder.txtMRN.setText(model.getAKU_MRNO());

        TransitionDrawable td = (TransitionDrawable) holder.contListItem.getBackground();

        if (model.isSelected()) {
            Helper.changeTransitionDrawableColor(td, activity.getColor(R.color.selected_item), 1);
            td.startTransition(200);
            AnimationHelper.fade(holder.imgSelected, 0, View.VISIBLE, VISIBLE, 1, 800);
        } else {
            Helper.changeTransitionDrawableColor(td, activity.getColor(R.color.c_white), 0);
            td.resetTransition();
            holder.imgSelected.setVisibility(View.GONE);
        }


        if (StringHelper.checkNotNullAndNotEmpty(model.getAKU_MRNO())) {
            holder.contParent.setAlpha(1f);
        } else {
            holder.contParent.setAlpha(0.5f);
        }

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final EMPLOYEE model) {
        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtEmployeeName)
        AnyTextView txtEmployeeName;
        @BindView(R.id.txtMRN)
        AnyTextView txtMRN;
        @BindView(R.id.txtDesignationAndGrade)
        AnyTextView txtDesignationAndGrade;
        @BindView(R.id.txtEmployeeID)
        AnyTextView txtEmployeeID;
        @BindView(R.id.txtDeptName)
        AnyTextView txtDeptName;
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
