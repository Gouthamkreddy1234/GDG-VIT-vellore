package com.gdgvitvellore.developers.gdgvitvellore.Parser;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gdgvitvellore.developers.gdgvitvellore.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shalini on 22-02-2015.
 */
public class ParsedActivity {
    private String jsonString;
    private JSONObject jsonOuterObject,activityActor,activityObject;
    private JSONArray jsonOuterArray;
    private JSONArray objectAttachments;
    private List<HashMap<String,String>> attachmentsList;

    public ParsedActivity(){

        jsonString=null;
        jsonOuterArray=null;
        jsonOuterObject=null;
    }
    public ParsedActivity(String jstring){

        jsonString=jstring;
        jsonOuterArray=null;
        try {
            jsonOuterObject=new JSONObject(jstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ParsedActivity(JSONObject jobj){

        jsonString=null;
        jsonOuterArray=null;
        jsonOuterObject=jobj;
    }
    public ParsedActivity(JSONArray jarr){

        jsonString=null;
        jsonOuterArray=jarr;
        jsonOuterObject=null;
    }
    public String getTitle() {
        try {
            return jsonOuterObject.get("title").toString();//tagname with title
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getPublishedDate() {
        try {
            return jsonOuterObject.get("published").toString();
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
    public String getActivityUrl() {
        try {
            return jsonOuterObject.get("url").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public void initializeActor(){
        try {
            activityActor=jsonOuterObject.getJSONObject("actor");
        } catch (JSONException e) {
            activityActor=null;
            e.printStackTrace();
        }
    }
    public void initializeObject(){
        try {
            activityObject=jsonOuterObject.getJSONObject("object");
        } catch (JSONException e) {
            activityActor=null;
            e.printStackTrace();
        }
    }
    public void initializeAttachments(){
        try {
            objectAttachments=activityObject.getJSONArray("attachments");//getting the array of objects starting with [ into the objectattachments
            // json array-->v v important
            Log.d("attacharray", String.valueOf(objectAttachments.length()));
        } catch (JSONException e) {
            objectAttachments=null;
            e.printStackTrace();
        }
    }
    public String getActorDisplayName() {
        try {
            return activityActor.get("displayName").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getActorUrl() {
        try {
            return activityActor.get("url").toString();
        } catch (JSONException e) {
            return e.getMessage();
        }
    }
    public String getActorImageUrl() {
        String actorImageUrl;
        try {
            actorImageUrl= activityActor.getJSONObject("image").getString("url");
        } catch (JSONException e) {
            return e.getMessage();
        }
        return actorImageUrl;
    }
    /*public Bitmap getImageBitmap(String url) {
        Bitmap image=getImageLoaderResponse(url).getBitmap();
        return image;
    }*/
    public void setImageBitmap(String imgurl, ImageView image)
    {
        final ImageView im=image;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        final ImageLoader.ImageContainer[] res = new ImageLoader.ImageContainer[1];
        imageLoader.get(imgurl, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ImageLoader", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    getImageBitmap(response);
                }
            }
            public void getImageBitmap(ImageLoader.ImageContainer response1) {
                Bitmap image1=response1.getBitmap();
                im.setImageBitmap(image1);
            }
        });
    }

    public JSONObject getActivityObject() {
        try {
            activityObject=jsonOuterObject.getJSONObject("object");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return activityObject;
    }
    public String getActivityObjectOriginalContent() {
        try {
            return activityObject.get("originalContent").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getActivityObjectContent() {
        try {
            return activityObject.get("content").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getActivityObjectType() {
        try {
            return activityObject.get("type").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getActivityObjectLinkedUrl() {
        try {
            return activityObject.get("url").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getActivityRepliesCount() {
        try {
            return activityObject.getJSONObject("replies").getInt("totalItems");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getActivityPlusOnesCount() {
        try {
            return activityObject.getJSONObject("plusoners").getInt("totalItems");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getActivityResharesCount() {
        try {
            return activityObject.getJSONObject("resharers").getInt("totalItems");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getAttachmentsCount() {
        Log.d("length",String.valueOf(objectAttachments.length()));
        return objectAttachments.length();
    }

    public JSONArray getObjectAttachmentsArray() {
        return objectAttachments;
    }

    public JSONObject getAttachment(int i) {
        JSONObject obj;
        try {
            obj= (JSONObject) objectAttachments.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    public List<HashMap<String,String>> getAttachmentsListMap() {//already extracting

        try {
            attachmentsList=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<objectAttachments.length();i++)
        {
            JSONObject attach= null;

            attach = (JSONObject) objectAttachments.get(i);//get the first object form the array of objects
            Log.d("attach object",attach.toString());//objetc attachments is not null

            HashMap<String,String> data=new HashMap<String,String>();
            data.put("objectType",attach.getString("objectType"));
            data.put("displayName",attach.getString("displayName"));
            data.put("content",attach.getString("content"));
            data.put("url",attach.getString("url"));
            if(hasAttachmentImage(attach))//number 1
            {
                JSONObject image=attach.getJSONObject("image");//image becomes the object(in the json file)
                data.put("imageUrl",image.getString("url"));//"url" becomes the Stirng inside the image tag
                Log.d("gouthamurl",image.getJSONObject("url").toString());//should dtart with an https
                data.put("imageType",image.getString("type"));
                data.put("imageHeight",image.getString("height"));
                data.put("imageWidth",image.getString("width"));
            }
            if(hasAttachmentFullImage(attach))//number 2
            {
                JSONObject fullImage=attach.getJSONObject("fullImage");
                data.put("fullImageUrl",fullImage.getString("url"));
                data.put("fullImageType",fullImage.getString("type"));
                data.put("fullImageHEight",fullImage.getString("height"));
                data.put("fullImageWidth",fullImage.getString("width"));
            }
            if(hasAttachmentEmbed(attach))//number 3
            {
                JSONObject embed=attach.getJSONObject("embed");
                data.put("embedUrl",embed.getString("url"));
                data.put("embedType",embed.getString("type"));
            }

            if(hasAttachmentThumbnails(attach))//number 4
            {
                JSONArray thumbnails=attach.getJSONArray("thumbnails");
                data.put("thumbnailCount",String.valueOf(thumbnails.length()));
            }
            attachmentsList.add(data);
         //   Log.d("List",attachmentsList.get(1).get("imageUrl"));

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attachmentsList;

    }
    public String getAttachmentImageUrl(JSONObject attach) {
        if(hasAttachmentImage(attach))
        {
            String url;
            try {

                url= attach.getJSONObject("image").getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return url;
        }
        return null;
    }


    public String getAttachmentImageHeight(JSONObject attach) {
        if(hasAttachmentImage(attach))
        {
            String height;
            try {
                height= attach.getJSONObject("image").getString("height");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return height;
        }
        return null;
    }
    public String getAttachmeTntImageWidth(JSONObject attach) {
        if(hasAttachmentImage(attach))
        {
            String width;
            try {
                width= attach.getJSONObject("image").getString("width");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return width;
        }
        return null;
    }
    public String getAttachmentFullImageUrl(JSONObject attach) {
        if(hasAttachmentFullImage(attach))
        {
            String url;
            try {
                url= attach.getJSONObject("fullImage").getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return url;
        }
        return null;
    }
    public String getAttachmentFullImageHeight(JSONObject attach) {
        if(hasAttachmentFullImage(attach))
        {
            String height;
            try {
                height= attach.getJSONObject("fullImage").getString("height");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return height;
        }
        return null;
    }
    public String getAttachmentFullImageWidth(JSONObject attach) {
        if(hasAttachmentFullImage(attach))
        {
            String width;
            try {
                width= attach.getJSONObject("fullImage").getString("width");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return width;
        }
        return null;
    }
    public String getAttachmentEmbedUrl(JSONObject attach) {
        if(hasAttachmentEmbed(attach))
        {
            String url;
            try {
                url= attach.getJSONObject("embed").getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return url;
        }
        return null;
    }
    public String getAttachmentEmbedType(JSONObject attach) {
        if(hasAttachmentEmbed(attach))
        {
            String type;
            try {
                type= attach.getJSONObject("embed").getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return type;
        }
        return null;
    }
    public List<HashMap<String,String>> getAttachmentsThumbNailsList(JSONObject attach) {
        List<HashMap<String,String>> thumbnailList=new ArrayList<HashMap<String, String>>();
        JSONArray thumbnails;
        try {
        if(hasAttachmentThumbnails(attach)) {
            thumbnails = attach.getJSONArray("thumbnails");

            for (int i = 0; i < thumbnails.length(); i++) {
                JSONObject nail = null;

                nail = (JSONObject) thumbnails.get(i);

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("description", nail.getString("content"));
                data.put("url", nail.getString("url"));
                JSONObject image = nail.getJSONObject("image");
                data.put("imageUrl", image.getString("url"));
                data.put("imageType", image.getString("type"));
                data.put("imageHeight", image.getString("height"));
                data.put("imageWidth", image.getString("width"));
                thumbnailList.add(data);

            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return thumbnailList;
    }
    public boolean hasAttachmentImage(JSONObject attach) {
        if(attach.has("image"))
        {
            return true;
        }
        else
            return false;

    }
    public boolean hasAttachmentFullImage(JSONObject attach) {
        if(attach.has("fullImage"))
        {
            return true;
        }
        else
            return false;

    }
    public boolean hasAttachmentEmbed(JSONObject attach) {
        if(attach.has("embed"))
        {
            return true;
        }
        else
            return false;

    }
    public boolean hasAttachmentThumbnails(JSONObject attach) {
        if(attach.has("thumbnails"))
        {
            return true;
        }
        else
            return false;

    }


}
