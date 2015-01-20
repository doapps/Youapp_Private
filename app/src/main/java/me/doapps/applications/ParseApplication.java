package me.doapps.applications;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import me.doapps.youapps.Push;
import me.doapps.youapps.R;

/**
 * Created by mili on 08/01/2015.
 */
public class ParseApplication extends Application {

    public ParseApplication(){}

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Parse SDK.
        Parse.initialize(this, getResources().getString(R.string.application_id), getResources().getString(R.string.client_key));

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, Push.class);
        //saving new installation
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
