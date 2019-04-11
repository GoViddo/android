package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.Fragments.SubscriptionFragment;
import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionCardLoadData;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;


public class RecyclerAdapterSubscription extends RecyclerView.Adapter<RecyclerAdapterSubscription.MyViewHolder> {

    private ArrayList<SubscriptionRecyclerModel> mSubscriptionrecyclerModels; // this data structure carries our title and description

    int mPosition;

    public RecyclerAdapterSubscription(ArrayList<SubscriptionRecyclerModel> recyclerModels) {
        this.mSubscriptionrecyclerModels = recyclerModels;
    }

    @Override
    public RecyclerAdapterSubscription.MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recycleview_subscribe_roundimg, parent, false);

        final RecyclerAdapterSubscription.MyViewHolder mViewHolder = new RecyclerAdapterSubscription.MyViewHolder(view);


        mViewHolder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SubscriptionCardLoadData.setSubscription_id(mSubscriptionrecyclerModels.get(mViewHolder.getPosition()).getmVideoId());
                FragmentTransaction transaction = ((AppCompatActivity)mViewHolder.context).getSupportFragmentManager().beginTransaction();

                SubscriptionFragment subscriptionFragment = new SubscriptionFragment();
                transaction = transaction.replace( R.id.fragment_container, subscriptionFragment );
                transaction.commit();

            }
        });


        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterSubscription.MyViewHolder holder, int position) {

        mPosition = position;

        Picasso.with(holder.context).load(mSubscriptionrecyclerModels.get(holder.getPosition()).getmSliderImage()).into(holder.roundedImageView);


    }


    @Override
    public int getItemCount() {
        return mSubscriptionrecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        TextView title;
        ImageView roundedImageView;
        CardView cardView;
        Context context;

        MyViewHolder(View view) {
            super(view);


            roundedImageView = view.findViewById(R.id.subscribe_img);
            context = view.getContext();
            title = view.findViewById( R.id.txtSubscribeTitle );

        }


    }
}




