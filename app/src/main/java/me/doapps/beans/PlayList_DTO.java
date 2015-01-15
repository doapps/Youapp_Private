package me.doapps.beans;

import org.json.JSONException;

/**
 * Created by Gantz on 8/01/15.
 */
public class PlayList_DTO extends API_DTO {

    private String id = "id";
    private String created = "created";
    private String updated = "updated";
    private String author = "author";
    private String title = "title";
    private String description = "description";
    private String size = "size";


    public PlayList_DTO(){}

    public String getId() {
        return parseString(id,getDataSource());
    }

    public String getCreated() {
        return parseString(created,getDataSource());
    }

    public String getUpdated() {
        return parseString(updated,getDataSource());
    }

    public String getAuthor() {
        return parseString(author,getDataSource());
    }

    public String getTitle() {
        return parseString(title,getDataSource());
    }

    public String getDescription() {
        return parseString(description,getDataSource());
    }

    public int getSize() {
        return parseInt(size,getDataSource());
    }

    public Thumbnail_DTO getThumbnail_dto()  {
        try {
            Thumbnail_DTO thumbnail_dto = new Thumbnail_DTO();
            thumbnail_dto.setDataSource(getDataSource().getJSONObject("thumbnail"));
            return thumbnail_dto;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
