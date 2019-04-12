package blockchainvideoapp.com.goviddo.goviddo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import blockchainvideoapp.com.goviddo.goviddo.R;
import blockchainvideoapp.com.goviddo.goviddo.coreclass.CommentsRecyclerModel;

public class RecyclerForComments extends RecyclerView.Adapter<RecyclerForComments.MyViewHolder> {
    private ArrayList<CommentsRecyclerModel> mOtherrecyclerModels; // this data structure carries our title and description


    int mPosition;

    public RecyclerForComments(ArrayList<CommentsRecyclerModel> recyclerModels) {
        this.mOtherrecyclerModels = recyclerModels;
    }



    @Override
    public RecyclerForComments.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.activity_commentlist, parent, false);

        final RecyclerForComments.MyViewHolder mViewHolder = new RecyclerForComments.MyViewHolder(view);



        // inflate your custom row layout here
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerForComments.MyViewHolder holder, int position) {
        mPosition = position;
      //  holder.textView.setText(mOtherrecyclerModels.get( mPosition ).getTitle()  );

        System.out.println(mOtherrecyclerModels.get( mPosition ).getProfilepic());

        holder.username.setText( mOtherrecyclerModels.get( mPosition ).getUsername() );

        Picasso.with( holder.context ).load( mOtherrecyclerModels.get( mPosition ).getProfilepic() ).into( holder.imageView );
        holder.commentss.setText( mOtherrecyclerModels.get( mPosition ).getComment() );


    }


    @Override
    public int getItemCount() {
        return mOtherrecyclerModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder mViewHolder;
        // view this our others_card layout, so intialize your variables here


        CardView cardview;
        ImageView imageView;
        TextView username,commentss;

        Context context;


        MyViewHolder(View view) {
            super(view);

            commentss = view.findViewById( R.id.commentss );
            cardview = view.findViewById( R.id.cardview_comments );
            imageView = view.findViewById( R.id.comment_profile_image );
            username = view.findViewById( R.id.txtCommentUserName );
            context = view.getContext();

            commentss.setMovementMethod(new ScrollingMovementMethod());


        }


    }
}

