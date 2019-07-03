package co.chatsdk.firebase.push;

import com.google.firebase.messaging.FirebaseMessagingService;

import timber.log.Timber;

/**
 * Created by ben on 9/1/17.
 */

public class InstanceIdService extends FirebaseMessagingService {

    public interface TokenChangeListener {
        void updated (String token);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Timber.v("Refreshed token: " + s);
        TokenChangeConnector.shared().updated(s);
    }
}
