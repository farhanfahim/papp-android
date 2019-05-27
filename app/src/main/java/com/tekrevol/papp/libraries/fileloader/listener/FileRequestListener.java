package com.tekrevol.papp.libraries.fileloader.listener;

import com.tekrevol.papp.libraries.fileloader.pojo.FileResponse;
import com.tekrevol.papp.libraries.fileloader.request.FileLoadRequest;

/**
 * Created by krishna on 12/10/17.
 */

public interface FileRequestListener<T> {
    void onLoad(FileLoadRequest request, FileResponse<T> response);

    void onError(FileLoadRequest request, Throwable t);
}
