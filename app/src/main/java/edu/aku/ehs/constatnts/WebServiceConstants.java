package edu.aku.ehs.constatnts;

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


    // FIXME: 6/25/2018 If TRUE, for testing purpose only
    public static boolean record_found_bypass = false;

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

    public static final String BASE_URL_LOCAL = "http://ahfapidev.aku.edu/api/";


    // UAT
    public static final String BASE_URL = "http://ehsapi.aku.edu/api/";
    public static final String GETIMAGE_BASE_URL = "https://familyhifazatmobileapiuat.aku.edu/getimage?path=";
    // LIVE
//    public static final String GETIMAGE_BASE_URL = "https://familyhifazatmobileapi.aku.edu/getimage?path=";
//    public static final String BASE_URL = "https://familyhifazatmobileapi.aku.edu/api/";

    public static String PACS_VIEWER_URL = "https://pacsviewer.aku.edu/api/PACSViewer/";
    public static String PACS_URL = "https://pacsviewer.aku.edu/api/";
    //    public static String GET_EMP_DEPT_URL = "https://testsecureacceptance.cybersource.com/pay/";
    public static String PAYMENT_GATEWAY_URL = "https://testsecureacceptance.cybersource.com/token/create/";

    public static final String WS_KEY_GET_TOKEN = "getToken";

    // People soft
    public static final String WS_TOKEN_CONSTANT = "Authorization: Basic QUtVX1RMX1JFU1RfRU1QX0RFUFQ6ezVDNEY0MkIzLUYyRDktNzQ1Ny0yQURDLTM5RTcxNDYyMDJCMn0=";
    public static final String WS_AKU_DEPT_EMP_GET_BASE_URL = "https://uerpdmo.aku.edu/PSIGW/RESTListeningConnector/PSFT_HR/AKU_DEPT_EMPS_GET.v1/";
    public static final String WS_AKU_DEPT_EMP_PART = "SHARE/{type}/{value}";



    /**
     * API PARAMS
     */
    public static final String PARAMS_REQUEST_METHOD = "requestmethod";
    public static final String PARAMS_REQUEST_DATA = "requestdata";

    // People soft API Params
    public static final String DIV_KEY = "2V";
    public static final String DEPT_KEY = "1D";
    public static final String EMPLOYEE_NO_KEY = "1E";
    public static final String MR_NO_KEY = "1M";
    public static final String GET_ALL_KEY = "*";


    /**
     * Temporary MRNumbers
     */
//    public static final String tempMRN_Neuro = "291-32-60";
//    public static final String tempMRN = "510-29-10";
//    public static final String tempMRN_ENDOSCOPY = "100-08-60";
//    //        public static final String tempMRN_RADIOLOGY = "294-71-23";
//    public static final String tempMRN_RADIOLOGY = "015-94-53";
//    public static final String tempMRN_immunization = "269-14-57";
//    public static final String temp_Specimen_Num = "47556226";
//    //    public static final String tempMRN_Cardio = "275-02-02";
////    public static final String tempMRN_Cardio = "289-49-15";
//    public static final String tempMRN_Cardio = "200-47-97";

    /**
     * REQUEST METHODS NAMES
     */

    // UserManager
    public static final String METHOD_USER_LOGIN = "UserManager.Login";
    public static final String METHOD_USER_GET_USER_IMAGE = "UserManager.GetUserImage";

    //DictionaryManager
    public static final String METHOD_ADD_SESSION = "DictionaryManager.AddSession";
    public static final String METHOD_UPDATE_SESSION = "DictionaryManager.UpdateSession";

    //SessionManager
    public static final String METHOD_GET_SESSION_LIST = "SessionManager.GetSessionList";
    public static final String METHOD_GET_SESSION_EMPLOYEES = "SessionManager.GetSessionEmp";
    public static final String METHOD_ADD_SESSION_EMPLOYEE = "SessionManager.AddSessionEmp";
    public static final String METHOD_UPDATE_SESSION_EMPLOYEE = "SessionManager.UpdateSessionEmp";


    public static final String METHOD_GET_ONE_TIME_TOKEN = "SharedManager.GetOneTimeCode";

}
