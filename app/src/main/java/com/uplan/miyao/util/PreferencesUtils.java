package com.uplan.miyao.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yabusai on 15/2/4.
 */
public class PreferencesUtils {

    /** ch_isvip:0; 0:非会员  1：会员专享 */
    public static String CH_ISVIP_0 = "0";
    public static String CH_ISVIP_1 = "1";

    public static String PREFERENCE_NAME = "miyaoAndroid";
    public static String LOGIN_STATE = "login_state";
    public static String PLAY_SESSION = "play_session";
    public static String USER_NAME = "username";
    public static String USER_TEL = "user_tel";
    public static String USER_PWD = "user_pwd";
    public static String IS_ACTIVEA = "is_active";
    public static String EXPIRE_TIME = "expire_time";
    public static String MESSAGE_NUM = "message_num";
    /** 首页面轮播图点击url地址 */
    public static String URL_BANNER_HOME_1 = "url_banner_home_1";
    public static String URL_BANNER_HOME_2 = "url_banner_home_2";
    public static String URL_BANNER_HOME_3 = "url_banner_home_3";

    /** 发现页面卢波图点url地址 */
    public static String URL_BANNER_DISCOVER_1 = "url_banner_discover_1";
    public static String URL_BANNER_DISCOVER_2 = "url_banner_discover_2";
    public static String URL_BANNER_DISCOVER_3 = "url_banner_discover_3";

    /** 首页中间产品页url地址 */
    public static String URL_CHURL1_HOME = "url_churl1_home";
    public static String URL_CHURL2_HOME = "url_churl2_home";
    public static String URL_CHURL3_HOME = "url_churl3_home";
    public static String URL_CHURL4_HOME = "url_churl4_home";

    /** 首页轮播图base64字符串 */
    public static String BITMAP_STRING_HOME_1 = "bitmap_string_home_1";
    public static String BITMAP_STRING_HOME_2 = "bitmap_string_home_2";
    public static String BITMAP_STRING_HOME_3 = "bitmap_string_home_3";

    /** 发现也轮播图base64字符串 */
    public static String BITMAP_STRING_DISCOVER_1 = "bitmap_string_discover_1";
    public static String BITMAP_STRING_DISCOVER_2 = "bitmap_string_discover_2";
    public static String BITMAP_STRING_DISCOVER_3 = "bitmap_string_discover_3";

    /** 首页中间产品页图base64字符串 */
    public static String BITMAP_STRING_CHANPIN1 = "bitmap_string_chanpin1";
    public static String BITMAP_STRING_CHANPIN2 = "bitmap_string_chanpin2";
    public static String BITMAP_STRING_CHANPIN3 = "bitmap_string_chanpin3";
    public static String BITMAP_STRING_CHANPIN4 = "bitmap_string_chanpin4";

    /** 首页中间产品页图权限 */
    public static String BOOLEAN_HOME_CH1_ISVIP = "boolean_home_ch1_isvip";
    public static String BOOLEAN_HOME_CH2_ISVIP = "boolean_home_ch2_isvip";
    public static String BOOLEAN_HOME_CH3_ISVIP = "boolean_home_ch3_isvip";
    public static String BOOLEAN_HOME_CH4_ISVIP = "boolean_home_ch4_isvip";


    /** 开机引导页图片 */
    public static String BITMAP_SPLASH_1 = "bitmap_splash_1";
    public static String BITMAP_SPLASH_2 = "bitmap_splash_2";
    public static String BITMAP_SPLASH_3 = "bitmap_splash_3";

    private PreferencesUtils() {
        throw new AssertionError();
    }

    /**
     * put string preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean clearString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }
}
