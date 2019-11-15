package com.example.glide;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class PreferencesHelperImp implements PreferencesHelper {
    private String SharedPreferencesKey = "SharedPreferencesKey";
    private final SharedPreferences mSharedPreferences;
    private static final String PREF_KEY_URL_DATA = "PREF_KEY_URL_DATA";

    private static final String PREF_KEY_STRING_DATA = "PREF_KEY_STRING_DATA";
    private static final String PREF_KEY_TITLE = "PREF_KEY_TITLE";
    private static final String PREF_KEY_LONG_DATA = "PREF_KEY_LONG_DATA";
    private static final String PREF_KEY_INTEGER_DATA = "PREF_KEY_INTEGER_DATA";

    public PreferencesHelperImp(Context context) {
        mSharedPreferences = context.getSharedPreferences(SharedPreferencesKey, Context.MODE_PRIVATE);
    }

    @Override
    public String getId() {
        return mSharedPreferences.getString(PREF_KEY_STRING_DATA,"");
    }

    @Override
    public void setId(String id) {
        mSharedPreferences.edit().putString(PREF_KEY_STRING_DATA, id).apply();
    }

    @Override
    public Integer getFarm() {
        return mSharedPreferences.getInt(PREF_KEY_INTEGER_DATA,0);
    }

    @Override
    public void setFarm(Integer farm) {
        mSharedPreferences.edit().putInt(PREF_KEY_INTEGER_DATA,farm).apply();
    }

    @Override
    public String getTitle() {
        return mSharedPreferences.getString(PREF_KEY_TITLE,"");
    }

    @Override
    public void setTitle(String title) {
        mSharedPreferences.edit().putString(PREF_KEY_TITLE ,title).apply();

    }

    @Override
    public String getUrl() {
        return mSharedPreferences.getString(PREF_KEY_URL_DATA,"");
    }

    @Override
    public void setUrl(String url) {
        mSharedPreferences.edit().putString(PREF_KEY_URL_DATA ,url).apply();
    }
}
