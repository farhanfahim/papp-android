package com.tekrevol.papp.fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.tekrevol.papp.models.receiving_model.OpenTokSessionRecModel;
import com.tekrevol.papp.widget.AnyTextView;
import com.tekrevol.papp.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    private static final String LOG_TAG = "Audio Call";
    @BindView(R.id.imgCameraSwitch)
    ImageView imgCameraSwitch;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private OpenTokSessionRecModel openTokSessionRecModel;

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

    }


    /* Activity lifecycle methods */

    @Override
    public void onPause() {

        Log.d(LOG_TAG, "onPause");


        if (mSession != null) {
            mSession.onPause();
        }

        super.onPause();


    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");

        super.onResume();

        if (mSession != null) {
            mSession.onResume();
        }
    }


    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");

        if (mSession != null) {
            mSession.disconnect();
        }
        super.onDestroy();


    }

    @Override
    public void setListeners() {

        mSession.setSignalListener(new Session.SignalListener() {
            @Override
            public void onSignalReceived(Session session, String s, String s1, Connection connection) {
                if (s.equals("101")) {
                    Log.d(TAG, "onSignalReceived: " + "End call");
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


    @OnClick(R.id.imgCancelCall)
    public void onViewClicked() {

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
        Log.d(LOG_TAG, "onDisconnected: Disconnected from session: " + session.getSessionId());

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

        Log.d(LOG_TAG, "onStreamDropped: Stream Dropped: " + stream.getStreamId() + " in session: " + session.getSessionId());

        if (mSubscriber != null) {
            mSubscriber = null;
            subscriberContainer.removeAllViews();
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

        Toast.makeText(getContext(), opentokError.getErrorDomain().name() + ": " + opentokError.getMessage() + " Please, see the logcat.", Toast.LENGTH_LONG).show();
        getCallActivity().finish();
    }

    @OnClick({R.id.imgMute, R.id.imgCancelCall, R.id.imgCameraSwitch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgMute:
                break;
            case R.id.imgCancelCall:
                endCall();
                break;
            case R.id.imgCameraSwitch:
                if (mPublisher != null) {
                    mPublisher.cycleCamera();
                    mPublisher.startPreview();
                }
                break;
        }
    }



    public void endCall() {


        mSession.sendSignal("101", "disconnect");
        if (mPublisher != null) {
            mPublisher.destroy();
        }




        getCallActivity().finish();
    }
}
