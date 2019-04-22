package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import blockchainvideoapp.com.goviddo.goviddo.coreclass.EndlessRecyclerViewScrollListner;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerCardViewModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;

public class RecyclerAdapterCardviewHome extends RecyclerView.Adapter<RecyclerAdapterCardviewHome.MyViewHolder> {


    private ArrayList<HomeRecyclerCardViewModel> mHomeRecyclerModels; // this data structure carries our title and description

    int mPosition;
    private boolean itShouldLoadMore = true;



    int mLastIdOfVideoForLoadMoreOptionOriginals = 0;
    int mLastIdOfVideoForLoadMoreOptionDrama = 0;
    int mLastIdOfVideoForLoadMoreOptionRomantic = 0;



    private RecyclerAdapterVideosHome home_video_adapter1, home_video_adapter2, home_video_adapter3;
    ArrayList<HomeRecyclerCardViewModel> mRecyclerModelsVideo, mRecyclerModelsVideoGoViddoOriginals, mRecyclerModelsVideoGoViddoDrama, mRecyclerModelsVideoGoViddoRomantic;;
    LinearLayoutManager mLinearLayoutManagerVideo;

    public RecyclerAdapterCardviewHome(ArrayList<HomeRecyclerCardViewModel> recyclerModels) {
        this.mHomeRecyclerModels = recyclerModels;

    }

    @Override
    public RecyclerAdapterCardviewHome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.home_video, parent, false );

        final MyViewHolder mViewHolder = new MyViewHolder( view );
        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterCardviewHome.MyViewHolder holder, int position) {

        holder.textView.setText(mHomeRecyclerModels.get( holder.getPosition() ).getHeading()  );

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(holder.context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setHasFixedSize(true);


        if(mHomeRecyclerModels.get( holder.getPosition()).getHeading().equalsIgnoreCase("Originals")) {
            home_video_adapter1 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoOriginals, holder.context);
            holder.recyclerView.setAdapter(home_video_adapter1);
            firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionOriginals,mRecyclerModelsVideoGoViddoDrama,mRecyclerModelsVideoGoViddoRomantic, home_video_adapter1, mHomeRecyclerModels.get(holder.getPosition()).getHeading(),  holder.recyclerView);
        }
        else if(mHomeRecyclerModels.get( holder.getPosition()).getHeading().equalsIgnoreCase("Series")){
            home_video_adapter2 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoDrama, holder.context);
            holder.recyclerView.setAdapter(home_video_adapter2);
            firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionDrama, mRecyclerModelsVideoGoViddoDrama, mRecyclerModelsVideoGoViddoRomantic, home_video_adapter2, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), holder.recyclerView);
        }
        else{
            home_video_adapter3 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoRomantic, holder.context);
            holder.recyclerView.setAdapter(home_video_adapter3);
            firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionRomantic, mRecyclerModelsVideoGoViddoDrama, mRecyclerModelsVideoGoViddoRomantic, home_video_adapter3, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), holder.recyclerView);
        }

        holder.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListner((LinearLayoutManager) holder.recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if(mHomeRecyclerModels.get( holder.getPosition()).getHeading().equalsIgnoreCase("Originals")) {
                    home_video_adapter1 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoOriginals, holder.context);
                    holder.recyclerView.setAdapter(home_video_adapter1);
                    firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionOriginals,mRecyclerModelsVideoGoViddoDrama,mRecyclerModelsVideoGoViddoRomantic, home_video_adapter1, mHomeRecyclerModels.get(holder.getPosition()).getHeading(),  holder.recyclerView);
                }
                else if(mHomeRecyclerModels.get( holder.getPosition()).getHeading().equalsIgnoreCase("Series")){
                    home_video_adapter2 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoDrama, holder.context);
                    holder.recyclerView.setAdapter(home_video_adapter2);
                    firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionDrama, mRecyclerModelsVideoGoViddoDrama, mRecyclerModelsVideoGoViddoRomantic, home_video_adapter2, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), holder.recyclerView);
                }
                else{
                    home_video_adapter3 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoRomantic, holder.context);
                    holder.recyclerView.setAdapter(home_video_adapter3);
                    firstLoadData(holder.context, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), mHomeRecyclerModels.get(holder.getPosition()).getCount(), mLastIdOfVideoForLoadMoreOptionRomantic, mRecyclerModelsVideoGoViddoDrama, mRecyclerModelsVideoGoViddoRomantic, home_video_adapter3, mHomeRecyclerModels.get(holder.getPosition()).getHeading(), holder.recyclerView);
                }


            }
        });



    }


    @Override
    public int getItemCount() {
        return mHomeRecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here

       RecyclerView recyclerView;
        TextView textView;
        Context context;

        MyViewHolder(View view) {
            super( view );

            textView = view.findViewById( R.id.card_text);
            context = view.getContext();
          recyclerView = view.findViewById( R.id.video_recycler );

            mRecyclerModelsVideo = new ArrayList<>();

            mRecyclerModelsVideoGoViddoOriginals = new ArrayList<>();
            mRecyclerModelsVideoGoViddoDrama = new ArrayList<>();
            mRecyclerModelsVideoGoViddoRomantic = new ArrayList<>();



        }


    }
    // this function will load 15 items as indicated in the LOAD_LIMIT variable field
    private void firstLoadData(final Context context, String videoGenere, int videoLimit, int videoLastId, final ArrayList<HomeRecyclerCardViewModel> recyclerCardViewModelGoVidoDrama, final ArrayList<HomeRecyclerCardViewModel> recyclerCardViewModelGoViddoRomantic, final RecyclerAdapterVideosHome home_video_adapter, final String type, final RecyclerView recyclerView) {

        String url = "http://178.128.173.51:3000/getVideoData";

        JSONObject params = new JSONObject();
        try {


            params.put( "videoGenere", videoGenere );
            params.put( "videoLimit", videoLimit );
            params.put( "videoLastId", videoLastId );


        } catch (JSONException e) {
            e.printStackTrace();

        }


        itShouldLoadMore = false; // lock this guy,(itShouldLoadMore) to make sure,
        // user will not load more when volley is processing another request
        // only load more when  volley is free

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                // remember here we are in the main thread, that means,
                //volley has finished processing request, and we have our response.
                // What else are you waiting for? update itShouldLoadMore = true;
                itShouldLoadMore = true;

                try {


                    // please note this last id how we have updated it
                    // if there are 4 items for example, and we are ordering in descending order,
                    // then last id will be 1. This is because outside a loop, we will get the last
                    // value [Thanks to JAVA]

                    String msg = response.getString("message");

                    if(msg.equalsIgnoreCase("success"))
                    {
                        JSONArray jsonArray = response.getJSONArray("data");


                        for (int i=0; i<jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String home_image_url = jsonObject1.getString("home_image");
                            String shorten_test = jsonObject1.getString("shorten_text");
                            String video_cipher_id = jsonObject1.getString("vdo_cipher_id");
                            int video_id = jsonObject1.getInt("video_id");
                            String video_genere = jsonObject1.getString("video_genere");

                            if (video_genere.equalsIgnoreCase("Originals"))
                            {

                                mRecyclerModelsVideoGoViddoOriginals.add(new HomeRecyclerCardViewModel(home_image_url,shorten_test, video_cipher_id, video_id, video_genere));
                                mLastIdOfVideoForLoadMoreOptionOriginals = video_id;
                            }
                            else  if (video_genere.equalsIgnoreCase("Series"))
                            {
                                mRecyclerModelsVideoGoViddoDrama.add(new HomeRecyclerCardViewModel(home_image_url,shorten_test, video_cipher_id, video_id, video_genere));
                                mLastIdOfVideoForLoadMoreOptionDrama = video_id;
                            }
                            else{
                                mRecyclerModelsVideoGoViddoRomantic.add(new HomeRecyclerCardViewModel(home_image_url,shorten_test, video_cipher_id, video_id, video_genere));
                                mLastIdOfVideoForLoadMoreOptionRomantic = video_id;
                            }


                        }


                    }


                    if (type.equalsIgnoreCase("Originals")) {

                        home_video_adapter1 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoOriginals, context);
                        recyclerView.setAdapter(home_video_adapter1);
                        home_video_adapter1.notifyDataSetChanged();
                    }
                    else if(type.equalsIgnoreCase("Series"))
                    {

                        home_video_adapter2 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoDrama, context);
                        recyclerView.setAdapter(home_video_adapter2);
                        home_video_adapter2.notifyDataSetChanged();

                    }
                    else{

                        home_video_adapter3 = new RecyclerAdapterVideosHome(mRecyclerModelsVideoGoViddoRomantic, context);
                        recyclerView.setAdapter(home_video_adapter3);
                        home_video_adapter3.notifyDataSetChanged();

                    }




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
                progressDialog.dismiss();
                Toast.makeText(context, "network error!", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put( "Content-Type", "application/json" );
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);


    }





}

