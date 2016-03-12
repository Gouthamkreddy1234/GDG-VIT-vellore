package com.gdgvitvellore.developers.gdgvitvellore.Events;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gautam on 3/14/2015.
 */
public class EventsParser {

    private String jsonString;
    private JSONObject jsonOuterObject, activityActor, activityObject;
    private JSONArray jsonOuterArray;
    private JSONArray objectAttachments;
    private HashMap<String,List<String>> hashMap;


    public EventsParser() {

        jsonString = null;
        jsonOuterArray = null;
        jsonOuterObject = null;
    }

    public EventsParser(String jstring) {

        jsonString = jstring;
        jsonOuterArray = null;
        try {
            jsonOuterObject = new JSONObject(jstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public EventsParser(JSONObject jobj) {//this is the main response

        jsonString = null;
        jsonOuterArray = null;
        jsonOuterObject = jobj;
    }

    public EventsParser(JSONArray eventsarr) {//this is the events array inside the response

        jsonString = null;
        jsonOuterArray = eventsarr;
        jsonOuterObject = null;
    }

    //---------------done init-------------------------------------
    public String getDate() {
        String temp=null;

            try {

                temp = jsonOuterObject.getString("date").toString();//date works fine!!
                Log.d("date", temp);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        return temp;
    }

    public String getName() {
        String temp=null;

        try {

            temp = jsonOuterObject.getString("name").toString();//date works fine!!
            Log.d("name", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }


    public String getDescription() {
        String temp=new String();

        try {

            temp = jsonOuterObject.getString("description").toString();//date works fine!!
            Log.d("description", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public String getImageurl() {
        String temp=new String();
        JSONObject jsoninnerobObject = null;

        try {

            jsoninnerobObject = jsonOuterObject.getJSONObject("image");//date works fine!!
            temp = jsoninnerobObject.getString("src").toString();
            Log.d("url", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public String getImageheight() {
        String temp=new String();

        try {

            temp = jsonOuterObject.getString("date").toString();//date works fine!!
            Log.d("date", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public String getwidth() {
        String temp=new String();

        try {

            temp = jsonOuterObject.getString("date").toString();//date works fine!!
            Log.d("date", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }


    public String getImagename() {
        String temp=new String();

        try {

            temp = jsonOuterObject.getString("date").toString();//date works fine!!
            Log.d("date", temp);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return temp;
    }


}




