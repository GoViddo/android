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
import blockchainvideoapp.com.goviddo.goviddo.coreclass.SubscriptionRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.TransactionRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.WatchLaterRecyclerModel;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.OnlinePlayerActivity;
import blockchainvideoapp.com.goviddo.goviddo.vdocipher.Utils;

public class RecyclerAdapterTransaction_Cardview  extends RecyclerView.Adapter<RecyclerAdapterTransaction_Cardview.MyViewHolder> {

    private ArrayList<TransactionRecyclerModel> mTransactionRecyclerModel; // this data structure carries our title and description

    int mPosition;

    public RecyclerAdapterTransaction_Cardview(ArrayList<TransactionRecyclerModel> recyclerModels) {
        this.mTransactionRecyclerModel = recyclerModels;
    }


    @Override
    public RecyclerAdapterTransaction_Cardview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.card_view_fragment, parent, false);

        final RecyclerAdapterTransaction_Cardview.MyViewHolder mViewHolder = new RecyclerAdapterTransaction_Cardview.MyViewHolder(view);

        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterTransaction_Cardview.MyViewHolder holder, int position) {

        mPosition = position;


        holder.mAmount.setText( mTransactionRecyclerModel.get( holder.getPosition() ).getmTransaction_amount() );
        holder.mDate.setText( mTransactionRecyclerModel.get( holder.getPosition() ).getmTransaction_date() );
        holder.Description.setText( mTransactionRecyclerModel.get( holder.getPosition() ).getmTransaction_memo() );
    }


    @Override
    public int getItemCount() {
        return mTransactionRecyclerModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        TextView mDate;
        TextView Description,mAmount;
        Context context;
        CardView cardView;
        MyViewHolder(View view) {
            super(view);


            cardView = view.findViewById( R.id.cardview_trans );
            context = view.getContext();
            mDate = view.findViewById( R.id.txt_view_date );
            Description=view.findViewById( R.id.txtviewfor );
            mAmount = view.findViewById( R.id.txt_view_amount );

        }


    }
}
