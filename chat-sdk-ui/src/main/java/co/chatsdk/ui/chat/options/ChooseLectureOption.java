package co.chatsdk.ui.chat.options;

import android.widget.Toast;

import co.chatsdk.core.rx.ObservableConnector;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.ChatOptionType;
import co.chatsdk.core.types.MessageSendProgress;
import co.chatsdk.core.utils.ActivityResultPushSubjectHolder;
import co.chatsdk.core.utils.PermissionRequestHandler;
import co.chatsdk.core.utils.StringChecker;
import co.chatsdk.ui.chat.MediaSelector;
import co.chatsdk.ui.utils.ToastHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by ben on 10/11/17.
 */

public class ChooseLectureOption extends BaseChatOption {

    public ChooseLectureOption() {
        super("Choose Lecture",null, null,  ChatOptionType.ChooseLecture);
    }

}
