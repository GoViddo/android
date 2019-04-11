package blockchainvideoapp.com.goviddo.goviddo.vdocipher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.vdocipher.aegis.media.ErrorDescription;
import com.vdocipher.aegis.media.Track;
import com.vdocipher.aegis.player.VdoPlayer;
import com.vdocipher.aegis.player.VdoPlayerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.activity.HomeActivity;
import blockchainvideoapp.com.goviddo.goviddo.activity.MainActivity;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.CommentsRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import de.hdodenhof.circleimageview.CircleImageView;

public class OnlinePlayerActivity extends AppCompatActivity implements VdoPlayer.InitializationListener {

    private final String TAG = "OnlinePlayerActivity";

    private VdoPlayer player;
    private VdoPlayerFragment playerFragment;
    private VdoPlayerControlView playerControlView;
    private String eventLogString = "";
    TextView mLikeBtn,mDislikeBtn,mAddList,mShare,mChannelName,mViewCount;
    TextView mSubscribe,mInvest, mTextVideoTitle, mTextDescriotion;
    Button mAdd;
    EditText mCommentBox;

    CircleImageView mChannelLogo;

    private boolean playWhenReady = true;
    private int currentOrientation;

    private volatile String mOtp;
    private volatile String mPlaybackInfo;

    public long mVideoPlayedTime = 0;
    public long mLastLoadedTime = 0;

    int comment_id;
    int user_id;
    String comment;


    LoginUserDetails mLoginUserDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate called");
        setContentView( R.layout.activity_vdo_onlineplayer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(uiVisibilityListener);


         mLoginUserDetails = new LoginUserDetails(OnlinePlayerActivity.this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (savedInstanceState != null) {
            mOtp = savedInstanceState.getString("otp");
            mPlaybackInfo = savedInstanceState.getString("playbackInfo");
        }

        playerFragment = (VdoPlayerFragment)getFragmentManager().findFragmentById(R.id.online_vdo_player_fragment);
        playerControlView = (VdoPlayerControlView)findViewById(R.id.player_control_view);

        currentOrientation = getResources().getConfiguration().orientation;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        initializePlayer();

        mLikeBtn = findViewById( R.id.txt_like );
        mDislikeBtn = findViewById( R.id.txt_dis_like );
        mAddList = findViewById( R.id.txt_add_to_list );
        mShare = findViewById( R.id.txt_share );
        mSubscribe = findViewById( R.id.btnSubscribe );
        mInvest = findViewById( R.id.btn_invest );
        mTextVideoTitle = findViewById( R.id.txt_video_title );
        mTextDescriotion = findViewById( R.id.txt_short_des );
        mChannelName = findViewById( R.id.textViewChannelName );
        mChannelLogo = findViewById( R.id.profile_image );
        mViewCount = findViewById( R.id.txt_view_count );
        mAdd = findViewById( R.id.addbtn );
        mCommentBox = findViewById( R.id.commentbox );

        String url1 = "http://178.128.173.51:3000/getVideoRelatedDetails";
        RequestQueue queue = Volley.newRequestQueue(OnlinePlayerActivity.this);
        JSONObject params1 = new JSONObject();
        try {


            params1.put( "userEmail",mLoginUserDetails.getEmail() );
            params1.put( "videoId",Utils.vdociper_id );


        }catch (JSONException e) {
            e.printStackTrace();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url1, params1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String video_Name = response.getString( "videoName" );
                    String channel_Name = response.getString( "channelName" );
                    String videoDescription = response.getString( "videoDescription" );
                    String channelLogo = response.getString( "channelLogo" );
                    int likeStatus = response.getInt( "likestatus" );
                    int subscriptionstatus = response.getInt( "subscriptionstatus" );
                    int videoViewCount = response.getInt( "viewCount" );
                    int like_count = response.getInt( "likeCount" );
                    int dislike_count = response.getInt( "dilikeCount" );

                    mTextVideoTitle.setText( video_Name );
                    mTextDescriotion.setText( videoDescription );
                    if(subscriptionstatus == 1 ){
                         mSubscribe.setText( "UnSubscribe" );
                    }
                    Picasso.with( OnlinePlayerActivity.this ).load( channelLogo ).into( mChannelLogo );
                    mChannelName.setText( channel_Name );
                    mViewCount.setText( videoViewCount + " views" );
                    mLikeBtn.setText( like_count+"" );
                    mDislikeBtn.setText( dislike_count+"" );


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put( "Content-Type", "application/json" );
                return headers;
            }
        };

        queue.add( jsonObjectRequest );








        mLikeBtn.setOnClickListener( new View.OnClickListener() {
        @Override

        public void onClick(View v) {
            String url = "http://178.128.173.51:3000/likeUnlikeSstore";
            RequestQueue queue = Volley.newRequestQueue(OnlinePlayerActivity.this);
            JSONObject params = new JSONObject();
            try {


                params.put( "emailid",mLoginUserDetails.getEmail() );

                params.put( "likestatus", 1);
                params.put( "videoid",Utils.vdociper_id );


            }catch (JSONException e) {
                  e.printStackTrace();
            }


            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString( "message" );
                    }catch (JSONException e){
                             e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i(TAG,"Error :" + error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put( "Content-Type", "application/json" );
                    return headers;
                }
            };

            queue.add( jsonObjectRequest );
        }
    });
    mDislikeBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://178.128.173.51:3000/likeUnlikeSstore";
                RequestQueue queue = Volley.newRequestQueue(OnlinePlayerActivity.this);
                JSONObject params = new JSONObject();
                try {


                    params.put( "emailid",mLoginUserDetails.getEmail() );
                    params.put( "likestatus", 0);
                    params.put( "videoid",Utils.vdociper_id );


                }catch (JSONException e) {
                    e.printStackTrace();
                }


                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString( "message" );
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG,"Error :" + error.toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put( "Content-Type", "application/json" );
                        return headers;
                    }
                };

                queue.add( jsonObjectRequest );

            }
        } );

    mAddList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( OnlinePlayerActivity.this, mLoginUserDetails.getEmail(), Toast.LENGTH_SHORT ).show();
            }
        } );


    mShare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( OnlinePlayerActivity.this,"Share", Toast.LENGTH_SHORT ).show();
            }
        } );
    mSubscribe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String url = "http://178.128.173.51:3000/subscriptChannel";

                RequestQueue queue = Volley.newRequestQueue(OnlinePlayerActivity.this);
                JSONObject params = new JSONObject();
                try {


                    params.put( "emailid",mLoginUserDetails.getEmail() );

                    if (mSubscribe.getText().toString().equalsIgnoreCase( "UnSubscribe" ))
                    {
                        params.put( "subscriptionstatus", 0 );
                    }
                    else {
                        params.put( "subscriptionstatus", 1 );
                    }
                    params.put( "videoid",Utils.vdociper_id );


                }catch (JSONException e) {
                    e.printStackTrace();
                }


                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString( "message" );

                            if(message.equalsIgnoreCase( "success" ))
                            {
                                if(mSubscribe.getText().toString().equalsIgnoreCase( "Subscribe" )) {
                                    mSubscribe.setText( "UnSubscribe" );
                                }
                                else{
                                    mSubscribe.setText( "Subscribe" );
                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i(TAG,"Error :" + error.toString());
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put( "Content-Type", "application/json" );
                        return headers;
                    }
                };

                queue.add( jsonObjectRequest );


            }
        } );
    mInvest.setVisibility( View.GONE );



    mAdd.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url = "http://178.128.173.51:3000/addComment";
            if (mCommentBox.toString() != "" && mCommentBox.length() >= 5) {


                RequestQueue queue = Volley.newRequestQueue( OnlinePlayerActivity.this );
                JSONObject params2 = new JSONObject();
                try {


                    params2.put( "emailId", mLoginUserDetails.getEmail() );
                    params2.put( "videoCipherId", Utils.vdociper_id );
                    params2.put( "comment", mCommentBox.toString() );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url, params2, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString( "messgae" );
                            System.out.println(message);

                            if (message.equalsIgnoreCase( "success" )){
                                mCommentBox.setText( "" );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i( TAG, "Error :" + error.toString() );
                    }
                } ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put( "Content-Type", "application/json" );
                        return headers;
                    }
                };

                queue.add( jsonObjectRequest );
            }

            else{
                Toast.makeText( OnlinePlayerActivity.this, "Comment Box Empty OR Comment is too Short", Toast.LENGTH_SHORT ).show();
            }
        }
    });



    String getcomment_url = "http://178.128.173.51:3000/getCommentList";

        RequestQueue queue1 = Volley.newRequestQueue( OnlinePlayerActivity.this );
        JSONObject params2 = new JSONObject();
        try {

            params2.put( "videoCipherId", Utils.vdociper_id );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ArrayList<CommentsRecyclerModel>recycler_comment = null;
        final JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest( Request.Method.GET, getcomment_url, params2, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                     String message = response.getString( "message" );
                     if(message.equalsIgnoreCase( "success" )) {
                         JSONArray obj = response.getJSONArray( "data" );

                         for (int i = 0; i < obj.length(); i++) {

                             JSONObject jsonObject = obj.getJSONObject( i );

                             comment_id = jsonObject.getInt( "comment_id" );
                             user_id = jsonObject.getInt( "userId" );
                             comment = jsonObject.getString( "comment" );

                             String profileimg_url = "http://178.128.173.51:3000/getUserProfilePics";
                             RequestQueue queueprofile = Volley.newRequestQueue( OnlinePlayerActivity.this );
                             JSONObject params = new JSONObject();
                             try {

                                 params.put( "userId", user_id);

                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                             final JsonObjectRequest jsonObjectRequestprofile = new JsonObjectRequest( Request.Method.GET, profileimg_url, params, new Response.Listener<JSONObject>() {
                                 @Override
                                 public void onResponse(JSONObject response) {
                                     try {

                                         String profilepic = response.getString( "profilPic" );
                                         String username = response.getString( "userName" );

                                         recycler_comment.add( new CommentsRecyclerModel( comment_id, user_id, comment, profilepic, username ) );
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }
                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {

                                 }
                             } );
                    }
                     }
                }

                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i( TAG, "Error :" + error.toString() );
            }
        } );

        queue1.add( jsonObjectRequest1 );


}

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart called");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop called");
        disablePlayerUI();
        super.onStop();


        Log.v("Video ID = ", Utils.vdociper_id );
        Log.v("User Emaild Id = ", mLoginUserDetails.getEmail());
        Log.v("Total Video Time = ",String.valueOf(mVideoPlayedTime));


        JSONObject params = new JSONObject();
        try {


            params.put( "vdoCipherId", Utils.vdociper_id );
            params.put( "userEmailId", mLoginUserDetails.getEmail() );
            params.put( "videoViewDuration", String.valueOf(mVideoPlayedTime) );



        } catch (JSONException e) {
            e.printStackTrace();

        }



        RequestQueue requestQueue = Volley.newRequestQueue( OnlinePlayerActivity.this );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, "http://178.128.173.51:3000/saveViewInformation", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        } ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put( "Content-Type", "application/json" );
                return headers;
            }
        };

        requestQueue.add( jsonObjectRequest );



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState called");
        super.onSaveInstanceState(outState);
        if (mOtp != null && mPlaybackInfo != null) {
            outState.putString("otp", mOtp);
            outState.putString("playbackInfo", mPlaybackInfo);
            Log.v(TAG, mPlaybackInfo);
        }
    }

    private void initializePlayer() {
        if (mOtp != null && mPlaybackInfo != null) {
            // initialize the playerFragment; a VdoPlayer instance will be received
            // in onInitializationSuccess() callback
            playerFragment.initialize(OnlinePlayerActivity.this);
            log("initializing player fragment");
        } else {
            // lets get otp and playbackInfo before creating the player
            obtainOtpAndPlaybackInfo();
        }
    }

    /**
     * Fetch (otp + playbackInfo) and initialize VdoPlayer
     * here we're fetching a sample (otp + playbackInfo)
     * TODO you need to generate/fetch (otp + playbackInfo) OR (signature + playbackInfo) for the
     * video you wish to play
     */
    private void obtainOtpAndPlaybackInfo() {
        // todo use asynctask
        log("fetching params...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Pair<String, String> pair = Utils.getSampleOtpAndPlaybackInfo();
                    mOtp = pair.first;
                    mPlaybackInfo = pair.second;
                    Log.i("OTP & Playback Info", mPlaybackInfo);
                    Log.i(TAG, "obtained new otp and playbackInfo");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initializePlayer();
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("Error fetching otp and playbackInfo: " + e.getClass().getSimpleName());
                            log("error fetching otp and playbackInfo");
                        }
                    });
                }
            }
        }).start();
    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OnlinePlayerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void log(String msg) {
        eventLogString += (msg + "\n");
    }

    private void showControls(boolean show) {
        Log.v(TAG, (show ? "show" : "hide") + " controls");
        if (show) {
            playerControlView.show();
        } else {
            playerControlView.hide();
        }
    }

    private void disablePlayerUI() {
//        showControls(false);
    }

    @Override
    public void onInitializationSuccess(VdoPlayer.PlayerHost playerHost, VdoPlayer player, boolean wasRestored) {
        Log.i(TAG, "onInitializationSuccess");
        log("onInitializationSuccess");
        this.player = player;
        player.addPlaybackEventListener(playbackListener);
        playerControlView.setPlayer(player);
        showControls(true);

        playerControlView.setFullscreenActionListener(fullscreenToggleListener);
        playerControlView.setControllerVisibilityListener(visibilityListener);

        // load a media to the player
        VdoPlayer.VdoInitParams vdoParams = new VdoPlayer.VdoInitParams.Builder()
                .setOtp(mOtp)
                .setPlaybackInfo(mPlaybackInfo)
                .setPreferredCaptionsLanguage("en")
                .build();
        player.load(vdoParams);
        log("loaded init params to player");
    }

    @Override
    public void onInitializationFailure(VdoPlayer.PlayerHost playerHost, ErrorDescription errorDescription) {
        String msg = "onInitializationFailure: errorCode = " + errorDescription.errorCode + ": " + errorDescription.errorMsg;
        log(msg);
        Log.e(TAG, msg);
        Toast.makeText(OnlinePlayerActivity.this, "initialization failure: " + errorDescription.errorMsg, Toast.LENGTH_LONG).show();
    }

    private VdoPlayer.PlaybackEventListener playbackListener = new VdoPlayer.PlaybackEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            log(Utils.playbackStateString(playWhenReady, playbackState));
            OnlinePlayerActivity.this.playWhenReady = playWhenReady;
        }

        @Override
        public void onTracksChanged(Track[] tracks, Track[] tracks1) {
            Log.i(TAG, "onTracksChanged");
            log("onTracksChanged");
        }

        @Override
        public void onBufferUpdate(long bufferTime) {}

        @Override
        public void onSeekTo(long millis) {
            Log.i(TAG, "onSeekTo: " + String.valueOf(millis));

        }

        @Override
        public void onProgress(long millis) {

            long mVideoTimeCheck = millis - mLastLoadedTime;

            if (mVideoTimeCheck > 3000)
            {
                mLastLoadedTime = millis;
            }

            mVideoPlayedTime = mVideoPlayedTime + (millis - mLastLoadedTime);
            mLastLoadedTime = millis;
        }

        @Override
        public void onPlaybackSpeedChanged(float speed) {
            Log.i(TAG, "onPlaybackSpeedChanged " + speed);
            log("onPlaybackSpeedChanged " + speed);
            Log.v(TAG, "onPlaybackSpeedChanged " + speed);

        }

        @Override
        public void onLoading(VdoPlayer.VdoInitParams vdoInitParams) {
            Log.i(TAG, "onLoading");
            log("onLoading");
        }

        @Override
        public void onLoadError(VdoPlayer.VdoInitParams vdoInitParams, ErrorDescription errorDescription) {
            String err = "onLoadError code: " + errorDescription.errorCode;
            Log.e(TAG, err);
            log(err);
        }

        @Override
        public void onLoaded(VdoPlayer.VdoInitParams vdoInitParams) {
            Log.i(TAG, "onLoaded");
            log("onLoaded");
        }

        @Override
        public void onError(VdoPlayer.VdoInitParams vdoParams, ErrorDescription errorDescription) {
            String err = "onError code " + errorDescription.errorCode + ": " + errorDescription.errorMsg;
            Log.e(TAG, err);
            log(err);
        }

        @Override
        public void onMediaEnded(VdoPlayer.VdoInitParams vdoInitParams) {
            Log.i(TAG, "onMediaEnded");
            log("onMediaEnded");
            Log.i( "onMediaEnded",vdoInitParams.playbackInfo);
            Log.v("Played Time = ", String.valueOf(mVideoPlayedTime));
        }
    };

    private VdoPlayerControlView.FullscreenActionListener fullscreenToggleListener = new VdoPlayerControlView.FullscreenActionListener() {
        @Override
        public boolean onFullscreenAction(boolean enterFullscreen) {
            showFullScreen(enterFullscreen);
            return true;
        }
    };

    private VdoPlayerControlView.ControllerVisibilityListener visibilityListener = new VdoPlayerControlView.ControllerVisibilityListener() {
        @Override
        public void onControllerVisibilityChange(int visibility) {
            Log.i(TAG, "controller visibility " + visibility);
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (visibility != View.VISIBLE) {
                    showSystemUi(false);
                }
            }
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        final int newOrientation = newConfig.orientation;
        final int oldOrientation = currentOrientation;
        currentOrientation = newOrientation;
        Log.i(TAG, "new orientation " +
                (newOrientation == Configuration.ORIENTATION_PORTRAIT ? "PORTRAIT" :
                        newOrientation == Configuration.ORIENTATION_LANDSCAPE ? "LANDSCAPE" : "UNKNOWN"));
        super.onConfigurationChanged(newConfig);
        if (newOrientation == oldOrientation) {
            Log.i(TAG, "orientation unchanged");
        } else if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // hide other views
            (findViewById(R.id.online_vdo_player_fragment)).setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            playerControlView.setFitsSystemWindows(true);
            // hide system windows
            showSystemUi(false);
            showControls(false);
        } else {
            // show other views
            (findViewById(R.id.online_vdo_player_fragment)).setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            playerControlView.setFitsSystemWindows(false);
            playerControlView.setPadding(0,0,0,0);
            // show system windows
            showSystemUi(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            showFullScreen(false);
            playerControlView.setFullscreenState(false);
        } else {
            super.onBackPressed();
        }
    }

    private void showFullScreen(boolean show) {
        Log.v(TAG, (show ? "enter" : "exit") + " fullscreen");
        if (show) {
            // go to landscape orientation for fullscreen mode
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            // go to portrait orientation
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    private void showSystemUi(boolean show) {
        Log.v(TAG, (show ? "show" : "hide") + " system ui");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!show) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    private View.OnSystemUiVisibilityChangeListener uiVisibilityListener = new View.OnSystemUiVisibilityChangeListener() {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            Log.v(TAG, "onSystemUiVisibilityChange");
            // show player controls when system ui is showing
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                Log.v(TAG, "system ui visible, making controls visible");
                showControls(true);
            }
        }
    };
}
