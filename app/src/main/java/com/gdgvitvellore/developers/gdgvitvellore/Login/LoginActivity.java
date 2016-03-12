package com.gdgvitvellore.developers.gdgvitvellore.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//import com.androidsocialnetworks.lib.SocialNetworkManager;
//import com.androidsocialnetworks.lib.listener.OnLoginCompleteListener;
import com.facebook.widget.LoginButton;
import com.gdgvitvellore.developers.gdgvitvellore.Feeds.HomeActivity;
import com.gdgvitvellore.developers.gdgvitvellore.R;
import com.google.android.gms.common.SignInButton;

import java.util.Arrays;

/**
 * Created by Gautam on 2/17/2015.
 */
public class LoginActivity extends FragmentActivity{
    private MainFragment mainFragment;
    //private SocialNetworkManager mSocialNetworkManager;
    private final String SOCIAL_NETWORK_TAG="social";
    private LoginButton fbButton;
    private SignInButton gpButton;
    private String action="";
    private String social="";
    private TextView skip;
    //FACEBOOK activity

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pass the conten
            setContentView(R.layout.login_activity);
        fbButton = (LoginButton) findViewById(R.id.fbButton);//crete this instance before i return the view
        gpButton = (SignInButton) findViewById(R.id.gplusButton);//crete this instance before i return the view
        skip=(TextView)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "skipclicked");
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();

            }
        });
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "fbbuttonclicked");
                MainFragment.signInWithFacebook();

            }
        });
        gpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("fragment", "gpbuttonclicked");
                MainFragment.signInWithGplus();

            }
        });



        Intent intent=getIntent();

        try {

            if (savedInstanceState == null) {
                // Add the fragment on initial activity setup

                mainFragment = new MainFragment(this);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, mainFragment)
                        .commit();
            }

            else {
                    // Or set the fragment from restored state info
                    mainFragment = (MainFragment) getSupportFragmentManager()
                            .findFragmentById(android.R.id.content);
                }

        }catch (Exception e) {
            e.printStackTrace();
        }
    /*    mSocialNetworkManager = (SocialNetworkManager) getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = SocialNetworkManager.Builder.from(this)
            .facebook()
                    .googlePlus()
                    .build();
            getSupportFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
        }*/
        fbButton.setReadPermissions("email", "user_friends");
        fbButton.setReadPermissions(Arrays.asList("email", "user_location", "user_birthday", "user_likes"));
        fbButton.setFragment(mainFragment);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);//home here refers to android home not app_home
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   /* @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fbButton){
            mSocialNetworkManager.getFacebookSocialNetwork().requestLogin(new OnLoginCompleteListener() {
                @Override
                public void onLoginSuccess(int socialNetworkID) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }

                @Override
                public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                                        finish();
                }
            });
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("freagment", "onactivityresult");

        if (requestCode == MainFragment.RC_SIGN_IN) {

            Log.e("activityresult", "onactivityresultRC_SIGN_IN");
            if (resultCode != Activity.RESULT_OK) {
                MainFragment.mSignInClicked = false;

                Log.e("activityresult", "onactivityresultRsultOK");
            }

            MainFragment.mIntentInProgress = false;

            if (!MainFragment.mGoogleApiClient.isConnecting()) {
                MainFragment.mGoogleApiClient.connect();

                Log.e("activityresult", "onactivityresultisconnecting");
            }
        }

    }
}
