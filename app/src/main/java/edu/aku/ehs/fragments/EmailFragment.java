package edu.aku.ehs.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnNewPacketReceivedListener;
import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.enums.BaseURLTypes;
import edu.aku.ehs.enums.SelectEmployeeActionType;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.helperclasses.ui.helper.UIHelper;
import edu.aku.ehs.managers.retrofit.GsonFactory;
import edu.aku.ehs.managers.retrofit.WebServices;
import edu.aku.ehs.models.SessionDetailModel;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.sending_model.EmailModel;
import edu.aku.ehs.models.wrappers.WebResponse;
import edu.aku.ehs.widget.AnyEditTextView;
import edu.aku.ehs.widget.TitleBar;
import mabbas007.tagsedittext.TagsEditText;

import static edu.aku.ehs.constatnts.Events.ON_EMPLOYEES_SELECTED_FOR_EMAIL;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class EmailFragment extends BaseFragment implements OnNewPacketReceivedListener {


    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    TagsEditText edtEmailAddress;
    @BindView(R.id.edtEmailSubject)
    AnyEditTextView edtEmailSubject;
    @BindView(R.id.edtEmailBody)
    AnyEditTextView edtEmailBody;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnSend)
    Button btnSend;
    SessionModel sessionModel;
    private ArrayList<SessionDetailModel> arrSessioDetailModel;
    private ArrayList<String> newTags = new ArrayList<>();

    public static EmailFragment newInstance(SessionModel sessionModel, ArrayList<SessionDetailModel> arrSessioDetailModel) {

        Bundle args = new Bundle();

        EmailFragment fragment = new EmailFragment();
        fragment.arrSessioDetailModel = arrSessioDetailModel;
        fragment.sessionModel = sessionModel;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeToNewPacket(this);

        StringBuilder employeeNames = new StringBuilder();
        for (SessionDetailModel sessionDetailModel : arrSessioDetailModel) {
            employeeNames.append(sessionDetailModel.getEmployeeName()).append("\n");
        }
        String bodyText = "Please get lab tests of the following employees:" + "\n" + employeeNames;
        edtEmailBody.setText(bodyText);
 
        if (!newTags.isEmpty()) {
            List<String> list = edtEmailAddress.getTags();
            list.addAll(newTags);

            CharSequence[] cs = list.toArray(new CharSequence[list.size()]);

            edtEmailAddress.setTags(cs);
            list.clear();
        }
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

    @OnClick({R.id.btnSend, R.id.btnCancel, R.id.imgSearchEmployees})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                EmailModel emailModel = new EmailModel();

                if (edtEmailAddress.getTags().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly write Email Address to Send Email");
                    return;
                }

                if (edtEmailSubject.getStringTrimmed().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly write Email Subject to Send Email");
                    return;
                }

                if (edtEmailBody.getStringTrimmed().isEmpty()) {
                    UIHelper.showShortToastInCenter(getContext(), "Kindly write Email Message to Send Email");
                    return;
                }


                emailModel.setReceipients(edtEmailAddress.getTags());
                emailModel.setMessage(edtEmailBody.getStringTrimmed());
                emailModel.setSubject(edtEmailSubject.getStringTrimmed());

                sendEmailCall(emailModel);

                break;
            case R.id.btnCancel:
                getBaseActivity().popBackStack();
                break;

            case R.id.imgSearchEmployees:
                getBaseActivity().addDockableFragment(SearchFragment.newInstance(SelectEmployeeActionType.SENDEMAIL, sessionModel), false);
                break;
        }
    }

    private void sendEmailCall(EmailModel emailModel) {
        new WebServices(getContext(), "", BaseURLTypes.EHS_BASE_URL, true)
                .webServiceRequestAPIAnyObject(WebServiceConstants.METHOD_EMAIL_SESSION, emailModel.toString(),
                        new WebServices.IRequestWebResponseAnyObjectCallBack() {
                            @Override
                            public void requestDataResponse(WebResponse<Object> webResponse) {
                                UIHelper.showToast(getContext(), webResponse.responseMessage);
                                getBaseActivity().popBackStack();
                            }

                            @Override
                            public void onError(Object object) {
                                if (object instanceof String) {
                                    UIHelper.showToast(getContext(), (String) object);
                                }
                            }
                        });
    }


    @Override
    public void onNewPacket(int event, Object data) {
        switch (event) {
            case ON_EMPLOYEES_SELECTED_FOR_EMAIL:

                if (data instanceof ArrayList) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    newTags = GsonFactory.getSimpleGson()
                            .fromJson(GsonFactory.getSimpleGson().toJson(data)
                                    , type);
                }

                break;
        }
    }


}
