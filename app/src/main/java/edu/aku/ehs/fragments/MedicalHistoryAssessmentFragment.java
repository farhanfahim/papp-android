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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.models.AssessmentQuestionModel;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

public class MedicalHistoryAssessmentFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.txtQuestionTitle)
    AnyTextView txtQuestionTitle;
    @BindView(R.id.txtQuestionSubTitle)
    AnyTextView txtQuestionSubTitle;
    @BindView(R.id.recylerView)
    RecyclerView recylerView;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.fabBack)
    FloatingActionButton fabBack;
    @BindView(R.id.fabNext)
    FloatingActionButton fabNext;


    private ArrayList<AssessmentQuestionModel> arrData;
    private AssessmentQuestionAdapter adapter;
    private RadioGroupAdapterListner onSwitchListener1;
    private RadioGroupAdapterListner onSwitchListener2;

    NewAssessmentViewPagerFragment parentFragment;
    private SessionDetailModel sessionDetailModel;


    public static MedicalHistoryAssessmentFragment newInstance(SessionDetailModel sessionDetailModel) {

        Bundle args = new Bundle();

        MedicalHistoryAssessmentFragment fragment = new MedicalHistoryAssessmentFragment();
        fragment.sessionDetailModel = sessionDetailModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_assessment_question_list;
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
    }

    private void initSwitchListener() {
        onSwitchListener1 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues1Yes) {
                arrData.get(adapterPosition).setAnswer1(true);
                adapter.notifyItemChanged(adapterPosition);
            } else {
                arrData.get(adapterPosition).setAnswer1(false);
                adapter.notifyItemChanged(adapterPosition);
            }
        };


        onSwitchListener2 = (radioGroup, i, adapterPosition) -> {
            if (i == R.id.rbQues2Yes) {
                arrData.get(adapterPosition).setAnswer2(true);
                adapter.notifyItemChanged(adapterPosition);
            } else {
                arrData.get(adapterPosition).setAnswer2(false);
                adapter.notifyItemChanged(adapterPosition);
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

    public ArrayList<AssessmentQuestionModel> getArrData() {
        return arrData;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentFragment = (NewAssessmentViewPagerFragment) getParentFragment();
        arrData = parentFragment.getArrMedicalHistory();
        adapter = new AssessmentQuestionAdapter(getBaseActivity(), arrData, this, onSwitchListener1, onSwitchListener2);


        txtQuestionTitle.setText("Medical History");

        if (!arrData.isEmpty()) {
            txtQuestionSubTitle.setText(arrData.get(0).getCategoryDescription());
        } else {
            txtQuestionSubTitle.setText("");
        }

        fabBack.setVisibility(View.GONE);
        bindView();
        bindData();
    }

    private void bindData() {
        adapter.notifyDataSetChanged();
    }


    private void bindView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        recylerView.setLayoutManager(mLayoutManager);
        ((DefaultItemAnimator) recylerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int resId = R.anim.layout_animation_fall_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        recylerView.setLayoutAnimation(animation);
        recylerView.setAdapter(adapter);
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
                break;
            case R.id.fabNext:
                parentFragment.viewpager.moveNext();
                break;
            case R.id.btnDone:
                break;
        }
    }
}
