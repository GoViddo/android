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
import blockchainvideoapp.com.goviddo.goviddo.coreclass.HomeRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;


public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.MyViewHolder> {

    private ArrayList<HomeRecyclerModel> homeRecyclerModels; // this data structure carries our title and description

    int mPosition;

    String mUrl;

    public RecyclerAdapterHome(ArrayList<HomeRecyclerModel> homeRecyclerModels) {
        this.homeRecyclerModels = homeRecyclerModels;
    }

    @Override
    public RecyclerAdapterHome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_layout_reviews, parent, false);

        final MyViewHolder mViewHolder = new MyViewHolder(view);


        mViewHolder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(  mViewHolder.context, OnlinePlayerActivity.class );
                mViewHolder.context.startActivity(intent );
                Utils.vdociper_id = homeRecyclerModels.get(mViewHolder.getPosition()).getmVdoCipherId();

                //Toast.makeText(mViewHolder.context, homeRecyclerModels.get(mViewHolder.getPosition()).getmVdoCipherId(), Toast.LENGTH_SHORT).show();
            }
        });


        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterHome.MyViewHolder holder, int position) {

        mPosition = position;

        Picasso.with(holder.context).load(homeRecyclerModels.get(holder.getPosition()).getmSliderImage()).into(holder.roundedImageView);

    }


    @Override
    public int getItemCount() {
        return homeRecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables her
        ImageView roundedImageView;

        Context context;

        MyViewHolder(View view) {
            super(view);


            roundedImageView = view.findViewById(R.id.title);
            context = view.getContext();



        }


    }
}


