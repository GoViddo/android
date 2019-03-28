package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerCardViewModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;

public class RecyclerAdapterVideosHome extends RecyclerView.Adapter<RecyclerAdapterVideosHome.MyViewHolder> {

    private ArrayList<HomeRecyclerCardViewModel> mHomeCardrecyclerModels; // this data structure carries our title and description

    int mPosition;
    Context mContext;

    public RecyclerAdapterVideosHome(ArrayList<HomeRecyclerCardViewModel> recyclerModels, Context context) {
        this.mHomeCardrecyclerModels = recyclerModels;
        mContext = context;

    }

    @Override
    public RecyclerAdapterVideosHome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_image_recycler, parent, false);

        final RecyclerAdapterVideosHome.MyViewHolder mViewHolder = new RecyclerAdapterVideosHome.MyViewHolder(view);


        mViewHolder.ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(  mContext, OnlinePlayerActivity.class );
                mContext.startActivity(intent );
                Utils.vdociper_id = mHomeCardrecyclerModels.get(mViewHolder.getPosition()).getVdoCipherid();

            }
        });


        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterVideosHome.MyViewHolder holder, int position) {
        mPosition = position;

        Picasso.with( mContext).load( mHomeCardrecyclerModels.get( position ).getUrl() ).into( holder.ImageView );

    }


    @Override
    public int getItemCount() {
        return mHomeCardrecyclerModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here

        CardView cardView;
        ImageView ImageView;



        MyViewHolder(View view) {
            super(view);


            ImageView = view.findViewById(R.id.video_image);



        }


    }
}
