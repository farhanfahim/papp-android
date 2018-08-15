package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class EmailFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    AnyEditTextView edtEmailAddress;
    @BindView(R.id.edtEmailSubject)
    AnyEditTextView edtEmailSubject;
    @BindView(R.id.edtEmailBody)
    AnyEditTextView edtEmailBody;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSend)
    Button btnSend;

    public static EmailFragment newInstance() {

        Bundle args = new Bundle();

        EmailFragment fragment = new EmailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_email;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Email");
        titleBar.showHome(getBaseActivity());
        titleBar.showBackButton(getBaseActivity());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnSend, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                UIHelper.showToast(getContext(), "Email Sent!!!");
                popBackStack();
                break;
            case R.id.btnCancel:
                popBackStack();
                break;
        }
    }
}
