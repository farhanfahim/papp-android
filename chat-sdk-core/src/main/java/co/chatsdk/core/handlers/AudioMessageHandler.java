package co.chatsdk.core.handlers;

import java.io.File;

import co.chatsdk.core.audio.Recording;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.interfaces.MessageDisplayHandler;
import co.chatsdk.core.types.MessageSendProgress;
import io.reactivex.Observable;

/**
 * Created by SimonSmiley-Andrews on 01/05/2017.
 */

public interface AudioMessageHandler {

    /**
     * Send an audio message
     */
    Observable<MessageSendProgress> sendMessage (File file, final Thread thread);

}
