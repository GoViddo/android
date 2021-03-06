package blockchainvideoapp.com.goviddo.goviddo.coreclass;

import android.widget.ImageView;

public class HomeRecyclerCardViewModel {

    String name, mHeading, mUrl, mShort_text,mVdo_cipherid , mVideogenere;
    int mCount, mVideoid;


    public HomeRecyclerCardViewModel(String heading, int count) {
        this.mHeading = heading;
        this.mCount = count;
    }

    public HomeRecyclerCardViewModel(String url, String short_text, String vdo_cipherid, int videoid,String video_genere) {
        this.mUrl = url;
        this.mShort_text = short_text;
        this.mVdo_cipherid = vdo_cipherid;
        this.mVideoid = videoid;
        this.mVideogenere = video_genere;

    }

    public String getUrl() {
        return mUrl;
    }

    public String getShortText() {
        return mShort_text;
    }

    public String getVdoCipherid() {
        return mVdo_cipherid;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setShortText(String short_text) {
        this.mShort_text = short_text;
    }

    public void setVdoCipherid(String vdo_cipherid) {
        this.mVdo_cipherid = vdo_cipherid;
    }

    public void setVideoid(int videoid) {
        this.mVideoid = videoid;
    }

    public int getVideoid() {
        return mVideoid;

    }

    public String getHeading() {
        return mHeading;
    }

    public int getCount() {
        return mCount;
    }
    }

