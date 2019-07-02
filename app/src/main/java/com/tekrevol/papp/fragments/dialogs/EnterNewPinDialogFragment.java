package com.tekrevol.papp.fragments.dialogs;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tekrevol.papp.callbacks.OnSpinnerOKPressedListener;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.managers.SharedPreferenceManager;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.PinEntryEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tekrevol.papp.R;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class EnterNewPinDialogFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.txtWrongPinNumber)
    AnyTextView txtWrongPinNumber;
    @BindView(R.id.txtPinCode)
    PinEntryEditText txtPinCode;
    @BindView(R.id.txtLogout)
    AnyTextView txtLogout;
    @BindView(R.id.contLogout)
    LinearLayout contLogout;
    @BindView(R.id.txtSave)
    AnyTextView txtSave;
    @BindView(R.id.txtCancel)
    AnyTextView txtCancel;
    @BindView(R.id.contButton)
    LinearLayout contButton;


    private String Title;
    private View.OnClickListener onCanceButtonClick;
    private SharedPreferenceManager sharedPreferenceManager;
    private OnSpinnerOKPressedListener onSaveClick;


    public EnterNewPinDialogFragment() {
    }

    public static EnterNewPinDialogFragment newInstance(View.OnClickListener onCanceButtonClick,  OnSpinnerOKPressedListener onSaveClick) {
        EnterNewPinDialogFragment frag = new EnterNewPinDialogFragment();

        frag.onCanceButtonClick = onCanceButtonClick;
        frag.onSaveClick = onSaveClick;

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_enter_new_pin_dialog, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyboardHelper.showSoftKeyboardForcefully(getContext(), txtPinCode);

    }

    private void bindData() {
        txtTitle.setText(getTitle());
        contLogout.setVisibility(View.GONE);
        txtWrongPinNumber.setVisibility(View.GONE);
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtSave, R.id.txtCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtSave:
                if (txtPinCode.getText().toString().length() == 4) {
                    onSaveClick.onItemSelect(txtPinCode.getText().toString());
                    KeyboardHelper.hideSoftKeyboard(getContext(), view);
                    dismiss();
                } else {
                    txtWrongPinNumber.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.txtCancel:
                onCanceButtonClick.onClick(view);
                KeyboardHelper.hideSoftKeyboard(getContext(), view);
                dismiss();
                break;
        }
    }

    public void clearField() {
        if (txtPinCode != null) {
            txtPinCode.setText(null);
        }
    }
}

