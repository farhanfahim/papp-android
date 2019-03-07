package edu.aku.ehs.adapters.recyleradapters;

import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.jcminarro.roundkornerlayout.RoundKornerRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.fragments.SessionDetailFragment;
import edu.aku.ehs.helperclasses.Helper;
import edu.aku.ehs.helperclasses.StringHelper;
import edu.aku.ehs.helperclasses.ui.helper.AnimationHelper;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;

import static android.view.View.VISIBLE;

/**
 */
public class SessionDetailAdapter extends RecyclerView.Adapter<SessionDetailAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private ArrayList<SessionDetailModel> filteredData;
    private Filter mFilter = new ItemFilter();
    private BaseActivity activity;
    private ArrayList<SessionDetailModel> arrData;

    public SessionDetailAdapter(BaseActivity activity, ArrayList<SessionDetailModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.filteredData = arrData;
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


        SessionDetailModel model = filteredData.get(i);
        holder.txtEmployeeName.setText(model.getEmployeeName());
        holder.txtStatus.setText(model.getStatusID());
        holder.txtEmployeeAge.setText(model.getAge() + " Y");
        holder.txtMRN.setText(model.getMedicalRecordNo());
        holder.txtEmployeeID.setText(model.getEmployeeNo());
        holder.txtDepartmentName.setText(model.getDepartmentName());



        holder.imgViewProfile.setVisibility(View.GONE);

        if (StringHelper.checkNotNullAndNotEmpty(model.getGender())) {
            if (model.getGender().equalsIgnoreCase("M")) {
                holder.txtEmployeeGender.setText("Male");
            } else {
                holder.txtEmployeeGender.setText("Female");
            }
        } else {
            holder.txtEmployeeGender.setText("N/A");
        }


        if (model.getStatusEnum() == EmployeeSessionState.SCHEDULED || model.getStatusEnum() == EmployeeSessionState.INPROGRESS) {
            holder.txDate.setText("Scheduled for: " + model.getDisplayScheduledDTTM());
        } else {
            holder.txDate.setText("");
        }

        if (model.getHasLabResult().equalsIgnoreCase("Y")) {
            if (model.getStatusID().equalsIgnoreCase(EmployeeSessionState.COMPLETED.canonicalForm())) {
                holder.stepView.setCurrentStep(2);
            } else {
                holder.stepView.setCurrentStep(1);
            }
        } else {
            holder.stepView.setCurrentStep(0);
        }


        switch (model.getStatusEnum()) {
            case ENROLLED:
                showOptions(holder);
                setStatusColor(holder, R.color.colorPrimaryDark);
                break;
            case SCHEDULED:
                showOptions(holder);
                setStatusColor(holder, R.color.fbutton_color_midnight_blue);
                break;
            case INPROGRESS:
                hideOptions(holder);
                setStatusColor(holder, R.color.base_amber);
                break;
            case COMPLETED:
                holder.imgViewProfile.setVisibility(VISIBLE);
                hideOptions(holder);
                setStatusColor(holder, android.R.color.holo_green_dark);
                break;
            case CANCELLED:
                hideOptions(holder);
                setStatusColor(holder, R.color.red_resistant);
                break;
        }


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


        if (SessionDetailFragment.isSelectingEmployeesForSchedule) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnSchedule.setVisibility(View.GONE);
        }



        holder.txtFullTimePartTime.setText(model.getFullTimePartTimeDesc());



        setListener(holder, model);
    }

    private void setStatusColor(ViewHolder holder, int colorID) {
        holder.contStatus.setBackgroundColor(activity.getColor(colorID));
        holder.txtStatus.setTextColor(activity.getColor(colorID));
    }

    private void hideOptions(ViewHolder holder) {
        holder.btnSchedule.setVisibility(View.GONE);
        holder.btnDelete.setVisibility(View.GONE);
    }

    private void showOptions(ViewHolder holder) {
        holder.btnSchedule.setVisibility(View.VISIBLE);
        holder.btnDelete.setVisibility(View.VISIBLE);
    }

    private void setListener(final ViewHolder holder, final SessionDetailModel model) {
        holder.btnSchedule.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.btnDelete.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.imgViewProfile.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
//        holder.stepView.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.stepView.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                onItemClick.onItemClick(holder.getAdapterPosition(), model, view);
                return true;
            } else return false;
        });
    }


    public ArrayList<SessionDetailModel> getFilteredData() {
        return filteredData;
    }


    @Override
    public int getItemCount() {
        return filteredData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtEmployeeName)
        AnyTextView txtEmployeeName;
        @BindView(R.id.txtEmployeeGender)
        AnyTextView txtEmployeeGender;
        @BindView(R.id.txtEmployeeAge)
        AnyTextView txtEmployeeAge;
        @BindView(R.id.txtMRN)
        AnyTextView txtMRN;
        @BindView(R.id.txtEmployeeID)
        AnyTextView txtEmployeeID;
        @BindView(R.id.txtDepartmentName)
        AnyTextView txtDepartmentName;
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
        @BindView(R.id.contStatus)
        RoundKornerRelativeLayout contStatus;
        @BindView(R.id.txDate)
        AnyTextView txDate;
        @BindView(R.id.txtFullTimePartTime)
        AnyTextView txtFullTimePartTime;
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
        @BindView(R.id.imgViewProfile)
        ImageView imgViewProfile;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public Filter getFilter() {

        return mFilter;
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<SessionDetailModel> list = arrData;

            int count = list.size();

//            final ArrayList<String> nlist = new ArrayList<String>(count);
            final ArrayList<SessionDetailModel> filterData = new ArrayList<SessionDetailModel>();

            String filterableString1;
            String filterableString2;
            String filterableString3;
            String filterableString4;
            String filterableString5;

            for (int i = 0; i < count; i++) {
                filterableString1 = list.get(i).getStatusID();
                filterableString2 = list.get(i).getDepartmentName();
                filterableString3 = list.get(i).getEmployeeName();
                filterableString4 = list.get(i).getEmployeeNo();
                filterableString5 = list.get(i).getMedicalRecordNo();
                if (filterableString1.toLowerCase().contains(filterString)
                        || filterableString2.toLowerCase().contains(filterString)
                        || filterableString3.toLowerCase().contains(filterString)
                        || filterableString4.toLowerCase().contains(filterString)
                        || filterableString5.toLowerCase().contains(filterString)
                        ) {
//                    nlist.add(filterableString);
                    filterData.add(list.get(i));
                }
            }

            results.values = filterData;
            results.count = filterData.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<SessionDetailModel>) results.values;
            notifyDataSetChanged();
        }

    }

    public SessionDetailModel getItem(int position) {
        return filteredData.get(position);
    }
}
