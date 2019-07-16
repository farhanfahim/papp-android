package com.tekrevol.papp.fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;
import com.tekrevol.papp.R;
import com.tekrevol.papp.fragments.abstracts.BaseFragment;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.receiving_model.OpenTokSessionRecModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class VideoCallFragment extends BaseFragment implements Session.SessionListener, PublisherKit.PublisherListener, SubscriberKit.SubscriberListener {


    Unbinder unbinder;
    @BindView(R.id.txtCallerName)
    AnyTextView txtCallerName;
    @BindView(R.id.publisher_container)
    FrameLayout publisherContainer;
    @BindView(R.id.imgMute)
    ImageView imgMute;
    @BindView(R.id.imgCancelCall)
    ImageView imgCancelCall;
    @BindView(R.id.txtTime)
    AnyTextView txtTime;
    @BindView(R.id.subscriber_container)
    FrameLayout subscriberContainer;
    boolean isSignalSender = false;

    private static final String LOG_TAG = "Video Call";
    @BindView(R.id.imgCameraSwitch)
    ImageView imgCameraSwitch;
    @BindView(R.id.imgPickCall)
    ImageView imgPickCall;
    @BindView(R.id.imgDeclineCall)
    ImageView imgDeclineCall;
    @BindView(R.id.contCallComingOption)
    LinearLayout contCallComingOption;
    @BindView(R.id.contCallAcceptedOptions)
    LinearLayout contCallAcceptedOptions;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private OpenTokSessionRecModel openTokSessionRecModel;


    long startTime = 0;
    String END_CALL = "101";
    String ACCEPT_CALL = "102";
    String REJECT_CALL = "103";

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();

    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hour = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            if (txtTime != null) {
                txtTime.setText(String.format("%d:%d:%02d", hour, minutes, seconds));
            }

            timerHandler.postDelayed(this, 500);
        }
    };

    public static VideoCallFragment newInstance(OpenTokSessionRecModel openTokSessionRecModel) {

        Bundle args = new Bundle();

        VideoCallFragment fragment = new VideoCallFragment();
        fragment.openTokSessionRecModel = openTokSessionRecModel;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_video_call;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {

        titleBar.resetViews();
        titleBar.setVisibility(View.GONE);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        initializeSession(openTokSessionRecModel.getApiKey(), openTokSessionRecModel.getOtkSessionId(), openTokSessionRecModel.getToken());

        txtTime.setVisibility(View.GONE);
        publisherContainer.setVisibility(View.GONE);
        imgMute.setVisibility(View.GONE);
        imgCameraSwitch.setVisibility(View.GONE);


        txtCallerName.setText(openTokSessionRecModel.getMentorName());
        ImageLoaderHelper.loadImageWithAnimations(imgProfile, openTokSessionRecModel.getMentorImage(), true);

        if (openTokSessionRecModel.isCaller()) {
            contCallAcceptedOptions.setVisibility(View.VISIBLE);
            contCallComingOption.setVisibility(View.GONE);
        } else {
            contCallAcceptedOptions.setVisibility(View.GONE);
            contCallComingOption.setVisibility(View.GONE);
        }


    }

    private void startTimer() {
        txtTime.setVisibility(View.VISIBLE);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }


    /* Activity lifecycle methods */

    @Override
    public void onPause() {

        Log.d(TAG, "onPause");

        super.onPause();

        if (mSession == null) {
            return;
        }
        mSession.onPause();

        if (getCallActivity().isFinishing()) {
            disconnectSession();
        }

    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();

        if (mSession == null) {
            return;
        }
        mSession.onResume();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");

        disconnectSession();
        timerHandler.removeCallbacks(timerRunnable);
        super.onDestroy();
    }

    @Override
    public void setListeners() {

        mSession.setSignalListener(new Session.SignalListener() {
            @Override
            public void onSignalReceived(Session session, String s, String s1, Connection connection) {

                if (isSignalSender) {
                    isSignalSender = false;
                    return;
                }
                if (s.equals(END_CALL)) {
                    Log.d(TAG, "onSignalReceived: " + "End call");
                    endCall(false);
                } else if (s.equals(ACCEPT_CALL)) {
                    startTimer();
                    publishVideo();
                } else if (s.equals(REJECT_CALL)) {
                    UIHelper.showToast(getContext(), "User is busy");
                    endCall(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initializeSession(String apiKey, String sessionId, String token) {

        mSession = new Session.Builder(getContext(), apiKey, sessionId).build();
        mSession.setSessionListener(this);
        mSession.connect(token);
    }


    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.d(LOG_TAG, "onStreamCreated: Publisher Stream Created. Own stream " + stream.getStreamId());

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

        Log.d(LOG_TAG, "onStreamDestroyed: Publisher Stream Destroyed. Own stream " + stream.getStreamId());
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

        Log.e(LOG_TAG, "onError: " + opentokError.getErrorDomain() + " : " +
                opentokError.getErrorCode() + " - " + opentokError.getMessage());
        showOpenTokError(opentokError);
    }


    @Override
    public void onConnected(Session session) {

        Log.d(LOG_TAG, "onConnected: Connected to session: " + session.getSessionId());

        if (!openTokSessionRecModel.isCaller()) {
            contCallAcceptedOptions.setVisibility(View.GONE);
            contCallComingOption.setVisibility(View.VISIBLE);
        }
    }

    private void publishVideo() {
        publisherContainer.setVisibility(View.VISIBLE);
        imgProfile.setVisibility(View.GONE);
        imgMute.setVisibility(View.VISIBLE);
        imgCameraSwitch.setVisibility(View.VISIBLE);

        // initialize Publisher and set this object to listen to Publisher events
        mPublisher = new Publisher.Builder(getContext()).build();
        mPublisher.setPublisherListener(this);

        // set publisher video style to fill view
        mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE,
                BaseVideoRenderer.STYLE_VIDEO_FILL);
        publisherContainer.addView(mPublisher.getView());

        if (mPublisher.getView() instanceof GLSurfaceView) {
            ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
        }

        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());

        mSession = null;
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.d(LOG_TAG, "onStreamReceived: New Stream Received " + stream.getStreamId() + " in session: " + session.getSessionId());

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(getContext(), stream).build();
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSubscriber.setSubscriberListener(this);
            mSession.subscribe(mSubscriber);
            subscriberContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

        Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());

        if (mSubscriber == null) {
            return;
        }

        if (mSubscriber.getStream().equals(stream)) {
            subscriberContainer.removeView(mSubscriber.getView());
            mSubscriber.destroy();
            mSubscriber = null;
        }

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "onError: " + opentokError.getErrorDomain() + " : " +
                opentokError.getErrorCode() + " - " + opentokError.getMessage() + " in session: " + session.getSessionId());

        showOpenTokError(opentokError);
    }


    @Override
    public void onConnected(SubscriberKit subscriberKit) {

        Log.d(LOG_TAG, "onConnected: Subscriber connected. Stream: " + subscriberKit.getStream().getStreamId());
    }

    @Override
    public void onDisconnected(SubscriberKit subscriberKit) {

        try {
            Log.d(LOG_TAG, "onDisconnected: Subscriber disconnected. Stream: " + subscriberKit.getStream().getStreamId());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {

        Log.e(LOG_TAG, "onError: " + opentokError.getErrorDomain() + " : " +
                opentokError.getErrorCode() + " - " + opentokError.getMessage());

        showOpenTokError(opentokError);
    }


    private void showOpenTokError(OpentokError opentokError) {
        if (getContext() == null)
            return;

//        Toast.makeText(getContext(), opentokError.getErrorDomain().name() + ": " + opentokError.getMessage() + " Please, see the logcat.", Toast.LENGTH_LONG).show();
        getCallActivity().finish();
    }

    @OnClick({R.id.imgMute, R.id.imgCancelCall, R.id.imgCameraSwitch, R.id.imgDeclineCall, R.id.imgPickCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgMute:
                if (mPublisher.getPublishAudio()) {
                    mPublisher.setPublishAudio(false);
                    imgMute.setColorFilter(getBaseActivity().getResources().getColor(R.color.base_reddish));
                } else {
                    mPublisher.setPublishAudio(true);
                    imgMute.setColorFilter(getBaseActivity().getResources().getColor(R.color.white));
                }
                break;
            case R.id.imgDeclineCall:
                rejectCall(true);
                break;
            case R.id.imgCancelCall:
                endCall(true);
                break;
            case R.id.imgCameraSwitch:
                if (mPublisher != null) {
                    mPublisher.cycleCamera();
                    mPublisher.startPreview();
                }
                break;


            case R.id.imgPickCall:
                isSignalSender = true;
                mSession.sendSignal(ACCEPT_CALL, "accept");
                contCallAcceptedOptions.setVisibility(View.VISIBLE);
                contCallComingOption.setVisibility(View.GONE);
                startTimer();
                publishVideo();


                break;
        }
    }


    private void rejectCall(boolean isSignalSender) {

        this.isSignalSender = isSignalSender;


        if (isSignalSender) {

            mSession.sendSignal(REJECT_CALL, "reject");

        }

        disconnectSession();
        getBaseActivity().finish();

    }


    private void endCall(boolean isSignalSender) {

        this.isSignalSender = isSignalSender;


        if (isSignalSender && mSession != null) {

            mSession.sendSignal(END_CALL, "disconnect", true);
        }

        disconnectSession();
        getBaseActivity().finish();


    }


    private void disconnectSession() {
        if (mSession == null) {
            return;
        }

        if (mSubscriber != null) {
            subscriberContainer.removeView(mSubscriber.getView());
            mSession.unsubscribe(mSubscriber);
            mSubscriber.destroy();
            mSubscriber = null;
        }

        if (mPublisher != null) {
            publisherContainer.removeView(mPublisher.getView());
            mSession.unpublish(mPublisher);
            mPublisher.destroy();
            mPublisher = null;
        }
        mSession.disconnect();
    }
}
