package co.chatsdk.ui.chat;

import java.io.File;

import co.chatsdk.core.audio.Recording;

/**
 * Created by ben on 10/11/17.
 */

public interface TextInputDelegate {

    void showOptions ();
    void hideOptions ();
    void onSendPressed(String text);
    void startTyping();
    void sendAudio (File file);
    void stopTyping();
    void onKeyboardShow ();
    void onKeyboardHide ();

}
