package com.tekrevol.papp.fragments.dialogs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnLinkAdd;
import com.tekrevol.papp.helperclasses.ui.helper.KeyboardHelper;
import com.tekrevol.papp.widget.AnyEditTextView;
import com.tekrevol.papp.widget.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by khanhamza on 21-Feb-17.
 */

public class HyperLinkDialogFragment extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.txtTitle)
    AnyTextView txtTitle;
    @BindView(R.id.edtLink)
    AnyEditTextView edtLink;
    @BindView(R.id.txtSave)
    AnyTextView txtSave;
    @BindView(R.id.txtCancel)
    AnyTextView txtCancel;
    @BindView(R.id.contButton)
    LinearLayout contButton;
    @BindView(R.id.edtTitle)
    AnyEditTextView edtTitle;

    private OnLinkAdd onSaveClick;


    public HyperLinkDialogFragment() {
    }

    public static HyperLinkDialogFragment newInstance(OnLinkAdd onSaveClick) {
        HyperLinkDialogFragment frag = new HyperLinkDialogFragment();

        frag.onSaveClick = onSaveClick;
        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hyperlink_popup, container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        KeyboardHelper.showSoftKeyboard(getContext(), edtTitle);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onResume() {
        super.onResume();
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
                if (edtTitle.testValidity() && edtLink.testValidity()) {
                    this.dismiss();
                    String linkURL = edtLink.getStringTrimmed();
                    if (URLUtil.isValidUrl(linkURL) && URLUtil.isNetworkUrl(linkURL)) {
                        onSaveClick.onItemAdd(edtTitle.getStringTrimmed(), linkURL);
                    } else {
                        linkURL = "https://" + linkURL;
                        onSaveClick.onItemAdd(edtTitle.getStringTrimmed(), linkURL);
                    }
                }
                break;

            case R.id.txtCancel:
                this.dismiss();
                break;
        }
    }
}

