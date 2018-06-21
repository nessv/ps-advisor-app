package org.fundacionparaguaya.adviserplatform.util;

import android.support.v7.app.AppCompatActivity;

import org.fundacionparaguaya.adviserplatform.BuildConfig;

public class AppConstants {
    public static final String FIRST_TIME_USER_KEY = "FIRST_TIME_USER_KEY";
    public static final String KEY_LAST_SYNC_TIME = "lastSyncTime";
    public static final int HTTP_SC_UNAUTHORIZED = 401;
    public static final int HTTP_SC_BAD_REQUEST = 400;
    public static final int TOTAL_TYPES_OF_INDICATORS = 3;
    public static final String SHARED_PREFS_NAME = "advisor_app";
    public static final String KEY_REFRESH_TOKEN = "refreshToken";
    public static final String KEY_USERNAME = "username";
    public static final String AUTH_KEY = "Basic " + BuildConfig.POVERTY_STOPLIGHT_API_KEY_STRING;
    public static final String KEY_TOKEN_EXPIRATION = "KEY_TOKEN_EXPIRATION";
    public static final String ORGANIZATION_ID = "ORGANIZATION_ID";


    private AppConstants() {
        //Utilities classes don't have public constructors
    }
}
