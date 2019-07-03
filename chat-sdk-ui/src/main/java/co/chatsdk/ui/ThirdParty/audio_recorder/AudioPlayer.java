package co.chatsdk.ui.ThirdParty.audio_recorder;

import android.media.MediaPlayer;

public class AudioPlayer {
    private String messageId;
    private static MediaPlayer mp = new MediaPlayer();
    private static final AudioPlayer ourInstance = new AudioPlayer();


    public static AudioPlayer getInstance() {
        return ourInstance;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private AudioPlayer() {
    }

    public MediaPlayer getPlayer() {
        return mp;
    }
}
