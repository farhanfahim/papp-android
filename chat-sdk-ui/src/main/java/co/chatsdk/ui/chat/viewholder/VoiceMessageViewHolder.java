package co.chatsdk.ui.chat.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import co.chatsdk.core.dao.Keys;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.MessageMetaValue;
import co.chatsdk.core.types.MessageSendStatus;
import co.chatsdk.ui.ThirdParty.audio_recorder.AudioPlayer;
import co.chatsdk.ui.chat.BaseMessageViewHolder;
import co.chatsdk.ui.chat.ChatActivity;
import co.chatsdk.ui.chat.MessageListItem;

public class VoiceMessageViewHolder extends BaseMessageViewHolder implements MediaPlayer.OnCompletionListener {

    private Handler handler;
    private ChatActivity activity;

    public VoiceMessageViewHolder(View itemView, Activity activity, Handler handler) {
        super(itemView, activity);
        this.handler = handler;
        this.activity = (ChatActivity) activity;
    }

    @Override
    public void setMessage(Message message) {
        super.setMessage(message);
        voiceProgressBar.setVisibility(View.GONE);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioPlayer.getInstance().getPlayer().pause();
                btnplay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getEntityID().equals(AudioPlayer.getInstance().getMessageId())) {
                    AudioPlayer.getInstance().getPlayer().start();
                    showPauseButton();
                } else {
                    if(message.getMetaValues().size() > 0) {
                        String path = null;
                        for(MessageMetaValue item: message.getMetaValues()) {
                            if(item.getKey().equals(Keys.MessageAudioURL)) {
                              path = item.getValue();
                            }
                        }

                        if(path == null) {
                            return;
                        }

                        String fileName = new File(path).getName();
                        if(fileName.contains("?")) {
                            if(!playRecording(message.getEntityID(), fileName.substring(0,  fileName.indexOf("?")))) {
                                downloadFile(path,fileName.substring(0,  fileName.indexOf("?")));
                            }
                        } else {
                            if(!playRecording(message.getEntityID(),fileName)) {
                                downloadFile(path,fileName);
                            }

                        }
                    }
                }
            }
        });

        btn_view_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getMetaValues().size() > 0) {
                    String path = null;
                    for(MessageMetaValue item: message.getMetaValues()) {
                        if(item.getKey().equals(Keys.MessageAudioURL)) {
                            path = item.getValue();
                        }
                    }

                    if(path == null) {
                        return;
                    }

                    String fileName = new File(path).getName();
                    if(fileName.contains("?")) {
                        File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
                        File audioFolder = new File(rootPath, "Audio");
                        File audioFile = new File(audioFolder.getAbsolutePath() + "/" + fileName.substring(0,  fileName.indexOf("?")));

                        Intent intent = new Intent("play_lecture");
                        // You can also include some extra data.
                        intent.putExtra("path", audioFile.getAbsolutePath());
                        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
                    }

                }



            }
        });

        if(message.getEntityID().equals(AudioPlayer.getInstance().getMessageId())) {
            setAudioCompletionListener();
            setSeekBarListener(message.getEntityID());
        }
        setProgress(message);
        setBubbleHidden(false);
        setTextHidden(true);
        setVoiceMessage(false);
        setAudioDuration(message);
    }

    private void showPlayButton() {
        btnplay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.INVISIBLE);
    }

    private void showPauseButton() {
        btnplay.setVisibility(View.INVISIBLE);
        btnPause.setVisibility(View.VISIBLE);
    }

    private String getFileName(Message message) {
        if( message.getMetaValues().size() > 0) {
            String path = null;
            for(MessageMetaValue item: message.getMetaValues()) {
                if(item.getKey().equals(Keys.MessageAudioURL)) {
                    path = item.getValue();
                }
            }

            if(path == null) {
                return "";
            }

            String fileName = new File(path).getName();
            if(fileName.contains("?")) {
                return fileName.substring(0,  fileName.indexOf("?"));
            } else {
                return fileName;
            }
        }

        return "";
    }

    private void setAudioDuration(Message message) {

        if(message.getMetaValues() == null || message.getMetaValues().size() < 1) {
            btnplay.setVisibility(View.INVISIBLE);
            btnPause.setVisibility(View.INVISIBLE);
            return;
        }

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
        File audioFolder = new File(rootPath, "Audio");
        File audioFile = new File(audioFolder.getAbsolutePath() + "/" + getFileName(message) );

        if(audioFile.exists()) {
            Uri uri = Uri.parse(audioFile.toURI().toString());
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(activity,uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if(durationStr != null) {
                int milliSeconds = Integer.parseInt(durationStr);
                tvDuration.setText(formatTime(milliSeconds));
            }
        }
    }

    public String formatTime(long millis) {
        long secs = millis / 1000;
        return String.format("%02d:%02d", secs / 60, secs % 60);
    }

    private void setAudioCompletionListener() {
       AudioPlayer.getInstance().getPlayer().setOnCompletionListener(this);
    }

    private void setProgress(Message message) {
        if(message.getEntityID().equals(AudioPlayer.getInstance().getMessageId())) {
            if(AudioPlayer.getInstance().getPlayer().isPlaying()) {
                btnplay.setVisibility(View.INVISIBLE);
                btnPause.setVisibility(View.VISIBLE);
            } else {
                MediaPlayer mp = AudioPlayer.getInstance().getPlayer();
                if(mp.getCurrentPosition() == mp.getDuration()) {
                    seekbar.setProgress(0);
                }
                btnplay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.INVISIBLE);
            }
            seekbar.setMax(AudioPlayer.getInstance().getPlayer().getDuration());
            seekbar.setProgress(AudioPlayer.getInstance().getPlayer().getCurrentPosition());
            setProgressUpdateListener();
        } else {
            seekbar.setProgress(0);
            btnplay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.INVISIBLE);
        }
    }

    private void downloadFile(String path, String name) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(path);

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
        File audioFolder = new File(rootPath, "Audio");

        if(!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        final File localFile = new File(audioFolder,name);
        voiceProgressBar.setVisibility(View.VISIBLE);
        btnplay.setVisibility(View.INVISIBLE);
        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                voiceProgressBar.setVisibility(View.GONE);
                btnplay.setVisibility(View.VISIBLE);

                Log.e("firebase ",";local tem file created  created " +localFile.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                voiceProgressBar.setVisibility(View.GONE);
                btnplay.setVisibility(View.VISIBLE);
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }

    public boolean playRecording(String messageId ,String name)  { // return boolean file exists or not
        File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
        File audioFolder = new File(rootPath, "Audio");
        File audioFile = new File(audioFolder.getAbsolutePath() + "/" + name );

        if(audioFile.exists()) {
            MediaPlayer mp = AudioPlayer.getInstance().getPlayer();
            mp.setOnCompletionListener(this);
            try {
                mp.reset();
                mp.setDataSource(audioFile.getAbsolutePath());
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
            mp.setVolume(100, 100);
            btnplay.setVisibility(View.INVISIBLE);
            btnPause.setVisibility(View.VISIBLE);
            seekbar.setMax(mp.getDuration());
            seekbar.setTag(messageId);
            setProgressUpdateListener();
            setSeekBarListener(message.getEntityID());
            String lastMessageId = AudioPlayer.getInstance().getMessageId();
            if(lastMessageId != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(activity.recyclerView.findViewHolderForAdapterPosition(getPositionByMessageId(lastMessageId)) instanceof VoiceMessageViewHolder) {
                            VoiceMessageViewHolder voiceViewHolder = ((VoiceMessageViewHolder) activity.recyclerView.findViewHolderForAdapterPosition(getPositionByMessageId(lastMessageId)));
                            if(voiceViewHolder != null) {
                                voiceViewHolder.showPlayButton();
                                voiceViewHolder.seekbar.setProgress(0);
                            }
                        }
                    }
                });
            }
            AudioPlayer.getInstance().setMessageId(messageId);
        } else {
            return false;
        }

        return true;
    }

    private int getPositionByMessageId(String messageId) {
        int position = 0;
        for (MessageListItem item : activity.messageListAdapter.getMessageItems()) {
            if(item.getMessage().getEntityID().equals(messageId)) {
                return position;
            }
            position++;
        }

        return position;
    }

    private void setProgressUpdateListener() {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,1000);
                int position = AudioPlayer.getInstance().getPlayer().getCurrentPosition();
                tvDuration.setText(formatTime(position));
                seekbar.setProgress(position);
            }
        },1000);
    }

    private void setSeekBarListener(String messageId) {
        seekbar.setTag(messageId);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && seekBar.getTag()!= null && seekBar.getTag().equals(AudioPlayer.getInstance().getMessageId())) {
                    AudioPlayer.getInstance().getPlayer().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        tvDuration.setText(formatTime(mp.getCurrentPosition()));
        AudioPlayer.getInstance().setMessageId("");
        handler.removeCallbacksAndMessages(null);
        seekbar.setProgress(0);
        btnplay.setVisibility(View.VISIBLE);
        btnPause.setVisibility(View.INVISIBLE);
    }
}
