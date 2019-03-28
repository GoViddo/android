package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionRecyclerModel;

public class RecyclerAdapterSubscriptionCard extends RecyclerView.Adapter<RecyclerAdapterSubscriptionCard.MyViewHolder> {

private ArrayList<SubscriptionRecyclerModel> mSubscriptionrecyclerModels; // this data structure carries our title and description

        int mPosition;

public RecyclerAdapterSubscriptionCard(ArrayList<SubscriptionRecyclerModel> recyclerModels) {
        this.mSubscriptionrecyclerModels = recyclerModels;
        }

@Override
public RecyclerAdapterSubscriptionCard.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recycleview_subscribe_cardvideo, parent, false);

final RecyclerAdapterSubscriptionCard.MyViewHolder mViewHolder = new RecyclerAdapterSubscriptionCard.MyViewHolder(view);


        mViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        }
        });


        // inflate your custom row layout here
        return mViewHolder;
        }

@Override
public void onBindViewHolder(final RecyclerAdapterSubscriptionCard.MyViewHolder holder, int position) {

        mPosition = position;

        holder.title.setText( mSubscriptionrecyclerModels.get( mPosition ).getmTitle() );


        }


@Override
public int getItemCount() {
        return mSubscriptionrecyclerModels.size();
        }

class MyViewHolder extends RecyclerView.ViewHolder {
    // view this our custom row layout, so intialize your variables here
    TextView title;
    TextView Description;
    ImageView imageView;
    CardView cardView;
    Context context;

    MyViewHolder(View view) {
        super(view);


        imageView = view.findViewById( R.id.VideoLikedToPlay);
        context = view.getContext();
        title = view.findViewById( R.id.txtShowTitle );
        Description=view.findViewById( R.id.txtShowDesc );
        cardView = view.findViewById(R.id.cardview_Subscription);

    }


}
}


