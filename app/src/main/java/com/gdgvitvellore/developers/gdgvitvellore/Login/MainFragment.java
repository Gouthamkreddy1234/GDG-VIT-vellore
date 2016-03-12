package com.gdgvitvellore.developers.gdgvitvellore.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;//singleton class-->Important
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.gdgvitvellore.developers.gdgvitvellore.Volley.SplashActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gautam on 2/17/2015.
 */
public class MainFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private UiLifecycleHelper uiLifecycleHelper;
    private URL image_value1 = null;
    String action="",social="";
    private LoginButton fbButton;
    private SignInButton gpButton;
    private TextView skip;
    private Activity mActivity;


    public static final int RC_SIGN_IN = 20;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 343;

    // Google client to interact with Google API
    public static GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    public static boolean mIntentInProgress;

    public static boolean mSignInClicked;

    public static Activity context;

    private static ConnectionResult mConnectionResult;



    public MainFragment() {

        Log.d("fragment", "cons0");
    }
       @SuppressLint("ValidFragment")
    public MainFragment(Activity a)
    {
        context=a;
    }


  /*  @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("fragment", "oncreateview");
        View view = inflater.inflate(R.layout.login_activity, container, false);//the login button is the the fragment


        fbButton = (LoginButton) view.findViewById(R.id.fbButton);//crete this instance before i return the view
        gpButton = (SignInButton) view.findViewById(R.id.gplusButton);//crete this instance before i return the view
        skip=(TextView)view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "skipclicked");
                startActivity(new Intent(getActivity(),HomeActivity.class));
                getActivity().finish();

            }
        });
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "fbbuttonclicked");
                doBatchRequest();

            }
        });
        gpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "gpbuttonclicked");
                signInWithGplus();

            }
        });


        fbButton.setReadPermissions("email", "user_friends");
        fbButton.setReadPermissions(Arrays.asList("email", "user_location", "user_birthday", "user_likes"));
        fbButton.setFragment(this);

        return view;
    }*/

    public static void signInWithGplus() {
        Log.e(TAG, "signinwithgpluss");

        if (!mGoogleApiClient.isConnecting()) {
            Log.e(TAG, "signinwithgplussisnotconnecting");
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    private static void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {

            Log.e(TAG, "resolvehasResolution");
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(context, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();

                Log.e(TAG, "resolvehasResolutioncatch");
            }
        }
    }

    private static void doBatchRequest() {


        Log.e("facebook", "dobatchrequest");
        String[] requestIds = {"me", "4"};

        RequestBatch requestBatch = new RequestBatch();
        for (final String requestId : requestIds) {
            requestBatch.add(new Request(Session.getActiveSession(),
                    requestId, null, null, new Request.Callback() {
                public void onCompleted(Response response) {

                    Log.e("dobatchrequest", "requestbatchoncompleted");
                    GraphObject graphObject = response.getGraphObject();

                    if (graphObject != null) {

                        Log.e("dobatchrequest", "graphobjectnull");

                        Log.d("id", graphObject.getProperty("id").toString());

                    }


                }
            }));

        }
        requestBatch.executeAsync();
    }
    public static void signInWithFacebook(){
        doBatchRequest();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        Log.i("Facebook", "onsessionstatechanged");

        if (state.isOpened()) {
            Log.i("onsessionstatechanged", "stateisopened");

            NewMeRequestReprise newMeRequestReprise= new NewMeRequestReprise(getActivity(),Request.newMeRequest(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        Log.i("onsessionstatechanged", "oncompleteduser!null");
                        GraphObject graphObject = response.getGraphObject();
                       /* if(action.equals("Logout")) {
                            if(social.equals("fb")){
                                callFacebookLogout(getActivity());
                            }

                        }*/

                            Log.i("Facebook", "fbbbbbbbbbbbbbbbb");
                            Intent intent = new Intent(getActivity(),HomeActivity.class);
                            intent.putExtra("fbBundle",buildUserInfoDisplay(user));
                            startActivity(intent);
                            getActivity().finish();


                        Log.d("response", response.toString());
                        Log.d("id", graphObject.getProperty("id").toString());
                        String id = graphObject.getProperty("id").toString();

                        URL image_value = null;
                        try {
                            image_value = new URL("https://graph.facebook.com/" + id + "/picture");
                            image_value1 = image_value;
                            Log.d("dpurl", image_value.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }


                    }
                }

            }));//async
            newMeRequestReprise.checkConnectionAndExecuteAsync();

       /*     try {
              Bitmap bitmap = downloadBitmap(image_value1.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            //Fragment class

        } else if (state.isClosed()) {
            Log.i("onsessionstatechanged", "stateisclosed");

        }
    }

   /* private Bitmap downloadBitmap(String url) throws IOException {
        HttpUriRequest request = new HttpGet(url.toString());
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(entity);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
                    bytes.length);
            return bitmap;
        } else {
            throw new IOException("Download failed, HTTP response code "
                    + statusCode + " - " + statusLine.getReasonPhrase());
        }
    }*/

    //outside the onsessionstatechange() which calls the onsessionstatechange() interface-->
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.i("callback", "call");
            onSessionStateChange(session, state, exception);
        }
    };




    public void onCreate(Bundle savedInstanceState) {//main_oncreate-->fragment-->oncreate()-->fragment-->oncreateview()
        Log.i("fragment", "oncreate");
        super.onCreate(savedInstanceState);
        uiLifecycleHelper = new UiLifecycleHelper(getActivity(), callback);
        uiLifecycleHelper.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        mGoogleApiClient.connect();
      /*  if(action.equals("Logout")){
            if(social.equals("fb")){
                callFacebookLogout(getActivity());
            }
            else if(social.equals("gplus")){
                signOutFromGplus();
            }
        }*/



    }
    public static void signOutFromGplus() {
        Log.e(TAG, "signoutwithgpluss");
        if (mGoogleApiClient.isConnected()) {
            Log.e(TAG, "signoutwithgplussisconnected");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }

        Log.e(TAG, "signoutwithgplussisnotconnected");
    }


    @Override
    public void onResume() {

        Log.i("freagment", "onresume");
        super.onResume();
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {

            Log.i("onresume", "session!null");
            onSessionStateChange(session, session.getState(), null);
        }

        uiLifecycleHelper.onResume();
        if(!mGoogleApiClient.isConnecting())
        mGoogleApiClient.connect();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("freagment", "onactivityresult");

        if (requestCode == RC_SIGN_IN) {

            Log.e(TAG, "onactivityresultRC_SIGN_IN");
            if (resultCode != Activity.RESULT_OK) {
                mSignInClicked = false;

                Log.e(TAG, "onactivityresultRsultOK");
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();

                Log.e(TAG, "onactivityresultisconnecting");
            }
        }

        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
        Log.i("onactivityresult", "fb");
    }

    @Override
    public void onPause() {
        Log.i("freagment", "onpause");
        super.onPause();
        uiLifecycleHelper.onPause();
    }

    @Override
    public void onDestroy() {
        Log.i("freagment", "ondestroy");
        super.onDestroy();
        uiLifecycleHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("freagment", "onsavedinsteancestate");
        super.onSaveInstanceState(outState);
        uiLifecycleHelper.onSaveInstanceState(outState);
    }

    private Bundle buildUserInfoDisplay(GraphUser user) {
        Log.i("freagment", "buildinfouserdiaplay");
        String image_value = "https://graph.facebook.com/" + user.getId() + "/picture?width=9999";
        Bundle b=new Bundle();
        b.putString("name",user.getName());
        b.putString("image",image_value);
        b.putString("email",user.asMap().get("email").toString());
        b.putString("social","fb");
        /*StringBuffer userInfo = new StringBuffer("");

        // Example: typed access (name)
        // - no special permissions required
        userInfo.append(String.format("Name: %s\n\n",
                user.getName()));

        // Example: typed access (birthday)
        // - requires user_birthday permission
        userInfo.append(String.format("Birthday: %s\n\n",
                user.getBirthday()));

        // Example: partially typed access, to location field,
        // name key (location)
        // - requires user_location permission
        userInfo.append(String.format("Location: %s\n\n",
                user.getLocation().getProperty("name")));

        // Example: access via property name (locale)
        // - no special permissions required
        userInfo.append(String.format("Locale: %s\n\n",
                user.getProperty("locale")));

        // Example: access via key for array (languages)
        // - requires user_likes permission
        //normal parsing
        JSONArray languages = (JSONArray)user.getProperty("languages");
        if (languages.length() > 0) {
            ArrayList<String> languageNames = new ArrayList<String> ();
            for (int i=0; i < languages.length(); i++) {
                JSONObject language = languages.optJSONObject(i);
                // Add the language name to a list. Use JSON
                // methods to get access to the name field.
                languageNames.add(language.optString("name"));
            }
            userInfo.append(String.format("Languages: %s\n\n",
                    languageNames.toString()));
        }*/

        return b;
    }


    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;

        Log.e(TAG, "onconnected");
        Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        /*if(action.equals("Logout")) {
            if(social.equals("gplus")){
                    signOutFromGplus();
                }

        }*/

            Log.i("Google", "gppppppppppppp");
            Intent intent = new Intent();
            intent.putExtra("gpBundle", getProfileInformation());
            intent.setClass(getActivity().getApplicationContext(), com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity.class);
            startActivity(intent);
            getActivity().finish();

        // Update the UI after signin

    }

    private Bundle getProfileInformation() {
        try {
            Log.e(TAG, "getprofileinfo");
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                Bundle b=new Bundle();
                b.putString("name",personName);
                b.putString("image",personPhotoUrl);
                b.putString("email",email);
                b.putString("social","gplus");
                return b;
            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(TAG, "onconnectionsuspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        Log.e(TAG, "onconnectionfailed");
        if (!result.hasResolution()) {

            Log.e(TAG, "onconnfailednoresolution");
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), getActivity(),
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage

            Log.e(TAG, "onconnfailed!mintentinprogress");
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all

                Log.e(TAG, "onconnfailed!mintentinprogressmsighinclicked");
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
            //Log.e(TAG, getActivity().getLocalClassName());
            try {
                if (getActivity().getLocalClassName() != null)
                    if (getActivity().getLocalClassName().equals("Volley.SplashActivity")) {
                        Session session = Session.getActiveSession();
                        if (!(session != null &&
                                (session.isOpened()))) {

                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));

                            //Log.i("onresume", "session!null");
                            //   onSessionStateChange(session, session.getState(), null);
                        }
                    }
            }
            catch (Exception e) {
            e.printStackTrace();
            }

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=activity;
    }

    public static void callFacebookLogout(Context context) {
        Log.i("freagment", "callfacebooklogout");
        Session session = Session.getActiveSession();//Session is a singleton over here-->important
        if (session != null) {

            Log.i("callfacebooklogout", "session!null");
            if (!session.isClosed()) {
                Log.i("callfacebooklogout", "sessionis!closed");
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            Log.i("callfacebooklogout", "sessionnull");
            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }
    }
}


       /* authButton.setBackgroundResource(R.drawable.drbg);
        authButton.setText("");
        authButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);*/