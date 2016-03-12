package com.gdgvitvellore.developers.gdgvitvellore.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shalini on 22-02-2015.
 */
public class ActivitiesFeedPage {
    private String jsonString;
    private JSONObject jsonOuterObject;
    private JSONArray items;

    //get an array from an array of objects


    public ActivitiesFeedPage(){

        jsonString=null;
        jsonOuterObject=null;
    }
    public ActivitiesFeedPage(String jstring){

        jsonString=jstring;
        try {
            jsonOuterObject=new JSONObject(jstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ActivitiesFeedPage(JSONObject jobj){//one

        jsonString=null;
        jsonOuterObject=jobj;
    }
    public String getKind() {
        try {
            return jsonOuterObject.get("kind").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getTitle() {
        try {
            return jsonOuterObject.get("title").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    public String getUpdatedDate() {
        try {
            return jsonOuterObject.get("updated").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public int getItemsCount() {
        try {
            items=jsonOuterObject.getJSONArray("items");
            return items.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public JSONArray getItemsJsonArray(){
        if(items==null)
            initializeItems();
        return items;
    }
    public String getNextPageToken(){
        if(hasNextPageToken()) {
            try {
                return jsonOuterObject.getString("nextPageToken");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public JSONObject getItemsJsonObject(int i){
        if(items==null)
            initializeItems();
        try {
            return (JSONObject) items.get(i);//getjsonobject(i)==same
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void initializeItems(){
        try {
            items=jsonOuterObject.getJSONArray("items");
        } catch (JSONException e) {
            items=null;
            e.printStackTrace();
        }
    }
    public void initializeItems(String s){
        try {
            items=jsonOuterObject.getJSONArray(s);
        } catch (JSONException e) {
            items=null;
            e.printStackTrace();
        }
    }

    private boolean hasNextPageToken() {
        if(jsonOuterObject.has("nextPageToken"))
        {
            return true;
        }
        else
            return false;

    }

}
