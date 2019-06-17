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
//    public static final String BASE_URL = "http://192.168.29.49/papp/";
//    public static final String IMAGE_BASE_URL = "http://192.168.29.49/papp/api/resize/";

    // LIVE
//    public static final String BASE_URL = "http://papp.apps.fomarkmedia.com/api/v1/;


    /**
     * API PATHS NAMES
     */

    public static final String PATH_REGISTER = "register";
    public static final String PATH_LOGIN = "login";
    public static final String PATH_GET_DEPARTMENTS = "departments";
    public static final String PATH_GET_SPECIALIZATIONS = "specializations";
    public static final String PATH_GET_USERS = "users";
    public static final String PATH_GET_REFRESH = "refresh";
    public static final String PATH_PROFILE = "profile";
    public static final String PATH_GIFTS = "gifts";
    public static final String PATH_REDEEM_POINTS = "redeem-points";
    public static final String PATH_TASKS = "tasks";
    public static final String PATH_ACCEPT_TASK = "task-users";
    public static final String PATH_COMPLETE_TASK = "upload-completed-task";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_ADD_DEPENDENT = "addDependant";
    public static final String PATH_CANCEL_TASK = "cancel-task";



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
    public static final String Q_PARAM_TYPE = "type";
    public static final String Q_PARAM_AVAILABLE = "available";
    public static final String Q_PARAM_STATUS = "status";
    public static final String Q_PARAM_MENTOR_ID = "mentor_id";


    /**
     * STATUS
     */

    public static final int PARAMS_TOKEN_EXPIRE = 401;
    public static final int PARAMS_TOKEN_BLACKLIST = 402;


    /**
     *  WSC KEYS
     */

    public static final String WSC_KEY_ATTACHMENT = "attachment[]";


}
