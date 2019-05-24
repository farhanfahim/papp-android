package com.android.papp.managers.retrofit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.papp.managers.FileManager;
import com.android.papp.managers.retrofit.entities.MultiFileModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.android.papp.enums.BaseURLTypes;
import com.android.papp.enums.FileType;
import com.android.papp.helperclasses.Helper;
import com.android.papp.helperclasses.ui.helper.UIHelper;
import com.android.papp.models.wrappers.WebResponse;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.papp.constatnts.WebServiceConstants.PARAMS_TOKEN_BLACKLIST;
import static com.android.papp.constatnts.WebServiceConstants.PARAMS_TOKEN_EXPIRE;

/**
 * Created by hamzakhan on 6/30/2017.
 */

public class WebServices {
    private WebServiceProxy apiService;
    private KProgressHUD mDialog;
    private Context mContext;

    public WebServices(Context activity, String token, BaseURLTypes baseURLTypes) {
        switch (baseURLTypes) {

            case BASE_URL:
                apiService = WebServiceFactory.getInstanceBaseURL(token);
                break;
            case XML_URL:
                apiService = WebServiceFactory.getInstanceXML();
        }


        mContext = activity;
        mDialog = UIHelper.getProgressHUD(mContext);
        if (!((Activity) mContext).isFinishing())
            mDialog.show();
    }

    public WebServices(Context activity, String token, BaseURLTypes baseURLTypes, boolean isShowLoader) {
        switch (baseURLTypes) {
            case BASE_URL:
                apiService = WebServiceFactory.getInstanceBaseURL(token);
                break;
            case XML_URL:
                apiService = WebServiceFactory.getInstanceXML();
        }

        mContext = activity;

        if (isShowLoader) {
            mDialog = UIHelper.getProgressHUD(mContext);

            if (!((Activity) mContext).isFinishing())
                mDialog.show();
        }

    }


    private static boolean IsResponseError(Response<WebResponse<Object>> response) {
        return !(response != null && !response.isSuccessful() && response.errorBody() != null);
    }


    private boolean hasValidStatus(Response<WebResponse<Object>> response) {
        if (response != null && response.body() != null) {
            return response.body().isSuccess();
        } else {
            return false;
        }
    }


    /**
     * TO UPLOAD FILE
     *
     * @param multiFileModelArrayList
     * @param jsonStringBody
     * @param callBack
     */

    public void webServiceUploadFileAPI(String path, ArrayList<MultiFileModel> multiFileModelArrayList, String jsonStringBody,
                                        final IRequestWebResponseAnyObjectCallBack callBack) {

        ArrayList<MultipartBody.Part> partArrayList = new ArrayList<>();


        if (jsonStringBody != null && !jsonStringBody.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStringBody);
                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = jsonObject.getString(key);
                    partArrayList.add(MultipartBody.Part.createFormData(key, value));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (multiFileModelArrayList != null && !multiFileModelArrayList.isEmpty()) {
            for (MultiFileModel multiFileModel : multiFileModelArrayList) {
                if (multiFileModel.getFile() == null || !multiFileModel.getFile().exists()) {
                    dismissDialog();
                    UIHelper.showShortToastInCenter(mContext, "File is empty.");
                    return;
                }

                MultipartBody.Part multipart = getMultipart(multiFileModel.getFileType(), multiFileModel.getFile(), multiFileModel.getKeyName());
                partArrayList.add(multipart);
            }
        }


        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                apiService.postMultipartAPI(path, partArrayList).enqueue(
                        new Callback<WebResponse<Object>>() {
                            @Override
                            public void onResponse(Call<WebResponse<Object>> call, Response<WebResponse<Object>> response) {
                                dismissDialog();
                                if (!IsResponseError(response)) {
                                    String errorBody;
                                    try {
                                        errorBody = response.errorBody().string();
                                        UIHelper.showShortToastInCenter(mContext, errorBody);
                                        callBack.onError("");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }

                                if (hasValidStatus(response)) {
                                    if (callBack != null)
                                        callBack.requestDataResponse(response.body());
                                } else {
                                    callBack.onError(errorToastForObject(response));
                                }
                            }

                            @Override
                            public void onFailure(Call<WebResponse<Object>> call, Throwable t) {
                                UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                                dismissDialog();
                                callBack.onError("");
                            }
                        });
            } else {
                dismissDialog();
                callBack.onError("Internet Error");
            }

        } catch (
                Exception e) {
            dismissDialog();
            e.printStackTrace();

        }
    }


    public Call<WebResponse<Object>> postAPIAnyObject(String path, String requestData, final IRequestWebResponseAnyObjectCallBack callBack) {
        RequestBody bodyRequestData = getRequestBody(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestData);

//        MultipartBody.Part multipartBody = MultipartBody.Part.create(bodyRequestData);

        Call<WebResponse<Object>> webResponseCall = apiService.postAPIWebResponseAnyObject(path, bodyRequestData);

        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                webResponseCall.enqueue(new Callback<WebResponse<Object>>() {
                    @Override
                    public void onResponse(Call<WebResponse<Object>> call, Response<WebResponse<Object>> response) {
                        dismissDialog();

                        if (response.body() == null) {
                            callBack.onError("empty response");
                            return;
                        }

                        if (response.isSuccessful() && response.body().isSuccess()) {
                            if (callBack != null)
                                callBack.requestDataResponse(response.body());
                        } else if (response.code() == PARAMS_TOKEN_EXPIRE) {
                            // FIXME: 2019-05-22 EXPIRE LOGIC
                            UIHelper.showToast(mContext, "TOKEN EXPIRE");
                        } else if (response.code() == PARAMS_TOKEN_BLACKLIST) {
                            // FIXME: 2019-05-22 LOGOUT LOGIC
                            UIHelper.showToast(mContext, "BLACK LIST");
                        } else {
                            callBack.onError(errorToastForObject(response));
                        }
                    }

                    @Override
                    public void onFailure(Call<WebResponse<Object>> call, Throwable t) {
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();
                        callBack.onError("");
                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

        return webResponseCall;
    }


    public Call<WebResponse<Object>> getAPIAnyObject(String path, int limit, int offset, final IRequestWebResponseAnyObjectCallBack callBack) {

        Call<WebResponse<Object>> webResponseCall = apiService.getAPIForWebresponseAnyObject(path, limit, offset);

        try {
            if (Helper.isNetworkConnected(mContext, true)) {
                webResponseCall.enqueue(new Callback<WebResponse<Object>>() {
                    @Override
                    public void onResponse(Call<WebResponse<Object>> call, Response<WebResponse<Object>> response) {
                        dismissDialog();

                        if (response.body() == null) {
                            callBack.onError("empty response");
                            return;
                        }

                        if (response.isSuccessful() && response.body().isSuccess()) {
                            if (callBack != null)
                                callBack.requestDataResponse(response.body());
                        } else if (response.code() == PARAMS_TOKEN_EXPIRE) {
                            // FIXME: 2019-05-22 EXPIRE LOGIC
                            UIHelper.showToast(mContext, "TOKEN EXPIRE");
                        } else if (response.code() == PARAMS_TOKEN_BLACKLIST) {
                            // FIXME: 2019-05-22 LOGOUT LOGIC
                            UIHelper.showToast(mContext, "TOKEN BLACK LIST");
                        } else {
                            callBack.onError(errorToastForObject(response));
                        }
                    }

                    @Override
                    public void onFailure(Call<WebResponse<Object>> call, Throwable t) {
                        UIHelper.showShortToastInCenter(mContext, "Something went wrong, Please check your internet connection.");
                        dismissDialog();
                        callBack.onError("");
                    }
                });
            } else {
                dismissDialog();
            }

        } catch (Exception e) {
            dismissDialog();
            e.printStackTrace();

        }

        return webResponseCall;
    }




    @NonNull
    public static MultipartBody.Part getMultipart(FileType fileType, File file, String keyName) {
        return MultipartBody.Part.createFormData(keyName, file.getName(),
                RequestBody.create(MediaType.parse(fileType.canonicalForm() + "/" + FileManager.getExtension(file.getName())), file)
        );
    }


    @NonNull
    private RequestBody getRequestBody(MediaType form, String trim) {
        return RequestBody.create(
                form, trim);
    }


    private void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    private String errorToastForObject(Response<WebResponse<Object>> response) {
        String responseMessage = "";

        if (response.body() != null) {
            responseMessage = response.body().message != null ? response.body().message : response.errorBody().toString();
        }

        if (responseMessage.isEmpty()) {
            UIHelper.showShortToastInCenter(mContext, "API Response Error ");
        } else {
            UIHelper.showShortToastInCenter(mContext, responseMessage);
        }
        return responseMessage;
    }


    public interface IRequestWebResponseAnyObjectCallBack {
        void requestDataResponse(WebResponse<Object> webResponse);

        void onError(Object object);
    }

    public interface IRequestWebResponseJustObjectCallBack {
        void requestDataResponse(Object webResponse);

        void onError(Object object);
    }

}
