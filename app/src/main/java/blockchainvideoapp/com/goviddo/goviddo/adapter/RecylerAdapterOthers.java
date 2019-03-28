package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.activity.MainActivity;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.LoginUserDetails;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.OtherRecyclerModel;


public class RecylerAdapterOthers extends RecyclerView.Adapter<RecylerAdapterOthers.MyViewHolder> {
private ArrayList<OtherRecyclerModel> mOtherrecyclerModels; // this data structure carries our title and description


    int mPosition;

    public RecylerAdapterOthers(ArrayList<OtherRecyclerModel> recyclerModels) {
        this.mOtherrecyclerModels = recyclerModels;

    }



    @Override
    public RecylerAdapterOthers.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.others_card, parent, false);

        final RecylerAdapterOthers.MyViewHolder mViewHolder = new RecylerAdapterOthers.MyViewHolder(view);

        mViewHolder.textView.setText( mOtherrecyclerModels.get( mPosition ).getTitle() );

        mViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOtherrecyclerModels.get(mViewHolder.getPosition()).getTitle().equalsIgnoreCase("sign out"))
                {
                    LoginUserDetails loginUserDetails = new LoginUserDetails(mViewHolder.context);
                    loginUserDetails.removeUserInfo();

                    Intent intent = new Intent(mViewHolder.context, MainActivity.class);
                    mViewHolder.context.startActivity(intent);
                    ((Activity)mViewHolder.context).finish();

                }


            }
        });






        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecylerAdapterOthers.MyViewHolder holder, int position) {
        mPosition = position;
        holder.textView.setText(mOtherrecyclerModels.get( mPosition ).getTitle()  );

    }


    @Override
    public int getItemCount() {
        return mOtherrecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // view this our others_card layout, so intialize your variables here


        TextView textView;

        Context context;


        MyViewHolder(View view) {
            super(view);

            textView=view.findViewById( R.id.txtTitle );
            context = view.getContext();


        }


    }
    }





