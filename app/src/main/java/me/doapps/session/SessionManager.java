package me.doapps.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jonathan on 14/01/2015.
 */
public class SessionManager {
    private static final String PREFERENCE_NAME = "youapp_preferences";
    private static final String URL = "ip";
    private int PRIVATE_MODE = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context=context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public String getUrl(){
        return preferences.getString(URL, null);
    }

    public void setUrl(String url){
        editor.putString(URL, url);
        editor.commit();
    }



}
