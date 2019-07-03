package co.chatsdk.ui.ThirdParty.audio_recorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.Random;

import co.chatsdk.core.dao.DaoCore;
import co.chatsdk.core.dao.Keys;

public class AudioRecorder {

    MediaRecorder recorder = new MediaRecorder();
    public String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return new File(path);
    }

    public AudioRecorder() {

    }

    private String getPath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path +=  "_audio.mp3";
        }

        File rootPath = new File(Environment.getExternalStorageDirectory(), "Chat_SDK");
        File audioFolder = new File(rootPath, "Audio");

        if(!audioFolder.exists()) {
            audioFolder.mkdirs();
        }

        return new File(audioFolder,path).getAbsolutePath();
    }

    boolean isStarted = false;
    public void start() {
        this.path = getPath("files%2F" + DaoCore.generateRandomName());

        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.e("audioRecorder", "SD Card is not mounted.  It is " + state + ".");
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("audioRecorder", "Path to file could not be created.");
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path);
        try {
            recorder.prepare();
            recorder.start();
            isStarted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            recorder.stop();
            recorder.reset();
            recorder.release();
        } catch (Exception e) {

        }
    }


}