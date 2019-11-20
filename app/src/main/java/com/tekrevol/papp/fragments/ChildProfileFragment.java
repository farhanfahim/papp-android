package com.tekrevol.papp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.sending_model.DependantIdSendingModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.tekrevol.papp.constatnts.WebServiceConstants.PATH_MENTOR_REQUEST;

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
    @BindView(R.id.contLayout)
    LinearLayout contLayout;
    @BindView(R.id.txtAge)
    AnyTextView txtAge;
    @BindView(R.id.txtGender)
    AnyTextView txtGender;
    @BindView(R.id.txtRequestAccess)
    AnyTextView txtRequestAccess;
    @BindView(R.id.txtLocation)
    AnyTextView txtLocation;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.contPointsEarned)
    LinearLayout contPointsEarned;
    @BindView(R.id.txtGuardianTitle)
    AnyTextView txtGuardianTitle;
    @BindView(R.id.containerGuardianTitlebar1)
    LinearLayout containerGuardianTitlebar1;
    @BindView(R.id.imgGuardianProfile)
    CircleImageView imgGuardianProfile;
    @BindView(R.id.txtGuardianName)
    AnyTextView txtGuardianName;
    @BindView(R.id.txtGuardianLocation)
    AnyTextView txtGuardianLocation;
    @BindView(R.id.contGuardianLayout)
    LinearLayout contGuardianLayout;

    UserModel userModel;
    UserModel parentModel;
    @BindView(R.id.contChildLayout)
    LinearLayout contChildLayout;
    @BindView(R.id.txtGuardianEmail)
    AnyTextView txtGuardianEmail;
    @BindView(R.id.txtGuardianPhoneNo)
    AnyTextView txtGuardianPhoneNo;


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


        String fullName = userModel.getUserDetails().getFullName();

        txtName.setText(fullName);

        ImageLoaderHelper.loadImageWithAnimationsByPath(imgProfile, userModel.getUserDetails().getImage(), true);
        String childAge = userModel.getUserDetails().getDob();

        txtAge.setText(getBirthYear(childAge));

        txtGender.setText(AppConstants.getGenderString(userModel.getUserDetails().getGender()));

        String childPoints = String.valueOf(userModel.getUserDetails().getTotalPoints());
        txtPoints.setText(childPoints);

        if (userModel.getUserDetails().getAddress() == null){
            txtLocation.setText("-");
        }else{
            txtLocation.setText(userModel.getUserDetails().getAddress());
        }

        int checkAccessible = userModel.getAccessable();
        if (checkAccessible == 1 && parentModel != null) {
            contGuardianLayout.setVisibility(View.VISIBLE);
            txtRequestAccess.setVisibility(View.GONE);

            ImageLoaderHelper.loadImageWithAnimationsByPath(imgGuardianProfile, parentModel.getUserDetails().getImage(), true);

            txtGuardianName.setText(parentModel.getUserDetails().getFullName());

            txtGuardianEmail.setText(parentModel.getEmail());

            if (parentModel.getUserDetails().getAddress() == null){
                txtGuardianLocation.setText("-");
            }else{
                txtGuardianLocation.setText(parentModel.getUserDetails().getAddress());
            }

            if (parentModel.getUserDetails().getAddress() == null){
                txtGuardianPhoneNo.setText("-");
            }else{
                String phoneNo = String.valueOf(parentModel.getUserDetails().getPhone());
                txtGuardianLocation.setText(phoneNo);
            }






        } else {
            contGuardianLayout.setVisibility(View.GONE);
            txtRequestAccess.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btnLeft1, R.id.txtRequestAccess})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLeft1:
                getBaseActivity().popBackStack();
                break;
            case R.id.txtRequestAccess:
                sendDependantId();
                //Toast.makeText(getContext(), "access request sent", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendDependantId() {


        DependantIdSendingModel dependantIdSendingModel = new DependantIdSendingModel();
        dependantIdSendingModel.setDependent_id(userModel.getId());

        getBaseWebService().postAPIAnyObject(PATH_MENTOR_REQUEST, dependantIdSendingModel.toString(), new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {
                UIHelper.showToast(getContext(), webResponse.message);
                getBaseActivity().popBackStack();
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    private String getBirthYear(String dob) {

        String[] parts = dob.split("-");

        String month = parts[1];
        String year = parts[0];
        String day = parts[2];

        int dd = Integer.parseInt(day);
        int mm = Integer.parseInt(month);
        int yy = Integer.parseInt(year);

        LocalDate birthDate = new LocalDate(yy, mm, dd);
        LocalDate currentDate = new LocalDate();

        return Years.yearsBetween(birthDate, currentDate).getYears() + " Years";
    }


}
