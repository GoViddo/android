package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import java.util.ArrayList;

public class WatchLaterRecyclerModel {
    public String mImageUrl,mVideoTitle,mVideoDesc;
    private String mVideoId;

    public WatchLaterRecyclerModel( String mVideoTitle, String mVideoId,String mImageUrl) {
        this.mImageUrl = mImageUrl;
        this.mVideoTitle = mVideoTitle;
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

    public String getmVideoId() {
        return mVideoId;
    }

    public void setmVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }
}
