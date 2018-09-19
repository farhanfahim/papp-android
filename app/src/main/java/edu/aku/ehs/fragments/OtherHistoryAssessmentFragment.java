package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.adapters.recyleradapters.AssessmentQuestionAdapter;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.callbacks.RadioGroupAdapterListner;
import edu.aku.ehs.enums.QuestionTypeEnum;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.fragments.dialogs.PinDialogFragment;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;
import edu.aku.ehs.widget.recyclerview_layout.CustomLayoutManager;

public class OtherHistoryAssessmentFragment extends BaseFragment implements OnItemClickListener {


    Unbinder unbinder;
    NewAssessmentViewPagerFragment parentFragment;
    @BindView(R.id.txtQuestionTitle1)
    AnyTextView txtQuestionTitle1;
    @BindView(R.id.txtQuestionSubTitle1)
    AnyTextView txtQuestionSubTitle1;
    @BindView(R.id.recylerView1)
    RecyclerView recylerView1;
    @BindView(R.id.txtQuestionTitle2)
    AnyTextView txtQuestionTitle2;
    @BindView(R.id.txtQuestionSubTitle2)
    AnyTextView txtQuestionSubTitle2;
    @BindView(R.id.recylerView2)
    RecyclerView recylerView2;
    @BindView(R.id.txtQuestionTitle3)
    AnyTextView txtQuestionTitle3;
    @BindView(R.id.txtQuestionSubTitle3)
    AnyTextView txtQuestionSubTitle3;
    @BindView(R.id.recylerView3)
    RecyclerView recylerView3;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.fabBack)
    FloatingActionButton fabBack;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;


    private ArrayList<AssessmentQuestionModel> arrData1;
    private ArrayList<AssessmentQuestionModel> arrData2;
    private ArrayList<AssessmentQuestionModel> arrData3;
    private AssessmentQuestionAdapter adapter1;
    private AssessmentQuestionAdapter adapter2;
    private AssessmentQuestionAdapter adapter3;
    private RadioGroupAdapterListner onSwitchListener1_r1;
    private RadioGroupAdapterListner onSwitchListener2_r1;
    private RadioGroupAdapterListner onSwitchListener1_r2;
    private RadioGroupAdapterListner onSwitchListener2_r2;
    private RadioGroupAdapterListner onSwitchListener1_r3;
    private RadioGroupAdapterListner onSwitchListener2_r3;
    private SessionDetailModel sessionDetailModel;
    private PinDialogFragment pinDialogFragment;


    public static OtherHistoryAssessmentFragment newInstance() {

        Bundle args = new Bundle();

        OtherHistoryAssessmentFragment fragment = new OtherHistoryAssessmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_assessment_question_list2;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.showHome(getBaseActivity());
        titleBar.setTitle("Employee Assessment");
        titleBar.showBackButton(getBaseActivity());
        titleBar.setEmployeeHeader(sessionDetailModel, getContext());

    }

    @Override
    public void setListeners() {

        pinDialogFragment = PinDialogFragment.newInstance(view -> {
        }, view -> getBaseActivity().addDockableFragment(EmployeeAnthropometricMeasurmentsFragment.newInstance(), false));

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSwitchListener();

        arrData1 = new ArrayList<AssessmentQuestionModel>();
        adapter1 = new AssessmentQuestionAdapter(getBaseActivity(), arrData1, this, onSwitchListener1_r1, onSwitchListener2_r1);
        arrData2 = new ArrayList<AssessmentQuestionModel>();
        adapter2 = new AssessmentQuestionAdapter(getBaseActivity(), arrData2, this, onSwitchListener1_r2, onSwitchListener2_r2);
        arrData3 = new ArrayList<AssessmentQuestionModel>();
        adapter3 = new AssessmentQuestionAdapter(getBaseActivity(), arrData3, this, onSwitchListener1_r3, onSwitchListener2_r3);
    }

    private void initSwitchListener() {

        // List 1
        onSwitchListener1_r1 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrData1.get(adapterPosition).setAnswer1(true);
                adapter1.notifyItemChanged(adapterPosition);
            } else {
                arrData1.get(adapterPosition).setAnswer1(false);
                adapter1.notifyItemChanged(adapterPosition);
            }
        };

        onSwitchListener2_r1 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrData1.get(adapterPosition).setAnswer2(true);
                adapter1.notifyItemChanged(adapterPosition);
            } else {
                arrData1.get(adapterPosition).setAnswer2(false);
                adapter1.notifyItemChanged(adapterPosition);
            }
        };

        // For List 2
        onSwitchListener1_r2 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrData2.get(adapterPosition).setAnswer1(true);
                adapter2.notifyItemChanged(adapterPosition);
            } else {
                arrData2.get(adapterPosition).setAnswer1(false);
                adapter2.notifyItemChanged(adapterPosition);
            }
        };

        onSwitchListener2_r2 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrData2.get(adapterPosition).setAnswer2(true);
                adapter2.notifyItemChanged(adapterPosition);
            } else {
                arrData2.get(adapterPosition).setAnswer2(false);
                adapter2.notifyItemChanged(adapterPosition);
            }
        };


        // For List 3
        onSwitchListener1_r3 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrData3.get(adapterPosition).setAnswer1(true);
                adapter3.notifyItemChanged(adapterPosition);
            } else {
                arrData3.get(adapterPosition).setAnswer1(false);
                adapter3.notifyItemChanged(adapterPosition);
            }
        };

        onSwitchListener2_r3 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrData3.get(adapterPosition).setAnswer2(true);
                adapter3.notifyItemChanged(adapterPosition);
            } else {
                arrData3.get(adapterPosition).setAnswer2(false);
                adapter3.notifyItemChanged(adapterPosition);
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentFragment = (NewAssessmentViewPagerFragment) getParentFragment();
        setTitleData();
        fabNext.setVisibility(View.GONE);
        btnDone.setVisibility(View.VISIBLE);

        bindView();
        bindData();
    }

    private void setTitleData() {
        txtQuestionTitle1.setText("Family History");
        txtQuestionSubTitle1.setText("Specific to Mother, Father, Brother and Sister");

        txtQuestionTitle2.setText("Psychosocial History");
        txtQuestionSubTitle2.setText("Over the last 2 weeks, How often have you felt?");


        txtQuestionTitle3.setText("Smoking Behaviors");
        txtQuestionSubTitle3.setText("Kindly answer the following Questions");
    }

    private void bindData() {
        // List 1
        arrData1.clear();
        AssessmentQuestionModel model1;
        for (String s : getBaseActivity().getResources().getStringArray(R.array.familyHistoryQuestions)) {
            model1 = new AssessmentQuestionModel(s, QuestionTypeEnum.FAMILYHISTORY);
            arrData1.add(model1);
        }
        adapter1.notifyDataSetChanged();


        // List 2

        arrData2.clear();
        AssessmentQuestionModel model2;
        for (String s : getBaseActivity().getResources().getStringArray(R.array.pschosocialHistoryQuestions)) {
            model2 = new AssessmentQuestionModel(s, QuestionTypeEnum.PSYCHOSOCIALHISTORY);
            arrData2.add(model2);
        }
        adapter2.notifyDataSetChanged();

        // List 3
        arrData3.clear();
        AssessmentQuestionModel model3;
        for (String s : getBaseActivity().getResources().getStringArray(R.array.smokingBehaviorsQuestions)) {
            model3 = new AssessmentQuestionModel(s, QuestionTypeEnum.SMOKINGBEHAVIOR);
            arrData3.add(model3);
        }
        adapter3.notifyDataSetChanged();
    }


    private void bindView() {
        // List 1
        RecyclerView.LayoutManager mLayoutManager1 = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView1.setLayoutManager(mLayoutManager1);
        ((DefaultItemAnimator) recylerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView1.setAdapter(adapter1);

        // List 2
        RecyclerView.LayoutManager mLayoutManager2 = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView2.setLayoutManager(mLayoutManager2);
        ((DefaultItemAnimator) recylerView2.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView2.setAdapter(adapter2);

        // List 3
        RecyclerView.LayoutManager mLayoutManager = new CustomLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recylerView3.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recylerView3.getItemAnimator()).setSupportsChangeAnimations(false);
        recylerView3.setAdapter(adapter3);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position, Object object, View view) {

    }


    @OnClick({R.id.fabBack, R.id.fabNext, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fabBack:
                parentFragment.viewpager.movePrevious();
                break;
            case R.id.fabNext:
                parentFragment.viewpager.moveNext();
                break;
            case R.id.btnDone:
                pinDialogFragment.setTitle("Enter Pin Number");
                pinDialogFragment.setCancelable(false);
                pinDialogFragment.clearField();
                pinDialogFragment.show(getBaseActivity().getSupportFragmentManager(), null);
                break;
        }
    }
}

