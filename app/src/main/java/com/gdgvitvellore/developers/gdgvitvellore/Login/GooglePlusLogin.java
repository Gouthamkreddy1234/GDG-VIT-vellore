package com.gdgvitvellore.developers.gdgvitvellore.Login;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GooglePlusLogin extends Fragment implements OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 343;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        mGoogleApiClient.connect();

    }



   /* protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();

            Log.e(TAG, "onstopdisconnect");
        }

        Log.e(TAG, "onstopconnected");
    }*/

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {

            Log.e(TAG, "resolvehasResolution");
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();

                Log.e(TAG, "resolvehasResolutioncatch");
            }
        }
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
        }

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        if (requestCode == RC_SIGN_IN) {

            Log.e(TAG, "onactivityresultRC_SIGN_IN");
            if (responseCode != Activity.RESULT_OK) {
                mSignInClicked = false;

                Log.e(TAG, "onactivityresultRsultOK");
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();

                Log.e(TAG, "onactivityresultisconnecting");
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;

        Log.e(TAG, "onconnected");
        Toast.makeText(getActivity(), "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);

    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {

        Log.e(TAG, "updateui");
        if (isSignedIn) {
            Log.e(TAG, "updateuisignedin");
        } else {
            Log.e(TAG, "updateuisignedout");
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
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


            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {

        Log.e(TAG, "onconnectionsuspended");
        mGoogleApiClient.connect();
        updateUI(false);
    }


    /**
     * Button on click listener
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;
        }
    }

    /**
     * Sign-in into google
     */
    private void signInWithGplus() {
        Log.e(TAG, "signinwithgpluss");

        if (!mGoogleApiClient.isConnecting()) {
            Log.e(TAG, "signinwithgplussisnotconnecting");
            mSignInClicked = true;
            resolveSignInError();
        }

        Log.e(TAG, "signinwithgplussisconnecting");
    }

    /**
     * Sign-out from google
     */
    private void signOutFromGplus() {
        Log.e(TAG, "signoutwithgpluss");
        if (mGoogleApiClient.isConnected()) {
            Log.e(TAG, "signoutwithgplussisconnected");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }

        Log.e(TAG, "signoutwithgplussisnotconnected");
    }

    /**
     * Revoking access from google
     */
    private void revokeGplusAccess() {

        Log.e(TAG, "revokewithgpluss");
        if (mGoogleApiClient.isConnected()) {
            Log.e(TAG, "revokewithgplussisconnected");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }

    /**
     * Background Async task to load user profile picture from url
     * */
}