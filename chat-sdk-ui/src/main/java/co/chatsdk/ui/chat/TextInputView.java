/*
 * Created by Itzik Braun on 12/3/2015.
 * Copyright (c) 2015 deluge. All rights reserved.
 *
 * Last Modification at: 3/12/15 4:27 PM
 */

package co.chatsdk.ui.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Rect;
import androidx.appcompat.widget.AppCompatImageButton;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import co.chatsdk.core.audio.Recording;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.utils.PermissionRequestHandler;
import co.chatsdk.core.utils.StringChecker;
import co.chatsdk.ui.R;
import co.chatsdk.ui.ThirdParty.audio_recorder.AudioRecordView;
import co.chatsdk.ui.utils.InfiniteToast;
import co.chatsdk.ui.utils.ToastHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TextInputView extends LinearLayout implements View.OnKeyListener, TextView.OnEditorActionListener, AudioRecordView.RecordingListener {

    protected WeakReference<TextInputDelegate> delegate;
    private Context context;
    public AudioRecordView audioRecordView;

    public TextInputView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TextInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TextInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public void setDelegate(TextInputDelegate delegate) {
        this.delegate = new WeakReference<>(delegate);
    }

    protected void init(){
        inflate(getContext(), R.layout.chat_sdk_view_message_box, this);
    }

    protected void initViews(){
        audioRecordView = findViewById(R.id.recordingView);
        audioRecordView.activity = getActivity();
        audioRecordView.setRecordingListener(this);
    }

    protected Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();

        if (isInEditMode()) {
            return;
        }

        audioRecordView.getSendView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = audioRecordView.getMessageView().getText().toString();

                if (delegate != null) {
                    if (isNetworkAvailable()) {
                        delegate.get().onSendPressed(msg);
                        audioRecordView.getMessageView().setText("");
                    } else {
                        ToastHelper.showInCenter(context, "No Internet Available");
                    }
                }
            }
        });

        audioRecordView.getAttachmentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    showOption();
                } else {
                    ToastHelper.showInCenter(context, "No Internet Available");
                }
            }
        });
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /** Show the message option popup, From here the user can send images and location messages.*/
    public void showOption () {
        if(delegate != null) {
            delegate.get().showOptions();
        }
    }

    /** Send a text message when the done button is pressed on the keyboard.*/
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND && delegate != null) {
            delegate.get().onSendPressed(getMessageText());
        }

        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // if enter is pressed start calculating
        if(delegate != null) {
            delegate.get().startTyping();
        }
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            int editTextLineCount = ((EditText) v).getLineCount();
            if (editTextLineCount >= getResources().getInteger(R.integer.chat_sdk_max_message_lines))
                return true;
        }
        return false;
    }

    public String getMessageText(){
        return audioRecordView.getMessageView().getText().toString();
    }




    @Override
    public void onRecordingStarted() {

    }

    @Override
    public void onRecordingLocked() {

    }

    @Override
    public void onRecordingCompleted(File file) {
        if (delegate != null) {
            delegate.get().sendAudio(file);
        }
    }

    @Override
    public void onRecordingCanceled() {

    }
}
