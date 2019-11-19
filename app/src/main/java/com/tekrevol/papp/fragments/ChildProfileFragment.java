package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.chatsdk.core.dao.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChildProfileFragment extends BaseFragment implements OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.btnLeft1)
    TextView btnLeft1;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.containerTitlebar1)
    LinearLayout containerTitlebar1;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtDesignation)
    AnyTextView txtDesignation;
    @BindView(R.id.contLayout)
    LinearLayout contLayout;
    @BindView(R.id.txtAge)
    AnyTextView txtAge;
    @BindView(R.id.txtGender)
    AnyTextView txtGender;
    @BindView(R.id.txtRequestAccess)
    AnyTextView txtRequestAccess;

    UserModel userModel;
    UserModel parentModel;


    public static ChildProfileFragment newInstance(UserModel userModel, UserModel parentModel) {

        Bundle args = new Bundle();

        ChildProfileFragment fragment = new ChildProfileFragment();
        fragment.userModel = userModel;
        fragment.parentModel = parentModel;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(int position, Object object, View view, Object type) {

    }

    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_chlid_profile;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.setVisibility(View.GONE);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String firstName = userModel.getUserDetails().getFirstName();
        String lastName = userModel.getUserDetails().getLastName();

        String childName = firstName+" "+lastName;

        txtName.setText(childName);

        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, userModel.getUserDetails().getImage(), true);
        String childAge = userModel.getUserDetails().getDob();

        txtAge.setText(childAge);

        txtGender.setText(AppConstants.getGenderString(userModel.getUserDetails().getGender()));





    }

    @OnClick({R.id.btnLeft1, R.id.txtRequestAccess})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLeft1:
                getBaseActivity().popBackStack();
                break;
            case R.id.txtRequestAccess:
        }
    }
}
