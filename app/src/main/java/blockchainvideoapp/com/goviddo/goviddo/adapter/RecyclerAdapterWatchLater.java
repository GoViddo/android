package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.WatchLaterRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;

public class RecyclerAdapterWatchLater extends RecyclerView.Adapter<RecyclerAdapterWatchLater.MyViewHolder> {


    private ArrayList<WatchLaterRecyclerModel> mWatchLaterRecyclerModel; // this data structure carries our title and description

    int mPosition;

    public RecyclerAdapterWatchLater(ArrayList<WatchLaterRecyclerModel> recyclerModels) {
        this.mWatchLaterRecyclerModel = recyclerModels;
    }

    @Override
    public RecyclerAdapterWatchLater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.watch_later_cardview, parent, false);

        final RecyclerAdapterWatchLater.MyViewHolder mViewHolder = new RecyclerAdapterWatchLater.MyViewHolder(view);


        mViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(  mViewHolder.context, OnlinePlayerActivity.class );
                mViewHolder.context.startActivity(intent );
                Utils.vdociper_id = mWatchLaterRecyclerModel.get(mViewHolder.getPosition()).getmVideoId();

            }
        });
        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        mPosition = position;

        Picasso.with(holder.context).load(mWatchLaterRecyclerModel.get(holder.getPosition()).getmImageUrl()).into(holder.imageView);
        holder.textViewVideoTitle.setText( mWatchLaterRecyclerModel.get( holder.getPosition() ).getmVideoTitle() );

    }


    @Override
    public int getItemCount() {
        return mWatchLaterRecyclerModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here

        Context context;
        CardView cardView;
        ImageView imageView;
        TextView textViewVideoTitle,textViewVideoDesc;

        MyViewHolder(View view) {
            super(view);


           cardView = view.findViewById( R.id.card_view_watch_later);
            context = view.getContext();
            imageView = view.findViewById(R.id.img_watchlater);
            textViewVideoTitle = view.findViewById(R.id.title_watchlater);
          //  textViewVideoDesc = view.findViewById(R.id.desc_watchlater);


        }


    }
}
