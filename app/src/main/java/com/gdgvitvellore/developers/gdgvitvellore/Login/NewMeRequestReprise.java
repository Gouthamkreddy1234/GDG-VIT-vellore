package com.gdgvitvellore.developers.gdgvitvellore.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Request;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by shalini on 14-04-2015.
 */
public class NewMeRequestReprise extends Request {
    private Activity context=null;
    private String url="http://www.google.com";
    private boolean mResult=false;
    private Request newMeRequest=null;


    public NewMeRequestReprise(Activity c,Request req){
        context=c;
        newMeRequest=req;
    }
    public void checkConnectionAndExecuteAsync() {

            new Checknet().execute();


    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }


    private class Checknet extends AsyncTask<Void, Void,String> {
        @Override
        protected String doInBackground(Void... params) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni == null) {
                    // There are no active networks.
                    return "false";
                } else{
                    if(isInternetAvailable())
                    return "true";
                    else
                        return "false";
                }

            }


        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if(result.equals("true")){
                    newMeRequest.executeAsync();
                }
                else{
                    Toast.makeText(context,"Check your internet connection",Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,"Failed to login to facebook",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,HomeActivity.class);
                    context.startActivity(intent);
                    context.finish();
                }

        }
    }
}


