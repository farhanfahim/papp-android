package com.android.papp.managers.retrofit;

import com.android.papp.constatnts.WebServiceConstants;
import com.android.papp.models.wrappers.WebResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public interface WebServiceProxy {


    @POST("api/v1/{path}")
    Call<Object> webServiceRequestAPIForJustObject(
            @Path("path") String postfix,
            @Body RequestBody requestData
    );


    @POST("api/v1/{path}")
    Call<WebResponse<Object>> webServiceRequestAPIForWebResponseAnyObject(
            @Path("path") String postfix,
            @Body RequestBody requestData
    );


    @Multipart
    @POST("api/v1/{path}")
    Call<WebResponse<Object>> uploadFileRequestApi(
            @Path("path") String postfix,
            @Part ArrayList<MultipartBody.Part> body

    );


    @GET("api/v1/{path}")
    Call<String> getDataFromAPI(
            @Path("path") String postfix
    );


//    @GET(WebServiceConstants.WS_KEY_AUTHENTICATE_USER)
//    Call<Object> getAuthenticatedUserDetails(
//            @Query("UserName") String userName,
//            @Query("Application") String application
//    );


}

