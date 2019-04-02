package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import android.widget.ImageView;

public class SubscriptionRecyclerModel {
    private int mVideoId;
    private String mSliderImage;
    private String mShortenText;
    private String mVdoCipherId;

    public SubscriptionRecyclerModel(int mVideoId, String mSliderImage, String mShortenText, String mVdoCipherId) {
        this.mVideoId = mVideoId;
        this.mSliderImage = mSliderImage;
        this.mShortenText = mShortenText;
        this.mVdoCipherId = mVdoCipherId;
    }

    public String getmVdoCipherId() {
        return mVdoCipherId;
    }

    public void setmVideoId(int mVideoId) {
        this.mVideoId = mVideoId;
    }

    public void setmSliderImage(String mSliderImage) {
        this.mSliderImage = mSliderImage;
    }

    public void setmShortenText(String mShortenText) {
        this.mShortenText = mShortenText;
    }

    public void setmVdoCipherId(String mVdoCipherId) {
        this.mVdoCipherId = mVdoCipherId;
    }

    public int getmVideoId() {
        return mVideoId;
    }

    public String getmSliderImage() {
        return mSliderImage;
    }

    public String getmShortenText() {
        return mShortenText;
    }
}


