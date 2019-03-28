package blockchainvideoapp.com.goviddo.goviddo.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterCardviewHome;
import blockchainvideoapp.com.goviddo.goviddo.adapter.RecyclerAdapterHome;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.EndlessRecyclerViewScrollListner;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerCardViewModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerModel;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{


    // we will be loading 15 items per page or per load
    // you can change this to fit your specifications.
    // When you change this, there will be no need to update your php page,
    // as php will be ordered what to load and limit by android java
    private static int LOAD_LIMIT = 15;

    // last id to be loaded from php page,
    // we will need to keep track or database id field to know which id was loaded last
    // and where to begin loading
    private String lastId = "0"; // this will issued to php page, so no harm make it string

    // we need this variable to lock and unlock loading more
    // e.g we should not load more when volley is already loading,
    // loading will be activated when volley completes loading
    private boolean itShouldLoadMore = true;

    private SliderLayout mDemoSlider;

    RecyclerView mRecyclerViewPreview;

    private RecyclerAdapterHome mRecyclerAdapterHomePreview;
    private ArrayList<HomeRecyclerModel> mHomeRecyclerModelsPreview;

    ProgressWheel mProgressWheelPreview;

    LinearLayoutManager mLayoutManager;


    RecyclerView mRecyclerCardview;

    public static RecyclerAdapterCardviewHome mRecyclerAdapterCardview;
    private ArrayList<HomeRecyclerCardViewModel> mRecyclerModelsCardview;

    ProgressWheel mProgressWheelCardview;

    LinearLayoutManager mLayoutManagerCardview;




    BaseSliderView.OnSliderClickListener referenceData = this;
    ViewPagerEx.OnPageChangeListener refernceOnPagechange = this;
    HashMap<String,String> url_maps,url_refresh;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.home_fragment, container, false);

        mDemoSlider = view.findViewById(R.id.slider);



        mHomeRecyclerModelsPreview = new ArrayList<>();

        mRecyclerAdapterHomePreview = new RecyclerAdapterHome( mHomeRecyclerModelsPreview );

        mRecyclerViewPreview =  view.findViewById(R.id.loadmore_recycler_view);

        mProgressWheelPreview =  view.findViewById(R.id.progress_wheel);
        mRecyclerModelsCardview = new ArrayList<>();

        mRecyclerAdapterCardview = new RecyclerAdapterCardviewHome(mRecyclerModelsCardview);

        mRecyclerCardview =  view.findViewById(R.id.home_video_recycler);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewPreview.setLayoutManager(mLayoutManager);
        mRecyclerViewPreview.setHasFixedSize(true);

        //we can now set adapter to recyclerView;
        mRecyclerViewPreview.setAdapter( mRecyclerAdapterHomePreview );


        //CardView Adapter Code

        String configuration_url = "http://178.128.173.51:3000/config";
        final String banner_image_url = "http://178.128.173.51:3000/getSliderImageData";



        final RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        final RequestQueue requestQueueForBannerImages = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET, configuration_url,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String bannerIimageCount = response.getString( "bannerImgCount" );
                    LOAD_LIMIT = Integer.parseInt(response.getString("previewCount"));

                    JSONObject bannerImageRequestData = new JSONObject();
                    bannerImageRequestData.put("sliderMaxCount", bannerIimageCount);

                    firstLoadData();

                    mRecyclerViewPreview.addOnScrollListener(new EndlessRecyclerViewScrollListner( mLayoutManager) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                            loadMore();
                        }
                    });


                    JsonObjectRequest jsonObjectRequestBannerImages = new JsonObjectRequest(Request.Method.POST, banner_image_url, bannerImageRequestData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            url_maps = new HashMap<String, String>();
                            //System.out.println(response.toString());

                            try {
                                String message = response.getString("message");

                                if (message.equalsIgnoreCase("success"))
                                {

                                    JSONArray jsonArrayBannerImageData = response.getJSONArray("data");

                                    for (int i =0; i< jsonArrayBannerImageData.length(); i++)
                                    {

                                        JSONObject jsonObjectSingleImageData = jsonArrayBannerImageData.getJSONObject(i);
                                        String imageUrl = jsonObjectSingleImageData.getString("slider_image");
                                        int videoId = jsonObjectSingleImageData.getInt("video_id");
                                        String videocipher_id = jsonObjectSingleImageData.getString("vdo_cipher_id" );


                                        url_maps.put(videocipher_id, imageUrl);

                                    }

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            for(String name : url_maps.keySet()){
                                TextSliderView textSliderView = new TextSliderView(getActivity());
                                // initialize a SliderLayout
                              //  System.out.println(url_maps.size());
                                textSliderView
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(referenceData);

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra",name);

                                mDemoSlider.addSlider(textSliderView);




                            }
                            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
                            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                            mDemoSlider.setDuration(4000);
                            mDemoSlider.addOnPageChangeListener(refernceOnPagechange);




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText( getActivity(), "Please check internet connection", Toast.LENGTH_SHORT ).show();

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put( "Content-Type", "application/json" );
                            return headers;
                        }
                    };

                    requestQueueForBannerImages.add(jsonObjectRequestBannerImages);




                    String categories = response.getString( "categories" );
                    String[] aa = categories.split( "\\:",-1 );
                    ArrayList<String> pushdata = new ArrayList<>(  );
                    for (int i=1; i<aa.length; i++)
                    {

                            if(i%2 == 1)
                            {
                                String[] namearr = aa[i].split( ",", -1 );
                                String namedisp = namearr[0].replaceAll( "\\'" ,"");
                                pushdata.add( namedisp );
                            }
                            else{
                                String[] countarr = aa[i].split( "\\}", -1 );
                                String countdisp = countarr[0];
                                pushdata.add( countdisp );
                            }

                    }

                    for (int i=0; i<pushdata.size(); i++)
                    {
                        if(i%2 == 0) {
                            mRecyclerModelsCardview.add( new HomeRecyclerCardViewModel( pushdata.get( i ), Integer.parseInt( pushdata.get( i + 1 ) ) ) );
                        }
                    }



                    mRecyclerAdapterCardview = new RecyclerAdapterCardviewHome(mRecyclerModelsCardview);


                    mLayoutManagerCardview= new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );

                    mRecyclerCardview.setLayoutManager( mLayoutManagerCardview );

                    //we can now set adapter to recyclerView;
                    mRecyclerCardview.setAdapter( mRecyclerAdapterCardview );
                    mRecyclerAdapterCardview.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText( getActivity(), "Network Error Home Fragment", Toast.LENGTH_SHORT ).show();

            }
        } );

        requestQueue.add( jsonObjectRequest );

        return view;
    }




    @Override
    public void onDestroy() {
        mDemoSlider.stopAutoCycle();
        super.onDestroy();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {}


    // this function will load 15 items as indicated in the LOAD_LIMIT variable field
    private void firstLoadData() {

        String url = "http://178.128.173.51:3000/getPreviewData";

        JSONObject params = new JSONObject();
        try {


            params.put( "previewMaxCount", 5 );
            params.put( "previewLastId", 0 );


        } catch (JSONException e) {
            e.printStackTrace();

        }

        itShouldLoadMore = false; // lock this guy,(itShouldLoadMore) to make sure,
        // user will not load more when volley is processing another request
        // only load more when  volley is free

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();




        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                // remember here we are in the main thread, that means,
                //volley has finished processing request, and we have our response.
                // What else are you waiting for? update itShouldLoadMore = true;
                itShouldLoadMore = true;

                try {


                    String msg = response.getString("message");
                    JSONArray data = response.getJSONArray("data");
                    for (int i=0; i<data.length(); i++)
                    {
                       JSONObject jsonObject = data.getJSONObject(i);

                        int videoId = jsonObject.getInt("video_id");


                            lastId = String.valueOf(videoId);


                        String sliderImage = jsonObject.getString("slider_image");
                        String shortenText = jsonObject.getString("shorten_text");
                        String vdoCipherId = jsonObject.getString("vdo_cipher_id");

                        mHomeRecyclerModelsPreview.add(new HomeRecyclerModel(videoId, sliderImage, shortenText, vdoCipherId));



                        mRecyclerAdapterHomePreview.notifyDataSetChanged();

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
                Toast.makeText(getActivity(), "network error!", Toast.LENGTH_SHORT).show();

            }
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);


    }









    private void loadMore() {

        String url = "http://178.128.173.51:3000/getPreviewData";


        JSONObject params = new JSONObject();
        try {


            params.put( "previewMaxCount", LOAD_LIMIT );
            params.put( "previewLastId", lastId );


        } catch (JSONException e) {
            e.printStackTrace();

        }
        // our php page starts loading from 250 to 1, because we have [ORDER BY id DESC]
        // So until you clearly understand everything, for this tutorial use ORDER BY ID DESC
        // so we will do something like this to the php page
        //==============================================
        // $limit = $_GET['limit']
        // $lastId = $_GET['lastId']
        // then [SELECT * FROM table_name WHERE id < $lastId ORDER_BY id DESC LIMIT $limit ]
        // here we shall load 15 items from table where lastId id less than last loaded id

        // if you are using [ASC] in sql, your query might change to tis
        // then [SELECT * FROM table_name WHERE id > $lastId ORDER_BY id DESC LIMIT $limit ]
        // for this tutorial let's stick to [DESC]


        itShouldLoadMore = false; // lock this until volley completes processing

        // progressWheel is just a loading spinner, please see the content_main.xml

       // mProgressWheelPreview.setVisibility(View.VISIBLE);


        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // remember here we are in the main thread, that means,
                //volley has finished processing request, and we have our response.
                // What else are you waiting for? update itShouldLoadMore = true;
                itShouldLoadMore = true;

                try {


                    String msg = response.getString("message");
                    JSONArray data = response.getJSONArray("data");
                    for (int i=0; i<data.length(); i++)
                    {
                        JSONObject jsonObject = data.getJSONObject(i);

                        int videoId = jsonObject.getInt("video_id");


                        lastId = String.valueOf(videoId);


                        String sliderImage = jsonObject.getString("slider_image");
                        String shortenText = jsonObject.getString("shorten_text");
                        String vdoCipherId = jsonObject.getString("vdo_cipher_id");

                        mHomeRecyclerModelsPreview.add(new HomeRecyclerModel(videoId, sliderImage, shortenText, vdoCipherId));



                        mRecyclerAdapterHomePreview.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressWheelPreview.setVisibility(View.GONE);
                // volley finished and returned network error, update and unlock  itShouldLoadMore
                itShouldLoadMore = true;

            }
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);

    }



    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }



}