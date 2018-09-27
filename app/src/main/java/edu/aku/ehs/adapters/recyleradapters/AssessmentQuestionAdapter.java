package edu.aku.ehs.adapters.recyleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.callbacks.RadioGroupAdapterListner;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.widget.AnyTextView;

/**
 */
public class AssessmentQuestionAdapter extends RecyclerView.Adapter<AssessmentQuestionAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private BaseActivity activity;
    private ArrayList<AssessmentQuestionModel> arrData;
    private RadioGroupAdapterListner onSwitchListener1;
    private RadioGroupAdapterListner onSwitchListener2;

    public AssessmentQuestionAdapter(BaseActivity activity, ArrayList<AssessmentQuestionModel> arrData, OnItemClickListener onItemClickListener, RadioGroupAdapterListner onSwitchListener1, RadioGroupAdapterListner onSwitchListener2) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.onSwitchListener1 = onSwitchListener1;
        this.onSwitchListener2 = onSwitchListener2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_screening_question_v2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        AssessmentQuestionModel model = arrData.get(i);
        holder.txtQuestion.setText(model.getQuestionDescription());

        if (model.isAnswer1()) {
            if (model.getAskIsUnderTreatment() != null && model.getAskIsUnderTreatment().equalsIgnoreCase("Y")) {
                holder.contQuestion2.setVisibility(View.VISIBLE);
            } else {
                holder.contQuestion2.setVisibility(View.GONE);
            }


            holder.rbQues1Yes.setChecked(true);

            if (model.isAnswer2()) {
                holder.rbQues2Yes.setChecked(true);
            } else {
                if (!(model.getIsUnderTreatment() == null || model.getIsUnderTreatment().isEmpty()))  {
                    holder.rbQues2No.setChecked(true);
                }
            }

        } else {
            holder.contQuestion2.setVisibility(View.GONE);
            if (!(model.getIsAnswerYes() == null || model.getIsAnswerYes().isEmpty())) {
                holder.rbQues1No.setChecked(true);
            }
        }

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final AssessmentQuestionModel model) {
        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
        holder.rgQues1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onSwitchListener1.onSwitch(radioGroup, i, holder.getAdapterPosition());
            }
        });

        holder.rgQues2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onSwitchListener2.onSwitch(radioGroup, i, holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtQuestion)
        AnyTextView txtQuestion;
        @BindView(R.id.rgQues1)
        RadioGroup rgQues1;
        @BindView(R.id.rbQues1Yes)
        RadioButton rbQues1Yes;
        @BindView(R.id.rbQues1No)
        RadioButton rbQues1No;
        @BindView(R.id.contQuestion1)
        LinearLayout contQuestion1;
        @BindView(R.id.rgQues2)
        RadioGroup rgQues2;
        @BindView(R.id.rbQues2Yes)
        RadioButton rbQues2Yes;
        @BindView(R.id.rbQues2No)
        RadioButton rbQues2No;
        @BindView(R.id.contQuestion2)
        RoundKornerLinearLayout contQuestion2;
        @BindView(R.id.contListItem)
        LinearLayout contListItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
