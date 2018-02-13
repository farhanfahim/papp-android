package edu.aku.akuh_health_first.managers.retrofit;

import edu.aku.akuh_health_first.constatnts.WebServiceConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebServiceFactory {

    private static Retrofit retrofitBase;
    private static Retrofit retrofitPACSViewer;
    private static Retrofit retrofitPACSDownload;
    private static Retrofit retrofitPACSToken;

    /***
     *      SINGLETON Design Pattern
     */
    public static WebServiceProxy getInstanceBaseURL(final String _token) {

        if (retrofitBase == null) {

//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                    .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader("_token", _token + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            });


            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitBase = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();

        }

        return retrofitBase.create(WebServiceProxy.class);
    }


    public static WebServiceProxy getInstancePACSURL(final String _token, final String bearerToken) {

        if (retrofitPACSViewer == null) {

//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                    .create();


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();

                    requestBuilder.addHeader("_token", _token + "");
                    requestBuilder.addHeader("Authorization", "Bearer " + bearerToken);
                    requestBuilder.addHeader("Requestor", "aku.edu");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            });


            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitPACSViewer = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.PACS_VIEWER_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();

        }

        return retrofitPACSViewer.create(WebServiceProxy.class);
    }


    public static WebServiceProxy getInstancePACImageDownload(final String bearerToken) throws IOException {

        if (retrofitPACSDownload == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(1000, TimeUnit.SECONDS);
            httpClient.readTimeout(1000, TimeUnit.SECONDS);
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    requestBuilder.addHeader("Authorization", "Bearer " + bearerToken);
                    requestBuilder.addHeader("Requestor", "aku.edu");
                    requestBuilder.addHeader("Accept", "image/jpeg");


                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            });


            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitPACSDownload = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.PACS_URL_DOWNLOAD)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();


        }

        return retrofitPACSDownload.create(WebServiceProxy.class);


    }

    public static WebServiceProxy getInstance() {

        if (retrofitPACSToken == null) {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            // set your desired log level
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(120, TimeUnit.SECONDS);
            httpClient.readTimeout(121, TimeUnit.SECONDS);


//             add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
//                    requestBuilder.addHeader("_token", _token + "");

                    // Request customization: add request headers

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }
            });


            // add logging as last interceptor
//            httpClient.addNetworkInterceptor(interceptor).addInterceptor(interceptor);  // <-- this is the important line!
            httpClient.addInterceptor(interceptor);  // <-- this is the important line!
            retrofitPACSToken = new Retrofit.Builder()
                    .baseUrl(WebServiceConstants.PACS_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSimpleGson()))
                    .client(httpClient.build())
                    .build();

        }

        return retrofitPACSToken.create(WebServiceProxy.class);
    }

}