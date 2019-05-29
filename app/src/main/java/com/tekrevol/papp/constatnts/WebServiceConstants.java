package com.tekrevol.papp.constatnts;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by khanhamza on 09-Mar-17.
 */

public class WebServiceConstants {


    private static Map<String, String> headers;

    public static Map<String, String> getHeaders(String token) {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("_token", token);
        }
        return headers;
    }

    /**
     * URLs
     */


    /**
     * BEFORE LIVE DEPLOYMENT
     * Change URL Live/ UAT
     * Change Image URL
     * Check Version Code
     * BaseApplication Fabric enable
     */


    // LOCAL
    public static final String BASE_URL = "http://papp.apps.fomarkmedia.com/";
    public static final String IMAGE_BASE_URL = "http://papp.apps.fomarkmedia.com/api/resize/";

    // LIVE
//    public static final String BASE_URL = "http://papp.apps.fomarkmedia.com/api/v1/;


    public static final String WS_KEY_GET_TOKEN = "getToken";

    /**
     * API PATHS NAMES
     */

    public static final String PATH_REGISTER = "register";
    public static final String PATH_LOGIN = "login";
    public static final String PATH_PROFILE = "profile";
    public static final String PATH_GET_DEPARTMENTS = "departments";
    public static final String PATH_GET_SPECIALIZATIONS = "specializations";
    public static final String PATH_GET_USERS = "users";
    public static final String PATH_GET_REFRESH = "refresh";


    /**
     * QUERY PARAMS
     */

    public static final String Q_PARAM_ROLE = "role";
    public static final String Q_PARAM_LIMIT = "limit";
    public static final String Q_PARAM_OFFSET = "offset";
    public static final String Q_PARAM_TOP_MENTOR = "top_mentor";
    public static final String Q_PARAM_MY_MENTOR = "my_mentor";
    public static final String Q_PARAM_SEARCH = "search";
    public static final String Q_PARAM_DEPT_ID = "department_id";


    /**
     * STATUS
     */

    public static final int PARAMS_TOKEN_EXPIRE = 401;
    public static final int PARAMS_TOKEN_BLACKLIST = 402;


}
