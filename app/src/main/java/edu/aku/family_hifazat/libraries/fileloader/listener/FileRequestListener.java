package edu.aku.family_hifazat.libraries.fileloader.listener;

import edu.aku.family_hifazat.libraries.fileloader.pojo.FileResponse;
import edu.aku.family_hifazat.libraries.fileloader.request.FileLoadRequest;

/**
 * Created by krishna on 12/10/17.
 */

public interface FileRequestListener<T> {
    void onLoad(FileLoadRequest request, FileResponse<T> response);

    void onError(FileLoadRequest request, Throwable t);
}
