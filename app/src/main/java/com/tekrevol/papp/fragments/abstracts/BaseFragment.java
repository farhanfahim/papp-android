package com.tekrevol.papp.fragments.abstracts;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tekrevol.papp.BaseApplication;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.activities.MainActivity;
import com.tekrevol.papp.callbacks.GenericClickableInterface;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.residemenu.ResideMenu;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.managers.FileManager;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.managers.retrofit.WebServices;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.UserModelWrapper;
import com.tekrevol.papp.models.wrappers.WebResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.tekrevol.papp.R;
import com.tekrevol.papp.activities.BaseActivity;
import com.tekrevol.papp.callbacks.OnNewPacketReceivedListener;
import com.tekrevol.papp.widget.TitleBar;
import com.google.gson.Gson;
import com.tekrevol.papp.BaseApplication;
import com.tekrevol.papp.activities.BaseActivity;
import com.tekrevol.papp.activities.HomeActivity;
import com.tekrevol.papp.activities.MainActivity;
import com.tekrevol.papp.constatnts.AppConstants;
import com.tekrevol.papp.enums.BaseURLTypes;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.residemenu.ResideMenu;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.tekrevol.papp.widget.TitleBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by khanhamza on 10-Feb-17.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, OnNewPacketReceivedListener {

    protected View view;
    public SharedPreferenceManager sharedPreferenceManager;
    public String TAG = "Logging Tag";
    public boolean onCreated = false;
    Disposable subscription;


    /**
     * This is an abstract class, we should inherit our fragment from this class
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getContext());
        onCreated = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentLayout(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBaseActivity().getTitleBar().resetViews();
        getBaseActivity().getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);   // Default Locked in this project
        getBaseActivity().getDrawerLayout().closeDrawer(Gravity.START);

        subscribeToNewPacket(this);

    }


    public boolean isMentor() {
        return sharedPreferenceManager.getBoolean(AppConstants.KEY_IS_MENTOR);
    }

    public UserModel getCurrentUser() {
        return sharedPreferenceManager.getCurrentUser();
    }

    public void setCurrentUser(UserModel user) {
        sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, user);
    }

    public String getToken() {
        return sharedPreferenceManager.getString(AppConstants.KEY_TOKEN);
    }

    public abstract int getDrawerLockMode();


    // Use  UIHelper.showSpinnerDialog
    @Deprecated
    public void setSpinner(ArrayAdapter adaptSpinner, final TextView textView, final Spinner spinner) {
        if (adaptSpinner == null || spinner == null)
            return;
        //selected item will look like a spinner set from XML
//        simple_list_item_single_choice
        adaptSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adaptSpinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = spinner.getItemAtPosition(position).toString();
                if (textView != null)
                    textView.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    protected abstract int getFragmentLayout();

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public HomeActivity getHomeActivity() {
        return (HomeActivity) getActivity();
    }

    public abstract void setTitlebar(TitleBar titleBar);


    public abstract void setListeners();

    @Override
    public void onResume() {
        super.onResume();
        onCreated = true;
        setListeners();

        if (getBaseActivity() != null) {
            setTitlebar(getBaseActivity().getTitleBar());
        }

        if (getBaseActivity() != null && getBaseActivity().getWindow().getDecorView() != null) {
            KeyboardHelper.hideSoftKeyboard(getBaseActivity(), getBaseActivity().getWindow().getDecorView());
        }

    }

    @Override
    public void onPause() {

        if (getBaseActivity() != null && getBaseActivity().getWindow().getDecorView() != null) {
            KeyboardHelper.hideSoftKeyboard(getBaseActivity(), getBaseActivity().getWindow().getDecorView());
        }

        super.onPause();

    }


    public void notifyToAll(int event, Object data) {
        BaseApplication.getPublishSubject().onNext(new Pair<>(event, data));
    }

    protected void subscribeToNewPacket(final OnNewPacketReceivedListener newPacketReceivedListener) {
        subscription = BaseApplication.getPublishSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pair>() {
                    @Override
                    public void accept(@NonNull Pair pair) throws Exception {
                        Log.e("abc", "on accept");
                        newPacketReceivedListener.onNewPacket((int) pair.first, pair.second);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("abc", "onDestroyView");
        if (subscription != null)
            subscription.dispose();
    }


    public void showNextBuildToast() {
        UIHelper.showToast(getContext(), "This feature is in progress");
    }

    public void showAPIRemainingToast() {
        UIHelper.showToast(getContext(), "API Remaining");
    }


    public Gson getGson() {
        return getBaseActivity().getGson();
    }


    @android.support.annotation.NonNull
    public WebServices getBaseWebService() {
        return new WebServices(getBaseActivity(), getToken(), BaseURLTypes.BASE_URL, true);
    }

    public void saveAndOpenFile(WebResponse<String> webResponse) {
        String fileName = AppConstants.FILE_NAME + DateManager.getTime(DateManager.getCurrentMillis()) + ".pdf";

        String path = FileManager.writeResponseBodyToDisk(getContext(), webResponse.result, fileName, AppConstants.getUserFolderPath(getContext()), true, true);

//                                final File file = new File(AppConstants.getUserFolderPath(getContext())
//                                        + "/" + fileName + ".pdf");
        final File file = new File(path);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FileManager.openFile(getContext(), file);
            }
        }, 100);
    }

    @Override
    public void onNewPacket(int event, Object data) {
        switch (event) {

        }
    }


    public ResideMenu getResideMenu() {
        return getHomeActivity().getResideMenu();
    }


    // FOR RESIDE MENU
    public void closeMenu() {
        getHomeActivity().getResideMenu().closeMenu();
    }


    public static void logoutClick(final BaseFragment baseFragment) {
        Context context = baseFragment.getContext();

//        new iOSDialogBuilder(context)
//                .setTitle(context.getString(R.string.logout))
//                .setSubtitle(context.getString(R.string.areYouSureToLogout))
//                .setBoldPositiveLabel(false)
//                .setCancelable(false)
//                .setPositiveListener(context.getString(R.string.yes), dialog -> {
//                    dialog.dismiss();
//                    baseFragment.sharedPreferenceManager.clearDB();
//                    baseFragment.getBaseActivity().clearAllActivitiesExceptThis(MainActivity.class);
//                })
//                .setNegativeListener(context.getString(R.string.no), dialog -> dialog.dismiss())
//                .build().show();


        final GenericDialogFragment genericDialogFragment = GenericDialogFragment.newInstance();

        genericDialogFragment.setTitle("Logout");
        genericDialogFragment.setMessage(context.getString(R.string.areYouSureToLogout));
        genericDialogFragment.setButton1("Yes", new GenericClickableInterface() {
            @Override
            public void click() {
                genericDialogFragment.dismiss();
                baseFragment.sharedPreferenceManager.clearDB();
                baseFragment.getBaseActivity().clearAllActivitiesExceptThis(MainActivity.class);
            }
        });

        genericDialogFragment.setButton2("No", new GenericClickableInterface() {
            @Override
            public void click() {
                genericDialogFragment.getDialog().dismiss();
            }
        });
        genericDialogFragment.show(baseFragment.getBaseActivity().getSupportFragmentManager(), null);


    }


    public void updateUser(WebServices.IRequestWebResponseAnyObjectCallBack iRequestWebResponseAnyObjectCallBack) {
        Map<String, Object> queryMap = new HashMap<>();


        getBaseWebService().getAPIAnyObject(WebServiceConstants.PATH_GET_USERS + "/" + getCurrentUser().getId(), queryMap, new WebServices.IRequestWebResponseAnyObjectCallBack() {
            @Override
            public void requestDataResponse(WebResponse<Object> webResponse) {

                UserModel userModel = getGson().fromJson(getGson().toJson(webResponse.result), UserModel.class);


                userModel.setTokenType(getCurrentUser().getTokenType());
                userModel.setAccessToken(getCurrentUser().getAccessToken());
                userModel.setExpiresIn(getCurrentUser().getExpiresIn());

                sharedPreferenceManager.putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModel);

                if (iRequestWebResponseAnyObjectCallBack != null) {
                    iRequestWebResponseAnyObjectCallBack.requestDataResponse(webResponse);
                }

            }

            @Override
            public void onError(Object object) {
                if (iRequestWebResponseAnyObjectCallBack != null) {
                    iRequestWebResponseAnyObjectCallBack.onError(object);
                }
            }
        });

    }
}
