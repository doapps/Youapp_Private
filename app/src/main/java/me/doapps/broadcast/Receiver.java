package me.doapps.broadcast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import me.doapps.session.SessionManager;
import me.doapps.youapps.YouApp;

/**
 * Created by mili on 08/01/2015.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        Bundle extras = intent.getExtras();
        String message = extras != null ? extras.getString("com.parse.Data") : "";
        Log.e("message", message);
        JSONObject jObject;
        try {
            jObject = new JSONObject(message);
            String temp_data = jObject.getString("alert");


            SessionManager session = new SessionManager(context);
            session.setUrl(temp_data);


            Intent i = new Intent(context, YouApp.class);
            i.putExtra("data", temp_data);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
