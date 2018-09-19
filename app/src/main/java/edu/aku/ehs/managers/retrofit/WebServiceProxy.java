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


    @FormUrlEncoded
    @POST("./")
    Call<Object> cyberSoft(
            @Field("reference_number") String reference_number,
            @Field("transaction_type") String transaction_type,
            @Field("currency") String currency,
            @Field("amount") String amount,
            @Field("locale") String locale,
            @Field("access_key") String access_key,
            @Field("profile_id") String profile_id,
            @Field("transaction_uuid") String transaction_uuid,
            @Field("signed_date_time") String signed_date_time,
            @Field("signed_field_names") String signed_field_names,
            @Field("unsigned_field_names") String unsigned_field_names,
            @Field("signature") String signature,
            @Field("payment_method") String payment_method,
            @Field("card_type") String card_type,
            @Field("card_number") String card_number,
            @Field("card_expiry_date") String card_eexpiry_date,
            @Field("card_cvn") String card_cvn,
            @Field("bill_to_forename") String bill_to_forename,
            @Field("bill_to_surname") String bill_to_surname,
            @Field("bill_to_email") String bill_to_email,
            @Field("bill_to_address_line1") String bill_to_address_line1,
            @Field("bill_to_address_city") String bill_to_address_city,
            @Field("bill_to_address_country") String bill_to_address_country,
            @Field("bill_to_address_postal_code") String bill_to_address_postal_code,
            @Field("bill_to_address_state") String bill_to_address_state

//            @Field("bill_to_phone") String bill_to_phone,
//            @Field("bill_to_company_name") String bill_to_company_name,
//            @Field("consumer_id") String consumer_id,
//            @Field("customer_ip_address") String customer_ip_address,
//            @Field("merchant_defined_data1") String merchant_defined_data1,


    );


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

}

