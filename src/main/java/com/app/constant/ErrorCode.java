package com.app.constant;

public interface ErrorCode {

    /**
     * Starting error code Account
     */
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-0000";
    public static final String ACCOUNT_ERROR_USERNAME_EXISTED = "ERROR-ACCOUNT-0001";
    public static final String ACCOUNT_ERROR_EMAIL_EXISTED = "ERROR-ACCOUNT-0002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-0003";
    public static final String ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN = "ERROR-ACCOUNT-0004";
    public static final String ACCOUNT_ERROR_EXCEEDED_NUMBER_OF_INPUT_ATTEMPT_OTP = "ERROR-ACCOUNT-0005";
    public static final String ACCOUNT_ERROR_OTP_INVALID = "ERROR-ACCOUNT-0006";
    public static final String ACCOUNT_ERROR_LOGIN_FAILED = "ERROR-ACCOUNT-0007";
    public static final String ACCOUNT_ERROR_PRIVATE_KEY_INVALID = "ERROR-PRIVATE-KEY-INVALID";
    public static final String ACCOUNT_ERROR_PASSWORD_INVALID = "ERROR-ACCOUNT-0008";
    public static final String ACCOUNT_ERROR_NEW_PASSWORD_INVALID = "ERROR-ACCOUNT-0009";
    public static final String ACCOUNT_ERROR_BIRTHDATE_INVALID = "ERROR-ACCOUNT-0010";
    public static final String ACCOUNT_ERROR_PHONE_EXISTED = "ERROR-ACCOUNT-0011";
    public static final String ACCOUNT_ERROR_NOT_ALLOW_DELETE_YOURSELF = "ERROR-ACCOUNT-0012";

    /**
     * Starting error code Group
     */
    public static final String GROUP_ERROR_NOT_FOUND = "ERROR-GROUP-0000";
    public static final String GROUP_ERROR_NAME_EXISTED = "ERROR-GROUP-0001";

    /**
     * Starting error code Permission
     */
    public static final String PERMISSION_ERROR_NAME_EXISTED = "ERROR-GROUP-0001";

    /**
     * Starting error code Category
     */
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-0000";
    public static final String CATEGORY_ERROR_NAME_EXISTED = "ERROR-CATEGORY-0001";

    /**
     * Starting error code Department
     */
    public static final String DEPARTMENT_ERROR_NOT_FOUND = "ERROR-DEPARTMENT-0000";
    public static final String DEPARTMENT_ERROR_NAME_EXISTED = "ERROR-DEPARTMENT-0001";

    /**
     * Starting error code Transaction
     */
    public static final String TRANSACTION_ERROR_NOT_FOUND = "ERROR-TRANSACTION-0000";
    public static final String TRANSACTION_ERROR_KIND_INVALID = "ERROR-TRANSACTION-0001";
    public static final String TRANSACTION_ERROR_NOT_ALLOW_UPDATE = "ERROR-TRANSACTION-0002";
    public static final String TRANSACTION_ERROR_NOT_ALLOW_DELETE = "ERROR-TRANSACTION-0003";
    public static final String TRANSACTION_ERROR_DOCUMENT_INVALID = "ERROR-TRANSACTION-0004";


    /**
     * Starting error code Service
     */
    public static final String SERVICE_ERROR_NOT_FOUND = "ERROR-SERVICE-0000";
    public static final String SERVICE_ERROR_NAME_EXISTED = "ERROR-SERVICE-0001";
    public static final String SERVICE_ERROR_KIND_INVALID = "ERROR-SERVICE-0002";
    public static final String SERVICE_ERROR_DATE_INVALID = "ERROR-SERVICE-0003";

    /**
     * Starting error code Service Schedule
     */
    public static final String SERVICE_SCHEDULE_ERROR_NOT_FOUND = "ERROR-SERVICE-SCHEDULE-0000";
    public static final String SERVICE_SCHEDULE_ERROR_NUMBER_OF_DUE_DAYS_EXISTED = "ERROR-SERVICE-SCHEDULE-0001";

    /**
     * Starting error code Notification Group
     * */
    public static final String NOTIFICATION_GROUP_ERROR_NOT_FOUND = "ERROR-NOTIFICATION-GROUP-0000";
    public static final String NOTIFICATION_GROUP_ERROR_NAME_EXISTED = "ERROR-NOTIFICATION-GROUP-0001";

    /**
     * Starting error code User Group Notification
     * */
    public static final String USER_GROUP_NOTIFICATION_ERROR_NOT_FOUND = "ERROR-USER-GROUP-NOTIFICATION-0000";
    public static final String USER_GROUP_NOTIFICATION_ERROR_ACCOUNT_AND_NOTIFICATION_GROUP_EXISTED = "ERROR-USER-GROUP-NOTIFICATION-0001";

    /**
     * Starting error code Notification
     * */
    public static final String NOTIFICATION_ERROR_NOT_FOUND = "ERROR-NOTIFICATION-0000";
    public static final String NOTIFICATION_ERROR_ALREADY_READ = "ERROR-NOTIFICATION-0001";

    /**
     * Starting error code Transaction History
     * */
    public static final String TRANSACTION_HISTORY_ERROR_NOT_FOUND = "ERROR-TRANSACTION-HISTORY-0000";

    /**
     * Starting error code Key Information
     * */
    public static final String KEY_INFORMATION_ERROR_NOT_FOUND = "ERROR-KEY-INFORMATION-0000";

    /**
     * Starting error code Key Information Group
     * */
    public static final String KEY_INFORMATION_GROUP_ERROR_NOT_FOUND = "ERROR-KEY-INFORMATION-GROUP-0000";
    public static final String KEY_INFORMATION_GROUP_ERROR_NAME_EXISTED = "ERROR-KEY-INFORMATION-GROUP-0001";

    /**
     * Starting error code Transaction Group
     * */
    public static final String TRANSACTION_GROUP_ERROR_NOT_FOUND = "ERROR-TRANSACTION-GROUP-0000";
    public static final String TRANSACTION_GROUP_ERROR_NAME_EXISTED = "ERROR-TRANSACTION-GROUP-0001";

    /**
     * Starting error code Service Group
     * */
    public static final String SERVICE_GROUP_ERROR_NOT_FOUND = "ERROR-SERVICE-GROUP-0000";
    public static final String SERVICE_GROUP_ERROR_NAME_EXISTED = "ERROR-SERVICE-GROUP-0001";

    /**
     * Starting error code Payment Period
     * */
    public static final String PAYMENT_PERIOD_ERROR_NOT_FOUND = "ERROR-PAYMENT-PERIOD-0000";
    public static final String PAYMENT_PERIOD_ERROR_DATE_INVALID = "ERROR-PAYMENT-PERIOD-0001";
    public static final String PAYMENT_PERIOD_ERROR_NOT_ALLOW_UPDATE = "ERROR-PAYMENT-PERIOD-0002";
    public static final String PAYMENT_PERIOD_ERROR_NOT_ALLOW_RECALCULATE = "ERROR-PAYMENT-PERIOD-0003";

    /**
     * Starting error code Setting
     */
    public static final String SETTING_ERROR_NOT_FOUND = "ERROR-SETTING-ERROR-0000";
    public static final String SETTING_ERROR_EXISTED_GROUP_NAME_AND_KEY_NAME = "ERROR-SETTING-ERROR-0001";

    /**
     * Starting error code Service Notification Group
     * */
    public static final String SERVICE_NOTIFICATION_GROUP_ERROR_NOT_FOUND = "ERROR-SERVICE-NOTIFICATION-GROUP-0000";
    public static final String SERVICE_NOTIFICATION_GROUP_ERROR_SERVICE_AND_NOTIFICATION_GROUP_EXISTED = "ERROR-SERVICE-NOTIFICATION-GROUP-0001";

}
