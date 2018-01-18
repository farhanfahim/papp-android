package edu.aku.akuh_health_first.managers.retrofit;

import com.google.gson.JsonObject;

import edu.aku.akuh_health_first.constatnts.WebServiceConstants;
import edu.aku.akuh_health_first.models.CardModel;
import edu.aku.akuh_health_first.models.Content;
import edu.aku.akuh_health_first.models.extramodels.AddressModel;
import edu.aku.akuh_health_first.models.wrappers.AddressWrapper;
import edu.aku.akuh_health_first.models.wrappers.AddressWrapper2;
import edu.aku.akuh_health_first.models.wrappers.NotificationWrapper;
import edu.aku.akuh_health_first.models.wrappers.WebResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public interface WebServiceProxy {


    @Multipart
    @POST("./")
    Call<WebResponse<JsonObject>> webServiceRequestAPI(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData
    );

    @Multipart
    @POST("./")
    Call<WebResponse<ArrayList<JsonObject>>> webServiceRequestAPIForArray(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData
    );


    @Multipart
    @POST("./")
    Call<WebResponse<String>> uploadFileRequestApi(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part MultipartBody.Part requestData
    );


    @Headers(WebServiceConstants.WS_KEY_GET_REQUESTOR)
    @GET(WebServiceConstants.WS_KEY_GET_TOKEN)
    Call<String> getToken();


    @GET(WebServiceConstants.WS_KEY_IMAGE_URL)
    Call<ResponseBody> downloadFileWithFixedUrl();

    @Multipart
    @POST("./")
    Call<WebResponse<String>> webServiceRequestAPIForWebResponseWithString(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData
    );


}

