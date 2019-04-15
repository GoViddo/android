package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterSubscription;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterSubscriptionCard;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.EndlessRecyclerViewScrollListner;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionCardLoadData;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionRecyclerModel;

public class SubscriptionFragment extends Fragment {

    public static int LOAD_LIMIT = 10;

    public SubscriptionFragment() {
        // Required empty public constructor
    }

    private boolean itShouldLoadMore = true;
    RecyclerView mRecyclerViewPreview;

    private RecyclerAdapterSubscription mRecyclerAdapterPreview;
    private ArrayList<SubscriptionRecyclerModel> mRecyclerModelsPreview;

    LinearLayoutManager mLayoutManager;


    RecyclerView mRecyclerView;

    private RecyclerAdapterSubscriptionCard mRecyclerAdapter;
    private ArrayList<SubscriptionRecyclerModel> mRecyclerModels;

    LinearLayoutManager mLinearLayoutManager;
    public String lastId;

    public int Channel_Id;
    int videoId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.subscription_fragment, container, false );

        mRecyclerModelsPreview = new ArrayList<SubscriptionRecyclerModel>();
        mRecyclerModels = new ArrayList<SubscriptionRecyclerModel>();

        String url = "http://178.128.173.51:3000/getSubscriptionList";
        final RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );

        mRecyclerAdapterPreview = new RecyclerAdapterSubscription( mRecyclerModelsPreview );
        mRecyclerAdapter  = new RecyclerAdapterSubscriptionCard( mRecyclerModels );

        mRecyclerView = view.findViewById( R.id.recycle_subscribe_cardvideo );

        mRecyclerViewPreview = view.findViewById( R.id.recycle_subscribe_roundimg );

        mLayoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );

        mRecyclerViewPreview.setLayoutManager( mLayoutManager );
        mRecyclerViewPreview.setHasFixedSize( true );


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );

        mRecyclerView.setLayoutManager( mLayoutManager1 );
        mRecyclerView.setHasFixedSize( true );

        //we can now set adapter to recyclerView;



        LoginUserDetails loginUserDetails = new LoginUserDetails(getActivity());

        JSONObject params = new JSONObject();
        try {


            params.put( "emailId", loginUserDetails.getEmail() );
            System.out.println( params.toString() );


        } catch (JSONException e) {
            e.printStackTrace();

        }


        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest( Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //              progressDialog.dismiss();
                // remember here we are in the main thread, that means,
                //volley has finished processing request, and we have our response.
                // What else are you waiting for? update itShouldLoadMore = true;
                itShouldLoadMore = true;
                System.out.println( response.toString() );
                try {


                    String msg = response.getString( "message" );
                    JSONArray data = response.getJSONArray( "data" );
                    for (int i = 0; i < data.length(); i++) {


                        JSONObject jsonObject = data.getJSONObject( i );


                        if (i== 0 && SubscriptionCardLoadData.getSubscription_id() == 0){
                            Channel_Id = jsonObject.getInt( "video_id" );
                            SubscriptionCardLoadData.setSubscription_id(Channel_Id);
                        }



                        videoId = jsonObject.getInt( "video_id" );


                        lastId = String.valueOf( videoId );



                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("channelId", videoId);

                        String channleListUrl = "http://178.128.173.51:3000/getChannelList";

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, channleListUrl, jsonObject1, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {

                                    System.out.println(response.toString());

                                    int channelId = response.getInt("channelId");
                                    String sliderImage = response.getString("slider_image");
                                    String shortenText = response.getString("shorten_text");
                                    String vdoCipherId = "";

                                    mRecyclerModelsPreview.add( new SubscriptionRecyclerModel( channelId, sliderImage, shortenText, vdoCipherId ) );

                                    mRecyclerViewPreview.setAdapter( mRecyclerAdapterPreview );
                                    mRecyclerAdapterPreview.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        Volley.newRequestQueue( getActivity() ).add( jsonObjectRequest );



                    }






                    JSONObject params1 = new JSONObject();
                    try {

                        Channel_Id = SubscriptionCardLoadData.getSubscription_id();
                        params1.put( "channelId",Channel_Id );

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    String url1 = "http://178.128.173.51:3000/getSubscriptionData";
                    System.out.println(params1.toString());
                    JsonObjectRequest jsonArrayRequest = new JsonObjectRequest( Request.Method.POST, url1, params1, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //              progressDialog.dismiss();
                            // remember here we are in the main thread, that means,
                            //volley has finished processing request, and we have our response.
                            // What else are you waiting for? update itShouldLoadMore = true;
                            itShouldLoadMore = true;
                            System.out.println( response.toString() );

                            try {


                                String msg = response.getString( "message" );
                                JSONArray data = response.getJSONArray( "data" );
                                for (int i = 0; i < data.length(); i++) {


                                    JSONObject jsonObject = data.getJSONObject( i );


                                    if (i== 0 && SubscriptionCardLoadData.getSubscription_id() == 0){
                                        Channel_Id = jsonObject.getInt( "video_id" );
                                    }
                                    int videoId = jsonObject.getInt( "video_id" );


                                    lastId = String.valueOf( videoId );


                                    String sliderImage = jsonObject.getString( "home_image" );
                                    String shortenText = jsonObject.getString( "shorten_text" );
                                    String vdoCipherId = jsonObject.getString( "vdo_cipher_id" );

                                    mRecyclerModels.add( new SubscriptionRecyclerModel( videoId, sliderImage, shortenText, vdoCipherId ) );

                                }

                                mRecyclerView.setAdapter( mRecyclerAdapter );
                                mRecyclerAdapter.notifyDataSetChanged();





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // please note how we have updated our last id variable which is initially 0 (String)
                            // outside the loop, java will return the last value, so here it will
                            // certainly give us lastId that we need

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // also here, volley is not processing, unlock it should load more
                            itShouldLoadMore = true;
//                progressDialog.dismiss();
                            Toast.makeText( getContext(), "Currently You haven't subscribed any channel", Toast.LENGTH_SHORT ).show();

                        }
                    } );

                    Volley.newRequestQueue( getActivity() ).add( jsonArrayRequest );





                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // please note how we have updated our last id variable which is initially 0 (String)
                // outside the loop, java will return the last value, so here it will
                // certainly give us lastId that we need

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also here, volley is not processing, unlock it should load more
                itShouldLoadMore = true;
//                progressDialog.dismiss();
                Toast.makeText( getActivity(), "network error!", Toast.LENGTH_SHORT ).show();

            }
        } );

        Volley.newRequestQueue( getActivity() ).add( jsonArrayRequest );


        return view;

    }



}

