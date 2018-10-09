package edu.aku.ehs.managers.retrofit;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import edu.aku.ehs.constatnts.WebServiceConstants;
import edu.aku.ehs.models.peoplesoft.department.DepartmentWrapper;
import edu.aku.ehs.models.peoplesoft.employee.EmployeeWrapper;
import edu.aku.ehs.models.wrappers.WebResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<WebResponse<String>> webServiceRequestAPIForWebResponseWithString(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData
    );

    @Multipart
    @POST("./")
    Call<WebResponse<Object>> webServiceRequestAPIForWebResponseAnyObject(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData
    );

    @Multipart
    @POST("./")
    Call<WebResponse<JsonObject>> uploadFileRequestApi(
            @Part(WebServiceConstants.PARAMS_REQUEST_METHOD) RequestBody requestMethod,
            @Part(WebServiceConstants.PARAMS_REQUEST_DATA) RequestBody requestData,
            @Part MultipartBody.Part body

    );


    @Headers("Requestor: aku.edu")
    @GET(WebServiceConstants.WS_KEY_GET_TOKEN)
    Call<String> getToken();


    @Headers(WebServiceConstants.WS_TOKEN_CONSTANT)
    @GET(WebServiceConstants.WS_AKU_DEPT_EMP_PART)
    Call<EmployeeWrapper> getEmpl(
            @Path("type") String type,
            @Path("value") String value
    );

    @Headers(WebServiceConstants.WS_TOKEN_CONSTANT)
    @GET(WebServiceConstants.WS_AKU_DEPT_EMP_PART)
    Call<DepartmentWrapper> getDept(
            @Path("type") String type,
            @Path("value") String value
    );

    @GET(WebServiceConstants.WS_KEY_AUTHENTICATE_USER)
    Call<Object> authenticateUser(
            @Query("UserName") String userName,
            @Query("Password") String password,
            @Query("Application") String application,
            @Query("WebUrl") String webURL
    );

    @GET(WebServiceConstants.WS_KEY_AUTHENTICATE_USER)
    Call<Object> getAuthenticatedUserDetails(
            @Query("UserName") String userName,
            @Query("Application") String application
    );


    @GET(WebServiceConstants.WS_KEY_AUTHENTICATE_USER)
    Call<Object> getAuthenticateEmailValidations(
            @Query("EmailRecepients") String emailAddresses
    );


}

