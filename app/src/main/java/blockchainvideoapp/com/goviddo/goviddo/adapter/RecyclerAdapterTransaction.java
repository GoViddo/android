package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.Fragments.TransactionFragment;
import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.TransactionRecyclerModel;

public class RecyclerAdapterTransaction extends   RecyclerView.Adapter<RecyclerAdapterTransaction.MyViewHolder> {

    private ArrayList<TransactionRecyclerModel> mTransactionRecyclerModel; // this data structure carries our title and description

    int mPosition;

    public RecyclerAdapterTransaction(ArrayList<TransactionRecyclerModel> recyclerModels) {
        this.mTransactionRecyclerModel = recyclerModels;
    }

    @Override
    public RecyclerAdapterTransaction.MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {


        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.card_view_fragment, parent, false );

        final RecyclerAdapterTransaction.MyViewHolder mViewHolder = new RecyclerAdapterTransaction.MyViewHolder( view );

        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterTransaction.MyViewHolder holder, int position) {

        mPosition = position;

        //   Picasso.with(holder.context).load(mSubscriptionrecyclerModels.get(holder.getPosition()).getmSliderImage()).into(holder.roundedImageView);

        holder.mtxt_lst_acc_bal.setText( mTransactionRecyclerModel.get( holder.getPosition() ).getmTransaction_amount() );

    }


    @Override
    public int getItemCount() {
        return mTransactionRecyclerModel.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our custom row layout, so intialize your variables here
        TextView  mtxtUserName,mtxtbalance,mtxt_total_investment,mtxt_lst_transaction,mtxt_total_earning,mtxt_lst_acc_bal;
        ImageView roundedImageView;
        RecyclerView recyclerView;
        Context context;

        MyViewHolder(View view) {
            super( view );


            roundedImageView = view.findViewById( R.id.profile_image );
            mtxtUserName = view.findViewById( R.id.txtUserName );
            mtxtbalance = view.findViewById( R.id.txtbalance );
            mtxt_total_investment = view.findViewById( R.id.txt_total_investment );
            mtxt_lst_transaction= view.findViewById( R.id.txt_lst_transaction );
            mtxt_total_earning  = view.findViewById( R.id.txt_total_earning );
            mtxt_lst_acc_bal = view.findViewById( R.id.txt_lst_acc_bal );
            recyclerView = view.findViewById( R.id.recycle_transaction );
            context = view.getContext();


        }


    }
}
