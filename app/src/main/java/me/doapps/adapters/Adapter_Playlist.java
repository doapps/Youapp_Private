package me.doapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.doapps.beans.PlayList_DTO;
import me.doapps.beans.PlayList_DTO;
import me.doapps.utils.RoundedTransformation;
import me.doapps.utils.Util_Fonts;
import me.doapps.youapp.R;

/**
 * Created by Gantz on 26/05/14.
 */
public class Adapter_Playlist extends BaseAdapter {

    protected ArrayList<PlayList_DTO> playList_dtos;
    protected Context context;
    private LayoutInflater layoutInflater;

    public Adapter_Playlist(Context context, ArrayList<PlayList_DTO> playList_dtos) {
        super();
        this.context = context;
        this.playList_dtos = playList_dtos;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return playList_dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return playList_dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        PlayList_DTO playList_dto = playList_dtos.get(position);

        if (playList_dto != null) {
            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.item_menu,null);

            holder.container = (LinearLayout) convertView.findViewById(R.id.item_container);
            holder.nombre = (TextView) convertView.findViewById(R.id.nombre);
            holder.imagen = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Picasso.with(context).
                load(playList_dto.getThumbnail_dto().getHqDefault()).
                centerInside().
                fit().
                transform(new RoundedTransformation(75,75)).
                into((holder.imagen));

        holder.nombre.setText(playList_dto.getTitle());
        //holder.container.setBackgroundResource(playList_dto.getColorResource());

        /**
         * Fonts
         */
        holder.nombre.setTypeface(Util_Fonts.setRobotoMedium(context));

        return convertView;
    }

    static class Holder {
        LinearLayout container;
        TextView nombre;
        ImageView imagen;
    }
}
