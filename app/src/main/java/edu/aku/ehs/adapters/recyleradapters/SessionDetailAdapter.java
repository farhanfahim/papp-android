package edu.aku.ehs.adapters.recyleradapters;

import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.helperclasses.Helper;
import edu.aku.ehs.helperclasses.ui.helper.AnimationHelper;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;

import static android.view.View.VISIBLE;

/**
 */
public class SessionDetailAdapter extends RecyclerView.Adapter<SessionDetailAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private BaseActivity activity;
    private ArrayList<SessionDetailModel> arrData;

    public SessionDetailAdapter(BaseActivity activity, ArrayList<SessionDetailModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_session_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SessionDetailModel model = arrData.get(i);
        holder.txtEmployeeName.setText(model.getEmployeeName());
        holder.txtStatus.setText(model.getStatus().canonicalForm());

        holder.btnSchedule.setVisibility(View.VISIBLE);
        holder.btnDelete.setVisibility(View.VISIBLE);
        switch (model.getStatus()) {
            case ENROLLED:
//                holder.btnSchedule.setText("Set Schedule");
                break;
            case SCHEDULED:
//                holder.btnSchedule.setText("Update Schedule");
                break;
            case INPROGRESS:
//                holder.btnSchedule.setText("Cancel Schedule");
                holder.btnSchedule.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                break;
            case CLOSED:
                holder.btnSchedule.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                break;
        }


        TransitionDrawable td = (TransitionDrawable) holder.contListItem.getBackground();


        if (model.isSelected()) {
            Helper.changeTransitionDrawableColor(td, activity.getColor(R.color.selected_item), 1);
            td.startTransition(400);
            AnimationHelper.fade(holder.imgSelected, 0, View.VISIBLE, VISIBLE, 1, 800);


        } else {
            Helper.changeTransitionDrawableColor(td, activity.getColor(R.color.c_white), 0);
            td.resetTransition();
            holder.imgSelected.setVisibility(View.GONE);

        }

        if (model.isInSelectionMode()) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnSchedule.setVisibility(View.GONE);
        } else {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.VISIBLE);
        }

        holder.stepView.setCurrentStep(2);


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SessionDetailModel model) {
        holder.btnSchedule.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.btnDelete.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
//        holder.stepView.setOnClickListener(view -> holder.contListItem.performClick());
        holder.stepView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    return holder.contListItem.performClick();
                } else return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtEmployeeName)
        AnyTextView txtEmployeeName;
        @BindView(R.id.txtEmployeeGender)
        AnyTextView txtEmployeeGender;
        @BindView(R.id.txtMRN)
        AnyTextView txtMRN;
        @BindView(R.id.txDate)
        AnyTextView txDate;
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
        @BindView(R.id.stepView)
        StepperIndicator stepView;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
        @BindView(R.id.btnSchedule)
        ImageView btnSchedule;
        @BindView(R.id.contListItem)
        RoundKornerLinearLayout contListItem;
        @BindView(R.id.imgSelected)
        ImageView imgSelected;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
