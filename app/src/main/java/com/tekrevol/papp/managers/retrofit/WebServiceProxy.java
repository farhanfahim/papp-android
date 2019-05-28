package com.tekrevol.papp.managers.retrofit;

import com.tekrevol.papp.constatnts.WebServiceConstants;
import com.tekrevol.papp.models.wrappers.WebResponse;
import com.google.gson.JsonObject;
import com.tekrevol.papp.models.wrappers.WebResponse;

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
    Call<WebResponse<Object>> postAPIWebResponseAnyObject(
            @Path("path") String postfix,
            @Body RequestBody requestData
    );


    @Multipart
    @POST("api/v1/{path}")
    Call<WebResponse<Object>> postMultipartAPI(
            @Path("path") String postfix,
            @Part ArrayList<MultipartBody.Part> body

    );


    /**
     *  GIVE role = 0, limit = 0, offset = 0 if dont want to use these parameters
     * @param postfix
     * @param role
     * @param limit
     * @param offset
     * @return
     */


    @GET("api/v1/{path}")
    Call<WebResponse<Object>> getAPIForWebresponseAnyObject(
            @Path("path") String postfix,
            @Query("role") int role,
            @Query("limit") int limit,
            @Query("offset") int offset
    );


//    @GET(WebServiceConstants.WS_KEY_AUTHENTICATE_USER)
//    Call<Object> getAuthenticatedUserDetails(
//            @Query("UserName") String userName,
//            @Query("Application") String application
//    );


}

