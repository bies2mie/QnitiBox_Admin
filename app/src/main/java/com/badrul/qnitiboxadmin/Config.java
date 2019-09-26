package com.badrul.qnitiboxadmin;

public class Config {

    public static final String SHARED_PREF_NAME = "jimatBox";

    public static final String MENU_TYPE = "menuType";
    public static final String MENU_DAY = "menuDay";
    public static final String ORDER_DATE = "orderDate";
    public static final String ORDER_TIME = "orderTime";

 public static final String ACCEPT_ORDER_URL = "http://gmartbox.cvmall.my/apps/admin/acceptorder.php";

 public static final String CANCEL_ORDER_URL = "http://gmartbox.cvmall.my/apps/admin/cancelorder.php";


 public static final String FEEDBACK_URL = "http://gmartbox.cvmall.my/apps/admin/feedback.php";



 public static final String SCANNER_URL = "http://gmartbox.cvmall.my/apps/admin/scanner.php?orderID=";

    public static final String ORDER_STATUS_COMPLETE = "http://gmartbox.cvmall.my/apps/admin/orderstatuscomplete.php";
    public static final String ORDER_STATUS_PROCESSING = "http://gmartbox.cvmall.my/apps/admin/orderstatusprocessing.php";
   // public static final String PROFILE = "http://gmartbox.cvmall.my/apps/getuserinfo.php?userEmail=";
    //FOR ORDER
    public static final String ORDER_ID = "orderid";
    public static final String CARD_ID = "cardID";
    public static final String NAME_ID = "nameID";
    public static final String PHONE_ID = "phoneID";
    public static final String EMAIL_ID = "emailID";
    public static final String MATRIX_ID = "matrixID";
    public static final String ORDER_TYPE = "orderType";
    public static final String ORDER_DAY = "orderDay";
    public static final String ORDER_DATE2 = "orderDate2";
    public static final String ORDER_TIME2 = "orderTime2";
    public static final String ORDER_COMPLETEDATE = "CompleteDate";
    public static final String ORDER_COMPLETETIME = "CompleteTime";
    public static final String ORDER_QTT = "orderQTT";
    public static final String ORDER_USERTYPE = "orderUserType";
    public static final String PICKUP_LOCATION = "puLocation";
    public static final String PICKUP_TIME = "puTime";

    public static final String COMPLETE_DATE = "completeDate";
    public static final String COMPLETE_TIME = "completeTime";
    public static final String ORDER_STATUS = "orderStatus";

    //For User
    public static final String USER_ID2 = "userID2";
    public static final String NAME_ID2 = "nameID2";
    public static final String PHONE_ID2 = "phoneID2";
    public static final String EMAIL_ID2 = "emailID2";
    public static final String MATRIX_ID2 = "matrixID";


    public static final String LOGIN_URL = "http://gmartbox.cvmall.my/apps/admin/loginadmin.php";
    //public static final String REGISTER_URL = "http://gmartbox.cvmall.my/apps/registerqniti.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
//public static final String KEY_ID = "userIC";
    public static final String KEY_PASSWORD = "userPass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //This would be used to store the phone number of current logged in user
    public static final String ID_SHARED_PREF = "userID";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
