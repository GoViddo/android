package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterTransaction_Cardview;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterWatchLater;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.WatchLaterRecyclerModel;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class WatchLaterFragment extends Fragment{

    public WatchLaterFragment() {
    }

    RecyclerView mRecyclerViewWatchLater;

    private RecyclerAdapterWatchLater mRecyclerAdapterWatchLater;
    private ArrayList<WatchLaterRecyclerModel> mRecyclerModelsWatchLater;

    LoginUserDetails loginUserDetail;
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_transaction, container, false);


        String url = "http://178.128.173.51:3000/transactionDetails";

        mRecyclerViewWatchLater = view.findViewById(R.id.recycle_transaction);

        mRecyclerViewWatchLater.setLayoutManager(mLayoutManager);
        mRecyclerViewWatchLater.setHasFixedSize(true);
        loginUserDetail = new LoginUserDetails(getActivity());

        JSONObject params = new JSONObject();
        try {


            params.put("email", loginUserDetail.getEmail());


        } catch (JSONException e) {
            e.printStackTrace();

        }
        mRecyclerModelsWatchLater = new ArrayList <>();


        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    System.out.println(response.toString());

                    String msg = response.getString("message");
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);




                        mRecyclerModelsWatchLater.add(new WatchLaterRecyclerModel());
                        mRecyclerAdapterWatchLater = new RecyclerAdapterTransaction_Cardview(mRecyclerModelsWatchLater);
                        mRecyclerViewWatchLater.setAdapter(mRecyclerAdapterWatchLater);
                        mRecyclerAdapterWatchLater.notifyDataSetChanged();

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
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
        return view;

    }
}







}
