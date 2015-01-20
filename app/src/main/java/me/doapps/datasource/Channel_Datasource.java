package me.doapps.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.doapps.adapters.Adapter_Playlist;
import me.doapps.beans.API_DTO;
import me.doapps.beans.Channel_DTO;
import me.doapps.beans.PlayList_DTO;

/**
 * Created by Gantz on 3/12/14.
 */
public class Channel_Datasource {

    private Context context;
    private Interface_Playlists interface_playlists;

    public Channel_Datasource(Context context) {
        this.context = context;
    }

    private ArrayList<Channel_DTO> getChannels(){

        Channel_DTO doappsme = new Channel_DTO("doappsme","#FFFFFF",android.R.color.white,android.R.drawable.ic_menu_directions,"","DoApps");
        Channel_DTO trucoteca = new Channel_DTO("trucotecaanalisis","#FFFFFF",android.R.color.white,android.R.drawable.ic_menu_directions,"","Trucoteca");
        Channel_DTO worldofwarcraft = new Channel_DTO("WorldofWarcraft","#FFFFFF",android.R.color.white,android.R.drawable.ic_menu_directions,"","World of Warcraft");
        Channel_DTO heartstone = new Channel_DTO("PlayHearthstone","#FFFFFF",android.R.color.white,android.R.drawable.ic_menu_directions,"","Hearthstone");
        Channel_DTO starcraft = new Channel_DTO("starcraft","#FFFFFF",android.R.color.white,android.R.drawable.ic_menu_directions,"","Star Craft");

        ArrayList<Channel_DTO> channel_dtos = new ArrayList<Channel_DTO>();
        channel_dtos.add(doappsme);
        channel_dtos.add(trucoteca);
        channel_dtos.add(worldofwarcraft);
        channel_dtos.add(heartstone);
        channel_dtos.add(starcraft);

        return channel_dtos;
    }

    public void getPlayList(String idCanal) {
        String URL = "http://gdata.youtube.com/feeds/api/users/"+idCanal+"/playlists?v=2.1&orderby=updated&alt=jsonc";
        if (isOnline()) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest mRequest = new StringRequest(Request.Method.GET,
                    URL,
                    mResponseListener,
                    mErrorListener);
            queue.add(mRequest);
        } else {
            Toast.makeText(context, "Debes estar conectado a Internet...", Toast.LENGTH_SHORT).show();
        }
    }

    Response.Listener<String> mResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject mObject = new JSONObject(response);
                API_DTO api_dto = new API_DTO();
                api_dto.setDataSource(mObject);
                interface_playlists.onFinishLoad(new Adapter_Playlist(context,api_dto.getData_dto().getPlayList_dtos()),api_dto.getData_dto().getPlayList_dtos());
            } catch (JSONException e) {
                Toast.makeText(context, "Ocurrio un error, intentelo nuevamente...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError e) {
            Toast.makeText(context, "Ocurrio un error, intentelo nuevamente...", Toast.LENGTH_SHORT).show();
        }
    };

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void setInterface_playlists(Interface_Playlists interface_playlists) {
        this.interface_playlists = interface_playlists;
    }



    public interface Interface_Playlists {
        void onFinishLoad(Adapter_Playlist adapter_playlist,ArrayList<PlayList_DTO> playList_dtos);
    }
}
