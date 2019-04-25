package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterTransaction;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdaptorListRecent;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdaptorRecent;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.RecentRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionCardLoadData;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.TransactionRecyclerModel;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class TransactionFragment extends Fragment {

    public TransactionFragment() {
    }

    RecyclerView mRecyclerViewTransaction;

    private RecyclerAdapterTransaction mRecyclerAdapterTransaction;
    private ArrayList <TransactionRecyclerModel> mRecyclerModelsTransaction;

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), HORIZONTAL, false);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_transaction, container, false);


        String url = "http://178.128.173.51:3000/transactionDetails";

        mRecyclerViewTransaction = view.findViewById(R.id.recycle_transaction);

        mRecyclerViewTransaction.setLayoutManager(mLayoutManager);
        mRecyclerViewTransaction.setHasFixedSize(true);

        LoginUserDetails loginUserDetails = new LoginUserDetails(TransactionFragment.this);

        JSONObject params = new JSONObject();
        try {


            params.put("email", loginUserDetails.getEmail());


        } catch (JSONException e) {
            e.printStackTrace();

        }
        mRecyclerModelsTransaction = new ArrayList <>();


        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener <JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    System.out.println(response.toString());

                    String msg = response.getString("message");
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);


                        String transaction_amount = jsonObject.getString("transaction_amount");
                        String transaction_memo = jsonObject.getString("transaction_memo");
                        String transaction_date = jsonObject.getString("transaction_date");


                        mRecyclerModelsTransaction.add(new TransactionRecyclerModel(transaction_amount, transaction_memo, transaction_date));
                        mRecyclerAdapterTransaction = new RecyclerAdapterTransaction(mRecyclerModelsTransaction);
                        mRecyclerViewTransaction.setAdapter(mRecyclerAdapterTransaction);
                        mRecyclerAdapterTransaction.notifyDataSetChanged();

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













