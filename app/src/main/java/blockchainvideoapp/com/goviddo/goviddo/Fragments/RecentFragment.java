package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdaptorListRecent;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdaptorRecent;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserInfo;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.RecentRecyclerModel;

public class RecentFragment extends Fragment {

    private static int LOAD_LIMIT = 15;

    private String lastId = "0";

    private boolean itShouldLoadMore = true;

    public RecentFragment() {
        // Required empty public constructor
    }

    RecyclerView mRecyclerViewRecentImage;
    private RecyclerAdaptorRecent mRecyclerAdapterRecentImage;
    private ArrayList<RecentRecyclerModel> mRecyclerModelsRecentImage;
    LinearLayoutManager mLinearLayoutManagerRecentImage;


    RecyclerView mRecyclerViewRecentList;
    private RecyclerAdaptorListRecent mRecyclerAdapterRecentList;
    private ArrayList<RecentRecyclerModel> mRecyclerModelsRecentList;
    LinearLayoutManager mLinearLayoutManagerRecentList;

    ProgressWheel mProgressWheelRecent;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment, container, false);


        String url = "http://178.128.173.51:3000/getUserHistory";

        mRecyclerViewRecentImage =  view.findViewById(R.id.image_recyler_view);

        mLinearLayoutManagerRecentImage = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );

        mRecyclerViewRecentImage.setLayoutManager( mLinearLayoutManagerRecentImage );
        mRecyclerViewRecentImage.setHasFixedSize(true);

        LoginUserDetails loginUserDetails = new LoginUserDetails(getActivity());

        JSONObject params = new JSONObject();
        try {


            params.put( "userEmial",  loginUserDetails.getEmail());


        } catch (JSONException e) {
            e.printStackTrace();

        }
        mRecyclerModelsRecentImage = new ArrayList<>();



        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    System.out.println(response.toString());

                    String msg = response.getString("message");
                    JSONArray data = response.getJSONArray("data");
                    for (int i=0; i<data.length(); i++)
                    {
                        JSONObject jsonObject = data.getJSONObject(i);

                        int videoId = jsonObject.getInt("videoId");


                        lastId = String.valueOf(videoId);


                        String videoName = jsonObject.getString("videoName");
                        String homeImage = jsonObject.getString("home_image");
                        String shortenText = jsonObject.getString("shorten_text");
                        String vdoCipherId = jsonObject.getString("vdoCipherId");
                        String vdoCipherDesc = jsonObject.getString("videoDescription");


                        mRecyclerModelsRecentImage.add( new RecentRecyclerModel( homeImage,vdoCipherId ) );
                        mRecyclerAdapterRecentImage = new RecyclerAdaptorRecent(mRecyclerModelsRecentImage);
                        mRecyclerViewRecentImage.setAdapter( mRecyclerAdapterRecentImage );
                        mRecyclerAdapterRecentImage.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // volley finished and returned network error, update and unlock  itShouldLoadMore

                //Toast.makeText( getContext(), "network error!", Toast.LENGTH_SHORT ).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);









        //we can now set adapter to recyclerView;

        //The Code for CardView in recent

        mRecyclerModelsRecentList = new ArrayList<>();
        mRecyclerModelsRecentList.add( new RecentRecyclerModel( "History" ) );
        mRecyclerModelsRecentList.add( new RecentRecyclerModel( "Privacy" ) );
        mRecyclerModelsRecentList.add( new RecentRecyclerModel( "Account" ) );
        mRecyclerModelsRecentList.add( new RecentRecyclerModel( "Transaction" ) );
        mRecyclerModelsRecentList.add( new RecentRecyclerModel( "Watch Later" ) );


        mRecyclerAdapterRecentList = new RecyclerAdaptorListRecent(mRecyclerModelsRecentList);

        mRecyclerViewRecentList = view.findViewById( R.id.list_recyler_view);
        mLinearLayoutManagerRecentList = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );

        mRecyclerViewRecentList.setLayoutManager( mLinearLayoutManagerRecentList );

        //we can now set adapter to recyclerView;
        mRecyclerViewRecentList.setAdapter( mRecyclerAdapterRecentList );

        return view;



    }
}
