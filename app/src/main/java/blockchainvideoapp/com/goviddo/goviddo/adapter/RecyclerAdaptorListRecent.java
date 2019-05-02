package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.Fragments.HomeFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.OthersFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.TransactionFragment;
import blockchainvideoapp.com.goviddo.goviddo.Fragments.WatchLaterFragment;
import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.RecentRecyclerModel;

public class RecyclerAdaptorListRecent extends RecyclerView.Adapter<RecyclerAdaptorListRecent.MyViewHolder> {
    private ArrayList<RecentRecyclerModel> mRecentRecyclerModels; // this data structure carries our title and description


    int mPosition;

    public RecyclerAdaptorListRecent(ArrayList<RecentRecyclerModel> recyclerModels) {
        this.mRecentRecyclerModels = recyclerModels;

    }



    @Override
    public RecyclerAdaptorListRecent.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recent_list, parent, false);

        final RecyclerAdaptorListRecent.MyViewHolder mViewHolder = new RecyclerAdaptorListRecent.MyViewHolder(view);

        mViewHolder.textView.setText( mRecentRecyclerModels.get( mPosition ).getMtext() );

        mViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Click listener
                if (mViewHolder.textView.getText().toString().equalsIgnoreCase("Account"))
                {


                    FragmentTransaction transaction = ((AppCompatActivity)mViewHolder.context).getSupportFragmentManager().beginTransaction();
                    OthersFragment othersFragment = new OthersFragment();
                    transaction.replace( R.id.fragment_container,othersFragment );
                    transaction.addToBackStack("OtherFragments");
                    transaction.commit();

                }

                if (mViewHolder.textView.getText().toString().equalsIgnoreCase("Transaction"))
                {


                    FragmentTransaction transaction = ((AppCompatActivity)mViewHolder.context).getSupportFragmentManager().beginTransaction();
                    TransactionFragment transactionFragment = new TransactionFragment();
                    transaction.replace( R.id.fragment_container, transactionFragment );
                    transaction.addToBackStack("TransactionFragments");
                    transaction.commit();

                }
                
                if (mViewHolder.textView.getText().toString().equalsIgnoreCase("Watch Later"))
                {
                    FragmentTransaction transaction = ((AppCompatActivity)mViewHolder.context).getSupportFragmentManager().beginTransaction();
                    WatchLaterFragment watchLaterFragment= new WatchLaterFragment();
                    transaction.replace( R.id.fragment_container, watchLaterFragment );
                    transaction.addToBackStack("WatchLaterFragment");
                    transaction.commit();
                }

            }
        });


        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdaptorListRecent.MyViewHolder holder, int position) {
        mPosition = position;
        holder.textView.setText(mRecentRecyclerModels.get( mPosition ).getMtext()  );

    }


    @Override
    public int getItemCount() {
        return mRecentRecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our others_card layout, so intialize your variables here

        TextView textView;
        Context context;


        MyViewHolder(View view) {
            super(view);

            textView = view.findViewById( R.id.txt_view_list );
            context = view.getContext();


        }


    }
}

