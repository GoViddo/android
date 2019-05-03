package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import java.util.ArrayList;

public class WatchLaterRecyclerModel {
    public String mImageUrl,mVideoTitle,mVideoDesc;
    private int mVideoId;

    public WatchLaterRecyclerModel(String mImageUrl, String mVideoTitle, String mVideoDesc, int mVideoId) {
        this.mImageUrl = mImageUrl;
        this.mVideoTitle = mVideoTitle;
        this.mVideoDesc = mVideoDesc;
        this.mVideoId = mVideoId;
    }



    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmVideoTitle() {
        return mVideoTitle;
    }

    public void setmVideoTitle(String mVideoTitle) {
        this.mVideoTitle = mVideoTitle;
    }

    public String getmVideoDesc() {
        return mVideoDesc;
    }

    public void setmVideoDesc(String mVideoDesc) {
        this.mVideoDesc = mVideoDesc;
    }

    public int getmVideoId() {
        return mVideoId;
    }

    public void setmVideoId(int mVideoId) {
        this.mVideoId = mVideoId;
    }
}
