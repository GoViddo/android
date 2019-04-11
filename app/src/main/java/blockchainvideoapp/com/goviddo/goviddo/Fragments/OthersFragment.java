package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.activity.MainActivity;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecylerAdapterOthers;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserInfo;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.OtherRecyclerModel;
import de.hdodenhof.circleimageview.CircleImageView;


public class OthersFragment extends Fragment {


    public OthersFragment(){
        //Required empty public constructor
    }

    RecyclerView mRecyclerViewPreview;

    private RecylerAdapterOthers mRecyclerAdapterPreview;
    private ArrayList<OtherRecyclerModel> mRecyclerModelsPreview;

    LinearLayoutManager mLayoutManager;

    View view;

    TextView mTxtUserName;
    CircleImageView mProfileImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate( R.layout.others_fragment, container, false );

        mRecyclerModelsPreview = new ArrayList<>();
        mRecyclerModelsPreview.add( new OtherRecyclerModel( "Setting" ) );
        mRecyclerModelsPreview.add( new OtherRecyclerModel( "Sign Out" ) );

        mTxtUserName = view.findViewById(R.id.txtUserName);
        mProfileImageView = view.findViewById(R.id.profile_image);



        String urlUserDetails = "http://178.128.173.51:3000/getUserInfoForAccount";

        LoginUserDetails loginUserDetails = new LoginUserDetails(getActivity());
        final String emailId = loginUserDetails.getEmail();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailId", emailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlUserDetails, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String first_name = response.getString("first_name");
                    String last_name = response.getString("last_name");
                    String email_id = response.getString("email_id");
                    String eosio_account_name = response.getString("eosio_account_name");
                    String gender = response.getString("gender");
                    String phone_no = response.getString("phone_no");
                    String birth_date = response.getString("birth_date");
                    String address = response.getString("address");
                    String country = response.getString("country");
                    String user_video_choice = response.getString("user_video_choice");

                    String user_profile_picture = response.getString("user_profile_picture");
                    String notification_token = response.getString("notification_token");

                    int userId = response.getInt("user_id");

                    int register_as_advisor = response.getInt("register_as_advisor");

                    int register_as_producer = response.getInt("register_as_producer");


                    mTxtUserName.setText(first_name+" "+last_name);

                    if (user_profile_picture != null)
                    {
                        Picasso.with(getActivity()).load(user_profile_picture).into(mProfileImageView);
                    }

                    LoginUserInfo loginUserInfo = new LoginUserInfo(first_name, last_name, email_id, eosio_account_name, gender, phone_no, birth_date, address, country, user_video_choice, user_profile_picture, notification_token, userId, register_as_advisor, register_as_producer);


                    mRecyclerAdapterPreview = new RecylerAdapterOthers(mRecyclerModelsPreview);

                    mRecyclerViewPreview = view.findViewById( R.id.recycle);
                    mLayoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );

                    mRecyclerViewPreview.setLayoutManager( mLayoutManager );

                    //we can now set adapter to recyclerView;
                    mRecyclerViewPreview.setAdapter( mRecyclerAdapterPreview );



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put( "Content-Type", "application/json" );
                return headers;
            }

        };

        requestQueue.add( jsonObjectRequest );


        return view;
    }


}