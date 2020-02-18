package com.tekrevol.papp.constatnts;

import android.content.Context;
import android.os.Environment;

import com.tekrevol.papp.BaseApplication;
import com.tekrevol.papp.managers.SharedPreferenceManager;


/**
 * Created by khanhamza on 4/20/2017.
 */

public class AppConstants {

    // Temporary User
    public static String tempUserName = "Developer/Tester";


    /**
     * Static Booleans
     */

    public static boolean isForcedResetFragment;

    /**
     * Date Formats
     */

    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String INPUT_DATE_FORMAT_AM_PM = "yyyy-dd-MM hh:mm:ss a";
    public static final String OUTPUT_DATE_FORMAT = "EEEE dd,yyyy";
    public static final String OUTPUT_DATE_FORMAT_AM_PM = "MMMM-dd-yyyy hh:mm a";
    public static final String INPUT_TIME_FORMAT = "yyyy-dd-MM hh:mm:ss a";
    public static final String OUTPUT_TIME_FORMAT = "hh:mm a";
    public static final String OUTPUT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String OUTPUT_DATE_TIME_FORMAT = "EEEE dd,yyyy hh:mm a";
    public static final String INPUT_LAB_DATE_FORMAT_AM_PM = "MM/dd/yyyy hh:mm:ss a";

    // Custom
    public static final String DOB_FORMAT = "yyyy-MM-dd";
    //   public static final String DISPLAY_DATE_ONLY_FORMAT = "dd MMM, YYYY";
    public static final String DISPLAY_DATE_ONLY_FORMAT = "dd MMM, YYYY";
    public static final String DISPLAY_TIME_ONLY_FORMAT = "hh:mm a";


    /**
     * Path to save Media
     */
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath()
            + "/" + BaseApplication.getApplicationName();

    public static final String DOC_PATH = ROOT_PATH + "/Docs";

    public static String getUserFolderPath(Context context) {
        return DOC_PATH + "/" + SharedPreferenceManager.getInstance(context).getCurrentUser().getUserDetails().getFirstName();
    }


    /**
     * MASKING FORMATs
     */

    public static final String CNIC_MASK = "99999-9999999-9";
    public static final String CARD_MASK = "9999-9999-9999";
    //    public static final String CARD_MASK = "wwww-wwww-wwww";
    public static final String MR_NUMBER_MASK = "999-99-99";


    /*************** INTENT DATA KEYS **************/
    public static final String LABORATORY_MODEL = "laboratoryModel";
    public static final String JSON_STRING_KEY = "JSON_STRING_KEY";
    public static final String IMAGE_PREVIEW_URL = "url";
    public static final String IMAGE_PREVIEW_TITLE = "title";
    public static final String GCM_DATA_OBJECT = "gcmDataObject";


    /*******************Preferences KEYS******************/
    public static final String KEY_CURRENT_USER_MODEL = "userModel";
    public static final String KEY_IS_MENTOR = "is_user_mentor";
    public static final String KEY_CODE = "code";
    public static final String USER_NOTIFICATION_DATA = "USER_NOTIFICATION_DATA";
    public static String FORCED_RESTART = "forced_restart";
    public static final String KEY_TOKEN = "getToken";
    public static final String KEY_FIREBASE_TOKEN = "firebase_token";
    public static final String KEY_FIREBASE_TOKEN_UPDATED = "FIREBASE_TOKEN_UPDATED";
    public static final String KEY_PIN_CODE = "pin_code";
    public static final String KEY_IS_PIN_ENABLE = "is_pin_enable";
    public static final String KEY_CURRENT_LOCATION = "current_location";


    /**
     * File Name initials if user download the pdf
     */
    public static String FILE_NAME = "Demo-App";


    /**
     * Data Static Strings
     */

    public static String AboutUs = "<B>PAPP</B> is a product of ..." +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    public static final String NO_RECORD_FOUND = "No Record Found";
    public static String DEVICE_OS_ANDROID = "android";
    public static String SOCIAL_MEDIA_PLATFORM_FACEBOOK = "facebook";
    public static int PARENT_ROLE = 3;
    public static int MENTOR_ROLE = 5;
    public static int SPONSOR_ROLE = 6;
    public static int DEPENDENT_ROLE = 4;
    public static int ACCESSIBLE = 1;
    public static int NOT_ACCESSIBLE = 0;
    public static int TASK_TYPE_USER = 10;
    public static int TASK_TYPE_MENTOR = 20;
    public static int TASK_STATUS_AVAILABLE = 0;
    public static int TASK_STATUS_ONGOING = 10;
    public static int TASK_STATUS_COMPLETED = 20;
    public static int TASK_STATUS_PENDING_ADMIN_APPROVAL = 40;
    public static int SESSION_TYPE_AUDIO = 10;
    public static int SESSION_TYPE_VIDEO = 20;
    public static int SESSION_TYPE_ONE_ON_ONE = 30;
    public static int SESSION_STATUS_PENDING = 0;
    public static int SESSION_STATUS_ACCEPTED_BY_MENTOR = 1;
    public static int SESSION_STATUS_COMPLETED = 2;
    public static int IS_MINE_ROLE = 1;
    public static String PAGE_SLUG_TERMS_AND_CONDITION = "terms-and-condition";
    public static String PAGE_SLUG_ABOUT = "about";


    public static String getGenderString(int gender) {
        switch (gender) {
            case 0:
                return "Female";
            case 1:
                return "Male";
            default:
                return "Male";
        }
    }


    public static int getGenderInt(String gender) {
        switch (gender) {
            case "Female":
                return 0;
            case "Male":
                return 1;
            default:
                return 1;
        }
    }


//    public static RegisteredDeviceModel getRegisteredDevice(Context context, Activity activity) {
//        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
//        RegisteredDeviceModel registeredDeviceModel = sharedPreferenceManager.getObject(KEY_REGISTERED_DEVICE, RegisteredDeviceModel.class);
//        if (registeredDeviceModel == null) {
//            registeredDeviceModel = new RegisteredDeviceModel();
//        }
//
//
//        // Set Device ID
//        if (registeredDeviceModel.getDeviceid() == null || registeredDeviceModel.getDeviceid().isEmpty()) {
//            registeredDeviceModel.setDeviceid(getDeviceID(context));
//        }
//
//
//        // Getting Display Metrics only if Display values not set
//
//        if (registeredDeviceModel.getDevicescreensize() == null || registeredDeviceModel.getDevicescreensize().isEmpty()) {
//            DisplayMetrics metrics = new DisplayMetrics();
//            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//            float yInches = metrics.heightPixels / metrics.ydpi;
//            float xInches = metrics.widthPixels / metrics.xdpi;
//            double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
//            if (diagonalInches >= 6.9) {
//                registeredDeviceModel.setDevicetype("Tablet");
//            } else {
//                registeredDeviceModel.setDevicetype("Phone");
//            }
//            registeredDeviceModel.setDeviceos(DEVICE_OS_ANDROID);
//            registeredDeviceModel.setDevicescreensize(metrics.heightPixels + "x" + metrics.widthPixels);
//            registeredDeviceModel.setDevicemanufacturer(Build.MANUFACTURER);
//            registeredDeviceModel.setDevicemodel(Build.MODEL);
//
//        }
//
//        registeredDeviceModel.setDeviceosversion(Build.VERSION.RELEASE);
//
//        try {
//            registeredDeviceModel.setAppVersion(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.d("App Constants:", "Get App Version: " + e.getLocalizedMessage());
//        }
//
//        SharedPreferenceManager.getInstance(context).putObject(KEY_REGISTERED_DEVICE, registeredDeviceModel);
//        return registeredDeviceModel;
//    }
//
//
//    public static InsertRegisteredDeviceModel getInsertRegisteredDevice(Context context, Activity activity) {
//        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);
//        InsertRegisteredDeviceModel insertRegisteredDeviceModel = sharedPreferenceManager.getObject(KEY_INSERT_REGISTERED_DEVICE, InsertRegisteredDeviceModel.class);
//        if (insertRegisteredDeviceModel == null) {
//            insertRegisteredDeviceModel = new InsertRegisteredDeviceModel();
//        }
//
//
//        // Set Device ID
//        if (insertRegisteredDeviceModel.getDeviceid() == null || insertRegisteredDeviceModel.getDeviceid().isEmpty()) {
//            insertRegisteredDeviceModel.setDeviceid(getDeviceID(context));
//        }
//
//
//        insertRegisteredDeviceModel.setDeviceos(DEVICE_OS_ANDROID);
//
//        SharedPreferenceManager.getInstance(context).putObject(KEY_REGISTERED_DEVICE, insertRegisteredDeviceModel);
//        return insertRegisteredDeviceModel;
//    }
//
//
//    private static String getDeviceID(Context context) {
//
///*String Return_DeviceID = USERNAME_and_PASSWORD.getString(DeviceID_key,"Guest");
//return Return_DeviceID;*/
//
//        TelephonyManager TelephonyMgr = (TelephonyManager) context.getApplicationContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//
//        String m_szImei = ""; // Requires
//        if (TelephonyMgr != null) {
//            m_szImei = TelephonyMgr.getDeviceId();
//        }
//// READ_PHONE_STATE
//
//// 2 compute DEVICE ID
//        String m_szDevIDShort = "35"
//                + // we make this look like a valid IMEI
//                Build.BOARD.length() % 10 + Build.BRAND.length() % 10
//                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
//                + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
//                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
//                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
//                + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
//                + Build.USER.length() % 10; // 13 digits
//// 3 android ID - unreliable
//        String m_szAndroidID = "";
//        if (getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) != null) {
//            m_szAndroidID = getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }
//// 4 wifi manager, read MAC address - requires
//// android.permission.ACCESS_WIFI_STATE or comes as null
////        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
////        String m_szWLANMAC = "";
////        if (wm != null) {
////            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
////        }
//// 5 Bluetooth MAC address android.permission.BLUETOOTH required
////        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
////        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
////        String m_szBTMAC = "";
////        if (m_BluetoothAdapter != null) {
////            m_szBTMAC = m_BluetoothAdapter.getAddress();
////        }
////        System.out.println("m_szBTMAC " + m_szBTMAC);
//
//// 6 SUM THE IDs
////        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
//        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID;
//        System.out.println("m_szLongID " + m_szLongID);
//        MessageDigest m = null;
//
//        // FIXME: 5/28/2018 commenting algo, 30 character value
//
////        try {
//////            m = MessageDigest.getInstance("MD5");
////            m = MessageDigest.getInstance("SHA-256");
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        }
//
//        // If SHA-256
//        if (m == null) {
//            if (!m_szLongID.isEmpty()) {
//                if (m_szLongID.length() > 30) {
//                    return m_szLongID.substring(0, 30);
//                } else {
//                    return m_szLongID;
//                }
//            } else {
//                return getDeviceID2(context);
//            }
//        }
//
//
//        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
//        byte p_md5Data[] = m.digest();
//
//        String m_szUniqueID = "";
//        for (int i = 0; i < p_md5Data.length; i++) {
//            int b = (0xFF & p_md5Data[i]);
//// if it is a single digit, make sure it have 0 in front (proper
//// padding)
//            if (b <= 0xF)
//                m_szUniqueID += "0";
//// add number to string
//            m_szUniqueID += Integer.toHexString(b);
//        }
//        m_szUniqueID = m_szUniqueID.toUpperCase();
//
//        Log.i("------DeviceID------", m_szUniqueID);
//        Log.d("DeviceIdCheck", "DeviceId that generated MPreferenceActivity:" + m_szUniqueID);
//
//        return m_szUniqueID;
//    }
//
//
//    public static String getDeviceID2(Context context) {
//        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//        String tmDevice = "", tmSerial = "", androidId = "";
//        UUID deviceUuid;
//
//        if (tm != null) {
//            tmDevice = "" + tm.getDeviceId();
//            tmSerial = "" + tm.getSimSerialNumber();
//            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        } else {
//            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//            deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        }
//
//        return deviceUuid.toString();
//
//    }


}
