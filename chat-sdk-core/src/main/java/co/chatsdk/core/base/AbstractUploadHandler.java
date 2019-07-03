package co.chatsdk.core.base;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import co.chatsdk.core.dao.DaoCore;
import co.chatsdk.core.handlers.UploadHandler;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.types.FileUploadResult;
import co.chatsdk.core.utils.ImageUtils;
import io.reactivex.Observable;

/**
 * Created by benjaminsmiley-andrews on 24/05/2017.
 */

public abstract class AbstractUploadHandler implements UploadHandler {

    public Observable<FileUploadResult> uploadImage(final Bitmap image) {
        return ChatSDK.upload().uploadFile(ImageUtils.getImageByteArray(image), "image.jpg", "image/jpeg");
    }

    @Override
    public Observable<FileUploadResult> uploadFile(File file) {
        return ChatSDK.upload().uploadFile(file.getName().replace("files%2F",""),convertFileToByteArray(file), "audio/mpeg");
    }

    public String getUUID() {
        return DaoCore.generateRandomName();
    }

    public byte[] convertFileToByteArray(File file) {

        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char)b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }

        return b;
    }
}
