package com.gdgvitvellore.developers.gdgvitvellore.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shalini on 14-03-2015.
 */
public class ProjectsParser {

    private String jsonString;
    private JSONObject jsonOuterObject;
    private JSONArray jsonOuterArray;
    private JSONArray imagesArray;
    private List<HashMap<String,String>> attachmentsList;

    public ProjectsParser(){

        jsonString=null;
        jsonOuterArray=null;
        jsonOuterObject=null;
    }
    public ProjectsParser(String jstring){

        jsonString=jstring;
        jsonOuterArray=null;
        try {
            jsonOuterObject=new JSONObject(jstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ProjectsParser(JSONObject jobj){

        jsonString=null;
        jsonOuterArray=null;
        jsonOuterObject=jobj;
    }
    public ProjectsParser(JSONArray jarr){

        jsonString=null;
        jsonOuterArray=jarr;
        jsonOuterObject=null;
    }
    public String getId() {
        try {
            return jsonOuterObject.get("id").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getName() {
        try {
            return jsonOuterObject.get("name").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getDate() {
        try {
            return jsonOuterObject.get("date").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getProjectLead() {
        try {
            return jsonOuterObject.get("projectlead").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getStartTime() {
        try {
            return jsonOuterObject.get("starttime").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getEndTime() {
        try {
            return jsonOuterObject.get("endtime").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getLongDescription() {
        try {
            return jsonOuterObject.get("longdescription").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getShortDescription() {
        try {
            return jsonOuterObject.get("shortdescription").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getBudget() {
        try {
            return jsonOuterObject.get("budget").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getStatus() {
        try {
            return jsonOuterObject.get("status").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getCategory() {
        try {
            return jsonOuterObject.get("category").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }

    public String[] getProjectTeamListArray() {
        JSONArray teamJson=null;
        try {
             teamJson= jsonOuterObject.getJSONArray("projectteam");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(teamJson!=null){
            String[] s;
            s=new String[teamJson.length()];
            for(int i=0;i<teamJson.length();i++)
            {
                try {
                    s[i]=teamJson.get(i).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return s;
        }

        return null;

    }
    public String getProjectTeamListString() {
        JSONArray teamJson=null;
        try {
            teamJson= jsonOuterObject.getJSONArray("projectteam");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(teamJson!=null){
            String s=null;
            for(int i=0;i<teamJson.length();i++)
            {
                try {
                    if(i<teamJson.length()-1)

                    s+=teamJson.get(i).toString()+",";

                else
                {
                    s+=teamJson.get(i).toString();
                }
                } catch (JSONException e) {
                e.printStackTrace();
            }
            }
            return s;
        }

        return null;

    }
    public void initializeImages(){
        try {
            imagesArray=jsonOuterObject.getJSONArray("image");
        } catch (JSONException e) {
            imagesArray=null;
            e.printStackTrace();
        }
    }
    public int getImageCount() {

            return imagesArray.length();
    }
    public JSONObject getImageObject(int i) {
        JSONObject obj;
        try {
            obj= (JSONObject) imagesArray.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }
    public String getDisplayImageUrl() {
        String url;
        try {
            url= ((JSONObject) imagesArray.get(1)).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }
    public String getContentImageUrl() {
        String url;
        try {
            url= ((JSONObject) imagesArray.get(0)).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }
    public HashMap<String,String> getImageAttributes(JSONObject imgobj) {
        HashMap<String,String> imgatt=new HashMap<String, String>();
        try {
                    imgatt.put("description", imgobj.getString("description"));
                    imgatt.put("url", imgobj.getString("url"));
                    imgatt.put("name", imgobj.getString("name"));
                    imgatt.put("height", imgobj.getString("height"));
                    imgatt.put("width", imgobj.getString("width"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imgatt;
    }


}
